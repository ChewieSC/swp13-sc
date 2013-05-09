package de.uni_leipzig.informatik.swp13_sc.ui;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.vaadin.server.ExternalResource;
import com.vaadin.server.FileDownloader;
import com.vaadin.server.FileResource;
import com.vaadin.server.Resource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.UI;
import com.vaadin.ui.Upload;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.VerticalSplitPanel;

import de.uni_leipzig.informatik.swp13_sc.converter.PGNToRDFConverterStream;
import de.uni_leipzig.informatik.swp13_sc.util.FileUtils;


/**
 * Hauptklasse der semChess UI
 * @author LingLong
 *
 */
@SuppressWarnings("serial")
public class Swp13scUI extends UI
{
	//---------SplitpanelLayout----------//
	HorizontalSplitPanel splitPanel;
	
	private VerticalLayout splitpanelLayout01;
	private VerticalLayout splitpanelLayout02;
	
	private GridLayout explorerLayout;
	
	
	
	//---------------Game-Explorer Instanzen---------------//
	private SemChessLogo logo;

	//---------------MenuView Instanzen-----------------//

	private SearchView searchView = new SearchView();  	
    private ConverterView converterView = new ConverterView();
    
    private Button btnStartGame = new Button("Game Explorer");
    private boolean inGame = false;

//------------------------------Seitenaufbau-------------------------------------//
	/**
	 * Methode wird zum erstellen der GUI und ihrer Komponenten aufgerufen
	 * -konstruiert die Seite
	 */
	@Override
	protected void init(VaadinRequest request) {

		searchView.initFunktion();
		converterView.initUpload();
		
		initLayout();
		initButtons();
		
	}
   //--------------------Methodenteil----------------------//	

    /**
     * Methode erstellt das Layout der Website	 */
    private void initLayout()
     {
       
      //Hauptfenster 	2geteilt
	  splitPanel = new HorizontalSplitPanel();
      setContent(splitPanel);   
     
      
      splitpanelLayout01 = new VerticalLayout();
      
      btnStartGame = new Button("Game Explorer");
      splitpanelLayout01.addComponent(btnStartGame);
      
      splitPanel.addComponent(splitpanelLayout01);
      splitPanel.addComponent(splitpanelLayout02);
      
      splitpanelLayout01.addComponent(searchView); 
      splitpanelLayout01.addComponent(converterView);
      
      //GameExplorer
      logo = new SemChessLogo(1,1);       
      splitPanel.addComponent(logo);
      
 
      //-------------Eigenschaften der GUIkomponenten----------//
        
      //Anpassen Searchfenster
      searchView.setMargin(true);
      searchView.setVisible(true);
      
     } 
  
 /**
  * Methode initialiesiert Buttons und ihre Listener 
  * bzw. funktionalitaeten
  */
  private void initButtons()
  {
    
    btnStartGame.addClickListener(new ClickListener(){

		@Override
		public void buttonClick(ClickEvent event) {

            if(inGame == false)
            {
            	splitPanel.removeComponent(logo);
            	initChessEngine();
            	inGame = true;
            }else{
            	   splitPanel.removeComponent(explorerLayout);
            	   splitPanel.addComponent(logo);
            	   inGame = false;
                 }			
		}    	
    });
  }
  
  
  private void initChessEngine()
  {	  
	  explorerLayout = new GridLayout(3,3);
	  explorerLayout.setSizeFull();  
	  
	  String basepath;	  
	  
	  basepath = VaadinService.getCurrent()
	             .getBaseDirectory().getAbsolutePath();	                       
	  
	        final Embedded applet = new Embedded();
	        applet.setType(Embedded.TYPE_BROWSER);
	        applet.setWidth("500px");
	        applet.setHeight("500px");
	        applet.setSource(new ExternalResource(basepath + "/WEB-INF/lib/carballo.html"));
	        
	        explorerLayout.addComponent(applet,1,1);
	       
	        explorerLayout.setComponentAlignment(applet, Alignment.MIDDLE_CENTER);
	        
	        splitPanel.addComponent(explorerLayout); 
  }
  
   
  public boolean postText( String text)
  {

	//Parser parser = new Parser(text, "test.txt" );
	  //Post text
	return true;  
  }



  
 
}