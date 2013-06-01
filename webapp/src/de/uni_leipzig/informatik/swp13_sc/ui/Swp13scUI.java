
package de.uni_leipzig.informatik.swp13_sc.ui;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.vaadin.server.ExternalResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.BrowserFrame;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * Hauptklasse der semChess UI
 * 
 * @author LingLong
 * @author Chewie
 * 
 */
@SuppressWarnings("serial")
public class Swp13scUI extends UI
{
    // ---------SplitpanelLayout----------//
    private HorizontalSplitPanel splitPanel;

    private VerticalLayout splitpanelLayout01;    

    private GridLayout explorerLayout;

    // ---------------Game-Explorer Instanzen---------------//
    private SemChessLogo logo;
   
    private HorizontalLayout innerReplayGameLayout;
    
    private VerticalLayout replayGameLayout;
    
    private VerticalLayout guiGameLayout;
    
    private Label lblInfoURI;
    
    //---------------MenuView-------------//
    private MenuView menuView;
    
    private TextArea taToParsURI; 
   
    private String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
    
    // ------------------------------Seitenaufbau-------------------------------------//
    /**
     * Methode wird zum erstellen der GUI und ihrer Komponenten aufgerufen
     * -konstruiert die Seite
     */
    @Override
    protected void init(VaadinRequest request)
    {
         initLayout();
         menuView.initFunktion(this);
         
    }

    // --------------------Methodenteil----------------------//

    /**
     * Methode erstellt das Layout der Website
     */
    @SuppressWarnings("deprecation")
    private void initLayout()
    {
        // Hauptfenster 2geteilt
        splitPanel = new HorizontalSplitPanel();
        setContent(splitPanel);

        splitpanelLayout01 = new VerticalLayout();   
     
        // Setze Splitter auf 35 % und 65 %
        splitPanel.setSplitPosition(35f, UNITS_PERCENTAGE);       
     
        //--------MenuView-------//
        menuView = new MenuView();
        splitPanel.addComponent(menuView);

        // GameExplorer
        logo = new SemChessLogo(1, 1);
        splitPanel.addComponent(logo);
        
    }
 

    @SuppressWarnings("deprecation")
    public  void initChessEngine()
    {
    	splitPanel.removeComponent(logo);
    	
        explorerLayout = new GridLayout(2, 2);
        explorerLayout.setSizeFull();

        BrowserFrame browser = new BrowserFrame("", new ExternalResource("http://pcai042.informatik.uni-leipzig.de:1351/test5/")); 
        browser.setWidth("800px");
        browser.setHeight("600px");
        Label lblHowToPlay = new Label("Die Möglichkeit ein Spiel aus der Datenbank durchzuspielen besteht zurzeit nur, wenn man eine Spiel-URI (z.B. aus der Simple-oder QuerySearch) unter dem Schachfeld in das Textfeld kopiert, danach Replay Game beim Button daneben und dann nochmal in der Schachoberfläche klickt.");
        lblHowToPlay.setWidth("800px");
        
        guiGameLayout = new VerticalLayout();
        guiGameLayout.setSpacing(true);
        replayGameLayout = new VerticalLayout();
        innerReplayGameLayout = new HorizontalLayout();
        innerReplayGameLayout.setSpacing(true);
        lblInfoURI = new Label("Spiel-URI hier eingeben: ");
        //TODO: 'Anleitung' zum Durchspielen ODER intuitiver gestalten
        taToParsURI = new TextArea();
        taToParsURI.setWidth("500px");
        taToParsURI.setHeight("45px");
        Button btnReplay = new Button("Replay Game");
        innerReplayGameLayout.addComponent(lblInfoURI);
        innerReplayGameLayout.addComponent(taToParsURI);
        innerReplayGameLayout.addComponent(btnReplay);
        
        btnReplay.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				
				if( taToParsURI.getValue() != ""){
					final ReplayGame rg = new ReplayGame();
					Label lblInfoURI2;
					//test with: http://pcai042.informatik.uni-leipzig.de/~swp13-sc/ChessOntology/Resources/R__Jamieson_H__Ardiansyah_1979_______1
					if (taToParsURI.getValue().contains("http://pcai042.informatik.uni-leipzig.de/~swp13-sc/ChessOntology/Resources/")){
						String pgn = rg.createPGN(taToParsURI.getValue());
						if (pgn.equals("Query konnte nicht erstellt werden. Überprüfen Sie die Game URI oder versuchen Sie es später nochmal.")){
							lblInfoURI2 = new Label(pgn, Label.CONTENT_XHTML);
							
						}
						else{
							lblInfoURI2 = new Label(rg.getInfo1(), Label.CONTENT_XHTML);
					        Label lblInfoURI3 = new Label(rg.getInfo2(), Label.CONTENT_XHTML);
					        lblInfoURI3.setWidth("800px");
					        replayGameLayout.addComponent(lblInfoURI3);
					        try {
								//FileUtils.writeStringToFile(new File(basepath + "temp.txt"), pgn); //mhmm, weiß nicht warum er die Klasse nicht erkennt aber naja...
					        	  // Create file
					        	  File tempFile = new File("/home/swp13-sc/apache-tomcat-6/webapps/test5/" + "temp.txt");
//					        	  File tempFile = new File("C:\\Users\\Chewie\\Documents\\temp.txt");
					        	  if (tempFile.exists()){ //TODO: does not seem to work, check!!
					        		  tempFile.delete();
					                  }
					        	  FileWriter writer=new FileWriter(tempFile);
				                  writer.write(pgn);
				                  writer.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
					else{
						lblInfoURI2 = new Label("Dies scheint keine gültige Spiel-URI zu sein. Bitte überprüfen Sie die Eingabe.", Label.CONTENT_XHTML);
					}
					lblInfoURI2.setWidth("800px");
			        replayGameLayout.addComponent(lblInfoURI2);
				}
			}
		});
        
        guiGameLayout.addComponent(browser);
        guiGameLayout.addComponent(lblHowToPlay);
        replayGameLayout.addComponent(innerReplayGameLayout);
        guiGameLayout.addComponent(replayGameLayout);
        
        //TODO: maybe go back to this layout, to evaluate after adding winning chances below 
//        explorerLayout.addComponent(replayGameLayout, 0, 1);
//        explorerLayout.setComponentAlignment(replayGameLayout, Alignment.TOP_LEFT);
		explorerLayout.addComponent(guiGameLayout, 0,0);
        explorerLayout.setComponentAlignment(guiGameLayout,Alignment.TOP_CENTER);

        splitPanel.addComponent(explorerLayout);
    }
    
   public void removeChessEngine()
   {
	   splitPanel.removeComponent(explorerLayout);
       splitPanel.addComponent(logo);
   }
    
    
    
}