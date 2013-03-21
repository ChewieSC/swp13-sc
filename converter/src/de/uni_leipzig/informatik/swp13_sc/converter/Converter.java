/**
 * Converter.java
 */

package de.uni_leipzig.informatik.swp13_sc.converter;

import de.uni_leipzig.informatik.swp13_sc.converter.input.Input;
import de.uni_leipzig.informatik.swp13_sc.converter.output.Output;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Converter
{
    // threadsafe datastore (concurrent, ...)
    
    // selected In-/Output classes for converting
    private Input input;
    private Output output;
    
    // TODO: make it static??
    //       or simple List?
    private Map<String, Input> inputFormats = new HashMap<String, Input>();
    private Map<String, Output> outputFormats = new HashMap<String, Output>();
    
    
    
    private loadInputsAndOutputs()
    {
        // TODO: how???
        this.inputFormats.add("pgn", new PGNFileInput()); // each Converter has an instance
                                                          // cannot be static
        this.inputFormats.add("pgn", PGNFileInput.class); // Class type
                                                          // needs reflection ...
                                                          // but for dynamic loading good
                                                          // e.g. load from dir all input classes
        this.inputFormats.add(PGNFileInput.getFormat(), new PGNFileInput());
                                                          // probably the best ...
    }
    
    public List<String> getInputFormats()
    {
        ArrayList<String> formats = new ArrayList<String>();
        for (String format : this.inputFormats.keySet())
        {
            formats.add(format);
        }
        return formats;
    }
    
    public List<String> getOutputFormats()
    {
        ArrayList<String> formats = new ArrayList<String>();
        for (String format : this.outputFormats.keySet())
        {
            formats.add(format);
        }
        return formats;
    }
}
