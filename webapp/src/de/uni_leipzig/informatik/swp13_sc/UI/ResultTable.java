package de.uni_leipzig.informatik.swp13_sc.ui;

import java.util.List;

import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.vaadin.ui.Table;

public class ResultTable extends Table {
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public ResultTable(List<String> resultList)
	 { 
	 this.addContainerProperty("URI", String.class, null);
	 int i;
	 for (i = 0; i<resultList.size();i++)
	 { 
	 this.addItem(new Object[]{resultList.get(i)},new Integer(i));
	 }
	 //table.addItem(new Object[]{"ENDE"},new Integer(i));
	 }
	
	
	
	public ResultTable(ResultSet resultSet)
	 { 
	 List<String> resultVars = resultSet.getResultVars();
	 Object resultObject[] = new Object[resultVars.size()]; 
	 while (resultSet.hasNext()) {
	 QuerySolution result = resultSet.nextSolution();
	 for(int i=0;i< resultVars.size();i++)
	 {
	 resultObject[i] = resultVars.get(i);
	 }
	 this.addItem(resultObject, new Integer(1));
	 }
	 }
	}
