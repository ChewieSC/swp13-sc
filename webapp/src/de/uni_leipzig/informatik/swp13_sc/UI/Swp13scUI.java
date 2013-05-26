
package de.uni_leipzig.informatik.swp13_sc.ui;

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
    private HorizontalSplitPanel splitPanel;

    private VerticalLayout splitpanelLayout01;    

    private GridLayout explorerLayout;

    // ---------------Game-Explorer Instanzen---------------//
    private SemChessLogo logo;
    
    //---------------MenuView-------------//
    private MenuView menuView;
   
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
    	
        explorerLayout = new GridLayout(3, 3);
        explorerLayout.setSizeFull();

        BrowserFrame browser = new BrowserFrame("",new ExternalResource("http://pcai042.informatik.uni-leipzig.de:1351/test5/")); 
        browser.setWidth("800px");
        browser.setHeight("600px");
        
        explorerLayout.addComponent(browser, 1,0);
        explorerLayout.setComponentAlignment(browser,Alignment.TOP_CENTER);
        
        
        splitPanel.addComponent(explorerLayout);
    }
    
   public void removeChessEngine()
   {
	   splitPanel.removeComponent(explorerLayout);
       splitPanel.addComponent(logo);
   }
    
    
    
}