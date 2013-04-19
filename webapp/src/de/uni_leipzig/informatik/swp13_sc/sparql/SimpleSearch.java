/**
 * SimpleSearch.java
 */
package de.uni_leipzig.informatik.swp13_sc.sparql;

import java.util.HashMap;
import java.util.Map;

import de.uni_leipzig.informatik.swp13_sc.datamodel.rdf.ChessRDFVocabulary;

/**
 * 
 *
 * @author Erik
 *
 */
public class SimpleSearch
{
    /**
     * fields
     */
    private Map<String, String> fields;
    /**
     * hasResult tells whether the query has been sent to the triplestore.
     */
    private boolean hasResult;
    
    // ------------------------------------------------------------------------
    // Constants
    
    // SPARQL Query
    /**
     * SPARQL_QUERY_NEWLINE
     */
    private final static String SPARQL_QUERY_NEWLINE = "\n";
    /**
     * SPARQL_QUERY_PREFIX_PREFIX
     */
    private final static String SPARQL_QUERY_PREFIX_PREFIX ="PREFIX ";
    /**
     * SPARQL_QUERY_PREFIX_CONT
     */
    private final static String SPARQL_QUERY_PREFIX_CONT = "cont:";
    /**
     * SPARQL_QUERY_PREFIX_CRES
     */
    private final static String SPARQL_QUERY_PREFIX_CRES = "cres:";
    /**
     * SPARQL_QUERY_PREFIX
     */
    private final static String SPARQL_QUERY_PREFIX =
            SPARQL_QUERY_PREFIX_PREFIX + SPARQL_QUERY_PREFIX_CONT + " <" +
            ChessRDFVocabulary.Prefix + ">" + SPARQL_QUERY_NEWLINE +
            SPARQL_QUERY_PREFIX_PREFIX + SPARQL_QUERY_PREFIX_CRES + " <" +
            ChessRDFVocabulary.getResourceURI() + ">" + SPARQL_QUERY_NEWLINE
            + SPARQL_QUERY_NEWLINE;
    
    /**
     * SPARQL_QUERY_SELECT_GAME_VAR
     */
    private final static String SPARQL_QUERY_SELECT_GAME_VAR = "game";
    /**
     * SPARQL_QUERY_SELECT_PLAYER_VAR
     */
    private final static String SPARQL_QUERY_SELECT_PLAYER_VAR = "player";
    /**
     * SPARQL_QUERY_SELECT_START
     */
    private final static String SPARQL_QUERY_SELECT_START = "SELECT ?";
    /**
     * SPARQL_QUERY_WHERE_START
     */
    private final static String SPARQL_QUERY_WHERE_START =
            "WHERE" + SPARQL_QUERY_NEWLINE + "{ " + SPARQL_QUERY_NEWLINE;
    /**
     * SPARQL_QUERY_WHERE_END
     */
    private final static String SPARQL_QUERY_WHERE_END = "}";
            //SPARQL_QUERY_NEWLINE + "}";
    
    /**
     * SPARQL_QUERY_FILTER_REGEX_START
     */
    private final static String SPARQL_QUERY_FILTER_REGEX_START =
            "FILTER( REGEX( STR(";
    /**
     * SPARQL_QUERY_FILTER_REGEX_MIDDLE
     */
    private final static String SPARQL_QUERY_FILTER_REGEX_MIDDLE = "), \"";
    /**
     * SPARQL_QUERY_FILTER_REGEX_END
     */
    private final static String SPARQL_QUERY_FILTER_REGEX_END = "\", \"i\") )";
    
    
    /**
     * SPARQL_QUERY_UNION_START
     */
    private final static String SPARQL_QUERY_UNION_START = "{" + SPARQL_QUERY_NEWLINE;
    /**
     * SPARQL_QUERY_UNION_MIDDLE
     */
    private final static String SPARQL_QUERY_UNION_MIDDLE =
            "}" + SPARQL_QUERY_NEWLINE + "UNION" + SPARQL_QUERY_NEWLINE + "{" +
                    SPARQL_QUERY_NEWLINE;
    /**
     * SPARQL_QUERY_UNION_END
     */
    private final static String SPARQL_QUERY_UNION_END = "}" + SPARQL_QUERY_NEWLINE;
    
