/**
 * ChessPosition.java
 */

package de.uni_leipzig.informatik.swp13_sc.datamodel;

import java.lang.Byte;
import java.lang.IndexOutOfBoundsException;
import java.lang.NumberFormatException;

/**
 * Is a position on a ChessBoard.<br />
 * Ranges from A1, A2, ... to H8.
 *
 * @author Erik
 */
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
    
    /**
     * Is the first part of a {@link ChessPosition}.<br />
     * e. g. "h" of "h4"
     */
    private final char letter;
    /**
     * Is the second part of a {@link ChessPosition}.<br />
     * e. g. "4" of "h4"
     */
    private final byte number;
    
    /**
     * Internal. Is used to generate metadata fields of a
     * {@link ChessPosition}. Used in Getters.
     */
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
    
    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString()
    {
        return this.name().toLowerCase();
    }
    
    /**
     * Compares a given String pos with this position object.
     * @param pos String determining a position in chess.
     * @return true if String pos is this position.
     */
    public boolean equalTo(String pos)
    {
        return this.name().equalsIgnoreCase(pos);
    }
    
    /**
     * Returns the first part of a position. The Letter.
     * @return char Characterpart of {@link ChessPosition}
     */
    public char getLetter()
    {
        return letter;
    }
    
    /**
     * Returns the second part of a position. The Number.
     * @return byte Numberpart of {@link ChessPosition}
     */
    public byte getNumber()
    {
        return number;
    }
    
    /**
     * Returns an index for easier use in programs.
     * @return int 
     */
    public int getPosX()
    {
        // TODO: check please.
        return this.getLetter() - 'a';
    }
    
    /**
     * Returns an index for easier use in programs.
     * @return int
     */
    public int getPosY()
    {
        return (int) this.getNumber() - 1;
    }
}
