/**
 * PGNToChessDataModelConverter.java
 */

package de.uni_leipzig.informatik.swp13_sc.converter;

import de.uni_leipzig.informatik.swp13_sc.converter.ChessDataModelToRDFConverter.OutputFormats;
import de.uni_leipzig.informatik.swp13_sc.datamodel.ChessGame;
import de.uni_leipzig.informatik.swp13_sc.datamodel.ChessMove;
import de.uni_leipzig.informatik.swp13_sc.datamodel.ChessPlayer;

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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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
    
    private final static String EMPTY = "";
    
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
    //\b(((([a-h]x)?[a-h][1-8](=[RNBQK])?)|([RNBQK][a-h]?[1-8]?x?[a-h][1-8])|(O\-O(\-O)?))\b(\+|#)?)\s*(\{.*?\})?
    private final static String regex_move_single =
            "\\b" + "(((" + regex_move_pawn + ")|(" + regex_move_otherfigure +
                    ")|(" + regex_move_castling + "))" + "\\b" + regex_move_checkCheckmate +
                    ")" + "\\s*" + regex_move_comment + "\\s*"; // checked !!
    
    //(\b[1-9][0-9]*\.\s*)\b((([a-h]x)?[a-h][1-8](=[RNBQK])?)|([RNBQK][a-h]?[1-8]?x?[a-h][1-8])|(O\-O(\-O)?))\b(\+|#)?\s*(\{.*?\})?(\b((([a-h]x)?[a-h][1-8](=[RNBQK])?)|([RNBQK][a-h]?[1-8]?x?[a-h][1-8])|(O\-O(\-O)?))\b(\+|#)?\s*(\{.*?\})?)?
    private final static String regex_move_double =
            "(" + regex_move_number + "\\s*" + ")" + regex_move_single + "(" +
                    regex_move_single + ")?"; // checked !!
    
    //\b(1\-0)|(0\-1)|(1\/2\-1\/2)\b
    private final static String regex_result =
            "\\b" + "(1\\-0)|(0\\-1)|(1\\/2\\-1\\/2)" + "\\b"; // checked!
    
    
    private final static String regex_meta_key = "([A-Z]\\w*)";
    private final static String regex_meta_value = "\\\"([^\\\"]*)\\\"";
    private final static String regex_meta_separator = "\\s+";
    private final static String regex_meta_start = "\\[";
    private final static String regex_meta_end = "\\]";
    private final static String regex_meta =
            regex_meta_start + regex_meta_key + regex_meta_separator +
                    regex_meta_value + regex_meta_end;
    // needs multi-line modifier if ^PATTERN$
    // check for multi-line modifier ... below
    private final static String regex_meta_start_line =
            "^" + regex_meta_start;
    
    
    // ------------------------------------------------------------------------
    
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
    
    private static Pattern pattern_double_move = Pattern.compile(regex_move_double);
    
    private static Pattern pattern_meta_start_line =
            Pattern.compile(regex_meta_start_line);//, Pattern.MULTILINE);
    
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
        
        String line = null;
        int nr = 0;
        boolean inMeta = false;
        boolean inMoves = false;
        boolean outside = true;
        ChessGame.Builder cgb = new ChessGame.Builder();
        Matcher metaLine;
        
        // start parse
        while (true)
        {
            try
            {
                line = reader.readLine();
            }
            catch (IOException e)
            {
                // log
                // break;
                return false;
            }
            if (line == null)
            {
                break;
            }
            
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
                    /*inMeta = false;*/
                    
                    // finish game and add to list
                    if (inMoves)
                    {
                        this.games.add(cgb.build());
                        cgb = new ChessGame.Builder();
                    }
                    inMoves = false;
                    nr = 0;
                }
            }
            //else if (line.charAt(0) == '[')
            else if (pattern_meta_start_line.matcher(line).lookingAt())
            {
                // -- meta data --
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
                    if ("White".equals(key))
                    {
                        cgb.setWhitePlayer(new ChessPlayer.Builder().setName(value).build());
                    }
                    else if ("Black".equals(key))
                    {
                        cgb.setBlackPlayer(new ChessPlayer.Builder().setName(value).build());
                    }
                    else if ("Date".equals(key))
                    {
                        cgb.setDate(value);
                    }
                    else if ("Round".equals(key))
                    {
                        cgb.setRound(value);
                    }
                    else if ("Result".equals(key))
                    {
                        cgb.setResult(value);
                    }
                    else if ("Site".equals(key))
                    {
                        cgb.setSite(value);
                    }
                    else if ("Event".equals(key))
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
                    // log
                }
                catch (IndexOutOfBoundsException e)
                {
                    // log
                }
                
            }
            else
            {
                if (outside)
                {
                    outside = false;
                    inMoves = true;
                }
                // -- moves --
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
                        // log
                    }
                    catch (IndexOutOfBoundsException e)
                    {
                        // log
                    }
                }
            }
            
        }
        // end parse
        try
        {
            reader.close();
        }
        catch (IOException e)
        {
            // log
            e.printStackTrace();
        }
        
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
        //OutputStream os = openOutputStream(outputFilename);
        OutputStream os = openZipOutputStream(outputFilename);
        
        if (os == null)
        {
            return false;
        }        
        if (this.chessToRDF == null)
        {
            return false;
        }
        
        // flush all
        this.chessToRDF.flushToStream(os, OutputFormats.TURTLE);
        try
        {
            os.close();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
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
        
        String entryname = (outputFilename.lastIndexOf('/') != -1) ?
                outputFilename.substring(outputFilename.lastIndexOf('/') + 1) :
                    outputFilename;
        
        try
        {
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
