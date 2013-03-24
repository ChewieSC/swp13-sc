/**
 * ChessPlayer.java
 */
 
package de.uni_leipzig.informatik.swp13_sc.datamodel;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//TODO: needs a Builder?

/**
 *
 *
 * @author Erik
 *
 */
public class ChessPlayer
{
    /**
     * givenName
     */
    private String givenName = null;
    /**
     * surName
     */
    private String surName = null;
    /**
     * name
     */
    private String name = null; // ?
    /**
     * birthDate
     */
    private Date birthDate = null;
    /**
     * deathDate
     */
    private Date deathDate = null;
    /**
     * elo
     */
    private int elo = 0;
    /**
     * nationality
     */
    private String nationality = null;
    /**
     * props
     */
    private Map<String, String> props = new HashMap<String, String>();
    
    // TODO: complete, please.
    
    /**
     * @param name
     */
    public ChessPlayer(String name)
    {
        this.name = name;
    }
    
    // TODO: think of useful constructors
    
    /**
     * @return
     */
    public String getName()
    {
    	if (this.name == null)
    	{
    	    return this.givenName + " " + this.surName;
    	}
        else
        {
    	    return this.name;
        }
    }
    
    /**
     * @return
     */
    public String getGivenName()
    {
        return this.givenName;
    }
    
    /**
     * @param name
     */
    public void setGivenName(String name)
    {
        this.givenName = name;
    }
    
    /**
     * @return
     */
    public String getSurname()
    {
        return this.surName;
    }
    
    /**
     * @param name
     */
    public void setSurname(String name)
    {
        this.surName = name;
    }
    
    /**
     * @return
     */
    public int getELO()
    {
        return this.elo;
    }
    
    /**
     * @param elo
     */
    public void setELO(int elo)
    {
        this.elo = elo;
    }
    
    /**
     * @return
     */
    public String getNationality()
    {
        return this.nationality;
    }
    
    /**
     * @param nationality
     */
    public void setNationality(String nationality)
    {
        this.nationality = nationality;
    }
    
    /**
     * @param key
     * @return Value to given key, or empty String "" if key was not found.
     */
    public String getValue(String key)
    {
        if (this.props.containsKey(key))
        {
            return this.props.get(key);
        }
        else
        {
            return "";
        }
    }
    
    /**
     * @param key
     * @param value
     * @return
     */
    public String setValue(String key, String value)
    {
        return this.props.put(key, value);
    }
}
