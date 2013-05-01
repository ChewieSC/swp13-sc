/**
 * ResourceRetriever.java
 */

package de.uni_leipzig.informatik.swp13_sc.sparql;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import virtuoso.jena.driver.VirtGraph;
import virtuoso.jena.driver.VirtuosoQueryExecution;
import virtuoso.jena.driver.VirtuosoQueryExecutionFactory;

import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.RDFNode;

import de.uni_leipzig.informatik.swp13_sc.datamodel.rdf.ChessRDFVocabulary;

/**
 * <p>
 * Servlet implementation class ResourceRetriever
 * </p>
 * <p>
 * It will retrieve chess URI / IRI resources. Therefore it will query the
 * triplestore Virtuoso with the URI / IRI as subject of each triple. The output
 * is very basic (a simple text string).
 * </p>
 * 
 * @author Erik
 */
public class ResourceRetriever extends HttpServlet
{
    private static final long serialVersionUID = 1L;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ResourceRetriever()
    {
        super();
    }
    
    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        // When overriding this method, read the request data, write the
        // response headers, get the response's writer or output stream object,
        // and finally, write the response data. It's best to include content
        // type and encoding. When using a PrintWriter object to return the
        // response, set the content type before accessing the PrintWriter
        // object.
        
        // for writing the response (very simple)
        PrintWriter pw = response.getWriter();
        
        // get the GET parameters / request variables
        // and contruct the chess resource URI / IRI
        String queryString = request.getQueryString();
        boolean extended = (queryString == null) ? false : queryString.contains("extended");
        String resourceID = request.getPathInfo();
        String resourceURI = ChessRDFVocabulary.getResourceURI() + resourceID.substring(1);
        
        // construct the SPARQL query
        String sparql = new StringBuilder()
                .append("SELECT ")
                .append("*")
                .append("\n")
                .append("WHERE {")
                .append("\n")
                .append("  ")
                .append("GRAPH ?graph {")
                .append("\n")
                .append("    ")
                .append("<")
                .append(resourceURI)
                .append(">")
                .append(" ?p ?o .")
                .append("\n")
                .append((extended) ? "    " : "")
                .append((extended) ? "?s ?p ?o ." : "")
                .append((extended) ? "\n" : "")
                .append("  ")
                .append("}")
                .append("\n")
                .append("}")
                .toString();
        
        // print the complete query
        pw.println("SPARQL-Request:");
        pw.println(sparql);
        pw.println();
        
        // query Virtuoso
        VirtGraph virtuosoGraph = new VirtGraph("jdbc:virtuoso://pcai042.informatik.uni-leipzig.de:1357", "dba", "dba");
        VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create(sparql, virtuosoGraph);
        ResultSet results = vqe.execSelect();
        
        // header of result resource section
        pw.println("Results for <" + resourceURI + ">:");
        pw.print("[ Nr");
        for (Object var : results.getResultVars())
        {
            pw.print(" | ?" + var);
        }
        pw.println(" ] :");
        
        // print all resources
        long i = 0;
        while (results.hasNext())
        {
            QuerySolution result = results.nextSolution();
            
            // the current number
            i++;
            pw.print(i);
            
            // write the value for each variable in the query
            for (Object var : results.getResultVars())
            {
                try
                {
                    RDFNode node = result.get((String) var);
                    pw.print(" | " + node);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    
                    // abort when error
                    // don't resume because there may be more than one and
                    // we don't want to have lots of meaningless output ...
                    pw.close();
                    return;
                }
            }
            
            pw.println();
        }
        
        // number of results
        pw.println();
        pw.println("Number of results: " + i);
        
        // flush and close output
        pw.flush();
        pw.close();
    }
}