    // ------------------------------------------------------------------------
    // FIELD Constants
    
    /**
     * FIELD_KEY_RESULTTYPE
     */
    public final static String FIELD_KEY_RESULTTYPE = "res-type";
    /**
     * FIELD_VALUE_RESULTTYPE_GAME
     */
    public final static String FIELD_VALUE_RESULTTYPE_GAME = "res-type#game";
    /**
     * FIELD_VALUE_RESULTTYPE_PLAYER
     */
    public final static String FIELD_VALUE_RESULTTYPE_PLAYER = "res-type#player";
    
    
    /**
     * FIELD_KEY_CG_DATE
     */
    public final static String FIELD_KEY_CG_DATE = "cg-date";
    /**
     * FIELD_KEY_CG_SITE
     */
    public final static String FIELD_KEY_CG_SITE = "cg-site";
    /**
     * FIELD_KEY_CG_EVENT
     */
    public final static String FIELD_KEY_CG_EVENT = "cg-event";
    /**
     * FIELD_KEY_CG_ROUND
     */
    public final static String FIELD_KEY_CG_ROUND = "cg-round";
    /**
     * FIELD_KEY_CG_RESULT
     */
    public final static String FIELD_KEY_CG_RESULT = "cg-result";
    /**
     * FIELD_VALUE_CG_RESULT_WHITE
     */
    public final static String FIELD_VALUE_CG_RESULT_WHITE = "1-0";
    /**
     * FIELD_VALUE_CG_RESULT_BLACK
     */
    public final static String FIELD_VALUE_CG_RESULT_BLACK = "0-1";
    /**
     * FIELD_VALUE_CG_RESULT_DRAW
     */
    public final static String FIELD_VALUE_CG_RESULT_DRAW = "1/2-1/2";
    
    
    public final static String FIELD_KEY_CP1_NAME = "cp-name[1]";
    public final static String FIELD_KEY_CP2_NAME = "cp-name[2]";
    public final static String FIELD_KEY_CP1_COLOR = "cp-color[1]";
    public final static String FIELD_KEY_CP2_COLOR = "cp-color[2]";
    public final static String FIELD_VALUE_CP_COLOR_BLACK = "black";
    public final static String FIELD_VALUE_CP_COLOR_WHITE = "white";
    public final static String FIELD_VALUE_CP_COLOR_NOCOLOR = "nocolor";
    
    // ------------------------------------------------------------------------
    
    public SimpleSearch()
    {
        this.fields = new HashMap<String, String>();
        this.hasResult = false;
    }
    
    public SimpleSearch(Map<String, String> fields)
    {
        this();
        this.fields = fields;
    }
        
    // ------------------------------------------------------------------------
    
    /**
     * Sets the search field data.
     * 
     * @param   key     field name
     * @param   value   input value
     */
    public void setField(String key, String value)
    {
        if (key == null)
        {
            return;
        }
        
        this.fields.put(key, value);
    }
    
    //setDBConnection()
    
    // ------------------------------------------------------------------------
    
    public boolean query()
    {
        // TODO: query virtuoso
        return false;
    }
    
    // ------------------------------------------------------------------------
    
    public Map<String, String> getResult()
    {
        
        return null;
    }
    
    public boolean hasResult()
    {
        return this.hasResult;
    }
    
    protected String constructSPARQLQueryGameIRI()
    {
        StringBuilder sb = new StringBuilder();
        
        // query prefix
        sb.append(SPARQL_QUERY_PREFIX);
        
        // query select clause
        sb.append(SPARQL_QUERY_SELECT_START)
            .append(SPARQL_QUERY_SELECT_GAME_VAR)
            .append(SPARQL_QUERY_NEWLINE)
            .append(SPARQL_QUERY_WHERE_START);
        
        // query result is a game
        sb.append(this.constructSPARQLQueryGamePart());
        // players
        sb.append(this.constructSPARQLQueryGamePlayerPart());
        
        // query end
        sb.append(SPARQL_QUERY_WHERE_END);
        
        
        
        return sb.toString();
    }
    
