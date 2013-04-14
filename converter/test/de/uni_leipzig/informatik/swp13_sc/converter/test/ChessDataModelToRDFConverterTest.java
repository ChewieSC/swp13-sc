/**
 * ChessDataModelToRDFConverterTest.java
 */
package de.uni_leipzig.informatik.swp13_sc.converter.test;

import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test Case for ChessDataModelToRDFConverter.
 *
 * @author Erik
 *
 */
public class ChessDataModelToRDFConverterTest
{

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
     * Test method for {@link de.uni_leipzig.informatik.swp13_sc.converter.ChessDataModelToRDFConverter#convert(de.uni_leipzig.informatik.swp13_sc.datamodel.ChessGame)}.
     */
    @Test
    public final void testConvertChessGame()
    {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link de.uni_leipzig.informatik.swp13_sc.converter.ChessDataModelToRDFConverter#convert(java.util.List)}.
     */
    @Test
    public final void testConvertListOfChessGame()
    {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link de.uni_leipzig.informatik.swp13_sc.converter.ChessDataModelToRDFConverter#flushToStream(java.io.OutputStream, de.uni_leipzig.informatik.swp13_sc.converter.ChessDataModelToRDFConverter.OutputFormats)}.
     */
    @SuppressWarnings("javadoc")
    @Test
    public final void testFlushToStream()
    {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link de.uni_leipzig.informatik.swp13_sc.converter.ChessDataModelToRDFConverter#getNormalizedString(java.lang.String)}.
     * Works only if accessor for getNormalizedString() in
     * ChessDataModelToRDFConverter.java is public!
     */
    @SuppressWarnings("javadoc")
    @Test
    public final void testGetNormalizedString()
    {
        /*String s1 = "abc";
        String s2 = ChessDataModelToRDFConverter
                .getNormalizedString("abc");
        char[] c1 = "abc".toCharArray();
        char[] c2 = ChessDataModelToRDFConverter
                .getNormalizedString("abc").toCharArray();
        char[] c3 = s1.toCharArray();
        char[] c4 = s2.toCharArray();*/
        
        /*assertArrayEquals("abc".toCharArray(),
                (ChessDataModelToRDFConverter
                .getNormalizedString("abc")).toCharArray());
        assertArrayEquals("aBC_de45_tj___jk".toCharArray(),
                ChessDataModelToRDFConverter
                .getNormalizedString("aBC%de45,tj(=!jk").toCharArray());
        assertArrayEquals("____3".toCharArray(),
                ChessDataModelToRDFConverter
                .getNormalizedString("���").toCharArray());*/
    }

    /**
     * Test method for {@link de.uni_leipzig.informatik.swp13_sc.converter.ChessDataModelToRDFConverter#getUniqueGameName(java.lang.String, java.lang.String, java.lang.String)}.
     */
    @SuppressWarnings("javadoc")
    @Test
    public final void testGetUniqueGameName()
    {
        fail("Not yet implemented"); // TODO
    }

}
