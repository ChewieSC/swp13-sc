
package de.uni_leipzig.informatik.swp13_sc.ui;

import java.util.ArrayList;
import java.util.List;

import virtuoso.jena.driver.VirtGraph;

import com.hp.hpl.jena.query.ResultSet;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import de.uni_leipzig.informatik.swp13_sc.datamodel.ChessMove;
import de.uni_leipzig.informatik.swp13_sc.sparql.ChessMoveListDataRetriever;
import de.uni_leipzig.informatik.swp13_sc.sparql.WinCalculator;
import de.uni_leipzig.informatik.swp13_sc.util.Configuration;

public class CalculatorView extends VerticalLayout
{

    private Swp13scUI ui;
    private WinCalculator wc;
    private ReplayGame rg;

    private HorizontalLayout gameProbLayoutInner;
    private Button btnNextProb;
    private Button btnPreProb;
    private Label lblInfo;

    private GameProbability gameProb;

    private List<String> moves;
    private int moveCount = 0;
    private String moveFen = null;
    private int length = 0;
    private ResultTable resultTable;
    private VerticalLayout expl;
    private boolean explorerLayoutEnabled;

    private double[] winChance = new double[3];

    /** 
     * Adds GameProbabilityLayout to GameExplorer Grid 
     */
    
    public CalculatorView(Swp13scUI ui)
    {
        this.ui = ui;
        
        wc = new WinCalculator();

        gameProbLayoutInner = new HorizontalLayout();
        btnNextProb = new Button("Next Turn");
        btnPreProb = new Button("Previous Turn");

        lblInfo = new Label();
        btnPreProb.setEnabled(false);

        gameProbLayoutInner.addComponent(btnNextProb);
        gameProbLayoutInner.addComponent(btnPreProb);

        gameProb = new GameProbability(500); // only for testing how size of this
                                           // table looks, it is no magic number

        addComponent(lblInfo);
        addComponent(gameProbLayoutInner);
        addComponent(gameProb);
        
        explorerLayoutEnabled = false;

//        initButtons(null, null);

    }

    /**
     * 
     */
    public void initMoves()
    {
        moves = rg.getFenList();
        //moveFen = moves.get(0); // check list
    }

    /**
     * adding functions to buttons
     * @param explorerLayout 
     */
    public void initButtons(ReplayGame rgTemp, VerticalLayout explorerLayout)
    {
        this.rg = rgTemp;
        this.expl = explorerLayout;
        
    	if(rg.getQuerySuccess() == false){
    		btnPreProb.setEnabled(false);
    		btnNextProb.setEnabled(false);
        }
    	
    	if(ui.getExplorerLayout() != null && !explorerLayoutEnabled ){
        	expl = ui.getExplorerLayout();
        	expl.addComponent(this);
            expl.setComponentAlignment(this, Alignment.MIDDLE_CENTER);
            explorerLayoutEnabled = true;
        }
    	
        btnNextProb.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event)
            {
                initMoves();
                nextProb();
            }
        });

        btnPreProb.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event)
            {
            	initMoves();
                prevProb();
            }
        });
    }

    /**
     * returns chances of next turn
     * 
     * @return chance of next trun
     */
    public void nextProb()
    {
        length = (moveFen == null) ? 0 : moveFen.length();

        moveCount++;
        if (moveCount >= 1){
        	btnPreProb.setEnabled(true);
        }

        moveFen = moves.get(moveCount);
        lblInfo.setCaption("Move: " + moveCount);
        
        //TESTING
        ResultSet result = wc.calculateChances(moveFen);
//        resultTable = new ResultTable(result);
//        System.out.println("Anzahl der Ergebnisse:" + resultTable.getNr());
//        addComponent(resultTable);
        

        winChance[0] = wc.getDraw();
        winChance[1] = wc.getWinBlack();
        winChance[2] = wc.getWinWhite();
        
       
        if (null == moveFen || length == moveFen.length())
        {
            lblInfo.setValue("No further turns");
            btnNextProb.setEnabled(false);
        }
        
        gameProb.showProb(winChance);
    }

    /**
     * returns chances of previous turn
     * 
     * @return chance of previous trun
     */
    public void prevProb()
    {
        if (moveCount < 1)
        {
            lblInfo.setCaption("No previous Move...");
        }
        else
        {
            moveCount--;
            
            if(moveCount < 1){
            	btnPreProb.setEnabled(false);
            }
            
            if (null != moveFen || length != moveFen.length())
            {
                btnNextProb.setEnabled(true);
            }
            
            moveFen = moves.get(moveCount);

            lblInfo.setValue("Move: " + moveCount);

            wc.calculateChances(moveFen);

            winChance[0] = wc.getDraw();
            winChance[1] = wc.getWinBlack();
            winChance[2] = wc.getWinWhite();
            
            gameProb.showProb(winChance);
        }
    }
}
