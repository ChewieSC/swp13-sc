package de.uni_leipzig.informatik.swp13_sc.sparql;

import virtuoso.jena.driver.VirtGraph;
import virtuoso.jena.driver.VirtuosoQueryExecution;
import virtuoso.jena.driver.VirtuosoQueryExecutionFactory;

import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.RDFNode;



/**
 * Fuehrt eine sparql-Anfrage an Virtuoso aus und gibt die Ergebnissmenge zurueck
 * @author LingLong
 *
 */
public class QuerySearch {

	private String sparqlQuery;	

	private final static String url = "jdbc:virtuoso://pcai042.informatik.uni-leipzig.de:1357";	

	private VirtGraph virtuosoGraph;
	
	private ResultSet results = null;

	/**
	 * Ein neues Objekt zur sparql-Querysuche wird erstellt
	 */
	public QuerySearch(String sparqlQuery)
	{
		this.sparqlQuery = sparqlQuery;

		System.out.println(sparqlQuery); // nur zum testen
		
		virtuosoGraph = new VirtGraph ("http://pcai042.informatik.uni-leipzig:1357.de/millionbase", url, "pgn", "pgn");	

		System.out.println(virtuosoGraph.getGraphUrl());

		VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create (this.sparqlQuery, virtuosoGraph);

	    results = vqe.execSelect();
		System.out.println("Results:");
		long i = 0;
		while (results.hasNext()) {
		    System.out.print(i);
			QuerySolution result = results.nextSolution();
			for (Object var : results.getResultVars()) {
			    try {
			        RDFNode node = result.get((String) var);
			        System.out.print(" | " + node);
			    } catch (Exception e) {

			    }
			}
		    System.out.println();
		}
	}
	
//	
//	public ResultSet getResultSset()
//	{
//		if(results == null)
//		{
//			System.out.println("<No Results>");
//			return null;
//		}
//		else{
//		return results;
//		   }
//	}

}