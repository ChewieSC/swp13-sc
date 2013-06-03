/**
 * ECOLinker.java
 */

package de.uni_leipzig.informatik.swp13_sc.linking;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import virtuoso.jena.driver.VirtGraph;
import virtuoso.jena.driver.VirtuosoQueryExecution;
import virtuoso.jena.driver.VirtuosoQueryExecutionFactory;

import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;

import de.uni_leipzig.informatik.swp13_sc.converter.ChessDataModelToRDFConverter.OutputFormats;
import de.uni_leipzig.informatik.swp13_sc.datamodel.ChessOpening;
import de.uni_leipzig.informatik.swp13_sc.datamodel.rdf.ChessRDFVocabulary;
import de.uni_leipzig.informatik.swp13_sc.sparql.ChessOpeningDataRetriever;
import de.uni_leipzig.informatik.swp13_sc.util.FileUtils;

/**
 * Links ECO resources to ChessGame resources.
 * 
 * @author Erik
 */
public class ECOLinker
{
    
    protected VirtGraph virtuosoGraph;
    protected Map<String, List<String>> ecoGameMapping;
    private boolean hasMappings;
    protected ChessOpeningDataRetriever codr;
    
    public final static int QUERY_TIMEOUT = 90;
    
    // ------------------------------------------------------------------------
    
    protected final static String SPARQL_ECOS_VAR = "?ECO_URI";
    protected final static String SPARQL_ECOS_GRAPH = "eco";
    protected final static String SPARQL_ECOS_FROM = ""; //"FROM <" + SPARQL_ECOS_GRAPH + ">\n";
    protected final static String SPARQL_ECOS = "SELECT DISTINCT "
            + SPARQL_ECOS_VAR + "\n" + SPARQL_ECOS_FROM + "WHERE\n{\n  "
            + SPARQL_ECOS_VAR + " a cont:ChessOpening .\n}\nORDER BY " + SPARQL_ECOS_VAR;
    
    // ------------------------------------------------------------------------
    
    protected final static String SPARQL_GAME_VAR = "?game";
    protected final static String SPARQL_GAME_MOVE_VAR_PREFIX = "?move_";
    
    // ------------------------------------------------------------------------
    
    /**
     * Creates a new ECOLinker object. Requires a VirtGraph to query a Virtuoso
     * triple store. Will set its query time out to 90 seconds to allow
     * efficient work.
     * 
     * @param   virtuosoGraph   Graph (connection) to triple store
     */
    public ECOLinker(VirtGraph virtuosoGraph)
    {
        this(virtuosoGraph, QUERY_TIMEOUT);
    }
    
