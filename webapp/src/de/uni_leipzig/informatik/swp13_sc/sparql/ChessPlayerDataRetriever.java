/**
 * ChessPlayerDataRetriever.java
 */
package de.uni_leipzig.informatik.swp13_sc.sparql;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Literal;

import de.uni_leipzig.informatik.swp13_sc.datamodel.ChessPlayer;
import de.uni_leipzig.informatik.swp13_sc.datamodel.rdf.ChessRDFVocabulary;
import virtuoso.jena.driver.VirtGraph;
import virtuoso.jena.driver.VirtuosoQueryExecution;
import virtuoso.jena.driver.VirtuosoQueryExecutionFactory;

/**
 * A retriever class for querying a triplestore over a VirtGraph (only
 * Virtuoso therefore). It returns for given URI/IRIs the first found
 * resource and wraps it into a {@link ChessPlayer} object.
 *
 * @author Erik
 *
 */
public class ChessPlayerDataRetriever
{
    /**
     * The virtuosoGraph (Virtuoso triplestore) which will be queried.
     */
    private final VirtGraph virtuosoGraph;
    
    /**
     * Internal. VARIABLE_NAME for the sparql queries generated.
     */
    private final static String VARIABLE_NAME = "?name";
    
    /**
     * Constructor. Requires the connection to the datastore (Virtuoso)
     * 
     * @param   virtuosoGraph   the triplestore which will be queried
     * @throws  Exception   if argument is null
     */
    public ChessPlayerDataRetriever(VirtGraph virtuosoGraph)
        throws Exception
    {
        if (virtuosoGraph == null)
        {
            throw new NullPointerException("virtuosoGraph is null");
        }
        
        this.virtuosoGraph = virtuosoGraph;
    }
    
    /**
     * Returns a  {@link ChessPlayer} resource for the given playerURI.
     * 
     * @param   playerURI   a correct chess player URI/IRI to a resource in the
     *                      triplestore
     * @return  a {@link ChessPlayer} resource if successful, null if wrong
     *          arguments or an error occurred
     */
    public ChessPlayer getPlayer(String playerURI)
    {
        if (playerURI == null)
        {
            return null;
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ")
            .append(VARIABLE_NAME)
            .append("\nWHERE\n{\n  <")
            .append(playerURI)
            .append("> <")
            .append(ChessRDFVocabulary.name.toString())
            .append("> ?name.\n}\nLIMIT 1");
        
        // DEBUG:
        System.out.println(sb.toString());
        
        VirtuosoQueryExecution vqeS = VirtuosoQueryExecutionFactory.create(sb.toString(), this.virtuosoGraph);
        
        try
        {
            ResultSet results = vqeS.execSelect();
            
            if (results.hasNext())
            {
                QuerySolution result = (QuerySolution) results.next();
                Literal name = result.getLiteral(VARIABLE_NAME);
                
                // create ChessPlayer data and set name.
                // return object
                return new ChessPlayer.Builder().setName(name.getString()).build();
            }
            // if no result returned.
            else
            {
                return null;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Returns a List with {@link ChessPlayer} objects for each URI/IRI found
     * in the parameter playerURIs.
     * 
     * @param   playerURIs  a List of Strings with the URI/IRIs of the chess
     *                      player resources which are to be queried
     * @return  List<{@link ChessPlayer}> with all the found resources - it
     *          won't be shown which URI/IRIs couldn't return data.
     */
    public List<ChessPlayer> getPlayers(List<String> playerURIs)
    {
        // if parameter null than return empty list
        if (playerURIs == null)
        {
            return new ArrayList<ChessPlayer>();
        }
        
        ArrayList<ChessPlayer> list = new ArrayList<ChessPlayer>();
        
        for (String playerURI : playerURIs)
        {
            ChessPlayer cp = this.getPlayer(playerURI);
            if (cp != null)
            {
                list.add(cp);
            }
        }
        
        return list;
    }
    
    /**
     * Returns a Map with {@link ChessPlayer} objects for each URI/IRI found
     * in the parameter playerURIs. All URI/IRIs will be present. Empty entries
     * will be null if no resource data could be found. Beware that double 
     * entries (e. b. the same URI twice) will be present as a single one.
     * 
     * @param   playerURIs  a List of Strings with the URI/IRIs of the chess
     *                      player resources which are to be queried
     * @return  Map<String, {@link ChessPlayer}> with all the found resources -
     *          resources which returned no data will be null.
     */
    public Map<String, ChessPlayer> getPlayersMap(List<String> playerURIs)
    {
        // if parameter null than return empty map
        if (playerURIs == null)
        {
            return new HashMap<String, ChessPlayer>();
        }
        
        HashMap<String, ChessPlayer> map = new HashMap<String, ChessPlayer>();
        
        for (String playerURI : playerURIs)
        {
            map.put(playerURI, this.getPlayer(playerURI));
        }
        
        return map;
    }
}
