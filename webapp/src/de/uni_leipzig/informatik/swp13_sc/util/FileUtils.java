/**
 * FileUtils.java
 */
package de.uni_leipzig.informatik.swp13_sc.util;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.zip.GZIPOutputStream;

/**
 * Some commonly used functions for working with files.
 *
 * @author Erik
 *
 */
public class FileUtils
{
    /**
     * Opens a (text file) writer with the given outputStream.
     * 
     * @param   outputStream     (File)InputStream to read from.
     * @return  BufferedWriter or null if error
     */
    public static BufferedWriter openWriter(OutputStream outputStream)
    {
        if (outputStream == null)
        {
            return null;
        }
        
        DataOutputStream dos = new DataOutputStream(outputStream);
        // default charset?
        OutputStreamWriter osr = new OutputStreamWriter(dos, Charset.defaultCharset());
        BufferedWriter bw = new BufferedWriter(osr);
        
        return bw;
    }
    
    /**
     * Opens a (text file) reader with the given inputStream.
     * 
     * @param   inputStream     (File)InputStream to read from.
     * @return  BufferedReader or null if error
     */
    public static BufferedReader openReader(InputStream inputStream)
    {
        if (inputStream == null)
        {
            return null;
        }
        
        DataInputStream dis = new DataInputStream(inputStream);
        // default charset?
        InputStreamReader isr = new InputStreamReader(dis, Charset.defaultCharset());
        BufferedReader br = new BufferedReader(isr);
        
        return br;
    }
    
    // ------------------------------------------------------------------------
    
    /**
     * Opens a new FileOutputStream.
     * 
     * @param   outputFilename  File to write into. Old content will be
     *                          overwritten
     * @return  FileOutputStream or null if error
     */
    public static FileOutputStream openOutputStream(String outputFilename)
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
        catch (Exception e)
        {
            // FileNotFoundException
            // SecurityException
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * Opens a new GZIPOutputStream for compressed output.
     * 
     * @param   outputFilename  File to write into.
     * @return  GZIPOutputStream (BufferedOutputStream in GZIPOutputStream)
     *          or null if error.
     * @see     http://stackoverflow.com/questions/1082320/what-order-should-i-use-gzipoutputstream-and-bufferedoutputstream
     */
    @SuppressWarnings("javadoc")
    public static GZIPOutputStream openGZIPOutputStream(String outputFilename)
    {
        if (outputFilename == null)
        {
            return null;
        }
        
        // normal file stream
        FileOutputStream fos = openOutputStream(outputFilename + ".gz");
        if (fos == null)
        {
            return null;
        }
        
        // BufferedOutputStream to increase performance
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        
        // gzip file stream
        GZIPOutputStream zos;
        try
        {
            zos = new GZIPOutputStream(bos);
        }
        catch(IOException e)
        {
            // ignore e.printStackTrace();
            return null;
        }
        
        return zos;
    }
    
    /**
     * Opens a new FileInputStream.
     * 
     * @param   inputFilename   File to open for reading.
     * @return  FileInputStream or null if error
     */
    public static FileInputStream openInputStream(String inputFilename)
    {
        if (inputFilename == null)
        {
            return null;
        }
        // try opening the input
        try
        {
            return new FileInputStream(inputFilename);
        }
        catch (Exception e)
        {
            // FileNotFoundException
            // SecurityException
            e.printStackTrace();
        }
        return null;
    }
}
