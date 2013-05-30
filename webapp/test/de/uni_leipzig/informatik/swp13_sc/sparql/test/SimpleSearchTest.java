/**
 * SimpleSearchTest.java
 */
package de.uni_leipzig.informatik.swp13_sc.sparql.test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import virtuoso.jena.driver.VirtGraph;

import de.uni_leipzig.informatik.swp13_sc.sparql.SimpleSearch;

/**
 * 
 *
 * @author Erik
 *
 */
public class SimpleSearchTest
{

    /**
     * Constructor
     */
    public SimpleSearchTest()
    {
    }

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception
    {
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception
    {
    }

    /**
     * Test method for {@link de.uni_leipzig.informatik.swp13_sc.sparql.SimpleSearch#SimpleSearch()}.
     */
    @Test
    public final void testSimpleSearch()
    {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link de.uni_leipzig.informatik.swp13_sc.sparql.SimpleSearch#SimpleSearch(java.util.Map)}.
     */
    @Test
    public final void testSimpleSearchMapOfStringString()
    {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link de.uni_leipzig.informatik.swp13_sc.sparql.SimpleSearch#setField(java.lang.String, java.lang.String)}.
     */
    @Test
    public final void testSetField()
    {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link de.uni_leipzig.informatik.swp13_sc.sparql.SimpleSearch#query()}.
     */
    @Test
    public final void testQuery()
    {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link de.uni_leipzig.informatik.swp13_sc.sparql.SimpleSearch#getResult()}.
     */
    @Test
    public final void testGetResult()
    {
        SimpleSearch ss = new SimpleSearch();
        
        ss.setDBConnection(new VirtGraph("millionbase",
                "jdbc:virtuoso://pcai042.informatik.uni-leipzig.de:1357", "dba", "dba"));
        ss.setField(SimpleSearch.FIELD_KEY_CG_RESULT, SimpleSearch.FIELD_VALUE_CG_RESULT_DRAW);
        
        List<String> res = ss.getResult();
        for (String s : res)
        {
            System.out.println(s);
        }
        System.out.println("Number of results: " + ss.getResultCount());
    }

    /**
     * Test method for {@link de.uni_leipzig.informatik.swp13_sc.sparql.SimpleSearch#hasResult()}.
     */
    @Test
    public final void testHasResult()
    {
        fail("Not yet implemented"); // TODO
    }
    
    /**
     * Test method for {@link de.uni_leipzig.informatik.swp13_sc.sparql.SimpleSearch#constructSPARQLCountWrapper(java.lang.String[])}.
     */
    @Test
    public final void testConstructSPARQLCountWrapper()
    {
        /*SimpleSearch ss = new SimpleSearch();
        
        String c1 = ss.constructSPARQLCountWrapper(new String[] {"a1", "a2", "b"});
        
        String c2 = ss.constructSPARQLCountWrapper(new String[] {"a1", "a2", null});
        
        String c3 = ss.constructSPARQLCountWrapper(new String[] {null});
        
        String c4 = ss.constructSPARQLCountWrapper(new String[] {});
        
        // only one String[] !
        //String c5 = ss.constructSPARQLCountWrapper(new String[] {"a1", "a2"}, new String[] {"b1", "b2"});
        
        // wrong arguments again !
        //String c6 = ss.constructSPARQLCountWrapper(new String[] {"a1", "a2"}, "b");
        
        String c7 = ss.constructSPARQLCountWrapper("a1", "a2", "b");
        
        String c8 = ss.constructSPARQLCountWrapper("");
        
        String c9 = ss.constructSPARQLCountWrapper("*");
        
        // no char !
        //String c10 = ss.constructSPARQLCountWrapper('*');
        
        String c11 = ss.constructSPARQLCountWrapper((String) null);
        
        ss.setDistinct(true);
        
        String c12 = ss.constructSPARQLCountWrapper(" *  ");
        
        // no validation for multiple empty or null string below here ...
        String c13 = ss.constructSPARQLCountWrapper(new String[] {"", ""});
        
        String c14 = ss.constructSPARQLCountWrapper(new String[] {"", "a2", ""});
        
        String c15 = ss.constructSPARQLCountWrapper(new String[] {null, "a2", "  "});        
        
        String c16 = ss.constructSPARQLCountWrapper(new String[] {null, null, null});*/
    }

    /**
     * Test method for {@link de.uni_leipzig.informatik.swp13_sc.sparql.SimpleSearch#constructSPARQLQueryGameIRI()}.
     */
    @Test
    public final void testConstructSPARQLQueryGameIRI()
    {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link de.uni_leipzig.informatik.swp13_sc.sparql.SimpleSearch#constructSPARQLQueryGamePart()}.
     */
    @Test
    public final void testConstructSPARQLQueryGamePart()
    {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link de.uni_leipzig.informatik.swp13_sc.sparql.SimpleSearch#constructSPARQLQueryGamePlayerPart()}.
     */
    @Test
    public final void testConstructSPARQLQueryGamePlayerPart()
    {
        /*SimpleSearch ss = new SimpleSearch();
        
        ss.setField(SimpleSearch.FIELD_KEY_CP1_COLOR, SimpleSearch.FIELD_VALUE_CP_COLOR_NOCOLOR);
        ss.setField(SimpleSearch.FIELD_KEY_CP1_NAME, "spieler1");        
        String pqu1 = ss.constructSPARQLQueryGamePlayerPart();
        
        ss.setField(SimpleSearch.FIELD_KEY_CP2_COLOR, SimpleSearch.FIELD_VALUE_CP_COLOR_BLACK);        
        String pqu2 = ss.constructSPARQLQueryGamePlayerPart();
        
        ss.setField(SimpleSearch.FIELD_KEY_CP2_NAME, "spieler2");        
        String pqu3 = ss.constructSPARQLQueryGamePlayerPart();
        
        ss.setField(SimpleSearch.FIELD_KEY_CP2_COLOR, SimpleSearch.FIELD_VALUE_CP_COLOR_NOCOLOR);        
        String pqu4 = ss.constructSPARQLQueryGamePlayerPart();
        
        ss.setField(SimpleSearch.FIELD_KEY_CP1_COLOR, SimpleSearch.FIELD_VALUE_CP_COLOR_BLACK);
        ss.setField(SimpleSearch.FIELD_KEY_CP2_COLOR, SimpleSearch.FIELD_VALUE_CP_COLOR_BLACK);
        String pqu5 = ss.constructSPARQLQueryGamePlayerPart();*/
    }

    /**
     * Test method for {@link de.uni_leipzig.informatik.swp13_sc.sparql.SimpleSearch#constructSPARQLQueryPlayerPart(java.lang.String, int)}.
     */
    @Test
    public final void testConstructSPARQLQueryPlayerPart()
    {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link de.uni_leipzig.informatik.swp13_sc.sparql.SimpleSearch#constructSPARQLQueryPlayerIRI()}.
     */
    @Test
    public final void testConstructSPARQLQueryPlayerIRI()
    {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link de.uni_leipzig.informatik.swp13_sc.sparql.SimpleSearch#getSPARQLQuery()}.
     */
    @Test
    public final void testGetSPARQLQuery()
    {
        SimpleSearch ss = new SimpleSearch();
        //ss.setField(SimpleSearch.FIELD_KEY_RESULTTYPE, SimpleSearch.FIELD_VALUE_RESULTTYPE_PLAYER);
        ss.setField(SimpleSearch.FIELD_KEY_RESULTTYPE, SimpleSearch.FIELD_VALUE_RESULTTYPE_GAME);
        ss.setField(SimpleSearch.FIELD_KEY_CG_EVENT, "");
        String qu = ss.getSPARQLQuery();
        
        ss.setField(SimpleSearch.FIELD_KEY_CG_EVENT, "");
        ss.setField(SimpleSearch.FIELD_KEY_CG_ROUND, "");
        ss.setField(SimpleSearch.FIELD_KEY_CG_RESULT, SimpleSearch.FIELD_VALUE_CG_RESULT_DRAW);
        ss.setField(SimpleSearch.FIELD_KEY_CG_SITE, "");
        ss.setField(SimpleSearch.FIELD_KEY_CG_DATE, "");
        qu = ss.getSPARQLQuery();
        
        ss.setField(SimpleSearch.FIELD_KEY_CP1_COLOR, SimpleSearch.FIELD_VALUE_CP_COLOR_BLACK);
        qu = ss.getSPARQLQuery();
        
        ss.setField(SimpleSearch.FIELD_KEY_CP1_COLOR, SimpleSearch.FIELD_VALUE_CP_COLOR_NOCOLOR);
        ss.setField(SimpleSearch.FIELD_KEY_CP2_COLOR, SimpleSearch.FIELD_VALUE_CP_COLOR_NOCOLOR);
        ss.setField(SimpleSearch.FIELD_KEY_CP1_NAME, "brown");
        ss.setField(SimpleSearch.FIELD_KEY_CP2_NAME, "");
        qu = ss.getSPARQLQuery();
        
        
        ss.setField(SimpleSearch.FIELD_KEY_RESULTTYPE, SimpleSearch.FIELD_VALUE_RESULTTYPE_PLAYER2);
        qu = ss.getSPARQLQuery();
        
        ss.setDistinct(false);
        ss.setField(SimpleSearch.FIELD_KEY_RESULTTYPE, SimpleSearch.FIELD_VALUE_RESULTTYPE_PLAYER1);
        qu = ss.getSPARQLQuery();
        
        ss.setCountResults(true);
        qu = ss.getSPARQLQuery();
        
        qu = ss.setCountResults(false)
                .setLimit(257)
                .getSPARQLQuery();
    }
    
    /**
     * Test method for {@link de.uni_leipzig.informatik.swp13_sc.sparql.SimpleSearch#setCountResults(boolean)}.
     */
    @Test
    public final void testSetCountResults()
    {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link de.uni_leipzig.informatik.swp13_sc.sparql.SimpleSearch#setDBConnection(virtuoso.jena.driver.VirtGraph)}.
     */
    @Test
    public final void testSetDBConnection()
    {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link de.uni_leipzig.informatik.swp13_sc.sparql.SimpleSearch#createQueries()}.
     */
    @Test
    public final void testCreateQueries()
    {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link de.uni_leipzig.informatik.swp13_sc.sparql.SimpleSearch#getResultCount()}.
     */
    @Test
    public final void testGetResultCount()
    {
        fail("Not yet implemented"); // TODO
    }


}
