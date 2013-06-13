package de.uni_leipzig.informatik.swp13_sc.ui;

import java.util.List;

import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Link;
import com.vaadin.ui.Table;
import com.vaadin.ui.Button.*;

public class ResultTable extends Table
{
	private static final long serialVersionUID = 1L;
	private long nr = 0;

	public ResultTable(final List<String> resultList,final Swp13scUI ui)
	{
		this.addContainerProperty("URI", Button.class, null);
		String uri;
		Button button;
		for (int i = 0; i < resultList.size(); i ++)
		{
			uri = resultList.get(i);
			
			button = generateButton(uri, ui);
			this.addItem(new Object[] {button}, new Integer(i));
		}
	}
	public ResultTable(final List<String> resultList)
	{
		this.addContainerProperty("URI", String.class, null);
		String uri;
		for (int i = 0; i < resultList.size(); i ++)
		{
			uri = resultList.get(i);			
			this.addItem(new Object[] {uri}, new Integer(i));
		}
	}	
	
	final Button generateButton(final String uri, final Swp13scUI ui)
	{
		Button button = new Button(uri);
		button.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				ui.initChessEngine(uri);
				
			}
		});
		return button;
		
	}
	
	
	public ResultTable(ResultSet resultSet)
	{
		setSelectable(true);
		List<String> resultVars = resultSet.getResultVars();
		Object resultObject[] = new Object[resultVars.size()-1];
		
		for (int i = 0; i < resultVars.size()-1; i++)
		{
			this.addContainerProperty(resultVars.get(i), String.class, null);
		}
		
		nr = 0;
		while (resultSet.hasNext())
		{
			QuerySolution result = resultSet.nextSolution();
			
			for (int i = 0; i < resultVars.size()-1; i++)
			{
				resultObject[i] = result.get(resultVars.get(i)).toString();
			}

			this.addItem(resultObject, new Long(nr++));
		}
	}
	
	public Long getNr(){
		return nr;
	}
}
