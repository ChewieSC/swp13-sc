/**
 * PGNToChessDataModelConverter.java
 */

package de.uni_leipzig.informatik.swp13_sc.converter;

import de.uni_leipzig.informatik.swp13_sc.converter.ChessDataModelToRDFConverter.OutputFormats;
import de.uni_leipzig.informatik.swp13_sc.datamodel.ChessGame;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 
 *
 * @author Erik
 *
 */
public class PGNToChessDataModelConverter
{
    
    private String inputFilename;
    private String outputFilename;
    private volatile boolean isConverting;
    private volatile boolean isParsing;
    private volatile boolean finishedConverting;
    private volatile boolean finishedParsing;
    private ChessDataModelToRDFConverter chessToRDF;
    private List<ChessGame> games;
    
    // ------------------------------------------------------------------------
    // regex strings
    private final static String regex_move_figure = "[RNBQK]";
    private final static String regex_move_source_row = "[1-8]";
    private final static String regex_move_source_column = "[a-h]";
    private final static String regex_move_source =
            regex_move_source_column + "?" + regex_move_source_row + "?";
    private final static String regex_move_captured = "x";
    private final static String regex_move_destination =
            regex_move_source_column + regex_move_source_row;
    
    private final static String regex_move_promotion = "=" + regex_move_figure;
    
    private final static String regex_move_check = "\\+";
    private final static String regex_move_checkmate = "#"; // ?
    private final static String regex_move_checkCheckmate =
            "(" + regex_move_check + "|" + regex_move_checkmate + ")?";
    
    private final static String regex_move_number = "\\b" + "[1-9][0-9]*\\."; // checked!
    private final static String regex_move_comment = "(" + "\\{[^\\}]*\\}" + ")?"; // ungreedy
    
    private final static String regex_move_castling_queen = "O\\-O\\-O";
    private final static String regex_move_castling_king = "O\\-O";
    //(O\\-O(\\-O)?)   <->   (O\\-O\\-O)|(O\\-O)   ???
    //O\-O(\-O)?
    private final static String regex_move_castling =
            //regex_move_castling_queen + "|" + regex_move_castling_king;
            // checked!? -> needs greedy
            "O\\-O(\\-O)?";
    
    //([a-h]x)?[a-h][1-8](=[RNBQK])?
    private final static String regex_move_pawn =
            "(" + regex_move_source_column + regex_move_captured + ")?" + 
                    regex_move_destination +
                    "(" + regex_move_promotion + ")?"; // checked!
    //[RNBQK][a-h]?[1-8]?x?[a-h][1-8]
    private final static String regex_move_otherfigure =
            regex_move_figure + regex_move_source + regex_move_captured
                    + "?" + regex_move_destination;
    
    // castling -> check ? maybe possible ...
    //\b((([a-h]x)?[a-h][1-8](=[RNBQK])?)|([RNBQK][a-h]?[1-8]?x?[a-h][1-8])|(O\-O(\-O)?))\b(\+|#)?\s*(\{.*?\})?
    private final static String regex_move_single =
            "\\b" + "((" + regex_move_pawn + ")|(" + regex_move_otherfigure +
                    ")|(" + regex_move_castling + "))" + "\\b" + regex_move_checkCheckmate +
                    "\\s*" + regex_move_comment + "\\s*"; // checked !!
    
    //(\b[1-9][0-9]*\.\s*)\b((([a-h]x)?[a-h][1-8](=[RNBQK])?)|([RNBQK][a-h]?[1-8]?x?[a-h][1-8])|(O\-O(\-O)?))\b(\+|#)?\s*(\{.*?\})?(\b((([a-h]x)?[a-h][1-8](=[RNBQK])?)|([RNBQK][a-h]?[1-8]?x?[a-h][1-8])|(O\-O(\-O)?))\b(\+|#)?\s*(\{.*?\})?)?
    private final static String regex_move_double =
            "(" + regex_move_number + "\\s*" + ")" + regex_move_single + "(" +
                    regex_move_single + ")?"; // checked !!
    
    //\b(1\-0)|(0\-1)|(1\/2\-1\/2)\b
    private final static String regex_result =
            "\\b" + "(1\\-0)|(0\\-1)|(1\\/2\\-1\\/2)" + "\\b"; // checked!
    
    
    private final static String regex_meta_key = "([A-Z][a-z]*)";
    private final static String regex_meta_value = "\\\"([^\\\"]*)\\\"";
    private final static String regex_meta_separator = "\\s+";
    private final static String regex_meta_start = "\\[";
    private final static String regex_meta_end = "\\]";
    private final static String regex_meta =
            regex_meta_start + regex_meta_key + regex_meta_separator +
                    regex_meta_value + regex_meta_end;
    // needs multi-line modifier
    
    // ------------------------------------------------------------------------
    
    private static Pattern pattern_single_move = Pattern.compile(regex_move_single);
    // Group 0: complete (pos, check, comment)
    // Group 1: move only (source, dest. pos)
    // Group 2: pawn moves only
    // Group 3: pawn move (capture + source column)
    // Group 4: promotion (promo, dest. figure)
    // Group 5: move of other figures
    // Group 6: castlings
    // Group 7: queen side castlings (queen part ? -O)
    // Group 8: check (check, checkmate (?))
    // Group 9: comments (-> {...})
    
    private static Pattern pattern_double_move = Pattern.compile(regex_move_double);
    
    private static Pattern pattern_meta = Pattern.compile(regex_meta);
    // Group 0: complete
    // Group 1: meta key
    // Group 2: meta value
    
    // ------------------------------------------------------------------------
    
    public PGNToChessDataModelConverter(String inputFilename, String outputFilename)
    {
        // no validation!
        this.inputFilename = inputFilename;
        this.outputFilename = outputFilename;
        
        this.isConverting = false;
        this.isParsing = false;
        this.finishedConverting = false;
        this.finishedParsing = false;
        
        this.games = null;
    }
    
    public boolean parse()
    {
        this.isParsing = true;
        this.games = new ArrayList<ChessGame>();        
        BufferedReader reader = openReader(this.inputFilename);
        if (reader == null)
        {
            return false;
        }
        
        // parse
        
        //
        
        this.isParsing = false;
        this.finishedParsing = true;
        return true;
    }
    
    public boolean convert()
    {
        this.isConverting = true;
        if (this.games == null || this.games.size() == 0)
        {
            return false;
        }
        this.chessToRDF = new ChessDataModelToRDFConverter();
        
        //convert
        this.chessToRDF.convert(games);
        
        this.isConverting = false;
        this.finishedConverting = true;
        return true;
    }
    
    public boolean write()
    {        
        return write(this.outputFilename);
    }
    
    public boolean write(String outputFilename)
    {
        FileOutputStream fos = openOutputStream(outputFilename);
        if (fos == null)
        {
            return false;
        }        
        if (this.chessToRDF == null)
        {
            return false;
        }
        
        // flush all
        this.chessToRDF.flushToStream(fos, OutputFormats.TURTLE);
        
        return true;
    }
    
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
            // log
        }
        catch (SecurityException e)
        {
            // log
        }
        return null;
    }
    
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
            // log
        }
        catch (SecurityException e)
        {
            // log
        }
        return null;
    }

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
