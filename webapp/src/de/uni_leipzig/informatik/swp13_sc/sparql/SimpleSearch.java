/**
 * SimpleSearch.java
 */
package de.uni_leipzig.informatik.swp13_sc.sparql;

import de.uni_leipzig.informatik.swp13_sc.datamodel.ChessMove;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import virtuoso.jena.driver.VirtGraph;
import virtuoso.jena.driver.VirtuosoQueryExecution;
import virtuoso.jena.driver.VirtuosoQueryExecutionFactory;

import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.RDFNode;

import de.uni_leipzig.informatik.swp13_sc.datamodel.rdf.ChessRDFVocabulary;

/**
 * A class for constructing SPARQL query Strings with the input of the web
 * interface. It returns the IRIs of found games or players.
 *
 * @author Erik
 * @author Lasse
 *
 */
public class SimpleSearch
{
    /**
     * The input field values of the web interface.
     */
    private Map<String, String> fields;
    /**
     * hasResult tells whether the query has been sent to the triplestore.
     */
    private boolean hasResult;
    /**
     * Selects only distinct values. (default)
     */
    private boolean distinct;
    /**
     * Sets the maximum number of results returned.
     */
    private int limit;
    /**
     * Tells the SPARQL Query composer to construct a COUNT query.
     */
    private boolean count;
    
    /**
     * The virtuosoGraph from which the results are fetched from.
     */
    private VirtGraph virtuosoGraph;
    
    /**
     * The result count. Will always be executed and available.
     */
    private long resultCount;
    /**
     * The list with results. String URI/IRIs from players or games.
     */
    private List<String> resultList;
    
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
    private final static String SPARQL_QUERY_PREFIX_CONT =
            ChessRDFVocabulary.getOntologyPrefixName() + ":";
    /**
     * SPARQL_QUERY_PREFIX_CRES
     */
    private final static String SPARQL_QUERY_PREFIX_CRES =
            ChessRDFVocabulary.getResourcePrefixName() + ":";
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
     * SPARQL_QUERY_SELECT_OPENING_VAR
     */
    private final static String SPARQL_QUERY_SELECT_OPENING_VAR = "opening";
    /**
     * SPARQL_QUERY_SELECT_PLAYER_VAR
     */
    private final static String SPARQL_QUERY_SELECT_PLAYER_VAR = "player";
    /**
     * SPARQL_QUERY_SELECT_PLAYER1_VAR
     */
    private final static String SPARQL_QUERY_SELECT_PLAYER1_VAR =
            SPARQL_QUERY_SELECT_PLAYER_VAR + '1';
    /**
     * SPARQL_QUERY_SELECT_PLAYER2_VAR
     */
    private final static String SPARQL_QUERY_SELECT_PLAYER2_VAR =
            SPARQL_QUERY_SELECT_PLAYER_VAR + '2';
    /**
     * SPARQL_QUERY_SELECT_START
     */
    private final static String SPARQL_QUERY_SELECT_START = "SELECT";
    /**
     * SPARQL_QUERY_SELECT_DISTINCT
     */
    private final static String SPARQL_QUERY_SELECT_DISTINCT = " DISTINCT";
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
     * SPARQL_QUERY_LIMIT
     */
    private final static String SPARQL_QUERY_LIMIT = "LIMIT "; //TODO: could also be in the config file
    
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
    
    /**
     * GAME_VARIABLE is the variable name of the chess game SPARQL query string.
     */
    public final static String GAME_VARIABLE = SPARQL_QUERY_SELECT_GAME_VAR;
    /**
     * PLAYER1_VARIABLE is the variable name of the first player in the
     * SPARQL query string.
     */
    public final static String PLAYER1_VARIABLE = SPARQL_QUERY_SELECT_PLAYER1_VAR;
    /**
     * PLAYER2_VARIABLE is the variable name of the second player in the
     * SPARQL query string.
     */
    public final static String PLAYER2_VARIABLE = SPARQL_QUERY_SELECT_PLAYER2_VAR;
    /**
     * COUNT_VARIABLE is the variable name of the column/return value variable
     * when counting is enabled.
     */
    public final static String COUNT_VARIABLE = "count";
    
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
     * FIELD_VALUE_RESULTTYPE_PLAYER1
     */
    public final static String FIELD_VALUE_RESULTTYPE_PLAYER1 =
            FIELD_VALUE_RESULTTYPE_PLAYER + '1';
    /**
     * FIELD_VALUE_RESULTTYPE_PLAYER2
     */
    public final static String FIELD_VALUE_RESULTTYPE_PLAYER2 =
            FIELD_VALUE_RESULTTYPE_PLAYER + '2';
    
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
     * FIELD_KEY_CG_ECO
     */
    public final static String FIELD_KEY_CG_ECO = "cg-eco";
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
    
    
    /**
     * FIELD_KEY_CP1_NAME
     */
    public final static String FIELD_KEY_CP1_NAME = "cp-name[1]";
    /**
     * FIELD_KEY_CP2_NAME
     */
    public final static String FIELD_KEY_CP2_NAME = "cp-name[2]";
    
