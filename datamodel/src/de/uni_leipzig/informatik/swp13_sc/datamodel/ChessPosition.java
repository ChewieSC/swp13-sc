/**
 * ChessPosition.java
 */

package de.uni_leipzig.informatik.swp13_sc.datamodel;

import java.lang.Byte;
import java.lang.IndexOutOfBoundsException;
import java.lang.NumberFormatException;

public enum ChessPosition
{
    A1, A2, A3, A4, A5, A6, A7, A8,
    B1, B2, B3, B4, B5, B6, B7, B8,
    C1, C2, C3, C4, C5, C6, C7, C8,
    D1, D2, D3, D4, D5, D6, D7, D8,
    E1, E2, E3, E4, E5, E6, E7, E8,
    F1, F2, F3, F4, F5, F6, F7, F8,
    G1, G2, G3, G4, G5, G6, G7, G8,
    H1, H2, H3, H4, H5, H6, H7, H8;
    
    private final char letter;
    private final byte number;
    
    private ChessPosition()
    {
        letter = this.name().toLowerCase().charAt(0);
        byte temp = 0;
        try
        {
            temp = Byte.parseByte(this.name().substring(1));
        }
        catch (NumberFormatException e)
        {
            // TODO: log warn!
        }
        catch (IndexOutOfBoundsException e)
        {
            // TODO: log warn!
        }
        finally
        {
            number = temp;
        }
    }
    
    @Override
    public String toString()
    {
        return this.name().toLowerCase();
    }
    
    public boolean equalTo(String pos)
    {
        return this.name().equalsIgnoreCase(pos);
    }
    
    public char getLetter()
    {
        return letter;
    }
    
    public byte getNumber()
    {
        return number;
    }
    
    public int getPosX()
    {
        // TODO: check please.
        return this.getLetter() - 'a';
    }
    
    public int getPosY()
    {
        return (int) this.getNumber() - 1;
    }
}
