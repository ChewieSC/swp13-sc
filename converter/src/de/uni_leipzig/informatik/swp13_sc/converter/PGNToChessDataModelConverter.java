/**
 * PGNToChessDataModelConverter.java
 */

package de.uni_leipzig.informatik.swp13_sc.converter;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import de.uni_leipzig.informatik.swp13_sc.converter.ChessDataModelToRDFConverter.OutputFormats;
import de.uni_leipzig.informatik.swp13_sc.datamodel.ChessGame;
import de.uni_leipzig.informatik.swp13_sc.datamodel.ChessMove;
import de.uni_leipzig.informatik.swp13_sc.datamodel.ChessPlayer;
import de.uni_leipzig.informatik.swp13_sc.datamodel.pgn.ChessPGNVocabulary;

/**
 * Converter class.<br />
 * Converts PGN Files (text input streams) into the internal chess data format
 * and then into RDF.<br />
 * 
 *
 * @author Erik
 * @see PGNToRDFConverterAllAtOnce
 */
public class PGNToChessDataModelConverter
{
    
    /**
     * The inputFilename.
     */
    private String inputFilename;
    /**
     * The reader used for the input file.
     */
    private BufferedReader reader;
    /**
     * The outputFilename.
     */
    private String outputFilename;
    /**
     * isConverting
     */
    private volatile boolean isConverting;
    /**
     * isParsing
     */
    private volatile boolean isParsing;
    /**
     * finishedConverting
     */
    private volatile boolean finishedConverting;
    /**
     * finishedParsing
     */
    private volatile boolean finishedParsing;
    /**
     * finishedInput
     */
    private volatile boolean finishedInput;
    /**
     * Converter used to convert ChessDataModel to RDF.
     */
    private ChessDataModelToRDFConverter chessToRDF;
    /**
     * List of all converted chess games. Internal chess data format.
     */
    private List<ChessGame> games;
    /**
     * complete numberOfGames
     */
    private AtomicInteger numberOfGames;
    
    // ------------------------------------------------------------------------
    
    /**
     * Internal. Empty String. Not null.
     */
    private final static String EMPTY = "";
    
    /**
     * ALL_GAMES indicates that all available games should be processed.
     */
    public final static int ALL_GAMES = -1;
    
    // regex strings
    /**
     * Regex for the figure types (without pawn).
     */
    private final static String regex_move_figure = "[RNBQK]";
    /**
     * Regex for the row field part notation.
     */
    private final static String regex_move_source_row = "[1-8]";
    /**
     * Regex for the column field notation.
     */
    private final static String regex_move_source_column = "[a-h]";
    /**
     * Regex for partial source field notation.
     */
    private final static String regex_move_source =
            regex_move_source_column + "?" + regex_move_source_row + "?";
    /**
     * Regex for a capture move.
     */
    private final static String regex_move_captured = "x";
    /**
     * Regex for the destination field in a move.
     */
    private final static String regex_move_destination =
            regex_move_source_column + regex_move_source_row;
    
    /**
     * Regex for promotion. e. g. a pawn was transformed into a queen -> "=Q"
     */
    private final static String regex_move_promotion = "=" + regex_move_figure;
    
    /**
     * Regex for check modifier.
     */
    private final static String regex_move_check = "\\+";
    /**
     * Regex for checkmate.
     */
    private final static String regex_move_checkmate = "#"; // ?
    /**
     * Regex for check or checkmate.
     */
    private final static String regex_move_checkCheckmate =
            "(" + regex_move_check + "|" + regex_move_checkmate + ")?";
    
    /**
     * Regex for the round number.
     */
    private final static String regex_move_number = "\\b" + "[1-9][0-9]*\\."; // checked!
    /**
     * Regex for a comment between moves.
     */
    private final static String regex_move_comment = "(" + "\\{[^\\}]*\\}" + ")?"; // ungreedy
    
    /**
     * Regex for queenside castling move. Not used.
     */
    private final static String regex_move_castling_queen = "O\\-O\\-O";
    /**
     * Regex for queenside castling move. Not used.
     */
    private final static String regex_move_castling_king = "O\\-O";
    //(O\\-O(\\-O)?)   <->   (O\\-O\\-O)|(O\\-O)   ???
    //O\-O(\-O)?
    /**
     * Regex for a castling move.
     */
    private final static String regex_move_castling =
            //regex_move_castling_queen + "|" + regex_move_castling_king;
            // checked!? -> needs greedy
            "O\\-O(\\-O)?";
    
