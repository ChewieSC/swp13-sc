/**
 * PGNToChessDataModelConverterTest.java
 */

package de.uni_leipzig.informatik.swp13_sc.converter.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.uni_leipzig.informatik.swp13_sc.converter.ChessDataModelToRDFConverter;
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
        PGNToChessDataModelConverter pgn2cdm = new PGNToChessDataModelConverter();
        pgn2cdm.setInputFilename("C:/temp/Ashley.pgn");
        ChessDataModelToRDFConverter cdm2rdf = new ChessDataModelToRDFConverter();
        pgn2cdm.parse();
        cdm2rdf.convert(pgn2cdm.getGames());
        cdm2rdf.write("C:/temp/Ashley.pgn.ttl");
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

}
