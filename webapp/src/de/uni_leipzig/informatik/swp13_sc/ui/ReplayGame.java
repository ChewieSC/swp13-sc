
package de.uni_leipzig.informatik.swp13_sc.ui;

import de.uni_leipzig.informatik.swp13_sc.datamodel.ChessGame;
import de.uni_leipzig.informatik.swp13_sc.datamodel.ChessMove;
import de.uni_leipzig.informatik.swp13_sc.sparql.ChessGameDataRetriever;
import de.uni_leipzig.informatik.swp13_sc.sparql.ChessMoveListDataRetriever;
import de.uni_leipzig.informatik.swp13_sc.util.Configuration;
import virtuoso.jena.driver.VirtGraph;

import java.util.ArrayList;
import java.util.List;

/**
 * Initiate the history to replay/analyze a selected game out of the triple
 * store/database
 * 
 * @author Chewie
 * 
 */
public class ReplayGame
{

    private VirtGraph virtuosoGraph;
    private ArrayList<String> fenList = new ArrayList<String>();
    private String blackPlayerName;
    private String whitePlayerName;
    private String eco;
    private String gameInfo;
    private String gameInfo2;
    private String gameInfo3; // TODO: for extra information from LIMES, etc.
    private boolean querySuccess = false;

    /**
     * Constructor. Empty (?)
     */
    public ReplayGame()
    {
        try
        {
            setUp();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Set up the VirtGraph
     * 
     * @throws java.lang.Exception
     */
    public void setUp() throws Exception
    {
        try
        {
            Configuration c = Configuration.getInstance();
//            this.virtuosoGraph = new VirtGraph ("millionbase",
//                    "jdbc:virtuoso://pcai042.informatik.uni-leipzig.de:1357",
//                    "dba", "dba");
            this.virtuosoGraph = new VirtGraph(c.getVirtuosoBasegraph(),
                    "jdbc:virtuoso://" + c.getVirtuosoHostname(),
                    c.getVirtuosoUsername(), c.getVirtuosoPassword());
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.err
                    .println("Could not create graph for virtuoso. Aborting.");
        }
    }

    /**
     * To Close the Virtuoso Graph at the End
     * 
     * @throws java.lang.Exception
     */
    public void tearDown() throws Exception
    {
        try
        {
            this.virtuosoGraph.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * method to pass in a game uri and get all information about it
     * 
     * @param uri
     *            chess game uri
     * @return ChessGame datamodel
     */
    public final ChessGame getGame(String uri)
    {
        ChessGame cg = null;
        try
        {
            ChessGameDataRetriever cgdr = new ChessGameDataRetriever(
                    virtuosoGraph);
            cgdr.createDefaultChessPlayerDataRetriever(); // to also get chess
                                                          // player data
            // http://pcai042.informatik.uni-leipzig.de/~swp13-sc/ChessOntology/Resources/Vitasek__bl__Franzen__wh__1987_______1
            // http://pcai042.informatik.uni-leipzig.de/~swp13-sc/ChessOntology#blackPlayer
            // <http://pcai042.informatik.uni-leipzig.de/~swp13-sc/ChessOntology/Resources/Vitasek__bl_>
            // http://pcai042.informatik.uni-leipzig.de/~swp13-sc/ChessOntology#move
            // cg1 =
            // cgdr.getGame("http://pcai042.informatik.uni-leipzig.de/~swp13-sc/ChessOntology/Resources/R__Jamieson_H__Ardiansyah_1979_______1");
            cg = cgdr.getGame(uri);

            blackPlayerName = cgdr.getBlackChessPlayer(uri).getName();
            whitePlayerName = cgdr.getWhiteChessPlayer(uri).getName();
            // eco = cgdr.getSingleGameProperty(uri,
            // "http://pcai042.informatik.uni-leipzig.de/~swp13-sc/ChessOntology#eco");
            eco = cg.getMetaValue("eco");
            tearDown();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.err.println("Exception thrown ...");
        }

        return cg;
    }

    /**
     * Seperate getMoves method, included in getGame, but still handy if only
     * moves are necessary
     * 
     * @param uri
     *            a game uri
     * @return list of chess moves
     */

    public final List<ChessMove> getMoves(String uri)
    {
        List<ChessMove> cgl = null;
        try
        {
            ChessMoveListDataRetriever cmdr = new ChessMoveListDataRetriever(
                    virtuosoGraph);

            // cgdr.createDefaultChessPlayerDataRetriever();

            cgl = cmdr.getMoves(uri);

            tearDown();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.err.println("Exception thrown ...");
        }
        return cgl;
    }

    /**
     * recreate PGN for Carballo
     */
    public String createPGN(String uri)
    {
    	querySuccess = false;

        ChessGame cg = getGame(uri);

        try
        {
            List<ChessMove> cgl = cg.getMoves();
            System.out.println(blackPlayerName);
            System.out.println(whitePlayerName);
            String gameEvent = cg.getEvent();
            String gameDate = cg.getDate();
            String gameSite = cg.getSite();
            String gameRound = cg.getRound();
            String gameResult = cg.getResult();

//            gameInfo = "Event: " + gameEvent + ", Runde: " + gameRound
//                    + "&emsp;&emsp;&emsp;&emsp; Datum: " + gameDate
//                    + "&emsp;&emsp;&emsp;&emsp; Ort: " + gameSite;
            gameInfo = "Event: " + gameEvent + ", Runde: " + gameRound
                    + "\t\t Datum: " + gameDate
                    + "\t Ort: " + gameSite;
            gameInfo2 = whitePlayerName + " vs. " + blackPlayerName
                    + "\t Ergebnis: " + gameResult
                    + "\t\t ECO: " + eco;
            // TODO: peakrating

            String pgn = "";
            int n = 1;
            int k = 0;

            for (ChessMove m : cgl)
            {
            	fenList.add(m.getFEN());
                k++;
                if (k != m.getNr())
                {
                    System.out.println("Missing move!");
                }
                if (m.getNr() % 2 == 1)
                {
                    pgn = pgn + n + ". " + m.getMove() + " ";
                }
                else
                {
                    pgn = pgn + m.getMove() + " ";
                    n++;
                }
            }
            System.out.println(pgn);
            
            querySuccess = true;

            return pgn;

        }
        catch (Exception e)
        {
            System.err.println(e);
        }
        return "Could not create query please try again later";

    }

    /**
     * get game info for event, round, date site
     */
    public String getInfo1()
    {
        return gameInfo;
    }

    /**
     * get game info for player names, eco and result
     */
    public String getInfo2()
    {
        return gameInfo2;
    }
    
    public ArrayList<String> getFenList(){
    	return fenList;
    }
    /**
     * state whether or not a prior query was successful, mainly for CalcularView
     * @return
     */
    public boolean getQuerySuccess()
    {
        return querySuccess;
    }
    
    // TODO: Show Player names, Event/Game number, Score
}