    /**
     * Creates a new ECOLinker object. Takes a new VirtGraph connection to a
     * Virtuoso triple store and sets it to the specified timeout. Creates a
     * new ChessOpeningDataRetriever with the same connection.
     * 
     * @param   virtuosoGraph   connection to Virtuoso triple store
     * @param   timeout     timeout in seconds
     */
    public ECOLinker(VirtGraph virtuosoGraph, int timeout)
    {
        this.virtuosoGraph = virtuosoGraph;
        this.virtuosoGraph.setQueryTimeout(timeout); // sec.
        
        this.hasMappings = false;
        
        try
        {
            this.codr = new ChessOpeningDataRetriever(this.virtuosoGraph);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    // ------------------------------------------------------------------------
    
    /**
     * Queries the triple store the retrieve all the opening (ECO) URIs.
     * 
     * @return  List<String> with chess opening (ECO) URIs or null on error
     */
    public List<String> getOpeningURIs()
    {
        List<String> list = new ArrayList<String>();

        System.out.println("Get Openings: " + SPARQL_ECOS);
        VirtuosoQueryExecution vqeS = VirtuosoQueryExecutionFactory.create(
                SPARQL_ECOS, this.virtuosoGraph);

        try
        {
            ResultSet results = vqeS.execSelect();

            while (results.hasNext())
            {
                QuerySolution result = (QuerySolution) results.next();

                RDFNode n = result.get(SPARQL_ECOS_VAR);
                list.add(n.toString());
            }

            vqeS.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }

        return list;
    }
    
    /**
     * Will query the triple store for games with equal opening moves like
     * those stored in the ChessOpening object.
     * 
     * @param   co  ChessOpening object (contains the opening moves)
     * @return  List<String> with mapped games for those opening moves
     */
    public List<String> getGamesForOpenings(ChessOpening co)
    {
        if ((co == null) || (co.getMoves() == null) || (co.getMoves().isEmpty()))
        {
            return new ArrayList<String>();
        }
        
        //construct query
        StringBuilder sb = new StringBuilder();
        
        // more efficient to move later moves to the front and common to the
        // back, reduce the result set early (?)
        for (int nr = 1; nr <= co.getMoves().size(); nr ++)
        //for (int nr = co.getMoves().size(); nr > 0; nr --)
        {
            String temp = sb.toString();
            
            sb = new StringBuilder();
            String moveVar = SPARQL_GAME_MOVE_VAR_PREFIX + nr;
            sb.append("SELECT ")
                .append(SPARQL_GAME_VAR)
                .append(" WHERE\n{\n  ");
            if (temp.length() > 0)
            {
                sb.append("{\n")
                .append(temp)
                .append("\n  }\n  ");
            }
            if (nr == 1)
            {
                sb.append(SPARQL_GAME_VAR)
                    .append(" a ")
                    .append(ChessRDFVocabulary.getOntologyPrefixName())
                    .append(":")
                    .append(ChessRDFVocabulary.ChessGame.getLocalName())
                    .append(" .\n  ");
            }
            sb.append(SPARQL_GAME_VAR)
                .append(" ")
                .append(ChessRDFVocabulary.getOntologyPrefixName())
                .append(":")
                .append(ChessRDFVocabulary.moves.getLocalName())
                .append(" ")
                .append(moveVar)
                .append(" .\n  ")
                
                .append(moveVar)
                .append(" a ")
                .append(ChessRDFVocabulary.getOntologyPrefixName())
                .append(":")
                .append(ChessRDFVocabulary.ChessMove.getLocalName())
                .append(" .\n  ")
                
                .append(moveVar)
                .append(" ")
                .append(ChessRDFVocabulary.getOntologyPrefixName())
                .append(":")
                .append(ChessRDFVocabulary.moveNr.getLocalName())
                .append(" ")
                .append(nr)
                .append(" .\n  ")
                
                .append(moveVar)
                .append(" ")
                .append(ChessRDFVocabulary.getOntologyPrefixName())
                .append(":")
                .append(ChessRDFVocabulary.move.getLocalName())
                .append(" \"")
                .append(co.getMoves().get(nr - 1).getMove())
                .append("\" .\n}");
            
            // FEN ?
        }
        
        System.out.println("Get Games: " + sb.toString());
        
        List<String> list = new ArrayList<String>();
        
        VirtuosoQueryExecution vqeS = VirtuosoQueryExecutionFactory.create(sb.toString(), this.virtuosoGraph);
        
        try
        {
            ResultSet results = vqeS.execSelect();
            
            while (results.hasNext())
            {
                QuerySolution result = (QuerySolution) results.next();

                RDFNode n = result.get(SPARQL_GAME_VAR);
                list.add(n.toString());
            }
            
            vqeS.close();
        }
        catch (Exception e)
        {
            System.out.println(e.getLocalizedMessage());
            e.printStackTrace();
            return new ArrayList<String>();
        }
        
        return list;
    }
    
    /**
     * For the given URIs it will query the VirtGraph to get chess openings
     * (ECOs). It will then take the moves to get chess games with equal
     * openings and returns them for the opening URI.
     * 
     * @param   uris    URIs of chess openings
     * @return  Map with URI of chess opening (ECO) and mapped games (URIs)
     */
    public Map<String, List<String>> getMappings(List<String> uris)
    {   
        if (this.codr == null)
        {
            System.err.println("Has no valid ChessOpeningDataRetriever!");
            return new HashMap<String, List<String>>();
        }
        
        if ((uris == null) || (uris.size() == 0))
        {
            System.err.println("No URIs to get ChessOntologies (ECOs) from!");
            return new HashMap<String, List<String>>();
        }
        
        Map<String, List<String>> mappings = new HashMap<String, List<String>>();
        
        // get Openings / ECOs objects
        for (String uri : uris)
        {
            ChessOpening co = this.codr.getOpening(uri);
            if (co == null)
            {
                System.err.println("<" + uri + "> is NULL ?");
                continue;
            }
            
            long start = System.currentTimeMillis();
            List<String> games = getGamesForOpenings(co);
            // %f --> #.######
            // %s -- Float.toString(((System.currentTimeMillis() - startFile) / 1000.0f))
            System.out.format("Took %s sec. for %d mappings with %s.%n",
                    Float.toString(((System.currentTimeMillis() - start) / 1000.f)),
                    games.size(), co);
            
            mappings.put(uri, games);
        }
        
        return mappings;
    }
    
    // ------------------------------------------------------------------------   
    
    /**
     * Will retrieve all chess openings (ECOs) stored in the triple store and
     * tries to get all chess games to which the opening moves can be mapped.
     * It will store the results in the class.
     * 
     * @return  cached map with all the mapped chess games and their openings
     */
    public Map<String, List<String>> getMappings()
    {
        if ((this.hasMappings) && (this.ecoGameMapping != null)
                && (this.ecoGameMapping.size() > 0))
        {
            return this.ecoGameMapping;
        }
        
        this.ecoGameMapping = new HashMap<String, List<String>>();
        this.ecoGameMapping = this.getMappings(this.getOpeningURIs());
        
        this.hasMappings = true;
        
        // get URIs of openings / ECOs
        return this.ecoGameMapping;
    }
    
    // ------------------------------------------------------------------------
    
    /**
     * Creates a default Model with prefixes for chess rdf data.
     * 
     * @return  a new Model (Jena)
     */
    protected Model createDefaultModel()
    {
        Model m = ModelFactory.createDefaultModel();
        m.setNsPrefix(ChessRDFVocabulary.getOntologyPrefixName(),
                ChessRDFVocabulary.getURI());
        m.setNsPrefix(ChessRDFVocabulary.getResourcePrefixName(),
                ChessRDFVocabulary.getResourceURI());
        return m;
    }
    
    /**
     * Creates a new Mapping-Model and fills it with the given mappings.
     * 
     * @param   mappings    Map with ECO URI and mapped chess game URIs
     * @return  a new Model (Jena) with mappings (it may be empty if the given
     *          mappings are empty!)
     */
    public Model createMappingModel(Map<String, List<String>> mappings)
    {
        Model m = createDefaultModel();
        if (mappings == null)
        {
            System.err.println("No mappings for model!");
            return m;
        }
        
        for (String eco : this.ecoGameMapping.keySet())
        {
            Resource r_eco = m.createResource(eco);
            
            for (String game : this.ecoGameMapping.get(eco))
            {
                Resource r_game = m.createResource(game);
                
                m.add(r_game, ChessRDFVocabulary.eco, r_eco);
            }
        }
        
        return m;
    }
    
    // ------------------------------------------------------------------------
    
    /**
     * Creates a new Mapping-Model, fills it with queried mappings and writes
     * it to the specified file.
     * 
     * @param   file    File to write into (GZIP-Stream)
     * @return  true if successful
     */
    public boolean writeMappingModel(String file)
    {
        Model m = this.createMappingModel(this.getMappings());
        
        try
        {
            OutputStream os = FileUtils.openGZIPOutputStream(file);
            
            long start = System.currentTimeMillis();
            m.write(os, OutputFormats.TURTLE.getFormat());
            System.out.format("Took %s sec. for writing %d mappings.%n",
                    Float.toString(((System.currentTimeMillis() - start) / 1000.f)),
                    m.size());
            
            os.flush();
            os.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
        
        return true;
    }
    
    // ------------------------------------------------------------------------
    
    /**
     * Main method. Start from command line.
     * Will create a default mapping and stores it in the file.
     * 
     * @param   args    <ol>
     *                      <li> graph to query</li>
     *                      <li> link to database</li>
     *                      <li> username</li>
     *                      <li> password</li>
     *                      <li> [outputfile  (default: Mapping_ECO_GAME.ttl)]</li>
     *                  </ol>
     */
    public static void main(String[] args)
    {
        if ((args.length < 4) || (args.length > 6))
        {
            System.exit(1);
        }
        
        String graph = args[0];
        String link = args[1];
        String user = args[2];
        String pass = args[3];
        String file = (args.length == 5) ? args[4] : "Mapping_ECO_GAME.ttl";
        
        ECOLinker ecol = new ECOLinker(new VirtGraph(graph, "jdbc:virtuoso://"
                + link, user, pass));
        ecol.writeMappingModel(file);
    }
}
