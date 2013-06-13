/**
 * ECOToRDFConverter.java
 */
package de.uni_leipzig.informatik.swp13_sc.converter;

/**
 * Converts a ECO file into a rdf file.
 *
 * @author Erik
 */
public class ECOToRDFConverter
{

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        if (args.length < 1)
        {
            System.err.println("ERR: Arguments missing.");
            System.out.println("Usage: java -jar Program.jar <input file>");
            System.exit(1);
        }
        
        String inputFile = args[0];
        String outputFile = inputFile + ".ttl";
        
        System.out.println("INFO: Input file is: " + inputFile);
        System.out.println("INFO: Output file is: " + outputFile);

        ECOToChessDataModelConverter eco2cdm = new ECOToChessDataModelConverter(inputFile);
        ChessDataModelToRDFConverter cdm2rdf = new ChessDataModelToRDFConverter();
        
        if (! eco2cdm.parse())
        {
            System.out.println("ERR: Couldn't parse openings!");
            System.exit(2);
        }
        
        if (! cdm2rdf.convertOpenings(eco2cdm.getOpenings()))
        {
            System.out.println("ERR: Couldn't convert openings to rdf!");
            System.exit(3);
        }
        
        if (! cdm2rdf.write(outputFile))
        {
            System.out.println("ERR: Couldn't write openings to " + outputFile + "!");
            System.exit(4);
        }
    }

}
