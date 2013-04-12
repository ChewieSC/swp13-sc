/**
 * ChessDataModelToRDFConverter.java
 */

package de.uni_leipzig.informatik.swp13_sc.converter;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;

import de.uni_leipzig.informatik.swp13_sc.datamodel.ChessGame;
import de.uni_leipzig.informatik.swp13_sc.datamodel.ChessMove;
import de.uni_leipzig.informatik.swp13_sc.datamodel.rdf.ChessRDFVocabulary;

/**
 * Converter class.<br />
 * Converts the Chess Datamodel classes to their RDF representation.
 *
 * @author Erik
 */
public class ChessDataModelToRDFConverter
{
    
    /**
     * Possible Output-Formats for Converter {@link
     * ChessDataModelToRDFConverter}
     *
     * @author Erik
     */
    public static enum OutputFormats
    {
        /**
         * TURTLE
         */
        TURTLE("TURTLE", "ttl"),
        /**
         * NTRIPEL
         */
        NTRIPEL("N-TRIPLE", "nt"),
        /**
         * RDF_XML
         */
        RDF_XML("RDF/XML", "rdf"),
        /**
         * RDF_XML_PRETTY
         */
        RDF_XML_PRETTY("RDF/XML-ABBREV", "rdf");
        
        /**
         * Internal Jena Format String.
         */
        private String format;        
        /**
         * Internal Jena File Extension String.
         */
        private String extension;
        
        /**
         * Internal. Constructor.
         * 
         * @param   format      Format in Jena
         * @param   extension   File extension
         */
        private OutputFormats(String format, String extension)
        {
            this.format = format;
            this.extension = extension;
        }
        
        /**
         * Returns the File-Extension.
         * 
         * @return  String
         */
        public String getExtension()
        {
            return this.extension;
        }
        
        /**
         * Returns the Output-Format.
         * 
         * @return  String
         */
        public String getFormat()
        {
            return this.format;
        }        
    }
    
    
    /**
     * Internal model for storing statements/tripel.
     */
    private Model model;
    /**
     * Internal. List of all game names.
     */
    //private Set<String> convertedGames;
    private Map<String, List<String>> convertedGames;
    /**
     * Internal. Seperator char for URI names.
     */
    private static char separator = '_';

    /**
     * Basic Constructor
     */
    public ChessDataModelToRDFConverter()
    {
        // create store
        model = ChessDataModelToRDFConverter.createModel();
        
        convertedGames = new HashMap<String, List<String>>();
    }
    
    /**
     * Creates a default Model for storing the RDF Statements.
     * 
     * @return  Model
     */
    protected static Model createModel()
    {
        // create store
        Model model = ModelFactory.createDefaultModel();
        // add namespace prefixes
        model.setNsPrefix("cont", ChessRDFVocabulary.getURI());
        model.setNsPrefix("cres", ChessRDFVocabulary.getResourceURI());
        model.setNsPrefix("xsd", "http://www.w3.org/2001/XMLSchema#");
        
        return model;
    }
    
    /**
     * Takes a {@link ChessGame} and converts it (and its members) to RDF data.<br />
     * Returns if values are null!
     * 
     * @param   game    {@link ChessGame} to convert
     */
    public void convert(ChessGame game)
    {
        // check for close?
        
        // check values
        if (game == null)
        {
            return;
        }
        if (game.getBlackPlayer() == null)
        {
            return;
        }
        if (game.getWhitePlayer() == null)
        {
            return;
        }
        if (game.getMoves() == null)
        {
            return;
        }
        
        // generate normalized player names
        String whitePlayer = getNormalizedString(game.getWhitePlayer().getName());
        String blackPlayer = getNormalizedString(game.getBlackPlayer().getName());
        
        // generate player resources
        Resource r_wPlayer = model.createResource(ChessRDFVocabulary
                .getResourceURI() + whitePlayer, ChessRDFVocabulary.ChessPlayer);
        Resource r_bPlayer = model.createResource(ChessRDFVocabulary
                .getResourceURI() + blackPlayer, ChessRDFVocabulary.ChessPlayer);
        
        // add data to players
        r_bPlayer.addProperty(ChessRDFVocabulary.name, game.getBlackPlayer().getName());
        r_wPlayer.addProperty(ChessRDFVocabulary.name, game.getWhitePlayer().getName());
        
        // --------------------------------------------------------------------
        // get date
        String date = game.getDate();
        if (date == null)
        {
            date = "_default_date_";
        }
        
        // generate game
        String gameName = getUniqueGameName(blackPlayer, whitePlayer, date);
        Resource r_game = model.createResource(ChessRDFVocabulary
                .getResourceURI() + gameName, ChessRDFVocabulary.ChessGame);
       
        // add both players to game
        model.add(r_game, ChessRDFVocabulary.blackPlayer, r_bPlayer)
            .add(r_game, ChessRDFVocabulary.whitePlayer, r_wPlayer);
        
        // add metadata to game
        for (String metaKey : game.getMetaData().keySet())
        {
            String metaValue = game.getMetaValue(metaKey);
            // TODO: Think of a better way!
            r_game.addProperty(model.createProperty(ChessRDFVocabulary.getURI()
                    + metaKey), metaValue);
        }
        
        // --------------------------------------------------------------------
        // add moves to game
        int nr = 0;
        for (ChessMove m : game.getMoves())
        {
            // convert & add move
            nr ++;
            Resource r_move = model.createResource(ChessRDFVocabulary.ChessMove)
                    .addProperty(ChessRDFVocabulary.move, m.getMove())
                    .addProperty(ChessRDFVocabulary.moveNr, "" + nr,
                            XSDDatatype.XSDnonNegativeInteger);
            model.add(r_game, ChessRDFVocabulary.moves, r_move);
            if (m.hasComment() && m.getComment() != null)
            {
                r_move.addProperty(ChessRDFVocabulary.comment, m.getComment());
            }
            if (m.getFEN() != null)
            {
                r_move.addProperty(ChessRDFVocabulary.fen, m.getFEN());
            }
        }
        // finished!
    }
    
