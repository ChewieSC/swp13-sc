package de.uni_leipzig.informatik.swp13_sc.ui;

import java.util.List;

import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.vaadin.ui.Table;

public class ResultTable extends Table
{
	private static final long serialVersionUID = 1L;

	public ResultTable(List<String> resultList)
	{
		this.addContainerProperty("Game URIs", String.class, null);
		
		for (int i = 0; i < resultList.size(); i ++)
		{
			this.addItem(new Object[] { resultList.get(i) }, new Integer(i));
		}
	}

	public ResultTable(ResultSet resultSet)
	{
	    if (resultSet == null)
	    {
	        this.addContainerProperty("Result", String.class, null);
	        this.addItem(new Object[] {"No results available!"}, new Long(1));
	        return;
	    }
	    
		@SuppressWarnings("unchecked")
		List<String> resultVars = resultSet.getResultVars();
		Object resultObject[] = new Object[resultVars.size()];
		
		for (int i = 0; i < resultVars.size(); i++)
		{
			this.addContainerProperty(resultVars.get(i), String.class, null);
		}
		
		long nr = 0;
		while (resultSet.hasNext())
		{
			QuerySolution result = resultSet.nextSolution();
			
			for (int i = 0; i < resultVars.size(); i++)
			{
				resultObject[i] = result.get(resultVars.get(i)).toString();
			}

			this.addItem(resultObject, new Long(nr ++));
		}
	}
}