    //([a-h]x)?[a-h][1-8](=[RNBQK])?
    /**
     * Regex for a pawn move.
     */
    private final static String regex_move_pawn =
            "(" + regex_move_source_column + regex_move_captured + ")?" + 
                    regex_move_destination +
                    "(" + regex_move_promotion + ")?"; // checked!
    //[RNBQK][a-h]?[1-8]?x?[a-h][1-8]
    /**
     * Regex for a move other than pawn.
     */
    private final static String regex_move_otherfigure =
            regex_move_figure + regex_move_source + regex_move_captured
                    + "?" + regex_move_destination;
    
    // castling -> check ? maybe possible ...
    //\b(((([a-h]x)?[a-h][1-8](=[RNBQK])?)|([RNBQK][a-h]?[1-8]?x?[a-h][1-8])|(O\-O(\-O)?))\b(\+|#)?)\s*(\{.*?\})?
    /**
     * Regex for a single move notation. Most important and mostly used.
     */
    private final static String regex_move_single =
            "\\b" + "(((" + regex_move_pawn + ")|(" + regex_move_otherfigure +
                    ")|(" + regex_move_castling + "))" + "\\b" + regex_move_checkCheckmate +
                    ")" + "\\s*" + regex_move_comment + "\\s*"; // checked !!
    
    //(\b[1-9][0-9]*\.\s*)\b((([a-h]x)?[a-h][1-8](=[RNBQK])?)|([RNBQK][a-h]?[1-8]?x?[a-h][1-8])|(O\-O(\-O)?))\b(\+|#)?\s*(\{.*?\})?(\b((([a-h]x)?[a-h][1-8](=[RNBQK])?)|([RNBQK][a-h]?[1-8]?x?[a-h][1-8])|(O\-O(\-O)?))\b(\+|#)?\s*(\{.*?\})?)?
    /**
     * Regex for a round (round number, first move and if present the second move).
     */
    private final static String regex_move_double =
            "(" + regex_move_number + "\\s*" + ")" + regex_move_single + "(" +
                    regex_move_single + ")?"; // checked !!
    
    //\b(1\-0)|(0\-1)|(1\/2\-1\/2)\b
    /**
     * Regex for pgn move part - the result at the end.
     */
    private final static String regex_result =
            "\\b" + "(1\\-0)|(0\\-1)|(1\\/2\\-1\\/2)" + "\\b"; // checked!
    
    
    /**
     * Regex for meta key name.
     */
    private final static String regex_meta_key = "([A-Z]\\w*)";
    /**
     * Regex for meta key value part.
     */
    private final static String regex_meta_value = "\\\"([^\\\"]*)\\\"";
    /**
     * Regex for separator between key and value.
     */
    private final static String regex_meta_separator = "\\s+";
    /**
     * Regex for meta data entry start char.
     */
    private final static String regex_meta_start = "\\[";
    /**
     * Regex for meta data entry end.
     */
    private final static String regex_meta_end = "\\]";
    /**
     * Regex for meta data entry element.
     */
    private final static String regex_meta =
            regex_meta_start + regex_meta_key + regex_meta_separator +
                    regex_meta_value + regex_meta_end;
    // needs multi-line modifier if ^PATTERN$
    // check for multi-line modifier ... below
    /**
     * Regex for meta data entry start. Used to check if there is one.
     */
    private final static String regex_meta_start_line =
            "^" + regex_meta_start;
    
    
    // ------------------------------------------------------------------------
    
    /**
     * Regex Pattern for a single chess move in algebraic notation.
     */
    private static Pattern pattern_single_move = Pattern.compile(regex_move_single);
    // Group 0: complete (pos, check, comment)
    // Group 1: move without comment
    // Group 2: move only (source, dest. pos, NO check!)
    // Group 3: pawn moves only (source, capture, dest., promo, figure)
    // Group 4: pawn move (capture + source column)
    // Group 5: promotion (promo, dest. figure)
    // Group 6: move of other figures (figure, source, capture, dest.)
    // Group 7: castlings (king, queen)
    // Group 8: queen side castlings (queen part ? -O)
    // Group 9: check (check, checkmate (?))
    // Group 10: comments (-> {...})
    
    ///**
    // * Regex Pattern for a complete round (-> 2 moves with number).
    // */
    //private static Pattern pattern_double_move = Pattern.compile(regex_move_double);
    
    /**
     * Regex Pattern for the start of a meta data entry line.
     */
    private static Pattern pattern_meta_start_line =
            Pattern.compile(regex_meta_start_line);//, Pattern.MULTILINE);
    
    /**
     * Regex Pattern for a single meta data entry line.
     */
    private static Pattern pattern_meta = Pattern.compile(regex_meta);
    // Group 0: complete
    // Group 1: meta key
    // Group 2: meta value
    
