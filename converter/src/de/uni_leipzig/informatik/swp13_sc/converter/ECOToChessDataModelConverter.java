/**
 * ECOToChessDataModelConverter.java
 */
package de.uni_leipzig.informatik.swp13_sc.converter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alonsoruibal.chess.Board;
import com.alonsoruibal.chess.Move;

import de.uni_leipzig.informatik.swp13_sc.datamodel.ChessMove;
import de.uni_leipzig.informatik.swp13_sc.datamodel.ChessOpening;
import de.uni_leipzig.informatik.swp13_sc.datamodel.pgn.ChessPGNVocabulary;
import de.uni_leipzig.informatik.swp13_sc.util.FileUtils;

/**
 * Converts a text file with ECOs into ChessDataModel objects.
 *
 * @author Erik
 */
public class ECOToChessDataModelConverter
{
    /**
     * The inputFilename.
     */
    private String inputFilename;
    /**
     * Used to check that the last input file is only opened once.
     * There occurred an infinite loop if the last line of a ECO-File
     * was empty (an additional last line!) and the file was reopened.
     */
    private String lastInputFilename;
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
     * List of all converted chess openings. Internal chess data format.
     */
    private List<ChessOpening> openings;
    
    // ------------------------------------------------------------------------
    
    /**
     * Regex Pattern for a single chess move in algebraic notation.
     */
    private static Pattern pattern_single_move = Pattern.compile(ChessPGNVocabulary.regex_move_single);
    
    /**
     * Internal. Empty String. Not null.
     */
    private final static String EMPTY = "";
    
    /**
     * ALL_OPENINGS indicates that all available openings should be processed.
     */
    public final static int ALL_OPENINGS = -1;
    
    /**
     * Separator sequenz between different columns in file.
     */
    private final static String SEPARATOR = " \t";
    /**
     * Separator sequenz length.
     * @see #SEPARATOR
     */
    private final static int SEPARATOR_LENGTH = SEPARATOR.length();
    
    // ------------------------------------------------------------------------
    
    /**
     * Creates a new Converter. Nothing more.
     * 
     * @param   inputFilename   Input filename used later while parsing.
     */
    public ECOToChessDataModelConverter(String inputFilename)
    {
        this();
        
        // no validation!
        this.inputFilename = inputFilename;
    }
    
    /**
     * Standard Constructor.
     */
    public ECOToChessDataModelConverter()
    {
        this.isParsing = false;
        this.finishedParsing = false;
        this.finishedInput = false;
        this.inputFilename = null;
        this.lastInputFilename = null;
        
        this.openings = new ArrayList<ChessOpening>();
    }
    
    // ------------------------------------------------------------------------
    
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
                return false;
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
    public int numberOfParsedOpenings()
   {
       return this.openings.size();
   }
    
    // ------------------------------------------------------------------------
    