    // Optional meta data
    /**
     * FIELD_KEY_CP1_ELO
     */
    public final static String FIELD_KEY_CP1_ELO = "cp-elo[1]";
    /**
     * FIELD_KEY_CP2_ELO
     */
    public final static String FIELD_KEY_CP2_ELO = "cp-elo[2]";
    /**
     * FIELD_KEY_CP1_NATION
     */
    public final static String FIELD_KEY_CP1_NATION = "cp-nation[1]";
    /**
     * FIELD_KEY_CP2_NATION
     */
    public final static String FIELD_KEY_CP2_NATION = "cp-nation[2]";
    /**
     * FIELD_KEY_CP1_TITLE
     */
    public final static String FIELD_KEY_CP1_TITLE = "cp-title[1]";
    /**
     * FIELD_KEY_CP2_TITLE
     */
    public final static String FIELD_KEY_CP2_TITLE = "cp-title[2]";
    /**
     * FIELD_KEY_CP1_BIRTH_DATE
     */
    public final static String FIELD_KEY_CP1_BIRTH_DATE = "cp-birthDate[1]";
    /**
     * FIELD_KEY_CP2_BIRTH_DATE
     */
    public final static String FIELD_KEY_CP2_BIRTH_DATE = "cp-birthDate[2]";
    /**
     * FIELD_KEY_CP1_DEATH_DATE
     */
    public final static String FIELD_KEY_CP1_DEATH_DATE = "cp-deathDate[1]";
    /**
     * FIELD_KEY_CP2_DEATH_DATE
     */
    public final static String FIELD_KEY_CP2_DEATH_DATE = "cp-deathDate[2]";
    /**
     * FIELD_KEY_CP1_BIRTH_PLACE
     */
    public final static String FIELD_KEY_CP1_BIRTH_PLACE = "cp-birthPlace[1]";
    /**
     * FIELD_KEY_CP2_BIRTH_PLACE
     */
    public final static String FIELD_KEY_CP2_BIRTH_PLACE = "cp-birthPlace[2]";
    /**
     * FIELD_KEY_CP1_PEAK_RANKING
     */
    public final static String FIELD_KEY_CP1_PEAK_RANKING = "cp-peakRanking[1]";
    /**
     * FIELD_KEY_CP2_PEAK_RANKING
     */
    public final static String FIELD_KEY_CP2_PEAK_RANKING = "cp-peakRanking[2]";
    
    /**
     * FIELD_KEY_CP1_COLOR
     */
    public final static String FIELD_KEY_CP1_COLOR = "cp-color[1]";
    /**
     * FIELD_KEY_CP2_COLOR
     */
    public final static String FIELD_KEY_CP2_COLOR = "cp-color[2]";
    /**
     * FIELD_VALUE_CP_COLOR_BLACK
     */
    public final static String FIELD_VALUE_CP_COLOR_BLACK = "black";
    /**
     * FIELD_VALUE_CP_COLOR_WHITE
     */
    public final static String FIELD_VALUE_CP_COLOR_WHITE = "white";
    /**
     * FIELD_VALUE_CP_COLOR_NOCOLOR
     */
    public final static String FIELD_VALUE_CP_COLOR_NOCOLOR = "nocolor";
    
    // ------------------------------------------------------------------------
    
    /**
     * Default constructor. Needs input with {@link #setField(String, String)}.
     */
    public SimpleSearch()
    {
        this.fields = new HashMap<String, String>();
        this.distinct = true;
        this.hasResult = false;
        this.resultCount = -1;
        this.limit = 100;
    }
    
    /**
     * Sets the fields from the web interface needed for constructing the
     * SPARQL query.
     * 
     * @param   fields  Map<String, String>
     */
    public SimpleSearch(Map<String, String> fields)
    {
        this();
        this.fields.putAll(fields);
    }
        
    // ------------------------------------------------------------------------
    
    /**
     * Sets the search field data.
     * 
     * @param   key     field name
     * @param   value   input value
     * @return  SimpleSearch (this) to chain calls
     */
    public SimpleSearch setField(String key, String value)
    {
        if (key == null)
        {
            return this;
        }
        
        this.fields.put(key, value);
        
        return this;
    }
    