    // ------------------------------------------------------------------------
    
    /**
     * Creates a new Converter. Nothing more.
     * 
     * @param   inputFilename   Input filename used later while parsing.
     * @param   outputFilename  Output filename used later while writing RDF to a file.
     */
    public PGNToChessDataModelConverter(String inputFilename, String outputFilename)
    {
        this();
        
        // no validation!
        this.inputFilename = inputFilename;
        this.outputFilename = outputFilename;
    }
    
    /**
     * Standard Constructor.
     */
    public PGNToChessDataModelConverter()
    {
        this.isConverting = false;
        this.isParsing = false;
        this.finishedConverting = false;
        this.finishedParsing = false;
        this.finishedInput = false;
        
        this.games = new ArrayList<ChessGame>();
        this.numberOfGames = new AtomicInteger();
    }
    
    /**
     * Returns true if the converter parsed the complete input.
     * 
     * @return  true if finished else false
     */
    public boolean finishedInputFile()
    {
        return this.finishedInput;
    }
    
    /**
     * Return the complete number of actual parsed games.
     * 
     * @return  int
     */
    public int numberOfParsedGames()
    {
        return numberOfGames.get();
    }
    
    /**
     * Returns the number of unconverted games still in memory.
     * 
     * @return  int
     */
    public int numberUnconvertedGames()
    {
        if (this.games != null)
        {
            return this.numberOfParsedGames() - this.games.size();
        }
        return numberOfParsedGames();
    }
    
    /**
     * Sets the input filename.
     * 
     * @param   inputFilename   Filename of input file
     */
    public void setInputFilename(String inputFilename)
    {
        this.inputFilename = inputFilename;
    }
    
