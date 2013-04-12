/**
 * PGNToRDFConverterRanged.java
 */
package de.uni_leipzig.informatik.swp13_sc.converter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.IllegalFormatException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipOutputStream;

import de.uni_leipzig.informatik.swp13_sc.converter.ChessDataModelToRDFConverter.OutputFormats;

/**
 * A PGN converter implementation.<br />
 * It takes a number which determines the split rate while outputting the
 * games into a archive.
 *
 * @author Erik
 *
 */
public class PGNToRDFConverterRanged
{
    private final static String PARAM_SPLIT_NUMBER = "--split";
    //private final static String PARAM_PROCESSING_NUMBER = "--processsplit";
    private final static String PARAM_COMPRESS_LEVEL = "--compresslevel";
    private final static String PARAM_DO_NOT_COMPRESS = "--dontcompress";
    private final static String PARAM_OUTPUT_FORMAT = "--format";
    private final static String PARAM_HELP = "--help";
    
    private int count = 1000; // default split rate for outputting
    //private int procCount = count; // default split rate while parsing
    private int compressLevel = 9; // highest compression
    private boolean dontcompress = false;
    private OutputFormats outFormat = OutputFormats.TURTLE;
    private List<String> files = new ArrayList<String>();
    
    /**
     * Base constructor
     */
    public PGNToRDFConverterRanged()
    {
        
    }
    
    /**
     * Entry point. Spins everything off.
     * 
     * @param   args    arguments from command line
     */
    public void start(String[] args)
    {
        this.parseArguments(args);
        this.processFiles();
    }
    
    /**
     * Parses the input arguments.
     * 
     * @param   args    arguments from command line
     */
    private void parseArguments(String[] args)
    {
        System.out.print("Parsing arguments ...");
        for (int i = 0; i < args.length; i ++)
        {
            String arg = args[i];
            if (arg.startsWith(PARAM_SPLIT_NUMBER))
            {
                int j = arg.indexOf('=');
                if (j != -1)
                {
                    String number = arg.substring(j + 1);
                    try
                    {
                        this.count = Integer.parseInt(number);
                    }
                    catch (NumberFormatException e)
                    {
                        // ignore
                    }
                    // 0 makes no sense
                    // less means all
                    if (this.count <= 0)
                    {
                        this.count = PGNToChessDataModelConverter.ALL_GAMES;
                    }
                }
            }
            else if (arg.startsWith(PARAM_COMPRESS_LEVEL))
            {
                int j = arg.indexOf('=');
                if (j != -1)
                {
                    String number = arg.substring(j + 1);
                    try
                    {
                        this.compressLevel = Integer.parseInt(number);
                    }
                    catch (NumberFormatException e)
                    {
                        // ignore
                    }
                    if (this.compressLevel > 9)
                    {
                        this.compressLevel = 9;
                    }
                    if (this.compressLevel <= -1)
                    {
                        this.dontcompress = true;
                    }
                }
            }
            else if (arg.startsWith(PARAM_DO_NOT_COMPRESS))
            {
                this.compressLevel = -1;
                this.dontcompress = true;
            }
            else if (arg.startsWith(PARAM_OUTPUT_FORMAT))
            {
                int j = arg.indexOf('=');
                if (j != -1)
                {
                    String f = arg.substring(j + 1);
                    for (OutputFormats fo : OutputFormats.values())
                    {
                        if (fo.getFormat().equalsIgnoreCase(f))
                        {
                            this.outFormat = fo;
                            break;
                        }
                    }
                }
            }
            else if (arg.startsWith(PARAM_HELP))
            {
                printHelp(); 
            }
            else
            {
                this.files.add(arg);
            }
        }        
        System.out.println(" finished.");
        
        System.out.println("  Using split number of:      " + count +
                ((count == PGNToChessDataModelConverter.ALL_GAMES)?"ALL_GAMES":""));
        System.out.println("  Using compression level of: " + compressLevel);
        System.out.println("  Using compression:          " + (! dontcompress));
        System.out.println("  Using output format:        " + outFormat.getFormat());
        System.out.println();
    }
    
