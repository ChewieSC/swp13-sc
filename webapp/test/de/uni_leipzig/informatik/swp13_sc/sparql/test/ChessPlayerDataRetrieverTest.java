/**
 * ChessPlayerDataRetriever.java
 */
package de.uni_leipzig.informatik.swp13_sc.sparql.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import virtuoso.jena.driver.VirtGraph;

import de.uni_leipzig.informatik.swp13_sc.datamodel.ChessPlayer;
import de.uni_leipzig.informatik.swp13_sc.datamodel.rdf.ChessRDFVocabulary;
import de.uni_leipzig.informatik.swp13_sc.sparql.ChessPlayerDataRetriever;

/**
 * JUnit Testcase for ChessPlayerDataRetriever.java.
 *
 * @author Erik
 *
 */
public class ChessPlayerDataRetrieverTest
{
    private VirtGraph virtuosoTestGraph;

    /**
     * Constructor. Empty (?)
     */
    public ChessPlayerDataRetrieverTest()
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
            this.virtuosoTestGraph = new VirtGraph("http://localhost:1358/millionbase",
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
     * Test method for {@link de.uni_leipzig.informatik.swp13_sc.sparql.ChessPlayerDataRetriever#ChessPlayerDataRetriever(virtuoso.jena.driver.VirtGraph)}.
     */
    @Test
    public final void testChessPlayerDataRetriever()
    {
        try
        {
            @SuppressWarnings("unused")
            ChessPlayerDataRetriever cpdr = new ChessPlayerDataRetriever(null);
            
            fail("Should have thrown an exception ...");
        }
        catch (Exception e)
        {
            // ignore
        }
        
        try
        {
            @SuppressWarnings("unused")
            ChessPlayerDataRetriever cpdr = new ChessPlayerDataRetriever(virtuosoTestGraph);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            fail("Exception thrown ...");
        }
        
        // TODO: some more cases ?
    }

    /**
     * Test method for {@link de.uni_leipzig.informatik.swp13_sc.sparql.ChessPlayerDataRetriever#getPlayer(java.lang.String)}.
     */
    @Test
    public final void testGetPlayer()
    {
        try
        {
            ChessPlayerDataRetriever cpdr = new ChessPlayerDataRetriever(virtuosoTestGraph);
            
            ChessPlayer cp = cpdr.getPlayer(null);
            assertNull("ChessPlayerDataRetriever getPlayer with parameter null should return null.", cp);
            
            ChessPlayer cp2 = cpdr.getPlayer("");
            assertNull("ChessPlayerDataRetriever getPlayer with parameter \"\" should return null.\n"
                    + "There shouldn't be such a resource ...", cp2);
            
            // TODO: before testing you should check if the triplestore contains the following data ...
            
            ChessPlayer cp3 = cpdr.getPlayer("http://pcai042.informatik.uni-leipzig.de/~swp13-sc/ChessOntology/Resources/Rowe__P___bl_");
            assertNotNull("Should have data ... Check Virtuoso and change test if neccessary.", cp3);
            assertEquals("Rowe, P. (bl)", cp3.getName());
            
            ChessPlayer cp4 = cpdr.getPlayer(ChessRDFVocabulary.getResourceURI() + "Hashim__Ali__bl_");
            assertNotNull("Should have data ... Check Virtuoso and change test if neccessary.", cp3);
            assertEquals("Hashim, Ali (bl)", cp4.getName());
            
            // ...
        }
        catch (Exception e)
        {
            e.printStackTrace();
            fail("Exception thrown ...");
        }
    }
    
    /**
     * Test method for {@link de.uni_leipzig.informatik.swp13_sc.sparql.ChessPlayerDataRetriever#getPlayers(java.util.List)}.
     */
    @Test
    public final void testGetPlayers()
    {
        try
        {
            ChessPlayerDataRetriever cpdr = new ChessPlayerDataRetriever(virtuosoTestGraph);
            
            List<ChessPlayer> cp = cpdr.getPlayers(null);
            assertNotNull(cp);
            assertEquals(0, cp.size());
            
            List<ChessPlayer> cp2 = cpdr.getPlayers(new ArrayList<String>());
            assertNotNull(cp2);
            assertEquals(0, cp2.size());
            
            // TODO: before testing you should check if the triplestore contains the following data ...
            
            ArrayList<String> l3 = new ArrayList<String>();
            l3.add("");
            l3.add("");
            List<ChessPlayer> cp3 = cpdr.getPlayers(l3);
            assertNotNull(cp3);
            assertEquals(0, cp3.size());
            
            ArrayList<String> l4 = new ArrayList<String>();
            l4.add("http://pcai042.informatik.uni-leipzig.de/~swp13-sc/ChessOntology/Resources/Rowe__P___bl_");
            l4.add(ChessRDFVocabulary.getResourceURI() + "Hashim__Ali__bl_");
            List<ChessPlayer> cp4 = cpdr.getPlayers(l4);
            assertNotNull(cp4);
            assertEquals(2, cp4.size());
            
            l4.add("");
            List<ChessPlayer> cp5 = cpdr.getPlayers(l4);
            assertNotNull(cp5);
            assertEquals(2, cp5.size());
            
            // ...
        }
        catch (Exception e)
        {
            e.printStackTrace();
            fail("Exception thrown ...");
        }
    }
    
    /**
     * Test method for {@link de.uni_leipzig.informatik.swp13_sc.sparql.ChessPlayerDataRetriever#getPlayersMap(java.util.List)}.
     */
    @Test
    public final void testGetPlayersMap()
    {
        try
        {
            ChessPlayerDataRetriever cpdr = new ChessPlayerDataRetriever(virtuosoTestGraph);
            
            Map<String, ChessPlayer> m1 = cpdr.getPlayersMap(null);
            assertNotNull(m1);
            assertEquals(0, m1.size());
            
            Map<String, ChessPlayer> m2 = cpdr.getPlayersMap(new ArrayList<String>());
            assertNotNull(m2);
            assertEquals(0, m2.size());
            
            // TODO: before testing you should check if the triplestore contains the following data ...
            
            ArrayList<String> l3 = new ArrayList<String>();
            l3.add("");
            l3.add("");
            Map<String, ChessPlayer> m3 = cpdr.getPlayersMap(l3);
            assertNotNull(m3);
            assertEquals(1, m3.size()); // only one entry
            assertNull(m3.get("")); // entry will be null
            
            ArrayList<String> l4 = new ArrayList<String>();
            l4.add("http://pcai042.informatik.uni-leipzig.de/~swp13-sc/ChessOntology/Resources/Rowe__P___bl_");
            l4.add(ChessRDFVocabulary.getResourceURI() + "Hashim__Ali__bl_");
            Map<String, ChessPlayer> m4 = cpdr.getPlayersMap(l4);
            assertNotNull(m4);
            assertEquals(2, m4.size());
            
            l4.add("");
            Map<String, ChessPlayer> m5 = cpdr.getPlayersMap(l4);
            assertNotNull(m5);
            assertEquals(3, m5.size());
            assertNull(m5.get(""));
            
            // ...
        }
        catch (Exception e)
        {
            e.printStackTrace();
            fail("Exception thrown ...");
        }
    }
}