    protected String constructSPARQLQueryGamePart()
    {
        StringBuilder sb = new StringBuilder();
        
        sb.append('?')
            .append(SPARQL_QUERY_SELECT_GAME_VAR)
            .append(' ')
            .append('a')
            .append(' ')
            .append(SPARQL_QUERY_PREFIX_CONT)
            .append(ChessRDFVocabulary.ChessGame.getLocalName())
            .append('.')
            .append(SPARQL_QUERY_NEWLINE);
        
        // event
        if (this.fields.containsKey(FIELD_KEY_CG_EVENT) &&
                (null != this.fields.get(FIELD_KEY_CG_EVENT)))
        {
            String var_event = "?event";
            sb.append('?')
                .append(SPARQL_QUERY_SELECT_GAME_VAR)
                .append(' ')
                .append(SPARQL_QUERY_PREFIX_CONT)
                .append(ChessRDFVocabulary.event.getLocalName())
                .append(' ')
                .append(var_event)
                .append('.')
                .append(SPARQL_QUERY_NEWLINE);
            
            sb.append(SPARQL_QUERY_FILTER_REGEX_START)
                .append(var_event)
                .append(SPARQL_QUERY_FILTER_REGEX_MIDDLE)
                .append(this.fields.get(FIELD_KEY_CG_EVENT))
                .append(SPARQL_QUERY_FILTER_REGEX_END)
                .append('.')
                .append(SPARQL_QUERY_NEWLINE);
        }
        
        // site
        if (this.fields.containsKey(FIELD_KEY_CG_SITE) &&
                (null != this.fields.get(FIELD_KEY_CG_SITE)))
        {
            String var_site = "?site";
            sb.append('?')
                .append(SPARQL_QUERY_SELECT_GAME_VAR)
                .append(' ')
                .append(SPARQL_QUERY_PREFIX_CONT)
                .append(ChessRDFVocabulary.site.getLocalName())
                .append(' ')
                .append(var_site)
                .append('.')
                .append(SPARQL_QUERY_NEWLINE);
            
            sb.append(SPARQL_QUERY_FILTER_REGEX_START)
                .append(var_site)
                .append(SPARQL_QUERY_FILTER_REGEX_MIDDLE)
                .append(this.fields.get(FIELD_KEY_CG_SITE))
                .append(SPARQL_QUERY_FILTER_REGEX_END)
                .append('.')
                .append(SPARQL_QUERY_NEWLINE);
        }
        
        // date 
        // TODO: change for ??
        if (this.fields.containsKey(FIELD_KEY_CG_DATE) &&
                (null != this.fields.get(FIELD_KEY_CG_DATE)))
        {
            String var_date = "?date";
            sb.append('?')
                .append(SPARQL_QUERY_SELECT_GAME_VAR)
                .append(' ')
                .append(SPARQL_QUERY_PREFIX_CONT)
                .append(ChessRDFVocabulary.date.getLocalName())
                .append(' ')
                .append(var_date)
                .append('.')
                .append(SPARQL_QUERY_NEWLINE);
            
            sb.append(SPARQL_QUERY_FILTER_REGEX_START)
                .append(var_date)
                .append(SPARQL_QUERY_FILTER_REGEX_MIDDLE)
                .append(this.fields.get(FIELD_KEY_CG_DATE))
                .append(SPARQL_QUERY_FILTER_REGEX_END)
                .append('.')
                .append(SPARQL_QUERY_NEWLINE);
        }
        
        // round
        if (this.fields.containsKey(FIELD_KEY_CG_ROUND) &&
                (null != this.fields.get(FIELD_KEY_CG_ROUND)))
        {
            String var_round = "?round";
            sb.append('?')
                .append(SPARQL_QUERY_SELECT_GAME_VAR)
                .append(' ')
                .append(SPARQL_QUERY_PREFIX_CONT)
                .append(ChessRDFVocabulary.round.getLocalName())
                .append(' ')
                .append(var_round)
                .append('.')
                .append(SPARQL_QUERY_NEWLINE);
            
            sb.append(SPARQL_QUERY_FILTER_REGEX_START)
                .append(var_round)
                .append(SPARQL_QUERY_FILTER_REGEX_MIDDLE)
                .append(this.fields.get(FIELD_KEY_CG_ROUND))
                .append(SPARQL_QUERY_FILTER_REGEX_END)
                .append('.')
                .append(SPARQL_QUERY_NEWLINE);
        }
        
        // round
        if (this.fields.containsKey(FIELD_KEY_CG_RESULT) &&
                (null != this.fields.get(FIELD_KEY_CG_RESULT)))
        {
            sb.append('?')
                .append(SPARQL_QUERY_SELECT_GAME_VAR)
                .append(' ')
                .append(SPARQL_QUERY_PREFIX_CONT)
                .append(ChessRDFVocabulary.result.getLocalName())
                .append(" \"")
                .append(this.fields.get(FIELD_KEY_CG_RESULT))
                .append("\".")
                .append(SPARQL_QUERY_NEWLINE);
        }
        
        return sb.toString();
    }
    
