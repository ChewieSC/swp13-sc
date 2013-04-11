/**
 * PGNToRDFConverterRanged.java
 */
package de.uni_leipzig.informatik.swp13_sc.converter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.IllegalFormatException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipOutputStream;

//import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

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
    private final static String PARAM_COMPRESS_LEVEL = "--compresslevel";
    private final static String PARAM_OUTPUT_FORMAT = "--format";
    
    private int count = 1000; // default split rate
    private int compressLevel = 9; // highest compression
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
                }
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
            else
            {
                this.files.add(arg);
            }
        }
        System.out.println(" finished.");
        
        System.out.println("  Using split number of:      " + count);
        System.out.println("  Using compression level of: " + compressLevel);
        System.out.println("  Using output format:        " + outFormat.getFormat());
        System.out.println();
    }

    /**
     * Processes the input files. Parse & Convert.
     */
    private void processFiles()
    {
        if (files.size() == 0)
        {
            System.out.println("No input files! ... Exiting!");
            return;
        }
        
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
            if (ext == null)
            {
                output = input;
            }
            else
            {
                output = input.substring(0, input.lastIndexOf('.'));
            }
            output = output + "." + outFormat.getExtension();
            String outputZip = output + ".zip";
            
            System.out.println("Converting PGN-File <" + input
                    + "> to RDF (" + outFormat.getFormat() + ") <" +
                    outputZip + ">");
            
            // ----------------------------------------------------------------
            // get relative path
            String relPath = output;
            try
            {
                File inputFile = new File(input);
                if (! inputFile.exists())
                {
                    System.out.println("Input file <" + input + "> doesn't exist!");
                    System.out.println();
                    continue;
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
            relPath = relPath.substring(0, relPath.lastIndexOf('.'));
            
            // ----------------------------------------------------------------
            // open outputfile
            FileOutputStream fos = openOutputStream(outputZip);
            if (fos == null)
            {
                continue;
            }
            ZipOutputStream zos = new ZipOutputStream(fos, Charset.defaultCharset());
            zos.setLevel(compressLevel);
            
            // ----------------------------------------------------------------            
            // parse & convert
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
                
                // compute entry name
                String entryName = relPath;
                if ((nr == 1) && (converter.finishedInputFile()))
                {
                    // only one entry
                    //entryName = entryName + "." + outFormat.getExtension();
                }
                else
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
            
            System.out.println("Finished input file. Processed " + 
                    converter.numberOfParsedGames() + " Games. Wrote " + nr +
                    " part(s) to Zip-Archive " + outputZip + ".");
            System.out.println();
            
            // ----------------------------------------------------------------
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
        }
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