    /**
     * Sets the input reader for this parser.
     * 
     * @param   reader  BufferedReader to read from.
     * @return  true if successful else false (can't read, no outputfilename
     *          or is already working)
     */
    public boolean setInputReader(BufferedReader reader)
    {
        // check if working
        if (this.isParsing)
        {
            return false;
        }
        
        // if no outputFilename can be computet return false
        if (inputFilename == null || outputFilename == null)
        {
            return false;
        }
        
        // check if readable
        try
        {
            if (! reader.ready())
            {
                this.reader = null;
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }
        
        this.reader = reader;
        return true;
    }
    
    /**
     * Parses the previously given input file to the internal chess data format.
     * Stops after count games. Can continue an old session if reader still open.
     * 
     * @param   count   Count chess games to parse or all if set to -1
     * @return  true if successful, false for error
     */
    public boolean parse(int count)
    {
        this.finishedParsing = false;
        this.isParsing = true;
        // determine if continuing file or new
        try
        {
            if (reader == null || ! reader.ready())
            {
                // new file
                reader = openReader(this.inputFilename);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        // check if valid reader
        if (reader == null)
        {
            return false;
        }
        
        String line = null;
        boolean inMoves = false;
        boolean outside = true;
        int gameCount = 0;
        List<String> metaLines = new ArrayList<String>();
        List<String> moveLines = new ArrayList<String>();
        
        // start parse
        while (true)
        {
            // get next line
            try
            {
                line = reader.readLine();
            }
            catch (IOException e)
            {
                e.printStackTrace();
                // break;
                return false;
            }
            // check if end
            if (line == null)
            {
                this.finishedInput = true;
                
                // needed for last game
                if (inMoves) {
                    ChessGame cg = parseGame(metaLines, moveLines);
                    if (cg != null)
                    {
                        this.games.add(cg);
                        this.numberOfGames.incrementAndGet();
                        gameCount ++;
                    }
                    metaLines = new ArrayList<String>();
                    moveLines = new ArrayList<String>();
                }
                
                break;
            }
            
            // sort lines and separate games
            if (EMPTY.equals(line))
            {
                // -- separator lines --
                if (outside)
                {
                    continue;
                }
                else
                {
                    outside = true;
                    
                    // start actual converting/parsing of meta, moves
                    if (inMoves)
                    {
                        inMoves = false;
                        
                        // start thread?
                        ChessGame cg = parseGame(metaLines, moveLines);
                        if (cg != null)
                        {
                            this.games.add(cg);
                            this.numberOfGames.incrementAndGet();
                            gameCount ++;
                        }
                        else
                        {
                            System.out.println("  --> invalid game ... ?");
                        }
                        
                        // check for end
                        if ((count != ALL_GAMES) && (gameCount >= count))
                        {
                            break;
                        }
                        
                        // fresh new line lists
                        metaLines = new ArrayList<String>();
                        moveLines = new ArrayList<String>();
                    }
                }
            }
            else if (line.charAt(0) == '[')
            //else if (pattern_meta_start_line.matcher(line).lookingAt())
            {
                // add meta data lines to list
                metaLines.add(line);
            }
            else
            {
                // add moves to list
                if (outside)
                {
                    outside = false;
                    inMoves = true;
                }
                moveLines.add(line);
            }
            
        }
        
        // end of parsing
        if (this.finishedInput)
        {
            try
            {
                reader.close();
                reader = null;
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        
        
        this.isParsing = false;
        this.finishedParsing = true;
        return true;
    }
    
    /**
     * Parses the whole input stream.
     * 
     * @return  true if successful
     */
    public boolean parse()
    {
        return this.parse(ALL_GAMES);
    }
    
    /**
     * Parses the given lines into a {@link ChessGame}.<br />
     * Called for each game.
     * 
     * @param   metaLines   meta data lines
     * @param   moveLines   move list lines
     * @return  ChessGame if successful else null.
     */
    protected ChessGame parseGame(List<String> metaLines, List<String> moveLines)
    {
        // input validation
        if (metaLines == null || metaLines.size() < 7)
        {
            return null;
        }
        if (moveLines == null || moveLines.size() == 0)
        {
            return null;
        }
        
        // new chess game
        ChessGame.Builder cgb = new ChessGame.Builder();
        
        // --------------------------------------------------------------------
        
        // parse meta data entries
        for (String line : metaLines)
        {
            Matcher meta = pattern_meta.matcher(line);
            
            // get values
            if (! meta.find())
            {
                // log line
                continue;
            }
            try
            {
                String key = meta.group(1);
                String value = meta.group(2);
                
                // switch(key) only with JRE 1.7 !
                if (ChessPGNVocabulary.Meta_Key_White.equals(key))
                {
                    cgb.setWhitePlayer(new ChessPlayer.Builder().setName(value).build());
                }
                else if (ChessPGNVocabulary.Meta_Key_Black.equals(key))
                {
                    cgb.setBlackPlayer(new ChessPlayer.Builder().setName(value).build());
                }
                else if (ChessPGNVocabulary.Meta_Key_Date.equals(key))
                {
                    cgb.setDate(value);
                }
                else if (ChessPGNVocabulary.Meta_Key_Round.equals(key))
                {
                    cgb.setRound(value);
                }
                else if (ChessPGNVocabulary.Meta_Key_Result.equals(key))
                {
                    cgb.setResult(value);
                }
                else if (ChessPGNVocabulary.Meta_Key_Site.equals(key))
                {
                    cgb.setSite(value);
                }
                else if (ChessPGNVocabulary.Meta_Key_Event.equals(key))
                {
                    cgb.setEvent(value);
                }
                else
                {
                    cgb.addMetaData(key, value);
                }         
            }
            catch (IllegalStateException e)
            {
                e.printStackTrace();
            }
            catch (IndexOutOfBoundsException e)
            {
                e.printStackTrace();
            }
        }
        
        // --------------------------------------------------------------------
        
        // parse moves
        int nr = 0;
        for (String line : moveLines)
        {
            Matcher move = pattern_single_move.matcher(line);
            while (move.find())
            {
                nr ++;
                ChessMove.Builder cmb = new ChessMove.Builder();
                try
                {
                    String m = move.group(1);
                    String comment = move.group(9);
                    
                    cgb.addMove(cmb.setNr(nr).setMove(m).setComment(comment).build());
                }
                catch (IllegalStateException e)
                {
                    e.printStackTrace();
                }
                catch (IndexOutOfBoundsException e)
                {
                    e.printStackTrace();
                }
            }
        }
        
        return cgb.build();
    }
    
    /**
     * Converts the internal chess data format into RDF tripel/statements.
     * 
     * @param   count   number of games to convert, rest is preserved
     * @return  true if successful, false if error
     */
    public boolean convert(int count)
    {
        if (this.games == null || this.games.isEmpty())
        {
            return false;
        }
        this.finishedConverting = false;
        this.isConverting = true;
        this.chessToRDF = new ChessDataModelToRDFConverter();
        
        // get count list elements
        List<ChessGame> gms = new ArrayList<ChessGame>();
        if (count != ALL_GAMES)
        {
            if (this.games.size() < count)
            {
                count = this.games.size();
            }
            try
            {
                List<ChessGame> part = this.games.subList(0, count);
                gms.addAll(part);
                part.clear();
            }
            catch (IndexOutOfBoundsException e)
            {
                e.printStackTrace();
            }
            catch (Exception e)
            {
                //IllegalArgumentException
                //NullPointerException
                //ClassCastException
                //UnsupportedOperationException
                e.printStackTrace();
            }
        }
        else
        {
            gms.addAll(this.games);
            this.games.clear();
        }
        
        // convert
        this.chessToRDF.convert(gms);
        
        this.isConverting = false;
        this.finishedConverting = true;
        return true;
    }
    
    /**
     * Converts the internal chess data format into RDF tripel/statements.
     * 
     * @return  true if successful
     */
    public boolean convert()
    {
        return this.convert(ALL_GAMES);
    }
    
    /**
     * Writes the RDF data to the previously given output file.
     * 
     * @return  true if successful else false
     */
    public boolean write()
    {        
        return write(this.outputFilename);
    }
    
    /**
     * Writes the RDF data to the specified outputFilename. Old file data will
     * be overwritten and therefore lost. Stream is closed eventually.
     * 
     * @param   outputFilename  File to write into.
     * @return  true if successful else false
     */
    public boolean write(String outputFilename)
    {
        //OutputStream os = openOutputStream(outputFilename);
        OutputStream os = openZipOutputStream(outputFilename);
              
        write(os);
        
        try
        {
            os.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        
        return true;
    }
    
    /**
     * Writes the RDF data to the specified OutputStream os.<br />
     * Uses the standard format {@link OutputFormats.TURTLE}
     * 
     * @param   os  Stream to write into
     * @return  true if successful
     */
    public boolean write(OutputStream os)
    {
        return write(os, OutputFormats.TURTLE);
    }
    
    /**
     * Writes the converted RDF data to the OutputStream os in the format.
     * 
     * @param   os      Stream to write into
     * @param   format  Outputformat
     * @return  true if successful, false on error
     */
    public boolean write(OutputStream os, OutputFormats format)
    {
        // check output stream
        if (os == null)
        {
            return false;
        }
        // check if there is something to write
        if ((! this.finishedConverting) || (this.chessToRDF == null))
        {
            return false;
        }
        
        // flush all
        boolean retVal = this.chessToRDF.flushToStream(os, format);        
        this.chessToRDF = null;
        
        return retVal;
    }
    
    /**
     * Opens a new FileInputStream.
     * 
     * @param   inputFilename   File to open for reading.
     * @return  FileInputStream or null if error
     */
    protected FileInputStream openInputStream(String inputFilename)
    {
        if (inputFilename == null)
        {
            return null;
        }
        // try opening the input
        try
        {
            return new FileInputStream(inputFilename);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (SecurityException e)
        {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * Opens a new FileOutputStream.
     * 
     * @param   outputFilename  File to write into. Old content will be
     *                          overwritten
     * @return  FileOutputStream or null if error
     */
    protected FileOutputStream openOutputStream(String outputFilename)
    {
        if (outputFilename == null)
        {
            return null;
        }
        // try opening output file
        try
        {
            return new FileOutputStream(outputFilename);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (SecurityException e)
        {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * Opens a new ZipOutputStream for compressed output.
     * 
     * @param   outputFilename  File to write into.
     * @return  ZipOutputStream or null if error.
     */
    protected ZipOutputStream openZipOutputStream(String outputFilename)
    {
        if (outputFilename == null)
        {
            return null;
        }
        
        // zip archive file
        FileOutputStream fos = openOutputStream(outputFilename + ".zip");
        if (fos == null)
        {
            return null;
        }
        
        // add normal file to zip archive
        ZipOutputStream zos = new ZipOutputStream(fos);
        zos.setLevel(9); // highest level
        
        try
        {
            File foc = new File(outputFilename);
            File fop = foc.getParentFile();
            String entryname = outputFilename;
            if (fop != null)
            {
                entryname = fop.toURI().relativize(foc.toURI()).getPath();
            }            
            
            ZipEntry ze = new ZipEntry(entryname);
        
            zos.putNextEntry(ze);
            
            return zos;
        }
        catch (NullPointerException e)
        {
            e.printStackTrace();
        }
        catch (IllegalArgumentException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        
        return null;
    }
    

    /**
     * Opens a text file reader with the given inputFilename.
     * 
     * @param   inputFilename   File to read from.
     * @return  BufferedReader or null if error
     */
    protected BufferedReader openReader(String inputFilename)
    {
        FileInputStream fis = openInputStream(inputFilename);
        if (fis == null)
        {
            return null;
        }
        
        DataInputStream dis = new DataInputStream(fis);
        // default charset?
        InputStreamReader isr = new InputStreamReader(dis, Charset.defaultCharset());
        BufferedReader br = new BufferedReader(isr);
        
        return br;
    }
    
}
