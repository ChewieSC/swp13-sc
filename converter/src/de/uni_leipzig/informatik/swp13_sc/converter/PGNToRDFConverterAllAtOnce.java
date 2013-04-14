/**
 * PGNToRDFConverterAllAtOnce.java
 */
package de.uni_leipzig.informatik.swp13_sc.converter;


/**
 * Simple PGN -> RDF conversion programme.
 *
 * @author Erik
 *
 */
public class PGNToRDFConverterAllAtOnce
{

    /**
     * main method
     * 
     * @param   args    String file parameter arguments
     */
    public static void main(String[] args)
    {
        if (args.length < 1)
        {
            System.out.println("Usage:");
            System.out.println("\tjava -jar PGNConverter.jar <pgnfile> [<pgnfile> ...]");
            return;
        }
        for (int i = 0; i < args.length; i ++)
        {
            long startTime = System.currentTimeMillis();
            System.out.println("Processing file " + (i+1) + "/" + args.length + ".");
            System.out.println("Converting PGN-File <" + args[i] + "> to RDF (Turtle) <" + args[i] + ".ttl>");
            PGNToChessDataModelConverter pgn2cdm = new PGNToChessDataModelConverter();
            pgn2cdm.setInputFilename(args[i]);
            ChessDataModelToRDFConverter cdm2rdf = new ChessDataModelToRDFConverter();
            System.out.print("Parsing data ...");
            if (! pgn2cdm.parse())
            {
                System.out.println(" aborting!");
                return;
            }
            System.out.println(" finished.");
            System.out.print("Converting data ...");
            if (! cdm2rdf.convert(pgn2cdm.getGames()))
            {
                System.out.println(" aborting!");
                return;
            }
            System.out.println(" finished.");
            System.out.print("Writing data ...");
            if (! cdm2rdf.write(args[i] + ".ttl"))
            {
                System.out.println(" aborting!");
                continue;
            }
            System.out.println(" finished.");
            System.out.println("Took " + ((System.currentTimeMillis() - startTime) / 1000.0) + " seconds.");
            System.out.println();
        }        
    }
}