    /**
     * Sets the 'Distinct-Mode'. Filters duplicate values.
     * 
     * @param   distinct    true if filtering else false
     * @return  SimpleSearch (this) to chain calls 
     */
    public SimpleSearch setDistinct(boolean distinct)
    {
        this.distinct = distinct;
        
        return this;
    }
    
    /**
     * Sets the maximum number of results to be returned. If the argument is
     * less 0 than the previous limit will stay in effect. If the argument is
     * 0 it will mean no limit at all.
     * 
     * @param   limit   maximum number of results greater 0
     * @return  SimpleSearch (this) to chain calls
     */
    public SimpleSearch setLimit(int limit)
    {
        if (limit >= 0)
        {
            this.limit = limit;
        }
        
        return this;
    }
    
    /**
     * Tells the SPARQL Query constructor to construct a COUNT SPARQL query
     * if set to true.
     * 
     * @param   count   construct a COUNT SPARQL query if true
     * @return  SimpleSearch (this) to chain calls
     */
    public SimpleSearch setCountResults(boolean count)
    {
        this.count = count;
        
        return this;
    }
    
    /**
     * Sets the connection graph to Virtuoso.
     * 
     * @param   virtuosoGraph   VirtGraph
     */
    public void setDBConnection(VirtGraph virtuosoGraph)
    {
        this.virtuosoGraph = virtuosoGraph;
    }
    
    // ------------------------------------------------------------------------
    
    /**
     * Queries the Virtuoso Triple-(Quad-)Store and saves the list with
     * player or game URI/IRIs internally.<br />
     * The queries are sent to Virtuoso - no preprocessing with Jena required.
     * 
     * @return  true on success else false
     */
    public boolean query()
    {
        if (this.virtuosoGraph == null)
        {
            return false;
        }
        
        this.resultList = new ArrayList<String>();
        
        try
        {
            //QueryExecution vqeS = QueryExecutionFactory.create(this.selectQuery, new VirtModel(virtuosoGraph));
            VirtuosoQueryExecution vqeS = VirtuosoQueryExecutionFactory.create(this.getSPARQLQuery(), virtuosoGraph);
            
            if (! this.count)
            {

            	
                ResultSet results = vqeS.execSelect();
                
                String vari = GAME_VARIABLE;
                if (this.fields.get(FIELD_KEY_RESULTTYPE).equalsIgnoreCase(FIELD_VALUE_RESULTTYPE_PLAYER1))
                {
                    vari = PLAYER1_VARIABLE;
                }
                else if (this.fields.get(FIELD_KEY_RESULTTYPE).equalsIgnoreCase(FIELD_VALUE_RESULTTYPE_PLAYER2))
                {
                    vari = PLAYER2_VARIABLE;
                }
                
                while(results.hasNext())
                {
                    QuerySolution result = (QuerySolution) results.next();
                    RDFNode iri = result.get(vari);
                    this.resultList.add(iri.toString());
                }
                // results of integer? (probably not)
                this.resultCount = this.resultList.size();
            }
            else
            {
                ResultSet results = vqeS.execSelect();
                
                if (results.hasNext())
                {
                    QuerySolution result = (QuerySolution) results.next();
                    Literal c = result.getLiteral(COUNT_VARIABLE);
                    this.resultCount = c.getLong();
                }
            }
            
            vqeS.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();

            this.hasResult = true;
            this.resultList = new ArrayList<String>();
            this.resultCount = -1;
            return false;
        }
        
        this.hasResult = true;
        
        return true;
    }
    
    // ------------------------------------------------------------------------
    
    /**
     * Returns the (cached) list of results of player or game URI/IRIs.
     * 
     * @return  List<String> with URI/IRIs or null on error!
     */
    public List<String> getResult()
    {
        if (! this.hasResult())
        {
            if (! this.query())
            {
                return null;
            }
        }
        
        return this.resultList;
    }
    
    /**
     * Returns the number of results of the previously executed query.
     * 
     * @return  Number (long) of results or -1 on error.
     */
    public long getResultCount()
    {
        if (! this.hasResult())
        {
            return -1;
        }
        
        return this.resultCount;
    }
    
    
	/**
     * Returns the number (!) of results without a LIMIT clause.
     * @return  Number (long) of results or -1 on error.
     */
    public long getResultCountUnLimited()
    {
        // check if not executed before - else skip
        if (! (this.hasResult && this.resultList != null && this.resultList.size() == 0))
        {
            // temporarily set count on
            boolean countOn = this.count;
            this.count = true;
            
            // query with default mode
            this.query();
            
            // set to previous value
            this.count = countOn;
        }
        
        return this.resultCount;
    }
    
    /**
     * Returns whether the search has results (even if they are zero) or false
     * if there was an error or no execution.
     * 
     * @return  true if the search was executed.
     */
    public boolean hasResult()
    {
        return this.hasResult;
    }
    
