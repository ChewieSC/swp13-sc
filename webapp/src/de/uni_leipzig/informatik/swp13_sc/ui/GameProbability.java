package de.uni_leipzig.informatik.swp13_sc.ui;

import java.util.List;
import com.vaadin.ui.Table.CellStyleGenerator;
import com.vaadin.ui.Table;
/**
*
*
*/
public class GameProbability extends Table implements CellStyleGenerator
{

/**
*
*
*/

	private double whiteProb = 0;
/**
*
*
*/

	private double drawProb = 0;
/**
*
*
*/

	private double blackProb = 0;
/**
*
*
*/

	private int whiteWidth;
/**
*
*
*/

	private int drawWidth;
/**
*
*
*/

	private int blackWidth;


/**
*
*
*/

	private int tableWidth;
	

/**
*
*
*/	

	public GameProbability(int tableWidth)
	{
		super();
		this.addStyleName("checkerboard");
		this.tableWidth = tableWidth;		
		this.addContainerProperty("White wins", Double.class,  null);   //
		this.addContainerProperty("Draw",  Double.class,  null);	// set 3 tablecolumns
		this.addContainerProperty("Black wins", Double.class, null);	//

		this.setSelectable(false);			//
        this.setMultiSelect(false);			//set some table-attributes
        this.setWidth(String.valueOf(tableWidth)+"px");

        //Zellinhalt
		this.addItem(new Object[] {whiteProb,drawProb,blackProb}, new Integer(1)); //make a row , which shows the probabilities
		this.setVisible(false);
		
		System.out.println("GameProb initialized");
		
		
	}

/**
*
*
*/

	public double getWhiteProb()
	{
		return whiteProb;
	}

/**
*
*
*/

	public double getDrawProb()
	{
		return drawProb;
	}

	/**
	*
	*
	*/
	public double getBlackProb()
	{
		return blackProb;
	}

	/**
	*
	*
	*/
	public void changeVisability()
	{
		if(this.isVisible() == false) {
		this.setVisible(true);
		}
		else {
		this.setVisible(false);
		}
	}

	
	
	/**
	*
	*
	*/
	public void showProb(double whiteProb,double drawProb,double blackProb)
	{
	this.whiteProb = whiteProb;
	this.drawProb = drawProb;
	this.blackProb = blackProb;
	whiteWidth = (int)(tableWidth * whiteProb);    //
	drawWidth = (int)(tableWidth * drawProb);	//the column-width according to the Probability and the Width of the table
	blackWidth = (int)(tableWidth * blackProb);	//


	this.setColumnWidth("White wins",  whiteWidth);
	this.setColumnWidth("Draw",  drawWidth);
	this.setColumnWidth("Black wins",  blackWidth);
	
	


	// to do farbe in die zelen einfügen ....
	//Versuch
		

	
	}

	public String getStyle(Object itemId, Object propertyId) 
	{
		if(propertyId.equals("White wins"))
			return "white";
		
		if(propertyId.equals("Draw"))
			return "gray";
		
		if(propertyId.equals("Black wins"))
			return "black";
		else
			return "green";
	}

	@Override
	public String getStyle(Table source, Object itemId, Object propertyId) {
		// TODO Auto-generated method stub
		return null;
	}
	

/*css code, siehe auch book of vaadin 5.15 ganz unten
 	
		.v-table-checkerboard .v-table-cell-content 
		{
			text-align: center;
			vertical-align: middle;

		}
		
		
		.v-table-cell-content-white 
		{
			background: white;
			color: black;
		}
	
		.v-table-cell-content-draw
		{
			background: gray;
			color: black;
		}
	
		.v-table-cell-content-black 
		{
			background: black;
			color: white;
		}
 */


}





//table.addContainerProperty("0", String.class, null, "", null, null);

//Set cell style generator


