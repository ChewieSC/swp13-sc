
package de.uni_leipzig.informatik.swp13_sc.ui;

import java.util.ArrayList;
import java.util.List;

import virtuoso.jena.driver.VirtGraph;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
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

    private HorizontalLayout gameProbLayoutInner;
    private Button btnNextProb;
    private Button btnPreProb;
    private Label lblInfo;

    private GameProbability gameProb;

    private List<String> moves;
    private int moveCount = 0;
    private String moveFen = null;

    private double[] winChance = new double[3];

    /** Adds GameProbabilityLayout to GameExplorer Grid */
    public CalculatorView(GridLayout expl)
    {
        ui = new Swp13scUI();

        wc = new WinCalculator();

        gameProbLayoutInner = new HorizontalLayout();
        btnNextProb = new Button("Next Turn");
        btnPreProb = new Button("Previous Turn");

        lblInfo = new Label();

        gameProbLayoutInner.addComponent(btnNextProb);
        gameProbLayoutInner.addComponent(btnPreProb);

        gameProb = new GameProbability(2); // only for testing how size of this
                                           // table looks, it is no magic number

        addComponent(lblInfo);
        addComponent(gameProbLayoutInner);
        addComponent(gameProb);

        expl.addComponent(this, 1, 1);
        expl.setComponentAlignment(this, Alignment.BOTTOM_RIGHT);

        initMoves();
        initButtons();

    }
    
    /**
     * Getting MoveList(fen) of the current Game
     */
    public List<String> getGameMoveFens(String gameURI)
    {
        Configuration c = Configuration.getInstance();
        ChessMoveListDataRetriever cmldr = null; // maybe catch null in an
                                                 // if-statement
        String fen;
        List<ChessMove> moveList = null; // same
        List<String> fenList = new ArrayList<String>();

        try
        {
            cmldr = new ChessMoveListDataRetriever(new VirtGraph(
                    c.getVirtuosoBasegraph(), "jdbc:virtuoso://"
                            + c.getVirtuosoHostname(), c.getVirtuosoUsername(),
                    c.getVirtuosoPassword()));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        if (gameURI == null)
        {
            System.out.println("No Game URI found");
        }
        else
        {
            moveList = cmldr.getMoves(gameURI);
        }

        for (ChessMove cm : moveList)
        {
            fen = cm.getFEN();
            fenList.add(fen);
        }

        return fenList;
    }

    /**
     * 
     */
    public void initMoves()
    {
        moves = this.getGameMoveFens(ui.getCurrentGameURI());
    }

    /**
     * adding funktions to buttons
     */
    private void initButtons()
    {
        btnNextProb.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event)
            {
                nextProb();
            }
        });

        btnNextProb.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event)
            {
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
        int length = moveFen.length();

        moveCount++;

        moveFen = moves.get(moveCount);
        lblInfo.setValue("Move: " + moveCount);

        winChance[0] = wc.getDraw();
        winChance[1] = wc.getWinBlack();
        winChance[2] = wc.getWinWhite();

        if (length == moveFen.length())
        {
            lblInfo.setValue("No further turns");
            btnNextProb.setEnabled(false);
        }
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
            lblInfo.setValue("No previous Move...");

            btnPreProb.setEnabled(false);
        }
        else
        {
            moveCount--;
            moveFen = moves.get(moveCount);

            lblInfo.setValue("Move: " + moveCount);

            wc.calculateChances(moveFen);

            winChance[0] = wc.getDraw();
            winChance[1] = wc.getWinBlack();
            winChance[2] = wc.getWinWhite();
        }
    }
}
