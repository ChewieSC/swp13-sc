/**
 * SimpleConverter.java
 */

package de.uni_leipzig.informatik.swp13_sc.converter;

import de.uni_leipzig.informatik.swp13_sc.converter.Converter;
import de.uni_leipzig.informatik.swp13_sc.converter.input.PGNFileInput;

/**
 *
 *
 * @author Erik
 *
 */
public class SimpleConverter extends Converter
{
	private String inputArgs;
	private String outputArgs;
	

	/**
	 * 
	 */
	public SimpleConverter(String inputFormat, String inputArgs, String outputFormat, String outputArgs)
	{
		this.loadInputsAndOutputs();
		
		if (this.supportsInput(inputFormat))
		{
			this.inputArgs = inputArgs;
			super.setInput(super.getInput(inputFormat));
		}
		if (this.supportsOutput(outputFormat))
		{
			this.outputArgs = outputArgs;
			super.setOutput(super.getOutput(outputFormat));
		}
		
		// TODO: args parsing / module choosing ...
	}

	/**
	 * 
	 */
	private void loadInputsAndOutputs()
	{
		super.addInput(new PGNFileInput(this));
		// TODO: ...
	}
	
	
}
