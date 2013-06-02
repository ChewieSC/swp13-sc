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
import com.hp.hpl.jena.rdf.model.Statement;

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
    // some constants
    // sparql prefix/start
    
    // move compare ...
    
    protected VirtGraph virtuosoGraph;
    protected Map<String, List<String>> ecoGameMapping;
    private boolean hasMappings;
    protected ChessOpeningDataRetriever codr;
    
    // ------------------------------------------------------------------------
    
    protected final static String SPARQL_ECOS_VAR = "?ECO_URI";
    protected final static String SPARQL_ECOS_GRAPH = "eco";
    protected final static String SPARQL_ECOS_FROM = "FROM <" + SPARQL_ECOS_GRAPH + ">\n";
    protected final static String SPARQL_ECOS = "SELECT DISTINCT "
            + SPARQL_ECOS_VAR + "\n" + SPARQL_ECOS_FROM + "WHERE\n{\n  "
            + SPARQL_ECOS_VAR + " a cont:ChessOpening .\n}\nORDER BY " + SPARQL_ECOS_VAR;
    
    // ------------------------------------------------------------------------
    
    protected final static String SPARQL_GAME_VAR = "?game";
    protected final static String SPARQL_GAME_MOVE_VAR_PREFIX = "?move_";
    
    // ------------------------------------------------------------------------
    
    public ECOLinker(VirtGraph virtuosoGraph)
    {
        this.virtuosoGraph = virtuosoGraph;
        //this.virtuosoGraph.setQueryTimeout(300); // in sec
        this.virtuosoGraph.setQueryTimeout(15);
        
        this.ecoGameMapping = new HashMap<String, List<String>>();
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
    
    public List<String> getGamesForOpenings(ChessOpening co)
    {
        if (co == null)
        {
            return new ArrayList<String>();
        }
        
        //construct query
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT DISTINCT ")
            .append(SPARQL_GAME_VAR)
            .append("\nWHERE\n{\n  ")
            .append(SPARQL_GAME_VAR)
            .append(" a ")
            .append(ChessRDFVocabulary.getOntologyPrefixName())
            .append(":")
            .append(ChessRDFVocabulary.ChessGame.getLocalName())
            .append(" .\n");
        
        // more efficient to move later moves to the front and common to the
        // back, reduce the result set early (?)
        for (int nr = co.getMoves().size(); nr > 0; nr --)
        {
            String moveVar = SPARQL_GAME_MOVE_VAR_PREFIX + nr;
            sb.append("  ")
                .append(SPARQL_GAME_VAR)
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
                .append("\" .\n");
            
            // FEN ?
        }
        
        sb.append("}");
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
    
    public Map<String, List<String>> getMappings()
    {
        if ((this.hasMappings) && (this.ecoGameMapping != null)
                && (this.ecoGameMapping.size() > 0))
        {
            return this.ecoGameMapping;
        }
        
        if (this.codr == null)
        {
            return null;
        }
        
        // get URIs of openings / ECOs
        List<String> uris = this.getOpeningURIs();
        if ((uris == null) || (uris.size() == 0))
        {
            return null;
        }
        
        // get Openings / ECOs objects
        for (String uri : uris)
        {
            ChessOpening co = this.codr.getOpening(uri);
            
            long start = System.currentTimeMillis();
            List<String> games = getGamesForOpenings(co);
            // %f --> #.######
            // %s -- Float.toString(((System.currentTimeMillis() - startFile) / 1000.0f))
            System.out.format("Took %s sec. for %d mappings with %s.%n",
                    Float.toString(((System.currentTimeMillis() - start) / 1000.f)),
                    games.size(), co);
            
            this.ecoGameMapping.put(uri, games);
        }
        
        this.hasMappings = true;
        
        return this.ecoGameMapping;
    }
    
    public Model createMappingModel()
    {
        this.getMappings();
        
        Model m = ModelFactory.createDefaultModel();
        
        for (String eco : this.ecoGameMapping.keySet())
        {
            Resource r_eco = m.createResource(eco, ChessRDFVocabulary.ChessOpening);
            
            for (String game : this.ecoGameMapping.get(eco))
            {
                Resource r_game = m.createResource(game, ChessRDFVocabulary.ChessGame);
                
                Statement stmt = m.createStatement(r_game, ChessRDFVocabulary.eco, r_eco);
                System.out.println(stmt);
                m.add(stmt);
                
                //m.add(r_game, ChessRDFVocabulary.eco, r_eco);
            }
        }
        
        
        return m;
    }
    
    public boolean writeMappingModel(String file)
    {
        Model m = this.createMappingModel();
        
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
     * @param args
     */
    public static void main(String[] args)
    {
        ECOLinker ecol = new ECOLinker(new VirtGraph("millionbase",
                "jdbc:virtuoso://pcai042.informatik.uni-leipzig.de:1357",
                "dba", "dba"));
        ecol.writeMappingModel("Mapping_ECO_GAME.ttl");
        
    }
}
