/**
 * Configuration.java
 */

package de.uni_leipzig.informatik.swp13_sc.util;

import java.io.File;
import java.io.OutputStream;
import java.util.Properties;

/**
 * A configuration class for easier and centralized access to common
 * configurations. e. g. the Virtuoso triple store access ...
 * 
 * @author Erik
 */
public class Configuration
{
    /**
     * The File object in which the configurations are stored.
     */
    protected final File configFile;
    /**
     * The actual configurations.
     */
    protected final Properties configs;
    /**
     * The default configurations.
     */
    protected final Properties defaultConfigs;
    
    // ------------------------------------------------------------------------
    
    /**
     * Default configuration file name.
     */
    public final static String CONFIG_FILE_NAME = "swp13_sc.config";
    
    /**
     * Key for virtuoso user name setting.
     */
    public final static String CONFIG_VIRT_USER = "virtuoso.username";
    /**
     * Key for virtuoso password setting.
     */
    public final static String CONFIG_VIRT_PASS = "virtuoso.password";
    /**
     * Key for virtuoso host name setting.
     */
    public final static String CONFIG_VIRT_HOST = "virtuoso.hostname";
    /**
     * Key for virtuoso base graph name setting.
     */
    public final static String CONFIG_VIRT_GRAPH = "virtuoso.basegraph";
    
    // ------------------------------------------------------------------------
    
    /**
     * Tries to create a new Configuration object with a default file name.
     * 
     * @throws  IllegalArgumentException
     */
    public Configuration()
            throws IllegalArgumentException
    {
        this(CONFIG_FILE_NAME);
    }
    
    /**
     * Creates a Configuration object and tries to load its values from the
     * specified file. If no value could be found it will try to create a
     * default file so it can be modified later as it serves as an example.
     * 
     * @param   configFile  file to load the configuration values from
     * @throws  IllegalArgumentException
     */
    public Configuration(String configFile)
            throws IllegalArgumentException
    {
        if (configFile == null || "".equalsIgnoreCase(configFile.trim()))
        {
            throw new IllegalArgumentException("Argument <configFile> is null or empty!");
        }
        this.configFile = new File(configFile);
        
        this.defaultConfigs = getDefaultKonfiguration();
        this.configs = new Properties(this.defaultConfigs);
        
        try
        {
            this.configs.load(FileUtils.openInputStream(this.configFile.getPath()));
        }
        catch (Exception e)
        {
            // IOException
            // FileNotFoundException [from FileUtils#openInputStream(String)]
            // IllegalArgumentException
            System.err.println("Could not load configuration file! Creating default file ...");
            e.printStackTrace();

            try
            {
                OutputStream os = FileUtils.openOutputStream(this.configFile
                        .getPath());
                this.defaultConfigs.store(os, "Default configurations for swp13-sc project");
                os.close();
                System.out.println("Wrote default configuration file to: " +
                        this.configFile.getAbsolutePath());
            }
            catch (Exception e1)
            {
                // IOException
                // ClassCastException
                // NullPointerException
                System.err.println("Could not write default configuration file!");
                e1.printStackTrace();
                
                System.out.println("Listing default configurations:");
                this.defaultConfigs.list(System.out);
            }
        }
    }
    
    // ------------------------------------------------------------------------
    
    /**
     * Returns the absolute path to the configuration file.
     * 
     * @return  String with file name and path
     */
    public String getConfigFilename()
    {
        return this.configFile.getAbsolutePath();
    }
    
    // ------------------------------------------------------------------------
    
    /**
     * Returns the user name credential of the virtuoso triple store connection.
     * 
     * @return  String with user name or null on error
     */
    public String getVirtuosoUsername()
    {
        return this.configs.getProperty(CONFIG_VIRT_USER);
    }
    
    /**
     * Returns the password credential of the virtuoso triple store connection.
     * 
     * @return  String with password or null on error
     */
    public String getVirtuosoPassword()
    {
        return this.configs.getProperty(CONFIG_VIRT_PASS);
    }
    
    /**
     * Returns the host name or URL to the virtuoso triple store.
     * 
     * @return  String with host name or null on error
     */
    public String getVirtuosoHostname()
    {
        return this.configs.getProperty(CONFIG_VIRT_HOST);
    }
    
    /**
     * Returns the base graph for VirtGraph connections.
     * 
     * @return  String with base graph or null on error
     */
    public String getVirtuosoBasegraph()
    {
        return this.configs.getProperty(CONFIG_VIRT_GRAPH);
    }
    
    // ------------------------------------------------------------------------
    
    /**
     * Sets a new configuration or adds a new value for the key and returns the
     * last value stored.
     * 
     * @see java.util.Properties#setProperty(String, String)
     */
    public String setProperty(String key, String value)
    {
        return (String) this.configs.setProperty(key, value);
    }
    
    /**
     * Returns a configuration value for this key or null if not found.
     * 
     * @see java.util.Properties#getProperty(String)
     */
    public String getProperty(String key)
    {
        return this.configs.getProperty(key);
    }
    
    /**
     * Returns a configuration value or the defaultValue if not found.
     * 
     * @see java.util.Properties#getProperty(String, String)
     */
    public String getProperty(String key, String defaultValue)
    {
        return this.configs.getProperty(key, defaultValue);
    }
    
    // ------------------------------------------------------------------------
    
    /**
     * Generates a new default Properties object as a base for custom settings.
     * 
     * @return  Properties
     */
    public Properties getDefaultKonfiguration()
    {
        Properties p = new Properties();
        p.setProperty(CONFIG_VIRT_USER, "dba");
        p.setProperty(CONFIG_VIRT_PASS, "dba");
        p.setProperty(CONFIG_VIRT_HOST, "localhost:1111");
        p.setProperty(CONFIG_VIRT_GRAPH, "");

        return p;
    }
}
