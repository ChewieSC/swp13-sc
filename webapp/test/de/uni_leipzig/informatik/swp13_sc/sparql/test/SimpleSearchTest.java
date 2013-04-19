/**
 * SimpleSearchTest.java
 */
package de.uni_leipzig.informatik.swp13_sc.sparql.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.uni_leipzig.informatik.swp13_sc.sparql.SimpleSearch;

/**
 * 
 *
 * @author Erik
 *
 */
public class SimpleSearchTest
{

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
        fail("Not yet implemented"); // TODO
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
        ss.setField(SimpleSearch.FIELD_KEY_CG_EVENT, "h d");
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
        ss.setField(SimpleSearch.FIELD_KEY_CP1_NAME, "SP1");
        ss.setField(SimpleSearch.FIELD_KEY_CP2_NAME, "SPIELER_ZWEI");
        qu = ss.getSPARQLQuery();
    }

}
