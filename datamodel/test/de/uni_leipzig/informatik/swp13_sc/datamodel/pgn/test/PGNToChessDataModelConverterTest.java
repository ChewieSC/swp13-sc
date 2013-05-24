/**
 * PGNToChessDataModelConverterTest.java
 */
package de.uni_leipzig.informatik.swp13_sc.datamodel.pgn.test;

import static org.junit.Assert.*;

import java.util.regex.Pattern;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.uni_leipzig.informatik.swp13_sc.converter.PGNToChessDataModelConverter;

/**
 * 
 *
 * @author Erik
 *
 */
public class PGNToChessDataModelConverterTest
{

    /**
     * Constructor
     */
    public PGNToChessDataModelConverterTest()
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
     * Test method for {@link de.uni_leipzig.informatik.swp13_sc.converter.PGNToChessDataModelConverter#parseGame(java.util.List, java.util.List)}.
     */
    @SuppressWarnings("javadoc")
    @Test
    public final void testParseGame()
    {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link de.uni_leipzig.informatik.swp13_sc.converter.PGNToChessDataModelConverter#normalizeName(java.lang.String)}.
     */
    @SuppressWarnings("javadoc")
    @Test
    public final void testNormalizeName()
    {
        String[] ss = new String[] {"Mustermann, Max (bl)",
                "Mustermann, Max, der 2. (bl)", "Mustermann, Max",
                "Mustermann (1.), Max (bl)", "Mustermann(, Max (bl))",
                "Mustermann, Max III.", "Mustermann, Max, der 2. (bl)",
                "Mus ter. mann, Ma x", "", "  ", "Mustermann", null,
                "Mustermann, Max, der 2.[bl]", "Mus ter. mann, Ma x",
                "Adams, M. (wh)"};
        
        for (String s : ss)
        {
            try
            {
                /*System.out.println("\"" + s + "\" -> \"" +
                        PGNToChessDataModelConverter.normalizeName(s) + "\"");*/
            }
            catch (Exception e)
            {   
            }
        }
    }

}
