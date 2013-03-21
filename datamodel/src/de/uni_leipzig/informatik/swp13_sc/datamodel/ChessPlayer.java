/**
 * ChessPlayer.java
 */
 
package de.uni_leipzig.informatik.swt13_sc.datamodel;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ChessPlayer
{
    private String givenName = null;
    private String surName = null;
    private String name = null; // ?
    private Date birthDate = null;
    private Date deathDate = null;
    private int elo = 0;
    private String nationality = null;
    private Map<String, String> props = new HashMap<String, String>();
    
    // TODO: complete, please.
    
    public ChessPlayer(String name)
    {
        this.name = name;
    }
    
    // TODO: think of useful constructors
    
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
    
    public String getGivenName()
    {
        return this.givenName;
    }
    
    public void setGivenName(String name)
    {
        this.givenName = name;
    }
    
    public String getSurname()
    {
        return this.surName;
    }
    
    public void setSurname(String name)
    {
        this.surName = name;
    }
    
    public int getELO()
    {
        return this.elo;
    }
    
    public void setELO(int elo)
    {
        this.elo = elo;
    }
    
    public String getNationality()
    {
        return this.nationality;
    }
    
    public void setNationality(String nationality)
    {
        this.nationality = nationality;
    }
    
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
    
    public String setValue(String key, String value)
    {
        return this.props.put(key, value);
    }
}
