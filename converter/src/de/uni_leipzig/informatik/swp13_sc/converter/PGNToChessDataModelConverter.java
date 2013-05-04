/**
 * PGNToChessDataModelConverter.java
 */

package de.uni_leipzig.informatik.swp13_sc.converter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.uni_leipzig.informatik.swp13_sc.datamodel.ChessGame;
import de.uni_leipzig.informatik.swp13_sc.datamodel.ChessMove;
import de.uni_leipzig.informatik.swp13_sc.datamodel.ChessPlayer;
import de.uni_leipzig.informatik.swp13_sc.datamodel.pgn.ChessPGNVocabulary;
import de.uni_leipzig.informatik.swp13_sc.logic.ChessBoard;
import de.uni_leipzig.informatik.swp13_sc.util.FileUtils;

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
     * isParsing
     */
    private volatile boolean isParsing;
    /**
     * finishedParsing
     */
    private volatile boolean finishedParsing;
    /**
     * finishedInput
     */
    private volatile boolean finishedInput;
    /**
     * List of all converted chess games. Internal chess data format.
     */
    private List<ChessGame> games;
    /**
     * complete numberOfGames
     */
    private AtomicInteger numberOfGames;
    
    /**
     * Used to check that the last input file is only opened once.
     * There occurred an infinite loop if the last line of a PGN-File
     * was empty (an additional last line!) and the file was reopened.
     */
    private String lastInputFilename;
    
    // ------------------------------------------------------------------------
    
    /**
     * Internal. Empty String. Not null.
     */
    private final static String EMPTY = "";
    
    /**
     * ALL_GAMES indicates that all available games should be processed.
     */
    public final static int ALL_GAMES = -1;
    
    // ------------------------------------------------------------------------
    
    /**
     * Regex Pattern for a single chess move in algebraic notation.
     */
    private static Pattern pattern_single_move = Pattern.compile(ChessPGNVocabulary.regex_move_single);
        
    /**
     * Regex Pattern for the start of a meta data entry line.
     */
    @SuppressWarnings("unused")
    private static Pattern pattern_meta_start_line =
            Pattern.compile(ChessPGNVocabulary.regex_meta_start_line);//, Pattern.MULTILINE);
    
    /**
     * Regex Pattern for a single meta data entry line.
     */
    private static Pattern pattern_meta = Pattern.compile(ChessPGNVocabulary.regex_meta);
    
    // ------------------------------------------------------------------------
    
    /**
     * Creates a new Converter. Nothing more.
     * 
     * @param   inputFilename   Input filename used later while parsing.
     */
    public PGNToChessDataModelConverter(String inputFilename)
    {
        this();
        
        // no validation!
        this.inputFilename = inputFilename;
    }
    
    /**
     * Standard Constructor.
     */
    public PGNToChessDataModelConverter()
    {
        this.isParsing = false;
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
     * Returns true if the converter finished parsing its given part.
     * The input file may be longer and not completely finished - only
     * the parsing method!
     * 
     * @return  true if finished.
     */
    public boolean finishedParsing()
    {
        return this.finishedParsing;
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
     * Sets the input reader for this parser. It takes the inputStream and
     * creates a new reader from it.
     * 
     * @param   inputStream     InputStream to read from
     */
    public void setInputStream(InputStream inputStream)
    {
        this.setInputReader(FileUtils.openReader(inputStream));
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
    
    // ------------------------------------------------------------------------
    
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
                // new file if not opened before
                //System.out.println("[DEBUG] Opened new input file.");
                if ((this.lastInputFilename == null) ||
                        (! this.lastInputFilename.equalsIgnoreCase(this.inputFilename)))
                {
                    this.lastInputFilename = this.inputFilename;
                    reader = FileUtils.openReader(FileUtils.openInputStream(this.inputFilename));
                }
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
        
        ChessBoard cb = new ChessBoard();
        
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
                    String comment = move.group(10);
                    
                    cb.move(move.group(1));
                    // DEBUG:
                    //System.out.println("{" + cb.getLastMoveSource() + "->" + cb.getLastMoveDestination()
                    //        + ", " + cb.getLastMoveFigure() + "}{" + cb.getFEN() + "}");
                    
                    cgb.addMove(cmb.setNr(nr).setMove(m).setFEN(cb.getFEN(null)).setComment(comment).build());
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
    
    // ------------------------------------------------------------------------
    
    /**
     * Returns count parsed games from the internal list and optionally
     * removes them after returning (to delete all references to them).
     * 
     * @param   count   Number of games to return.
     * @param   remove  true if they should be removed.
     * @return  List<{@link
     *          de.uni_leipzig.informatik.swp13_sc.datamodel.ChessGame}>
     */
    public List<ChessGame> getGames(int count, boolean remove)
    {
        if (this.games == null || this.games.isEmpty())
        {
            return null;
        }
        
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
                if (remove)
                {
                    part.clear();
                }
            }
            catch (Exception e)
            {
                // IndexOutOfBoundsException
                // IllegalArgumentException
                // NullPointerException
                // ClassCastException
                // UnsupportedOperationException
                e.printStackTrace();
            }
        }
        else
        {
            gms.addAll(this.games);
            if (remove)
            {
                this.games.clear();
            }
        }
        
        return gms;
    }
    
    /**
     * Returns all the parsed games and removes them from the internal list.
     * 
     * @return  List<{@link
     *          de.uni_leipzig.informatik.swp13_sc.datamodel.ChessGame}>
     */
    public List<ChessGame> getGames()
    {
        return this.getGames(ALL_GAMES, true);
    }
    
    /**
     * Returns all the parsed games and optionally removes them form the list.
     * 
     * @param   remove  If true all the internally saved games are cleared.
     * @return  List<{@link
     *          de.uni_leipzig.informatik.swp13_sc.datamodel.ChessGame}>
     */
    public List<ChessGame> getGames(boolean remove)
    {
        return this.getGames(ALL_GAMES, remove);
    }
        
}
