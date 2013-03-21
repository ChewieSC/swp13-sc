/**
 * SimpleConverter.java
 */

package de.uni_leipzig.informatik.swp13_sc.converter;

import de.uni_leipzig.informatik.swp13_sc.converter.Converter;
import de.uni_leipzig.informatik.swp13_sc.converter.input.PGNFileInput;

public class SimpleConverter extends Converter
{
   

   public SimpleConverter()
   {
       this.loadInputsAndOutputs();
   }

   private void loadInputsAndOutputs()
   {
       super.addInput(new PGNFileInput(this));
   }
   
   
}
