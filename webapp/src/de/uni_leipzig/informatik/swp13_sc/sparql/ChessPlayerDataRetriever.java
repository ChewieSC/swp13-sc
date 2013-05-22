/**
 * ChessPlayerDataRetriever.java
 */
package de.uni_leipzig.informatik.swp13_sc.sparql;

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
import com.hp.hpl.jena.rdf.model.Resource;

import de.uni_leipzig.informatik.swp13_sc.datamodel.ChessPlayer;
import de.uni_leipzig.informatik.swp13_sc.datamodel.rdf.ChessRDFVocabulary;

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
     * Determines if the given URI/IRI is a chess player resource.
     * 
     * @param   playerURI URI/IRI to check
     * @return  true if chess player resource else false
     */
    public boolean isPlayer(String playerURI)
    {
        if (playerURI == null)
        {
            return false;
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT *\nWHERE\n{\n  <")
            .append(playerURI)
            .append("> a <")
            .append(ChessRDFVocabulary.ChessPlayer)
            .append(">.\n}");
        
        // DEBUG:
        //System.out.println(sb.toString());
        
        VirtuosoQueryExecution vqeS = VirtuosoQueryExecutionFactory.create(sb.toString(), this.virtuosoGraph);
        
        boolean ret = false;
        
        try
        {
            ResultSet results = vqeS.execSelect();
            
            if (results.hasNext())
            {
                ret = true;
            }
            
            vqeS.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
        
        return ret;
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
        sb.append("SELECT DISTINCT ?prop ?value\nWHERE\n{  <")
        .append(playerURI)
        .append("> ?prop ?value.\n}");
        
        // DEBUG:
        //System.out.println(sb.toString());
        
        VirtuosoQueryExecution vqeS = VirtuosoQueryExecutionFactory.create(sb.toString(), this.virtuosoGraph);
        
        try
        {
            ResultSet results = vqeS.execSelect();
            
            ChessPlayer.Builder cpb = new ChessPlayer.Builder();
            
            while (results.hasNext())
            {
                QuerySolution result = (QuerySolution) results.next();
                
                if (result.get("value").isLiteral()) 
                {
                    Resource l_prop = result.getResource("prop");
                    String prop = l_prop.getLocalName();

                    Literal l_value = result.getLiteral("value");
                    String value = l_value.toString();
                    
                    if (ChessRDFVocabulary.name.getLocalName().equalsIgnoreCase(prop))
                    {
                        cpb.setName(value);
                    }
                    // add rest to meta data
                    else
                    {
                        cpb.addMetaData(prop, value);
                    }
                    
                    // DEBUG:
                    //System.out.println("Prop: " + prop + ", Value: " + value);
                }
            }

            vqeS.close();
            
            return cpb.build();
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
