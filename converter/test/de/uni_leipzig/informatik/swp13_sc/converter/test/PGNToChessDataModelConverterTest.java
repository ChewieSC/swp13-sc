/**
 * PGNToChessDataModelConverterTest.java
 */

package de.uni_leipzig.informatik.swp13_sc.converter.test;

import static org.junit.Assert.*;

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
     * Basis constructor
     */
    public PGNToChessDataModelConverterTest()
    {
        PGNToChessDataModelConverter c = new PGNToChessDataModelConverter(
                "C:/temp/Ashley.pgn", "C:/temp/Ashley.pgn.ttl");
        c.parse();
        c.convert();
        c.write();
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
     * Test method for
     * {@link de.uni_leipzig.informatik.swp13_sc.converter.PGNToChessDataModelConverter#PGNToChessDataModelConverter(java.lang.String, java.lang.String)}
     * .
     */
    @Test
    public final void testPGNToChessDataModelConverter()
    {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for
     * {@link de.uni_leipzig.informatik.swp13_sc.converter.PGNToChessDataModelConverter#parse()}
     * .
     */
    @Test
    public final void testParse()
    {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for
     * {@link de.uni_leipzig.informatik.swp13_sc.converter.PGNToChessDataModelConverter#convert()}
     * .
     */
    @Test
    public final void testConvert()
    {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for
     * {@link de.uni_leipzig.informatik.swp13_sc.converter.PGNToChessDataModelConverter#write()}
     * .
     */
    @Test
    public final void testWrite()
    {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for
     * {@link de.uni_leipzig.informatik.swp13_sc.converter.PGNToChessDataModelConverter#write(java.lang.String)}
     * .
     */
    @Test
    public final void testWriteString()
    {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for
     * {@link de.uni_leipzig.informatik.swp13_sc.converter.PGNToChessDataModelConverter#openInputStream(java.lang.String)}
     * .
     */
    @SuppressWarnings("javadoc")
    @Test
    public final void testOpenInputStream()
    {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for
     * {@link de.uni_leipzig.informatik.swp13_sc.converter.PGNToChessDataModelConverter#openOutputStream(java.lang.String)}
     * .
     */
    @SuppressWarnings("javadoc")
    @Test
    public final void testOpenOutputStream()
    {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for
     * {@link de.uni_leipzig.informatik.swp13_sc.converter.PGNToChessDataModelConverter#openReader(java.lang.String)}
     * .
     */
    @SuppressWarnings("javadoc")
    @Test
    public final void testOpenReader()
    {
        fail("Not yet implemented"); // TODO
    }

}
