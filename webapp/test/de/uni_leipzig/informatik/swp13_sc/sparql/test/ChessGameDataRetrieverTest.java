/**
 * ChessGameDataRetrieverTest.java
 */
package de.uni_leipzig.informatik.swp13_sc.sparql.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import virtuoso.jena.driver.VirtGraph;
import de.uni_leipzig.informatik.swp13_sc.datamodel.ChessGame;
import de.uni_leipzig.informatik.swp13_sc.datamodel.ChessMove;
import de.uni_leipzig.informatik.swp13_sc.sparql.ChessGameDataRetriever;

/**
 * 
 *
 * @author Erik
 *
 */
public class ChessGameDataRetrieverTest
{
    private VirtGraph virtuosoTestGraph;

    /**
     * Constructor. Empty (?)
     */
    public ChessGameDataRetrieverTest()
    {
    }

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception
    {
        try
        {
            //this.virtuosoTestGraph = new VirtGraph("http://localhost:1358/millionbase",
            //        "jdbc:virtuoso://pcai042.informatik.uni-leipzig.de:1357", "dba", "dba");
            this.virtuosoTestGraph = new VirtGraph("http://localhost:1358/millionbase_fen",
                    "jdbc:virtuoso://pcai042.informatik.uni-leipzig.de:1357", "dba", "dba");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            fail("Could not create test graph for virtuoso. Aborting Test.");
        }
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception
    {
        try
        {
            this.virtuosoTestGraph.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Test method for {@link de.uni_leipzig.informatik.swp13_sc.sparql.ChessGameDataRetriever#ChessGameDataRetriever(virtuoso.jena.driver.VirtGraph)}.
     */
    @Test
    public final void testChessGameDataRetriever()
    {
        try
        {
            @SuppressWarnings("unused")
            ChessGameDataRetriever cpdr = new ChessGameDataRetriever(null);
            
            fail("Should have thrown an exception ...");
        }
        catch (Exception e)
        {
            // ignore
        }
        
        try
        {
            @SuppressWarnings("unused")
            ChessGameDataRetriever cpdr = new ChessGameDataRetriever(virtuosoTestGraph);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            fail("Exception thrown ...");
        }
        
        // TODO: some more cases ?
    }

    /**
     * Test method for {@link de.uni_leipzig.informatik.swp13_sc.sparql.ChessGameDataRetriever#getGame(java.lang.String)}.
     */
    @Test
    public final void testGetGame()
    {
        try
        {
            ChessGameDataRetriever cgdr = new ChessGameDataRetriever(virtuosoTestGraph);
            
            ChessGame cg1 = cgdr.getGame("http://pcai042.informatik.uni-leipzig.de/~swp13-sc/ChessOntology/Resources/Rowe__P___bl__Abdoulay__C___wh__1998_10_07_1");
            System.out.println(cg1);
            
            // ----------------------------------------------------------------
            
            // for retrieving chess player data
            cgdr.createDefaultChessPlayerDataRetriever();
            
            ChessGame cg2 = cgdr.getGame("http://pcai042.informatik.uni-leipzig.de/~swp13-sc/ChessOntology/Resources/Rowe__P___bl__Abdoulay__C___wh__1998_10_07_1");
            System.out.println(cg2);
            
            
            // TODO: add some more logic
        }
        catch (Exception e)
        {
            e.printStackTrace();
            fail("Exception thrown ...");
        }
    }

    /**
     * Test method for {@link de.uni_leipzig.informatik.swp13_sc.sparql.ChessGameDataRetriever#getMoves(java.lang.String)}.
     */
    @Test
    public final void testGetMoves()
    {
        try
        {
            ChessGameDataRetriever cgdr = new ChessGameDataRetriever(virtuosoTestGraph);
            
            //cgdr.createDefaultChessPlayerDataRetriever();
            
            List<ChessMove> cgl = cgdr.getMoves("http://pcai042.informatik.uni-leipzig.de/~swp13-sc/ChessOntology/Resources/Rowe__P___bl__Abdoulay__C___wh__1998_10_07_1");
            assertNotNull(cgl);
            assertEquals(85, cgl.size());
            // check - only moves with fen in there ...
            //for (ChessMove m : cgl)
            //{
            //    System.out.println(m);
            //}
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
            fail("Exception thrown ...");
        }
    }

}
