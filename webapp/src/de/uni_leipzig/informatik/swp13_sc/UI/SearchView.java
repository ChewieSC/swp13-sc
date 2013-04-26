package de.uni_leipzig.informatik.swp13_sc;

import com.vaadin.data.Container;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.ui.Button;
import com.vaadin.ui.AbstractTextField.TextChangeEventMode;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

/**
 * Kapselt das Layout und die Funktionalitäten des Suchfensters und dessen Komponenten
 * @author LingLong
 *
 */
public class SearchView extends VerticalLayout {

	//---------------Suchfenster Instanzen-----------------//
	
	/**Inneres Layout fuer Suchfenster*/
	private HorizontalLayout searchLayoutInner;

	/**Label mit Ueberschrift */
	private Label lblSearch;

    /**Button fur Suchfunktion*/    
    private Button btnSearch;
    
    /**Button für Erweiterte Suche*/
	private Button btnEx;
	
	/**Button zum staten der Qureysuche */
	private Button btnQuerySearch;
	
    /**neues Suchfeld*/
	private TextField tfSearch ;
	
	
	
	//--------------Instanzen des erwiterten Suchfensters------------//
	
	/**inneres Layout*/
	HorizontalLayout exSearchInner;
	
	/**InfoLabel erweiterte Suche*/
	 Label lblExSearch ;
		
	/**Button fuer aufruf der erweiterten Suche*/
	 Button btnExSearch ;
	 
	 /**Back*/
	 Button btnEndExSearch;
		
	/**Layout für erweiterte Suche	 */
     FormLayout exSearchLayout ;
		
	/**Suchfelder für erweiterte Suche	 */
	 FieldGroup exSearchFields ;
	
	/**Beschreibungen der einzelnen Suchfelder*/
	private static final String[] searchFieldNames = new String[] {
		"Name","Vorname", "Elo-Rang", "Jahr", "Test", "Dings", "Bums"
		};
	//---------------------Instanzen Querysuche-----------------------//	
	
	private HorizontalLayout qLayoutInner;
	
	private Label lblQSearch ;
	
	private Button btnQSearch ;	
			
	/**Textfeld zum einfügen bzw. schreiben von Queries*/
	private TextArea taQuery = new TextArea();
	
	private Button btnEndQ ;
	
	//-----------------------------------------------------//
	
    /**
     * extended search abfrage ja/nein */
	boolean exSearch = false;
	
	
	
	//Konstruktor
	/**
	 * Hier wird ein Objekt erzeugt welches das Layout des Suchefensters und seine Funktion aufbaut	 */
	public SearchView()
	{				
		searchLayoutInner = new HorizontalLayout();
		searchLayoutInner.setSizeFull();
	    searchLayoutInner.setWidth("100%");
	
	  
		
		lblSearch = new Label("Suche nach Schachpartien");		
		addComponent(lblSearch);
		
		btnSearch = new Button("Suche");
		btnEx = new Button("Erweierte Suche");
		btnQuerySearch = new Button("QuerySuche");
		
		tfSearch = new TextField();		
		tfSearch.setInputPrompt("Suchbegriff");
	    tfSearch.setTextChangeEventMode(TextChangeEventMode.LAZY);   	      
	   
	             
	      tfSearch.setWidth("100%");
	      //setExpandRatio(tfSearch, 1);
             
	      searchLayoutInner.addComponent(tfSearch);
	      searchLayoutInner.addComponent(btnSearch);
	      searchLayoutInner.addComponent(btnEx);
	      searchLayoutInner.addComponent(btnQuerySearch);
	      
	    addComponent(searchLayoutInner);
	}
	
	
	
	/** Baut View fuer die erweiterte Suche auf*/
	public void initExSearch()
	{
	 exSearchInner = new HorizontalLayout();
	 exSearchInner.setSizeFull();
	 exSearchInner.setWidth("100%");
		
	 lblExSearch = new Label("Suchmaske für Erweiterte Suche");
					
	 btnExSearch = new Button("Suchen");
	 btnEndExSearch = new Button("Zurück");
	 
	  btnEndExSearch.addListener(new ClickListener()
	   {
	    	@Override
		   public void buttonClick(ClickEvent event)
	   	  {   		
	        removeComponent(lblExSearch);
	        removeComponent( exSearchInner);
	        removeComponent( exSearchLayout);
	    	btnSearch.setEnabled(true);
		
		  }
			
	   });
	 
	 exSearchInner.addComponent(btnExSearch);
	 exSearchInner.addComponent(btnEndExSearch);
					
	
	 exSearchLayout = new FormLayout();					
	 exSearchFields = new FieldGroup();	 

     for (String fieldName : searchFieldNames)
      {
                  TextField field = new TextField(fieldName);
                  exSearchLayout.addComponent(field);
                  field.setWidth("100%");

                  exSearchFields.bind(field, fieldName);
      }
         exSearchLayout.addComponent(btnExSearch );
         exSearchFields.setBuffered(false);
				 
				 addComponent(lblExSearch);			 
				 addComponent(exSearchLayout);
				 addComponent(exSearchInner);	
			
	}
	
	
	
	/**Baut View fuer die Querysuche auf*/
	public void initQSearch()
	{
		qLayoutInner = new HorizontalLayout();
		qLayoutInner.setSizeFull();
		qLayoutInner.setWidth("100%");
		
		lblQSearch = new Label("sparql Abfrage für Fortgeschrittene Benutzer");
		
		
		taQuery = new TextArea();
		taQuery.setWidth("100%");
		
		btnEndQ = new Button("Zurück");
		
		btnEndQ.addListener(new ClickListener()
		   {
		    	@Override
			   public void buttonClick(ClickEvent event)
		   	  {   		
		    	removeComponent(lblQSearch);
		    	removeComponent(taQuery);
		    	removeComponent(qLayoutInner);
		    	btnSearch.setEnabled(true);
			
			  }
				
		   });
		
		
		btnQSearch = new Button("Absenden");
		
    	qLayoutInner.addComponent(btnEndQ);
		qLayoutInner.addComponent(btnQSearch);
		
		addComponent(lblQSearch);
		addComponent(taQuery);
		addComponent(qLayoutInner);
		
		
	}
	
	public void initFunktion()
	{
	  //------------------Buttons Suchfenster-------------------// 
	  btnEx.addListener(new ClickListener()
		   {
		    	@Override
			   public void buttonClick(ClickEvent event)
		   	  {   		
		    	initExSearch(); 
		    	btnSearch.setEnabled(false);
			
			  }
				
		   });
	  
	  
	  btnQuerySearch.addListener(new ClickListener()
	   {
	    	@Override
		   public void buttonClick(ClickEvent event)
	   	  {   		
	    	initQSearch();    	
	    	btnSearch.setEnabled(false);
		  }
			
	   });
		
	}
	
	
	
}
