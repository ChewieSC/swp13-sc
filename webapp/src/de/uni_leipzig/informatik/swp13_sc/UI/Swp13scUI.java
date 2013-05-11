
package de.uni_leipzig.informatik.swp13_sc.ui;

import com.vaadin.server.ExternalResource;
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
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * Hauptklasse der semChess UI
 * 
 * @author LingLong
 * 
 */
@SuppressWarnings("serial")
public class Swp13scUI extends UI
{
    // ---------SplitpanelLayout----------//
    HorizontalSplitPanel splitPanel;

    private VerticalLayout splitpanelLayout01;
    private VerticalLayout splitpanelLayout02;

    private GridLayout explorerLayout;

    // ---------------Game-Explorer Instanzen---------------//
    private SemChessLogo logo;

    // ---------------MenuView Instanzen-----------------//

    private SearchView searchView = new SearchView();
    private ConverterView converterView = new ConverterView();

    private Button btnStartGame = new Button("Game Explorer");
    private boolean inGame = false;
    private Button btnStartSearchView = new Button("Suche");
    private boolean searchViewEnabled = false;
    private Button btnStartConverterView = new Button("Konverter PGN->RDF");
    private boolean converterViewEnabled = false;

    // ------------------------------Seitenaufbau-------------------------------------//
    /**
     * Methode wird zum erstellen der GUI und ihrer Komponenten aufgerufen
     * -konstruiert die Seite
     */
    @Override
    protected void init(VaadinRequest request)
    {
        searchView.initFunktion();
        converterView.initUpload();

        initLayout();
        initButtons();
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

        HorizontalLayout horiOptionLayout = new HorizontalLayout(btnStartGame, btnStartSearchView, btnStartConverterView);
        horiOptionLayout.setMargin(true);
        horiOptionLayout.setSpacing(true);
        splitpanelLayout01.addComponent(horiOptionLayout);

        splitPanel.addComponent(splitpanelLayout01);
        splitPanel.addComponent(splitpanelLayout02);
        // Setze Splitter auf 35 % und 65 %
        splitPanel.setSplitPosition(35f, UNITS_PERCENTAGE);

        splitpanelLayout01.addComponent(searchView);
        searchViewEnabled = true;
        //splitpanelLayout01.addComponent(converterView);

        // GameExplorer
        logo = new SemChessLogo(1, 1);
        splitPanel.addComponent(logo);

        // -------------Eigenschaften der GUIkomponenten----------//

        // Anpassen Searchfenster
        searchView.setMargin(true);
        searchView.setVisible(true);
    }

    /**
     * Methode initialiesiert Buttons und ihre Listener bzw. funktionalitaeten
     */
    private void initButtons()
    {
        btnStartGame.addClickListener(new ClickListener()
        {
            @Override
            public void buttonClick(ClickEvent event)
            {
                if (inGame == false)
                {
                    splitPanel.removeComponent(logo);
                    initChessEngine();
                    inGame = true;
                }
                else
                {
                    splitPanel.removeComponent(explorerLayout);
                    splitPanel.addComponent(logo);
                    inGame = false;
                }
            }
        });
        
        btnStartSearchView.addClickListener(new ClickListener()
        {
            @Override
            public void buttonClick(ClickEvent event)
            {
                if (searchViewEnabled)
                {
                    splitpanelLayout01.removeComponent(searchView);
                    searchViewEnabled = false;
                }
                else
                {
                    splitpanelLayout01.addComponent(searchView);
                    searchViewEnabled = true;
                }
            }
        });
        
        btnStartConverterView.addClickListener(new ClickListener()
        {
            @Override
            public void buttonClick(ClickEvent event)
            {
                if (converterViewEnabled)
                {
                    splitpanelLayout01.removeComponent(converterView);
                    converterViewEnabled = false;
                }
                else
                {
                    splitpanelLayout01.addComponent(converterView);
                    converterViewEnabled = true;
                }
            }
        });
    }

    @SuppressWarnings("deprecation")
    private void initChessEngine()
    {
        explorerLayout = new GridLayout(3, 3);
        explorerLayout.setSizeFull();

        String basepath;

        basepath = VaadinService.getCurrent().getBaseDirectory()
                .getAbsolutePath();

        final Embedded applet = new Embedded();
        applet.setType(Embedded.TYPE_BROWSER);
        applet.setWidth("600px");
        applet.setHeight("510px");
        applet.setSource(new ExternalResource(basepath
                + "/WEB-INF/lib/carballo.html"));

        explorerLayout.addComponent(applet, 1, 1);

        explorerLayout.setComponentAlignment(applet, Alignment.MIDDLE_CENTER);

        splitPanel.addComponent(explorerLayout);
    }
}