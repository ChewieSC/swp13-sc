package de.uni_leipzig.informatik.swp13_sc.ui;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.vaadin.server.ExternalResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.BrowserFrame;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
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
    // ---------PanelLayout----------//
    private VerticalLayout mainFrame;

    private VerticalLayout explorerLayout = null;

    // ---------------Game-Explorer Instanzen---------------//
    private SemChessLogo logo;

    private HorizontalLayout innerReplayGameLayout;

    private VerticalLayout replayGameLayout;

    private VerticalLayout guiGameLayout;

    private Label lblInfoURI = new Label("", ContentMode.PREFORMATTED);
    private Label lblInfoURI2 = new Label("", ContentMode.PREFORMATTED);
    private Label lblInfoURI3 = new Label("", ContentMode.PREFORMATTED);
    
    private CalculatorView calcView;

    // ---------------MenuView-------------//
    private MenuView menuView;

    private TextArea taToParsURI;

    private String basepath = VaadinService.getCurrent().getBaseDirectory()
            .getAbsolutePath();

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
        calcView = new CalculatorView(this);

    }

    // --------------------Methodenteil----------------------//

    /**
     * Methode erstellt das Layout der Website
     */
    @SuppressWarnings("deprecation")
    private void initLayout()
    {
        // Hauptfenster 2geteilt
    	mainFrame = new VerticalLayout();
        setContent(mainFrame);

        // --------MenuView-------//
        menuView = new MenuView();
        mainFrame.addComponent(menuView);

        // GameExplorer
        logo = new SemChessLogo(1, 1);
        mainFrame.addComponent(logo);

    }

    @SuppressWarnings("deprecation")
    public void initChessEngine(String uri)
    {

        try
        {
            removeChessEngine();
        }
        catch (Exception ex)
        {
        }
        mainFrame.removeComponent(logo);
        menuView.removeSearchViewIfEnabled();
        menuView.removeConverterViewIfEnabled();
        
        explorerLayout = new VerticalLayout();
        explorerLayout.setSizeFull();

        BrowserFrame browser = new BrowserFrame("", new ExternalResource(
                "http://pcai042.informatik.uni-leipzig.de:1351/test5/"));
        browser.setWidth("700px");
        browser.setHeight("500px");
        Label lblHowToPlay = new Label(
                "Ein Spiel kann nach Auslesen der Spielinfos (Klick auf 'Replay Game') " +
                "in der Schachoberfläche nachgespielt werden.");
        lblHowToPlay.setWidth("700px");
        Label lblHowToCalc = new Label(
                "Hier kann man sich für jeden einzelnen Zug des Spiels " +
                "die Gewinnwahrscheinlichkeiten der beiden Spieler ansehen.");
        lblHowToCalc.setWidth("700px");

        guiGameLayout = new VerticalLayout();
        guiGameLayout.setSpacing(true);
        replayGameLayout = new VerticalLayout();
        innerReplayGameLayout = new HorizontalLayout();
        innerReplayGameLayout.setSpacing(true);
        lblInfoURI = new Label("Spiel-URI hier eingeben: ");
        // TODO: 'Anleitung' zum Durchspielen ODER intuitiver gestalten
        taToParsURI = new TextArea();
        taToParsURI.setWidth("400px");
        taToParsURI.setHeight("45px");
        Button btnReplay = new Button("Replay Game");
        innerReplayGameLayout.addComponent(lblInfoURI);
        innerReplayGameLayout.addComponent(taToParsURI);
        innerReplayGameLayout.addComponent(btnReplay);
        
        if(!taToParsURI.getValue().equals("")){
        	uri = taToParsURI.getValue();
        }
        else{
        	taToParsURI.setValue(uri);
        }
        
        
        btnReplay.addClickListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event)
            {

                if (!taToParsURI.getValue().equals(""))
                {
                    final ReplayGame rg = new ReplayGame();
                    

                    // test with:
                    // http://pcai042.informatik.uni-leipzig.de/~swp13-sc/ChessOntology/Resources/R__Jamieson_H__Ardiansyah_1979_______1
                    if (taToParsURI
                            .getValue()
                            .startsWith(
                                    "http://pcai042.informatik.uni-leipzig.de/~swp13-sc/ChessOntology/Resources/"))
                    {
                        String pgn = rg.createPGN(taToParsURI.getValue());
//                        calcView.initButtons(rg, explorerLayout);
                        if (pgn.equals("Query konnte nicht erstellt werden. Überprüfen Sie die Game URI oder versuchen Sie es später nochmal."))
                        {
                            lblInfoURI2.setCaption(pgn);
                        }
                        else
                        {
                            replayGameLayout.removeComponent(lblInfoURI3);
                            replayGameLayout.removeComponent(lblInfoURI2);
                            lblInfoURI2 = new Label(rg.getInfo1(), ContentMode.PREFORMATTED);
                            lblInfoURI3 = new Label(rg.getInfo2(), ContentMode.PREFORMATTED);
                            lblInfoURI3.setWidth("700px");
                            replayGameLayout.addComponent(lblInfoURI3);
                            replayGameLayout.addComponent(lblInfoURI2);

                            try
                            {
                                // FileUtils.writeStringToFile(new File(basepath
                                // + "temp.txt"), pgn); //mhmm, weiß nicht warum
                                // er die Klasse nicht erkennt aber naja...
                                // Create file
                                File tempFile = new File(
                                        "/home/swp13-sc/apache-tomcat-6/webapps/test5/"
                                                + "temp.txt");
                                // File tempFile = new
                                // File("C:\\Users\\Chewie\\Documents\\temp.txt");
                                if (tempFile.exists())
                                { // TODO: does not seem to work, check!!
                                    tempFile.delete();
                                }
                                FileWriter writer = new FileWriter(tempFile);
                                writer.write(pgn);
                                writer.close();
                            }
                            catch (IOException e)
                            {
                                e.printStackTrace();
                            }
                        }
                    }
                    else
                    {
                        lblInfoURI2.setCaption(
                                "Dies scheint keine gültige Spiel-URI zu sein. Bitte überprüfen Sie die Eingabe.");
                    }
                    lblInfoURI2.setWidth("700px");
                }
            }
        });


        guiGameLayout.addComponent(browser);
        guiGameLayout.setComponentAlignment(browser, Alignment.TOP_CENTER);
        guiGameLayout.addComponent(lblHowToPlay);
        guiGameLayout.setComponentAlignment(lblHowToPlay, Alignment.TOP_CENTER);
        replayGameLayout.addComponent(innerReplayGameLayout);
        replayGameLayout.setComponentAlignment(innerReplayGameLayout, Alignment.TOP_CENTER);
        guiGameLayout.addComponent(replayGameLayout);
        guiGameLayout.setComponentAlignment(replayGameLayout, Alignment.TOP_CENTER);

        // TODO: maybe go back to this layout, to evaluate after adding winning
        // chances below
        // explorerLayout.addComponent(replayGameLayout, 0, 1);
        // explorerLayout.setComponentAlignment(replayGameLayout,
        // Alignment.TOP_LEFT);
        explorerLayout.addComponent(guiGameLayout);
        explorerLayout.setComponentAlignment(guiGameLayout, Alignment.TOP_CENTER);

        mainFrame.addComponent(explorerLayout);
        
        if(!taToParsURI.getValue().equals("")){
        	btnReplay.click();
        }
        
    }
    
    
    /**
     * Get URI of current game
     */
    public String getCurrentGameURI()
    {
        if (taToParsURI == null || taToParsURI.getValue().equals(""))
        {
            System.out.println("No Game URI found");
            return null;
        }
        else
        {
            return taToParsURI.getValue();
        }
    }
    
    public void removeChessEngine()
    {
        mainFrame.removeComponent(explorerLayout);
        mainFrame.addComponent(logo);
    }
    
    public void removeLogoFromMainFrame(){
    	mainFrame.removeComponent(logo);
    }
    
    public void addLogoToMainFrame(){
    	mainFrame.addComponent(logo);
    }
    
    
	public VerticalLayout getExplorerLayout() {
		return explorerLayout;
	}

}