    /**
     * Prints the help string and exits the programme after that.
     */
    private void printHelp()
    {
        System.out.println();
        System.out.println("Help to PGNToRDFConverterRanged");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("Usage:");
        System.out.println("\tjava -jar <archiv>.jar [options] <file> [<files> ...]");
        System.out.println();
        System.out.println("\t" + PARAM_HELP + "\t\t\tPrints this message.");
        System.out.println("\t" + PARAM_SPLIT_NUMBER + "=<number>\tSplitts the output"
                + " file / zip entries after\n\t\t\t\t<number> processed chess games."
                + "\n\t\t\t\tIf the <number> is -1 it will output all.\n\t\t\t\t"
                + "level:\t0 == STORE ... 9 == HIGHEST");
        System.out.println("\t" + PARAM_COMPRESS_LEVEL + "=<0-9>\tSets the compression"
                + " level of the zip output\n\t\t\t\tarchive. If it is -1 it won't compress.");
        System.out.println("\t" + PARAM_DO_NOT_COMPRESS + "\t\tForces the programm to"
                + " output all data into a\n\t\t\t\tuncompressed state.\n\t\t\t\t"
                + PARAM_COMPRESS_LEVEL + " == -1!");
        /*System.out.println("\t" + PARAM_OUTPUT_FILE + "=<file>\t\tUses the specified"
                + " output file if only a single\n\t\t\t\tinput file exists. If there"
                + " are more input\n\t\t\t\tfiles this parameter will be ignored.");*/
        System.out.print("\t" + PARAM_OUTPUT_FORMAT + "=<format>\tUses the specified"
                + "output format for the RDF\n\t\t\t\tdata. The standard format is TURTLE."
                + "\n\t\t\t\tAvailable output formats are:\n\t\t\t\t");
        System.out.println("{TURTLE, N-TRIPLE, RDF/XML, RDF/XML-ABBREV}");
        System.out.println();
        System.out.println("Examples:");
        System.out.println("- If the archive name is PGNConverter.jar and the file separator '/' ...");
        System.out.println("#> java -jar PGNConverter.jar dir1/dir2/file.pgn");
        System.out.println("#> java -jar PGNConverter.jar dir1/dir2/*.pgn");
        System.out.println("#> java -jar PGNConverter.jar file.pgn file2.pgn");
        System.out.println("#> java -jar PGNConverter.jar --split=250 file.pgn");
        System.out.println("#> java -jar PGNConverter.jar --format=RDF/XML file.pgn");
        
        try
        {
            System.exit(0);
        }
        catch (SecurityException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Processes the input files. Parse & Convert.
     */
    private void processFiles()
    {
        if (files.size() == 0)
        {
            System.out.println("No input files! ... Exiting!");
            printHelp();
        }
        
        long startProcessing = System.currentTimeMillis();
        
        for (int i = 0; i < files.size(); i ++)
        {
            System.out.println("Processing input file " + (i+1) + "/" + files.size() + ".");
            
            // ----------------------------------------------------------------
            // get file names
            String input = files.get(i);
            String ext = null;
            if (input.indexOf('.') != -1)
            {
                ext = input.substring(input.lastIndexOf('.') + 1);
            }            
            String output;
            // multiple input files -> multiple output files ...
            if (ext == null)
            {
                output = input;
            }
            else
            {
                output = input.substring(0, input.lastIndexOf('.'));
            }
            output = output + "." + outFormat.getExtension();
            
            
            System.out.println("Converting PGN-File <" + input
                    + "> to RDF (" + outFormat.getFormat() + ") <" +
                    output + "> ...");
            long startFile = System.currentTimeMillis();
            
            // ----------------------------------------------------------------
                        
            if (! this.dontcompress)
            {
                this.processToZipStream(input, output);
            }
            else
            {
                this.processToStream(input, output);
            }
            
            // ----------------------------------------------------------------
            
            System.out.println("Finished input file. Took " + 
                    ((System.currentTimeMillis() - startFile) / 1000.0) +
                    " seconds.");
            
            
            // ----------------------------------------------------------------            
            
        }
        
        System.out.println("Took " + ((System.currentTimeMillis() - startProcessing) / 1000.0) + " seconds total.");
    }
    
    /**
     * "OutputHandler" for using Zip files.
     * 
     * @param   input   input filename
     * @param   output  output filename
     * @return  true if successful else false
     */
    protected boolean processToZipStream(String input, String output)
    {
        String outputZip = output + ".zip";
        
        // get relative path
        String relPath = output;
        try
        {
            File inputFile = new File(input);
            if (! inputFile.exists())
            {
                System.out.println("Input file <" + input + "> doesn't exist!");
                System.out.println();
                return false;
            }
            File parentFile = inputFile.getParentFile();
            
            if (parentFile != null)
            {
                relPath = parentFile.toURI().relativize(inputFile.toURI()).getPath();
            }
        }
        catch (NullPointerException e)
        {
            e.printStackTrace();
        }
        // without extension
        relPath = (relPath.indexOf('.') != -1) ?
                relPath.substring(0, relPath.lastIndexOf('.')) : relPath;
        
        // --------------------------------------------------------------------
        
        // open outputfile
        FileOutputStream fos = openOutputStream(outputZip);
        if (fos == null)
        {
            return false;
        }
        ZipOutputStream zos = new ZipOutputStream(fos);
        zos.setLevel(compressLevel);
        
        // --------------------------------------------------------------------
        
        // parse & convert
        long startFile = System.currentTimeMillis();
        PGNToChessDataModelConverter converter = new PGNToChessDataModelConverter();
        converter.setInputFilename(input);
        
        int nr = 0;
        while (! converter.finishedInputFile())
        {
            nr ++;
            System.out.println("  Working on Part: " + nr);
            
            // Parse only count games.
            System.out.print("    Parsing data ...");
            converter.parse(count);
            System.out.println(" finished.");
            
            // Convert all the parsed games in memory.
            System.out.print("    Converting data ...");
            converter.convert(PGNToChessDataModelConverter.ALL_GAMES);
            System.out.println(" finished");
            
            // Output all the converted games.
            // compute entry name
            String entryName = relPath;
            if ((nr != 1) || (! converter.finishedInputFile()))
            {
                // two or more entries
                try
                {
                    entryName = String.format("%s.part_%03d", entryName, nr);
                }
                catch (IllegalFormatException e)
                {
                    e.printStackTrace();
                }
                catch (NullPointerException e)
                {
                    e.printStackTrace();
                }
            }
            entryName = entryName + "." + outFormat.getExtension();
            
            System.out.print("    Writing Zip-Archive-Entry: " + entryName + " ...");
            
            // generate entry
            try
            {
                ZipEntry ze = new ZipEntry(entryName);
                zos.putNextEntry(ze);
                
                // write to stream
                converter.write(zos, outFormat);
                
                zos.closeEntry();
                zos.flush();
            }
            catch (Exception e)
            {
                // NullPointerException
                // IllegalArgumentException
                // ZipException
                // IOException
                e.printStackTrace();
            }
            
            System.out.println(" finished.");
        }
        
        // write the generate chess game names into a file
        try
        {
            ZipEntry ze = new ZipEntry("generateChessGameName.txt");
            zos.putNextEntry(ze);
            converter.writeConvertedGameNames(zos);
            zos.closeEntry();
            zos.flush();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        System.out.println("  Processed " + converter.numberOfParsedGames() +
                " games in " + ((System.currentTimeMillis() - startFile) / 1000.0) +
                " seconds. Wrote " + nr + " part(s) to Zip-Archive " + outputZip + ".");
        
        // --------------------------------------------------------------------
        // close output file
        try
        {
            zos.close();
        }
        catch (ZipException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        
        return true;
    }
    
    /**
     * "OutputHandler" for normal FileOutputStreams. Processes the input file
     * and writes it into output.
     * 
     * @param   input   input filename
     * @param   output  output filenam
     * @return  true if successful else false
     */
    protected boolean processToStream(String input, String output)
    {
        // parse & convert
        long startFile = System.currentTimeMillis();
        PGNToChessDataModelConverter converter = new PGNToChessDataModelConverter();
        converter.setInputFilename(input);
        
        int nr = 0;
        while (! converter.finishedInputFile())
        {
            nr ++;
            System.out.println("  Working on Part: " + nr);
            
            System.out.print("    Parsing data ...");
            converter.parse(count);
            System.out.println(" finished.");
            
            System.out.print("    Converting data ...");
            converter.convert(PGNToChessDataModelConverter.ALL_GAMES);
            System.out.println(" finished");
            
            // compute file part name
            // without extension
            String filePartName = (output.indexOf('.') != -1) ?
                    output.substring(0, output.lastIndexOf('.')) : output;
            if ((nr != 1) || (! converter.finishedInputFile()))
            {
                // two or more entries
                try
                {
                    filePartName = String.format("%s.part_%03d", filePartName, nr);
                }
                catch (IllegalFormatException e)
                {
                    e.printStackTrace();
                }
                catch (NullPointerException e)
                {
                    e.printStackTrace();
                }
            }
            filePartName = filePartName + "." + outFormat.getExtension();
            
            System.out.print("    Writing File-Part: " + filePartName + " ...");
            
            // generate new OutputStream
            try
            {
                // open outputfile
                FileOutputStream fos = openOutputStream(output);
                if (fos == null)
                {
                    System.out.println("    Couldn't open output file <" +
                            filePartName + "> ! Skipping outputting data!");
                    continue;
                    //return false;
                }
                
                // write to stream
                converter.write(fos, outFormat);
                
                fos.flush();
                fos.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            
            System.out.println(" finished.");
        }
        
        System.out.println("  Processed " + converter.numberOfParsedGames() +
                " games in " + ((System.currentTimeMillis() - startFile) / 1000.0) +
                " seconds. Wrote " + nr + " File(-Parts).");
        
        return true;
    }
    
    /**
     * Opens a new FileOutputStream.
     * 
     * @param   outputFilename  File to write into. Old content will be
     *                          overwritten
     * @return  FileOutputStream or null if error
     */
    protected FileOutputStream openOutputStream(String outputFilename)
    {
        if (outputFilename == null)
        {
            return null;
        }
        // try opening output file
        try
        {
            return new FileOutputStream(outputFilename);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (SecurityException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    
    
    /**
     * Main method.
     * 
     * @param   args
     */
    public static void main(String[] args)
    {
        PGNToRDFConverterRanged converter = new PGNToRDFConverterRanged();
        converter.start(args);        
    }
}