    /**
     * Converts the whole list of {@link ChessGame}s.
     * 
     * @param   games   List<{@link ChessGame}>
     */
    public void convert(List<ChessGame> games)
    {
        if (this.model.isClosed())
        {
            return;
        }
        
        for (ChessGame g : games)
        {
            // to add all games fail-safe
            try
            {
                convert(g);
            }
            // TODO: differentiate exceptions
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Converts a subset of the list games.
     * 
     * @param   games   List to convert.
     * @param   count   Number of items to convert before stopping
     */
    public void convert(List<ChessGame> games, int count)
    {
        if (this.model.isClosed())
        {
            return;
        }
        
        for (int i = 0; (i < count) && (! games.isEmpty()); i ++)
        {
            try
            {
                convert(games.remove(0));
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Flushes the model into the OutputStream output with the format.
     * 
     * @param   output  OutputStream used.
     * @param   format  Format of Output
     * @return  true if successful else false
     */
    public boolean flushToStream(OutputStream output, OutputFormats format)
    {
        if (this.model.isClosed())
        {
            return false;
        }
        
        if (output == null)
        {
            return false;
        }
        
        String f = null;
        if (format != null)
        {
            f = format.getFormat();
        }
        
        model.write(output, f, null).close();
        
        // reuse this class -> need fresh model
        
        //System.gc();
        
        model = ChessDataModelToRDFConverter.createModel();
        
        return true;
    }
    
    /**
     * Returns a Set of all converted game names.
     * 
     * @return  unmodifiableSet<String>
     */
    public Map<String, List<String>> getConvertedGameNames()
    {
        return Collections.unmodifiableMap(this.convertedGames);
    }
    
    /**
     * Normalizes the String str. For use as URI.<br />
     * All non-alpha-numeric characters are turned to '_'. The resulting String
     * contains only letters, numbers and the character '_'.
     * 
     * @param   str     String to normalize
     * @return  normalized String
     */
    protected static String getNormalizedString(String str)
    {
        if (str == null)
        {
            return "_null_";
        }

        // Use Regex Pattern?
        int specialCount = 0;
        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length; i ++)
        {
            char c = chars[i];
            if (Character.isLetterOrDigit(c))
            {
                int number = c;
                if (number > 0x7a)
                {
                    chars[i] = separator;
                    specialCount ++;
                }
            }
            else
            {
                chars[i] = separator;
            }
        }
        return (new String(chars)) + ((specialCount > 0) ? separator + "" + specialCount : "");
    }
    
    /**
     * Generates a new unique game identifier.
     * 
     * @param   black   black player's name
     * @param   white   white player's name
     * @param   date    date of game
     * @return  String with unique ID
     */
    protected String getUniqueGameName(String black, String white, String date)
    {
        String key = getNormalizedString(black) + separator +
                getNormalizedString(white) + separator +
                getNormalizedString(date);
        String game;
        if (convertedGames.containsKey(key))
        {
            List<String> games = convertedGames.get(key);
            
            game = key + separator + "" + (games.size() + 1);
            games.add(game);
        }
        else
        {
            List<String> games = new ArrayList<String>();
            game = key + separator + "" + (games.size() + 1);
            games.add(game);
            this.convertedGames.put(key, games);
        }
        
        return game;
    }
}
