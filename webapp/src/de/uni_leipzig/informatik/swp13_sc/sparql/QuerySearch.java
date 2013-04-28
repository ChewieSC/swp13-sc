package de.uni_leipzig.informatik.swp13_sc.sparql;

import virtuoso.jena.driver.*;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.RDFNode;

import com.hp.hpl.jena.query.*;
import com.hp.hpl.jena.rdf.model.RDFNode;



/**
 * Fuehrt eine sparql-Anfrage an Virtuoso aus und gibt die Ergebnissmenge zurück
 * @author LingLong
 *
 */
public class QuerySearch {
	
	private String sparqlQuery;	

	private String url = "jdbc:virtuoso://localhost:1111";	
	
	private VirtGraph set;
	
	/**
	 * Ein neues Objekt zur sparql-Querysuche wird erstellt
	 */
	public QuerySearch(String sparqlQuery)
	{
		this.sparqlQuery = sparqlQuery;
		
		System.out.println(sparqlQuery);//nur zum testen
		
		try{
		    set = new VirtGraph ("penis",url, "dba", "dba");
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		System.out.println("test");
		
		System.out.println(set.getGraphUrl());//test nach url
		
		Query query = QueryFactory.create(sparqlQuery);

		/*			STEP 4			*/
		VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create (query, set);

		System.out.println("bis dahin gehts"); //test weiter
		
				ResultSet results = vqe.execSelect();
				while (results.hasNext()) {
					QuerySolution result = results.nextSolution();
				    RDFNode graph = result.get("graph");
				    RDFNode s = result.get("s");
				    RDFNode p = result.get("p");
				    RDFNode o = result.get("o");
				    System.out.println(graph + " { " + s + " " + p + " " + o + " . }");
				}
	
	}

}
