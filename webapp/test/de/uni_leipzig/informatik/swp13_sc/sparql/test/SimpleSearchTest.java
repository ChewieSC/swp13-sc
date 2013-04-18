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
    }

}