    /**
     * Constructs a COUNT SPARQL-Wrapper around given variables or a COUNT(*)
     * for a lot of different cases where the input is wrong ...<br />
     * Sets DISTINCT depending on class status.
     * 
     * @param   variable_names  Open String array to allow a lot of possibilities
     * @return  COUNT(*) or COUNT( <variables> ) -> SPARQL String
     */
    protected String constructSPARQLCountWrapper(String ... variable_names)
    {
        StringBuilder sb = new StringBuilder();
        
        sb.append(" ( COUNT (")
            .append((this.distinct) ? SPARQL_QUERY_SELECT_DISTINCT : "");
        
        if (variable_names == null || variable_names.length == 0 ||
                (variable_names.length == 1 && (variable_names[0] == null ||
                variable_names[0].trim().equals("*") ||
                variable_names[0].trim().equals(""))))
        {
            sb.append(" *");
        }
        else
        {
            // only a single variable works ! -> take the first one
            // or *
            sb.append(" ?")
                .append(variable_names[0]);
        }
        
        // rename as COUNT_VARIABLE
        // not the default: 'callret-0'
        sb.append(" ) AS ?")
            .append(COUNT_VARIABLE)
            .append(" )");
        
        return sb.toString();
    }
    
    /**
     * Constructs a SPARQL query string for selecting chess game IRIs depending
     * on the values used in the web interface.
     * 
     * @return  SPARQL-Query String
     */
    protected String constructSPARQLQueryGameIRI()
    {
        StringBuilder sb = new StringBuilder();
        
        // query prefix
        sb.append(SPARQL_QUERY_PREFIX);
        
        // query select clause
        sb.append(SPARQL_QUERY_SELECT_START);
        
        if (this.count)
        {
            sb.append(constructSPARQLCountWrapper(SPARQL_QUERY_SELECT_GAME_VAR));
        }
        else
        {
            sb.append((this.distinct) ? SPARQL_QUERY_SELECT_DISTINCT : "")
                .append(" ?")
                .append(SPARQL_QUERY_SELECT_GAME_VAR);
        }
            
        sb.append(SPARQL_QUERY_NEWLINE)
            .append(SPARQL_QUERY_WHERE_START);
        
        // query result is a game
        sb.append(this.constructSPARQLQueryGamePart());
        // players
        sb.append(this.constructSPARQLQueryGamePlayerPart());
        
        // query end
        sb.append(SPARQL_QUERY_WHERE_END);
        
        // limit results if not counting and not unlimited
        if ((! this.count) && (this.limit > 0))
        {
            sb.append(SPARQL_QUERY_NEWLINE)
                .append(SPARQL_QUERY_LIMIT)
                .append(this.limit);
        }
        
        return sb.toString();
    }
    
    /**
     * Constructs a SPARQL query string with the game variable. It uses all the
     * fields from the web interface.
     * 
     * @return  SPARQL-Query String (part only)
     */
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
        // TODO: changes needed for ??
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
        
        // result
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
        
        // eco (textual)
        if (this.fields.containsKey(FIELD_KEY_CG_ECO) &&
                (null != this.fields.get(FIELD_KEY_CG_ECO)))
        {
            String var_eco = "?eco_var";
            sb  // UNION
                // eco as literal on game
                .append(SPARQL_QUERY_UNION_START)
                .append('?')
                .append(SPARQL_QUERY_SELECT_GAME_VAR)
                .append(' ')
                .append(SPARQL_QUERY_PREFIX_CONT)
                .append(ChessRDFVocabulary.eco.getLocalName())
                .append(" \"")
                .append(this.fields.get(FIELD_KEY_CG_ECO))
                .append("\".")
                .append(SPARQL_QUERY_NEWLINE)
                .append(SPARQL_QUERY_UNION_MIDDLE)
                // eco as resource linked to game
                .append('?')
                .append(SPARQL_QUERY_SELECT_GAME_VAR)
                .append(' ')
                .append(SPARQL_QUERY_PREFIX_CONT)
                .append(ChessRDFVocabulary.eco.getLocalName())
                .append(" ?")
                .append(SPARQL_QUERY_SELECT_OPENING_VAR)
                .append('.')
                .append(SPARQL_QUERY_NEWLINE)
                // UNION eco as code or as name
                .append(SPARQL_QUERY_UNION_START)
                // eco as code
                .append('?')
                .append(SPARQL_QUERY_SELECT_OPENING_VAR)
                .append(' ')
                .append(SPARQL_QUERY_PREFIX_CONT)
                .append(ChessRDFVocabulary.openingCode.getLocalName())
                .append(" \"")
                .append(this.fields.get(FIELD_KEY_CG_ECO))
                .append("\".")
                .append(SPARQL_QUERY_NEWLINE)
                .append(SPARQL_QUERY_UNION_MIDDLE)
                // now eco as name with filter
                .append('?')
                .append(SPARQL_QUERY_SELECT_OPENING_VAR)
                .append(' ')
                .append(SPARQL_QUERY_PREFIX_CONT)
                .append(ChessRDFVocabulary.openingName.getLocalName())
                .append(' ')
                .append(var_eco)
                .append('.')
                .append(SPARQL_QUERY_NEWLINE)
                .append(SPARQL_QUERY_FILTER_REGEX_START)
                .append(var_eco)
                .append(SPARQL_QUERY_FILTER_REGEX_MIDDLE)
                .append(this.fields.get(FIELD_KEY_CG_ECO))
                .append(SPARQL_QUERY_FILTER_REGEX_END)
                .append('.')
                .append(SPARQL_QUERY_NEWLINE)
                .append(SPARQL_QUERY_UNION_END)
                .append(SPARQL_QUERY_UNION_END);
        }
        