    /**
     * Parses the previously given input file to the internal chess data format.
     * Stops after count openings. Can continue an old session if reader still
     * open.
     * 
     * @param   count   Count chess openings to parse or all if set to -1
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
        int openingCount = 0;
        
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
                
                break;
            }
            
            String code;
            String name;
            String moves;
            
            int i1 = line.indexOf(SEPARATOR);
            if (i1 == -1)
            {
                System.out
                        .println("WARN: Couldn't find SEPARATOR sequenz in input line: \""
                                + line + "\"");
                continue;
            }
            int i2 = line.indexOf(SEPARATOR, i1 + SEPARATOR_LENGTH);
            if (i2 == -1)
            {
                System.out
                        .println("WARN: Couldn't find second SEPARATOR sequenz in input line: \""
                                + line + "\"");
                continue;
            }
            
            code = line.substring(0, i1);
            name = line.substring(i1 + SEPARATOR_LENGTH, i2);
            moves = line.substring(i2 + SEPARATOR_LENGTH);
            
            ChessOpening co = parseOpening(code, name, moves);
            // DEBUG:
            //System.out.println(co);
            if (co == null)
            {
                System.out.println("WARN: Couldn't parse ChessOpening!");
            }
            else
            {
                this.openings.add(co);
                openingCount ++;
            }
            
            // check for end
            if ((count != ALL_OPENINGS) && (openingCount >= count))
            {
                break;
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
        return this.parse(ALL_OPENINGS);
    }
    
    /**
     * Parses the given Strings into a {@link ChessOpening}.<br />
     * Called for each line in the input file / opening.
     * 
     * @param   code    code name of opening
     * @param   name    human readable name
     * @param   moves   list of moves for this opening
     * @return  ChessOpening if successful else null.
     */
    protected ChessOpening parseOpening(String code, String name, String moves)
    {
        // input validation
        if (code == null || code.length() != 3)
        {
            return null;
        }
        if (name == null || EMPTY.equals(name))
        {
            return null;
        }
        if (moves == null || EMPTY.equals(moves))
        {
            return null;
        }
        
        // new chess opening
        ChessOpening.Builder cob = new ChessOpening.Builder().setCode(code).setName(name);
        
        // --------------------------------------------------------------------
        
        //ChessBoard my_cb = new ChessBoard();
        //boolean first = true;
        Board cb = new Board();
        cb.startPosition();
        
        // parse moves
        int nr = 0;
        Matcher move = pattern_single_move.matcher(moves);
        while (move.find())
        {
            nr ++;
            ChessMove.Builder cmb = new ChessMove.Builder();

            String m = move.group(1);
            String comment = move.group(10);

            cmb.setNr(nr).setMove(m).setComment(comment);

            try
            {
                int moveInt = Move.getFromString(cb, m, true);
                //if (! cb.move(m))
                if (! cb.doMove(moveInt, true))
                {
                    System.err.println("WARN: move \"" + m + "\" -> \""
                            + Move.toStringExt(moveInt) +
                            "\" in <Opening " +
                            (this.openings.size() - 1) + ": " + code + ", " +
                            name + ">.");

                    return null;
                }

                String fen = cb.getFen();
                fen = fen.substring(0, fen.indexOf(' '));
                cmb.setFEN(fen);

                // Compare FEN generation
                //if (first)
                //{
                //    my_cb.move(m);
                //    if (! fen.equals(my_cb.computeFEN()))
                //    {
                //        System.out.println("WARN: Different FEN in game " +
                //                (numberOfGames.get() + 1) + ", move " + nr);
                //        first = false;
                //    }
                //}
            }
            catch (Exception e)
            {
                // IllegalStateException
                // IndexOutOfBoundsException
                System.out.println("ERROR in <Opening " +
                        (this.openings.size() - 1) + ": " + code + ", " + name
                        + ">.");
                e.printStackTrace();
                return null;
            }

            cob.addMove(cmb.build());
        }
        
        return cob.build();
    }
    
    // ------------------------------------------------------------------------
    
    /**
     * Returns count parsed openings from the internal list and optionally
     * removes them after returning (to delete all references to them).
     * 
     * @param   count   Number of openings to return.
     * @param   remove  true if they should be removed.
     * @return  List<{@link
     *          de.uni_leipzig.informatik.swp13_sc.datamodel.ChessOpening}>
     */
    public List<ChessOpening> getOpenings(int count, boolean remove)
    {
        if (this.openings == null || this.openings.isEmpty())
        {
            return null;
        }
        
        // get count list elements
        List<ChessOpening> ops = new ArrayList<ChessOpening>();
        if (count != ALL_OPENINGS)
        {
            if (this.openings.size() < count)
            {
                count = this.openings.size();
            }
            try
            {
                List<ChessOpening> part = this.openings.subList(0, count);
                ops.addAll(part);
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
            ops.addAll(this.openings);
            if (remove)
            {
                this.openings.clear();
            }
        }
        
        return ops;
    }
    
    /**
     * Returns all the parsed openings and removes them from the internal list.
     * 
     * @return  List<{@link
     *          de.uni_leipzig.informatik.swp13_sc.datamodel.ChessOpening}>
     */
    public List<ChessOpening> getOpenings()
    {
        return this.getOpenings(ALL_OPENINGS, true);
    }
    
    /**
     * Returns all the parsed openings and optionally removes them form the list.
     * 
     * @param   remove  If true all the internally saved openings are cleared.
     * @return  List<{@link
     *          de.uni_leipzig.informatik.swp13_sc.datamodel.ChessOpening}>
     */
    public List<ChessOpening> getOpenings(boolean remove)
    {
        return this.getOpenings(ALL_OPENINGS, remove);
    }
}