    protected String constructSPARQLQueryGamePlayerPart()
    {
        StringBuilder sb = new StringBuilder();
        String p1 = SPARQL_QUERY_SELECT_PLAYER_VAR + '1';
        String p2 = SPARQL_QUERY_SELECT_PLAYER_VAR + '2';
        
        if (FIELD_VALUE_CP_COLOR_NOCOLOR.equalsIgnoreCase(this.fields.get(FIELD_KEY_CP1_COLOR)))
        {
            sb.append(SPARQL_QUERY_UNION_START)
                .append('?')
                .append(SPARQL_QUERY_SELECT_GAME_VAR)
                .append(' ')
                .append(SPARQL_QUERY_PREFIX_CONT)
                .append(ChessRDFVocabulary.whitePlayer.getLocalName())
                .append(" ?")
                .append(p1)
                .append('.')
                .append(SPARQL_QUERY_NEWLINE)
                .append(this.constructSPARQLQueryPlayerPart(p1, 1))
                .append('?')
                .append(SPARQL_QUERY_SELECT_GAME_VAR)
                .append(' ')
                .append(SPARQL_QUERY_PREFIX_CONT)
                .append(ChessRDFVocabulary.blackPlayer.getLocalName())
                .append(" ?")
                .append(p2)
                .append('.')
                .append(SPARQL_QUERY_NEWLINE)
                .append(this.constructSPARQLQueryPlayerPart(p2, 2))
                .append(SPARQL_QUERY_UNION_MIDDLE)
                .append('?')
                .append(SPARQL_QUERY_SELECT_GAME_VAR)
                .append(' ')
                .append(SPARQL_QUERY_PREFIX_CONT)
                .append(ChessRDFVocabulary.blackPlayer.getLocalName())
                .append(" ?")
                .append(p1)
                .append('.')
                .append(SPARQL_QUERY_NEWLINE)
                .append(this.constructSPARQLQueryPlayerPart(p1, 1))
                .append('?')
                .append(SPARQL_QUERY_SELECT_GAME_VAR)
                .append(' ')
                .append(SPARQL_QUERY_PREFIX_CONT)
                .append(ChessRDFVocabulary.whitePlayer.getLocalName())
                .append(" ?")
                .append(p2)
                .append('.')
                .append(SPARQL_QUERY_NEWLINE)
                .append(this.constructSPARQLQueryPlayerPart(p2, 2))
                .append(SPARQL_QUERY_UNION_END);
        }
        else
        {
            String p1c = ChessRDFVocabulary.whitePlayer.getLocalName();
            String p2c = ChessRDFVocabulary.blackPlayer.getLocalName();
            if (this.fields.containsKey(FIELD_KEY_CP1_COLOR) &&
                    (null != this.fields.get(FIELD_KEY_CP1_COLOR)))
            {
                if (FIELD_VALUE_CP_COLOR_BLACK.equalsIgnoreCase(this.fields.get(FIELD_KEY_CP1_COLOR)))
                {
                    p1c = ChessRDFVocabulary.blackPlayer.getLocalName();
                    p2c = ChessRDFVocabulary.whitePlayer.getLocalName();
                }
            }
            else if (this.fields.containsKey(FIELD_KEY_CP2_COLOR) &&
                    (null != this.fields.get(FIELD_KEY_CP2_COLOR)))
            {
                if (FIELD_VALUE_CP_COLOR_WHITE.equalsIgnoreCase(this.fields.get(FIELD_KEY_CP2_COLOR)))
                {
                    p1c = ChessRDFVocabulary.blackPlayer.getLocalName();
                    p2c = ChessRDFVocabulary.whitePlayer.getLocalName();
                }
            }
            
            sb.append('?')
                .append(SPARQL_QUERY_SELECT_GAME_VAR)
                .append(' ')
                .append(SPARQL_QUERY_PREFIX_CONT)
                .append(p1c)
                .append(" ?")
                .append(p1)
                .append('.')
                .append(SPARQL_QUERY_NEWLINE)
                .append(this.constructSPARQLQueryPlayerPart(p1, 1))
                .append('?')
                .append(SPARQL_QUERY_SELECT_GAME_VAR)
                .append(' ')
                .append(SPARQL_QUERY_PREFIX_CONT)
                .append(p2c)
                .append(" ?")
                .append(p2)
                .append('.')
                .append(SPARQL_QUERY_NEWLINE)
                .append(this.constructSPARQLQueryPlayerPart(p2, 2));
        }
        
        return sb.toString();
    }
    