        return sb.toString();
    }
    
    /**
     * Constructs a SPARQL query string. It puts the game and player parts together.
     * Creates a UNION if needed.<br />
     * Will only work with resources where players and chess games are linked!
     *  
     * @return  SPARQL-Query String (part only)
     */
    protected String constructSPARQLQueryGamePlayerPart()
    {
        StringBuilder sb = new StringBuilder();
        
        if (FIELD_VALUE_CP_COLOR_NOCOLOR.equalsIgnoreCase(this.fields.get(FIELD_KEY_CP1_COLOR)))
        {
            sb.append(SPARQL_QUERY_UNION_START)
                .append('?')
                .append(SPARQL_QUERY_SELECT_GAME_VAR)
                .append(' ')
                .append(SPARQL_QUERY_PREFIX_CONT)
                .append(ChessRDFVocabulary.whitePlayer.getLocalName())
                .append(" ?")
                .append(SPARQL_QUERY_SELECT_PLAYER1_VAR)
                .append('.')
                .append(SPARQL_QUERY_NEWLINE)
                .append(this.constructSPARQLQueryPlayerPart(SPARQL_QUERY_SELECT_PLAYER1_VAR, 1))
                .append('?')
                .append(SPARQL_QUERY_SELECT_GAME_VAR)
                .append(' ')
                .append(SPARQL_QUERY_PREFIX_CONT)
                .append(ChessRDFVocabulary.blackPlayer.getLocalName())
                .append(" ?")
                .append(SPARQL_QUERY_SELECT_PLAYER2_VAR)
                .append('.')
                .append(SPARQL_QUERY_NEWLINE)
                .append(this.constructSPARQLQueryPlayerPart(SPARQL_QUERY_SELECT_PLAYER2_VAR, 2))
                .append(SPARQL_QUERY_UNION_MIDDLE)
                .append('?')
                .append(SPARQL_QUERY_SELECT_GAME_VAR)
                .append(' ')
                .append(SPARQL_QUERY_PREFIX_CONT)
                .append(ChessRDFVocabulary.blackPlayer.getLocalName())
                .append(" ?")
                .append(SPARQL_QUERY_SELECT_PLAYER1_VAR)
                .append('.')
                .append(SPARQL_QUERY_NEWLINE)
                .append(this.constructSPARQLQueryPlayerPart(SPARQL_QUERY_SELECT_PLAYER1_VAR, 1))
                .append('?')
                .append(SPARQL_QUERY_SELECT_GAME_VAR)
                .append(' ')
                .append(SPARQL_QUERY_PREFIX_CONT)
                .append(ChessRDFVocabulary.whitePlayer.getLocalName())
                .append(" ?")
                .append(SPARQL_QUERY_SELECT_PLAYER2_VAR)
                .append('.')
                .append(SPARQL_QUERY_NEWLINE)
                .append(this.constructSPARQLQueryPlayerPart(SPARQL_QUERY_SELECT_PLAYER2_VAR, 2))
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
                .append(SPARQL_QUERY_SELECT_PLAYER1_VAR)
                .append('.')
                .append(SPARQL_QUERY_NEWLINE)
                .append(this.constructSPARQLQueryPlayerPart(SPARQL_QUERY_SELECT_PLAYER1_VAR, 1))
                .append('?')
                .append(SPARQL_QUERY_SELECT_GAME_VAR)
                .append(' ')
                .append(SPARQL_QUERY_PREFIX_CONT)
                .append(p2c)
                .append(" ?")
                .append(SPARQL_QUERY_SELECT_PLAYER2_VAR)
                .append('.')
                .append(SPARQL_QUERY_NEWLINE)
                .append(this.constructSPARQLQueryPlayerPart(SPARQL_QUERY_SELECT_PLAYER2_VAR, 2));
        }
        
        return sb.toString();
    }
    
    /**
     * Constructs the part of the SPARQL query with the player variable.
     * 
     * @param   var_name    variable name of player
     * @param   nr          number of player (1 or 2)
     * @return  SPARQL-Query String (part only)
     */
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
        String elo = null;
        String birthDate = null;
        String deathDate = null;
        String birthPlace = null;
        String peakRanking = null;
        String title = null;
        String nation = null;
        if (nr == 1)
        {
            if (this.fields.containsKey(FIELD_KEY_CP1_NAME) &&
                (null != this.fields.get(FIELD_KEY_CP1_NAME)))
            {
                name = this.fields.get(FIELD_KEY_CP1_NAME);
            }
            // additional optional data
            if (this.fields.containsKey(FIELD_KEY_CP1_ELO) &&
                    (null != this.fields.get(FIELD_KEY_CP1_ELO)))
            {
                elo = this.fields.get(FIELD_KEY_CP1_ELO);
            }
            if (this.fields.containsKey(FIELD_KEY_CP1_TITLE) &&
                    (null != this.fields.get(FIELD_KEY_CP1_TITLE)))
            {
                title = this.fields.get(FIELD_KEY_CP1_TITLE);
            }
            if (this.fields.containsKey(FIELD_KEY_CP1_NATION) &&
                    (null != this.fields.get(FIELD_KEY_CP1_NATION)))
            {
                nation = this.fields.get(FIELD_KEY_CP1_NATION);
            }
            if (this.fields.containsKey(FIELD_KEY_CP1_BIRTH_DATE) &&
                    (null != this.fields.get(FIELD_KEY_CP1_BIRTH_DATE)))
            {
                birthDate = this.fields.get(FIELD_KEY_CP1_BIRTH_DATE);
            }
            if (this.fields.containsKey(FIELD_KEY_CP1_BIRTH_PLACE) &&
                    (null != this.fields.get(FIELD_KEY_CP1_BIRTH_PLACE)))
            {
                birthPlace = this.fields.get(FIELD_KEY_CP1_BIRTH_PLACE);
            }
            if (this.fields.containsKey(FIELD_KEY_CP1_DEATH_DATE) &&
                    (null != this.fields.get(FIELD_KEY_CP1_DEATH_DATE)))
            {
                deathDate = this.fields.get(FIELD_KEY_CP1_DEATH_DATE);
            }
            if (this.fields.containsKey(FIELD_KEY_CP1_PEAK_RANKING) &&
                    (null != this.fields.get(FIELD_KEY_CP1_PEAK_RANKING)))
            {
                peakRanking = this.fields.get(FIELD_KEY_CP1_PEAK_RANKING);
            }
        }
        else if (nr == 2)
        {
            if (this.fields.containsKey(FIELD_KEY_CP2_NAME) &&
                    (null != this.fields.get(FIELD_KEY_CP2_NAME)))
            {
                name = this.fields.get(FIELD_KEY_CP2_NAME);
            }
            // additional optional data
            if (this.fields.containsKey(FIELD_KEY_CP2_ELO) &&
                    (null != this.fields.get(FIELD_KEY_CP2_ELO)))
            {
                elo = this.fields.get(FIELD_KEY_CP2_ELO);
            }
            if (this.fields.containsKey(FIELD_KEY_CP2_TITLE) &&
                    (null != this.fields.get(FIELD_KEY_CP2_TITLE)))
            {
                title = this.fields.get(FIELD_KEY_CP2_TITLE);
            }
            if (this.fields.containsKey(FIELD_KEY_CP2_NATION) &&
                    (null != this.fields.get(FIELD_KEY_CP2_NATION)))
            {
                nation = this.fields.get(FIELD_KEY_CP2_NATION);
            }
            if (this.fields.containsKey(FIELD_KEY_CP2_BIRTH_DATE) &&
                    (null != this.fields.get(FIELD_KEY_CP2_BIRTH_DATE)))
            {
                birthDate = this.fields.get(FIELD_KEY_CP2_BIRTH_DATE);
            }
            if (this.fields.containsKey(FIELD_KEY_CP2_BIRTH_PLACE) &&
                    (null != this.fields.get(FIELD_KEY_CP2_BIRTH_PLACE)))
            {
                birthPlace = this.fields.get(FIELD_KEY_CP2_BIRTH_PLACE);
            }
            if (this.fields.containsKey(FIELD_KEY_CP2_DEATH_DATE) &&
                    (null != this.fields.get(FIELD_KEY_CP2_DEATH_DATE)))
            {
                deathDate = this.fields.get(FIELD_KEY_CP2_DEATH_DATE);
            }
            if (this.fields.containsKey(FIELD_KEY_CP2_PEAK_RANKING) &&
                    (null != this.fields.get(FIELD_KEY_CP2_PEAK_RANKING)))
            {
                peakRanking = this.fields.get(FIELD_KEY_CP2_PEAK_RANKING);
            }
        }
        
        
        if (name != null)
        {
            String var_name_player = var_name + "_name";
            sb.append('?')
                .append(var_name)
                .append(' ')
                .append(SPARQL_QUERY_PREFIX_CONT)
                .append(ChessRDFVocabulary.name.getLocalName())
                .append(" ?")
                .append(var_name_player)
                .append('.')
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
        
        if (elo != null)
        {
            String var_name_player = var_name + "_elo";
            sb.append('?')
                .append(var_name)
                .append(' ')
                .append(SPARQL_QUERY_PREFIX_CONT)
                .append(ChessRDFVocabulary.elo.getLocalName())
                .append(" ?")
                .append(var_name_player)
                .append('.')
                .append(SPARQL_QUERY_NEWLINE);
            
            sb.append(SPARQL_QUERY_FILTER_REGEX_START)
                .append('?')
                .append(var_name_player)
                .append(SPARQL_QUERY_FILTER_REGEX_MIDDLE)
                .append(elo)
                .append(SPARQL_QUERY_FILTER_REGEX_END)
                .append('.')
                .append(SPARQL_QUERY_NEWLINE);
        }
        if (title != null)
        {
            String var_name_player = var_name + "_title";
            sb.append('?')
                .append(var_name)
                .append(' ')
                .append(SPARQL_QUERY_PREFIX_CONT)
                .append(ChessRDFVocabulary.title.getLocalName())
                .append(" ?")
                .append(var_name_player)
                .append('.')
                .append(SPARQL_QUERY_NEWLINE);
            
            sb.append(SPARQL_QUERY_FILTER_REGEX_START)
                .append('?')
                .append(var_name_player)
                .append(SPARQL_QUERY_FILTER_REGEX_MIDDLE)
                .append(title)
                .append(SPARQL_QUERY_FILTER_REGEX_END)
                .append('.')
                .append(SPARQL_QUERY_NEWLINE);
        }
        if (nation != null)
        {
            String var_name_player = var_name + "_nation";
            sb.append('?')
                .append(var_name)
                .append(' ')
                .append(SPARQL_QUERY_PREFIX_CONT)
                .append(ChessRDFVocabulary.nation.getLocalName())
                .append(" ?")
                .append(var_name_player)
                .append('.')
                .append(SPARQL_QUERY_NEWLINE);
            
            sb.append(SPARQL_QUERY_FILTER_REGEX_START)
                .append('?')
                .append(var_name_player)
                .append(SPARQL_QUERY_FILTER_REGEX_MIDDLE)
                .append(nation)
                .append(SPARQL_QUERY_FILTER_REGEX_END)
                .append('.')
                .append(SPARQL_QUERY_NEWLINE);
        }
        if (birthDate != null)
        {
            String var_name_player = var_name + "_birthDate";
            sb.append('?')
                .append(var_name)
                .append(' ')
                .append(SPARQL_QUERY_PREFIX_CONT)
                .append(ChessRDFVocabulary.birthDate.getLocalName())
                .append(" ?")
                .append(var_name_player)
                .append('.')
                .append(SPARQL_QUERY_NEWLINE);
            
            sb.append(SPARQL_QUERY_FILTER_REGEX_START)
                .append('?')
                .append(var_name_player)
                .append(SPARQL_QUERY_FILTER_REGEX_MIDDLE)
                .append(birthDate)
                .append(SPARQL_QUERY_FILTER_REGEX_END)
                .append('.')
                .append(SPARQL_QUERY_NEWLINE);
        }
        if (birthPlace != null)
        {
            String var_name_player = var_name + "_birthPlace";
            sb.append('?')
                .append(var_name)
                .append(' ')
                .append(SPARQL_QUERY_PREFIX_CONT)
                .append(ChessRDFVocabulary.birthPlace.getLocalName())
                .append(" ?")
                .append(var_name_player)
                .append('.')
                .append(SPARQL_QUERY_NEWLINE);
            
            sb.append(SPARQL_QUERY_FILTER_REGEX_START)
                .append('?')
                .append(var_name_player)
                .append(SPARQL_QUERY_FILTER_REGEX_MIDDLE)
                .append(birthPlace)
                .append(SPARQL_QUERY_FILTER_REGEX_END)
                .append('.')
                .append(SPARQL_QUERY_NEWLINE);
        }
        if (deathDate != null)
        {
            String var_name_player = var_name + "_deathDate";
            sb.append('?')
                .append(var_name)
                .append(' ')
                .append(SPARQL_QUERY_PREFIX_CONT)
                .append(ChessRDFVocabulary.deathDate.getLocalName())
                .append(" ?")
                .append(var_name_player)
                .append('.')
                .append(SPARQL_QUERY_NEWLINE);
            
            sb.append(SPARQL_QUERY_FILTER_REGEX_START)
                .append('?')
                .append(var_name_player)
                .append(SPARQL_QUERY_FILTER_REGEX_MIDDLE)
                .append(deathDate)
                .append(SPARQL_QUERY_FILTER_REGEX_END)
                .append('.')
                .append(SPARQL_QUERY_NEWLINE);
        }
        if (peakRanking != null)
        {
            String var_name_player = var_name + "_peakRanking";
            sb.append('?')
                .append(var_name)
                .append(' ')
                .append(SPARQL_QUERY_PREFIX_CONT)
                .append(ChessRDFVocabulary.peakRanking.getLocalName())
                .append(" ?")
                .append(var_name_player)
                .append('.')
                .append(SPARQL_QUERY_NEWLINE);
            
            sb.append(SPARQL_QUERY_FILTER_REGEX_START)
                .append('?')
                .append(var_name_player)
                .append(SPARQL_QUERY_FILTER_REGEX_MIDDLE)
                .append(peakRanking)
                .append(SPARQL_QUERY_FILTER_REGEX_END)
                .append('.')
                .append(SPARQL_QUERY_NEWLINE);
        }
        
        return sb.toString();
    }
    
    /**
     * Constructs the SPARQL query for selecting player IRIs.
     * 
     * @param   var_name_player     the variable name into which the values are selected
     * @return  SPARQL-Query String
     */
    protected String constructSPARQLQueryPlayerIRI(String var_name_player)
    {
        StringBuilder sb = new StringBuilder();
        
        // query prefix
        sb.append(SPARQL_QUERY_PREFIX);
        
        // query select clause
        sb.append(SPARQL_QUERY_SELECT_START);
        
        if (this.count)
        {
            sb.append(constructSPARQLCountWrapper(var_name_player));
        }
        else
        {
            sb.append((this.distinct) ? SPARQL_QUERY_SELECT_DISTINCT : "")
                .append(" ?")
                .append(var_name_player);
        }
            
        sb.append(SPARQL_QUERY_NEWLINE)
            .append(SPARQL_QUERY_WHERE_START);
        
        // query result is a game
        sb.append(this.constructSPARQLQueryGamePart());
        // players
        sb.append(this.constructSPARQLQueryGamePlayerPart());
        
        // query end
        sb.append(SPARQL_QUERY_WHERE_END);
        
        // limit results if not counting and not unlimited
        if ((! this.count) && (this.limit > 0))
        {
            sb.append(SPARQL_QUERY_NEWLINE)
                .append(SPARQL_QUERY_LIMIT)
                .append(this.limit);
        }
        
        return sb.toString();
    }
    
    /**
     * Creates a SPARQL query string with the values from the web interface.
     * 
     * @return  SPARQL-Query String
     */
    public String getSPARQLQuery()
    {
        if (FIELD_VALUE_RESULTTYPE_PLAYER1.equalsIgnoreCase(this.fields.get(FIELD_KEY_RESULTTYPE)))
        {
            return constructSPARQLQueryPlayerIRI(SPARQL_QUERY_SELECT_PLAYER1_VAR);
        }
        else if (FIELD_VALUE_RESULTTYPE_PLAYER2.equalsIgnoreCase(this.fields.get(FIELD_KEY_RESULTTYPE)))
        {
            return constructSPARQLQueryPlayerIRI(SPARQL_QUERY_SELECT_PLAYER2_VAR);
        }
        else
        {
            this.setField(FIELD_KEY_RESULTTYPE, FIELD_VALUE_RESULTTYPE_GAME);
            return this.constructSPARQLQueryGameIRI();
        }
    }
    /**
     * Creates a SPARQL COUNT query string with the values from the web
     * interface.<br />Can be used to count the result before displaying all.
     * 
     * @return  SPARQL-Query String with COUNT (...)
     */
    public String getSPARQLCountQuery()
    {
        // temporary set count on
        boolean countOn = this.count;
        this.count = true;
        
        // get query with default mode
        String query = this.getSPARQLQuery();
        
        // set to previous value
        this.count = countOn;
        
        return query;
    }
}
