
package de.uni_leipzig.informatik.swp13_sc.ui;

import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class MenuView extends VerticalLayout
{

    // ---------------MenuView Instanzen-----------------//

    private HorizontalLayout menuInnerLayout;

    private Swp13scUI ui;

    private SearchView searchView = new SearchView();

    private ConverterView converterView = new ConverterView();

    private Button btnStartGame = new Button("Game Explorer");

    private Button btnStartSearchView = new Button("Suche");

    private Button btnStartConverterView = new Button("Konverter PGN->RDF");

    private boolean searchViewEnabled = false;

    private boolean converterViewEnabled = false;

    private boolean inGame = false;

    public MenuView()
    {
        menuInnerLayout = new HorizontalLayout(btnStartGame,
                btnStartSearchView, btnStartConverterView);
        menuInnerLayout.setMargin(true);
        menuInnerLayout.setSpacing(true);

        converterView.initUpload();

        // Anpassen Searchfenster
        searchView.setMargin(true);
        searchView.setVisible(true);
        searchView.initFunktion();

        addComponent(menuInnerLayout);

    }

    /**
     * Methode initialiesiert Buttons und ihre Listener bzw. funktionalitaeten
     */
    public void initFunktion(final Swp13scUI ui)
    {
        this.ui = ui;
        btnStartGame.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event)
            {
            	//check if one of the other two windows are open
            	removeConverterViewIfEnabled();
            	removeSearchViewIfEnabled();
                
                if (inGame == false)
                {
                    ui.initChessEngine("");
                    inGame = true;
                }
                else
                {
                    ui.removeChessEngine();
                    inGame = false;
                }
            }
        });

        btnStartSearchView.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event)
            {
            	searchView.setUi(ui);
            	//check if one of the other two windows are open
            	removeConverterViewIfEnabled();
            	removeGameViewIfEnabled();

                if (converterViewEnabled)
                {
                    removeComponent(converterView);
                    converterViewEnabled = false;
                }
                
                if (searchViewEnabled)
                {
                    removeComponent(searchView);
                    searchViewEnabled = false;
                    ui.addLogoToMainFrame();
                }
                else
                {
                	ui.removeLogoFromMainFrame();
                    addComponent(searchView);
                    searchViewEnabled = true;
                }
            }
        });

        btnStartConverterView.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event)
            {
            	//check if one of the other two windows are open
            	removeSearchViewIfEnabled();
            	removeGameViewIfEnabled();
            	
            	/////
                if (converterViewEnabled)
                {
                    removeComponent(converterView);
                    ui.addLogoToMainFrame();
                    converterViewEnabled = false;
                }
                else
                {
                	ui.removeLogoFromMainFrame();
                    addComponent(converterView);
                    converterViewEnabled = true;
                }
            }
        });
    }
    
    public void removeConverterViewIfEnabled()
    {
    	if (converterViewEnabled)
        {
            removeComponent(converterView);
            converterViewEnabled = false;
        }

    }
    
    public void removeSearchViewIfEnabled()
    {
    	if (searchViewEnabled)
        {
            removeComponent(searchView);
            searchViewEnabled = false;
        }

    }
    
    public void removeGameViewIfEnabled()
    {
        if (inGame == true)
        {
            ui.removeChessEngine();
            inGame = false;
        }

    }
    
    
    
	
}