    protected String constructSPARQLQueryPlayerPart(String var_name, int nr)
    {
        StringBuilder sb = new StringBuilder();
        
        sb.append('?')
            .append(var_name)
            .append(' ')
            .append('a')
            .append(' ')
            .append(SPARQL_QUERY_PREFIX_CONT)
            .append(ChessRDFVocabulary.ChessPlayer.getLocalName())
            .append('.')
            .append(SPARQL_QUERY_NEWLINE);
        
        // get name
        String name = null;        
        if (nr == 1)
        {
            if (this.fields.containsKey(FIELD_KEY_CP1_NAME) &&
                (null != this.fields.get(FIELD_KEY_CP1_NAME)))
            {
                name = this.fields.get(FIELD_KEY_CP1_NAME);
            }
        }
        else if (nr == 2)
        {
            if (this.fields.containsKey(FIELD_KEY_CP2_NAME) &&
                    (null != this.fields.get(FIELD_KEY_CP2_NAME)))
                {
                    name = this.fields.get(FIELD_KEY_CP2_NAME);
                }
        }
        
        
        if (name != null)
        {
            String var_name_player = var_name + "-name";
            sb.append('?')
                .append(var_name)
                .append(' ')
                .append(SPARQL_QUERY_PREFIX_CONT)
                .append(ChessRDFVocabulary.name.getLocalName())
                .append(" ?")
                .append(var_name_player)
                .append(' ')
                .append(SPARQL_QUERY_NEWLINE);
            
            sb.append(SPARQL_QUERY_FILTER_REGEX_START)
                .append('?')
                .append(var_name_player)
                .append(SPARQL_QUERY_FILTER_REGEX_MIDDLE)
                .append(name)
                .append(SPARQL_QUERY_FILTER_REGEX_END)
                .append('.')
                .append(SPARQL_QUERY_NEWLINE);
        }
        
        return sb.toString();
    }
    
    protected String constructSPARQLQueryPlayerIRI()
    {
        
        return null;
    }
    
    public String getSPARQLQuery()
    {
        if (FIELD_VALUE_RESULTTYPE_PLAYER.equalsIgnoreCase(this.fields.get(FIELD_KEY_RESULTTYPE)))
        {
            return constructSPARQLQueryPlayerIRI();
        }
        else
        {
            this.setField(FIELD_KEY_RESULTTYPE, FIELD_VALUE_RESULTTYPE_GAME);
            return this.constructSPARQLQueryGameIRI();
        }
    }
}
