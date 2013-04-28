package de.uni_leipzig.informatik.swp13_sc.ui;

import com.vaadin.data.Container;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
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



import de.uni_leipzig.informatik.swp13_sc.sparql.QuerySearch;

/**
 * Kapselt das Layout und die Funktionalitäten des Suchfensters und dessen Komponenten
 * @author LingLong
 *
 */
public class SearchView extends VerticalLayout {

	/**Inneres Layout fuer Suchfenster*/
	private HorizontalLayout searchLayoutInner;
	
	/**Label mit Ueberschrift */
	private Label lblSearch;
	
	/**Button zum oeffnen der Suchfunktion*/    
	private Button btnOpenSearch;

	/**Button zum oeffnen der sparql-Querysuche*/
	private Button btnOpenQuerySearch;
	
	
	private boolean simpleSearchActive = false;
	private boolean querySearchActive = false;
	
	//----- simpleSearch Instanzen -----//
	
	private HorizontalLayout simpleSearchLayoutInner;
	
	/**Suchbutton*/
	private Button btnSearch;
	
	/**Layout für erweiterte Suche	 */
    private FormLayout exSearchLayout ;
		
	/**Suchfelder für erweiterte Suche	 */
	private FieldGroup exSearchFields ;
	
	/**Beschreibungen der einzelnen Suchfelder*/
	private static final String[] searchFieldNames = new String[] {
		"Name","Vorname", "Elo-Rang", "Jahr", "Test", "Dings", "Bums"
		};
	
	/**beendet simpleSearch*/
	private Button btnEndSearch;
	
	
	//----- Instanzen Querysuche -----//	
	
	    private QuerySearch querySearch;
	
		private HorizontalLayout qLayoutInner;
		
		private Label lblQSearch ;
		
		private Button btnQSearch ;	
				
		/**Textfeld zum einfügen bzw. schreiben von Queries*/
		private TextArea taQuery = new TextArea();
			
	    /**beendet Querysuche*/
        private	Button btnEndQSearch;

    
  //Konstruktor
 /**
  * Hier wird ein Objekt erzeugt welches das Layout des Suchefensters und seine Funktion aufbaut */   
 public SearchView()
 {
	    searchLayoutInner = new HorizontalLayout(); 
	    searchLayoutInner.setSizeFull();
	    searchLayoutInner.setWidth("100%");		
	    
	    lblSearch = new Label("Suche nach Schachpartien");		
		addComponent(lblSearch);
	 
	    btnOpenSearch = new Button("Suche");		
		btnOpenQuerySearch = new Button("QuerySuche");
		
	
	    searchLayoutInner.addComponent(btnOpenSearch);	     
	    searchLayoutInner.addComponent(btnOpenQuerySearch);	      
	    addComponent(searchLayoutInner);
 }
 
 
 public void initSearch()
 {	 		
	 simpleSearchLayoutInner = new HorizontalLayout();
	 simpleSearchLayoutInner.setSizeFull();
	 simpleSearchLayoutInner.setWidth("100%");	
					
	 btnSearch = new Button("Suche Starten");
	 btnEndSearch = new Button("Zurück"); 	
	 
	 btnEndSearch.addListener(new ClickListener()
	   {
	    	@Override
		   public void buttonClick(ClickEvent event)
	   	  {   		
	    	removeComponent(exSearchLayout);
	    	removeComponent(simpleSearchLayoutInner);
	    	btnOpenSearch.setEnabled(true);
		
		  }
			
	   });
	 
	 simpleSearchLayoutInner.addComponent(btnSearch);
	 simpleSearchLayoutInner.addComponent(btnEndSearch);					
	
	 exSearchLayout = new FormLayout();					
	 exSearchFields = new FieldGroup();	 

     for (String fieldName : searchFieldNames)
      {
                  TextField field = new TextField(fieldName);
                  exSearchLayout.addComponent(field);
                  field.setWidth("100%");

                  exSearchFields.bind(field, fieldName);
      }
         exSearchLayout.addComponent(btnSearch );
         exSearchFields.setBuffered(false);				 
				
				 addComponent(exSearchLayout);
				 addComponent(simpleSearchLayoutInner);				 
 }
 
 
 
 public void initQuerySearch()
 {
		qLayoutInner = new HorizontalLayout();
		qLayoutInner.setSizeFull();
		qLayoutInner.setWidth("100%");
		
		lblQSearch = new Label("sparql Abfrage für Fortgeschrittene Benutzer");
		
		
		taQuery = new TextArea();
		taQuery.setWidth("100%");
		
		
		
		btnEndQSearch = new Button("Zurück");		
		btnEndQSearch.addListener(new ClickListener()
		   {
		    	@Override
			   public void buttonClick(ClickEvent event)
		   	  {   		
		    	removeComponent(lblQSearch);
		    	removeComponent(taQuery);
		    	removeComponent(qLayoutInner);
		    	btnOpenSearch.setEnabled(true);
		    	btnOpenQuerySearch.setEnabled(true);
		    	querySearchActive = false;
			
			  }
				
		   });
		
		
		btnQSearch = new Button("Absenden");		
		btnQSearch.addListener(new ClickListener()
		{
			@Override
			public void buttonClick(ClickEvent event)
			{	
			 System.out.println("hier ist was passiert");	
			 querySearch = new QuerySearch(taQuery.getValue());				
			}
			
		});
		
 	    qLayoutInner.addComponent(btnEndQSearch);
		qLayoutInner.addComponent(btnQSearch);
		
		addComponent(lblQSearch);
		addComponent(taQuery);
		addComponent(qLayoutInner);
 }
 
 
 /**erzeugt uebergeordnette Suchfensterfunktionen*/
 public void initFunktion()
 {
	 //oeffne simpleSearchView
	 btnOpenSearch.addListener(new ClickListener()
	   {
	    	@Override
		   public void buttonClick(ClickEvent event)
	   	  {  
	    	if(querySearchActive == true)
	    	{
	    		removeComponent(lblQSearch);
		    	removeComponent(taQuery);
		    	removeComponent(qLayoutInner);
		    	simpleSearchActive = true;
		    	querySearchActive = false;
		    	
		    	initSearch(); 
		    	
		    	btnOpenSearch.setEnabled(false);
		    	btnOpenQuerySearch.setEnabled(true);
		    	
	    	}
	    	else{
	    		simpleSearchActive = true;
	    		initSearch(); 
		    	btnOpenSearch.setEnabled(false);
	    	}
	    	
		
		  }
			
	   });
	 
	 //oeffne QuerySearchView
	 btnOpenQuerySearch.addListener(new ClickListener()
	   {
	    	@Override
		   public void buttonClick(ClickEvent event)
	   	  {  
	    	if(simpleSearchActive == true)
	    	{
	    		removeComponent(exSearchLayout);
		    	removeComponent(simpleSearchLayoutInner);
		    	btnOpenSearch.setEnabled(true);
		    	btnOpenQuerySearch.setEnabled(false);
		    	
	    		initQuerySearch();
	    		
	    		simpleSearchActive = false;
	    		querySearchActive = true;
	    	}
	    	else{
	    		
	    		 initQuerySearch(); 
		    	 btnOpenQuerySearch.setEnabled(false);
		    	 querySearchActive = true;
		    	 
	    	    }
	    	
		
		  }
			
	   });
	 
	 
 }
	
	
}
