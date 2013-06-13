package de.uni_leipzig.informatik.swp13_sc.sparql;

import virtuoso.jena.driver.VirtGraph;
import virtuoso.jena.driver.VirtuosoQueryExecution;
import virtuoso.jena.driver.VirtuosoQueryExecutionFactory;

import com.hp.hpl.jena.query.ResultSet;

import de.uni_leipzig.informatik.swp13_sc.util.Configuration;

/**
 * Fuehrt eine sparql-Anfrage an Virtuoso aus und gibt die Ergebnissmenge zurueck
 * @author LingLong
 *
 */
public class QuerySearch {

	private String sparqlQuery;	

	private VirtGraph virtuosoGraph;
	
	private ResultSet results = null;
	
	private int numberOfResults=0;

	/**
	 * Ein neues Objekt zur sparql-Querysuche wird erstellt
	 */
	public QuerySearch(String sparqlQuery)
	{
		this.sparqlQuery = sparqlQuery;
		
		Configuration c = Configuration.getInstance();
		
//		//TESTING
//		virtuosoGraph = new VirtGraph ("millionbase",
//        "jdbc:virtuoso://pcai042.informatik.uni-leipzig.de:1357",
//        "dba", "dba");
		virtuosoGraph = new VirtGraph (c.getVirtuosoBasegraph(),
		        "jdbc:virtuoso://" + c.getVirtuosoHostname(),
		        c.getVirtuosoUsername(), c.getVirtuosoPassword());
		virtuosoGraph.setQueryTimeout(15); // 15 s

		System.out.println(virtuosoGraph.getGraphUrl());
	}
	
	
	public boolean sendQuery()
	{
		try
		{
//			numberOfResults=0;
			VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create (this.sparqlQuery, virtuosoGraph);
		    results = vqe.execSelect();
//		    for ( ; results.hasNext() ; ){
//				numberOfResults++;
//			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public ResultSet getResultSet()
	{
		if (results == null)
		{
			if (! sendQuery())
			{
				System.out.println("<No Results>");
				return null;
			}
		}
		return results;
	}
	
	public int getNumberOfResults(){
		return numberOfResults;
	}
}