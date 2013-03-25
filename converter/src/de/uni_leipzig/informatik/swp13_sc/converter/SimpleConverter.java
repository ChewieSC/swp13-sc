/**
 * SimpleConverter.java
 */

package de.uni_leipzig.informatik.swp13_sc.converter;

import de.uni_leipzig.informatik.swp13_sc.converter.Converter;
import de.uni_leipzig.informatik.swp13_sc.converter.input.FileInput;
import de.uni_leipzig.informatik.swp13_sc.converter.input.Input;
import de.uni_leipzig.informatik.swp13_sc.converter.input.PGNFileInput;
import de.uni_leipzig.informatik.swp13_sc.converter.output.FileOutput;
import de.uni_leipzig.informatik.swp13_sc.converter.output.Output;

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
			Input i = super.getInput(inputFormat);
			if (i instanceof FileInput)
			{
				FileInput fi = (FileInput) i;
				fi.setFilename(this.inputArgs);
			}
			super.setInput(i);
		}
		if (this.supportsOutput(outputFormat))
		{
			this.outputArgs = outputArgs;
			Output o = super.getOutput(outputFormat);
			if (o instanceof FileOutput)
			{
				FileOutput fo = (FileOutput) o;
				// set data ... outputArgs
			}
			super.setOutput(o);
		}
		
		// TODO: args parsing / module choosing ...
	}

	/**
	 * Sample Module loading
	 */
	private void loadInputsAndOutputs()
	{
		super.addInput(new PGNFileInput(this));
		// TODO: ...
	}
	
	
	
	public static void main(String[] args)
	{
		SimpleConverter sc = new SimpleConverter("pgn", "filname.pgn", "ttl", "filename.ttl");
		
		sc.beginConvert();
	}
	
	
}
