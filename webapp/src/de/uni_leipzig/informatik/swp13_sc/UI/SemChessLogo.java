package de.uni_leipzig.informatik.swp13_sc.ui;


import java.io.File;

import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.Panel;

public class SemChessLogo extends GridLayout{

	String basepath;
	FileResource resource ;
	Image image;
	
	public SemChessLogo(int x, int y)
	{
	super(x,y);
	// Find the application directory
	basepath = VaadinService.getCurrent()
	             .getBaseDirectory().getAbsolutePath();

	// Image as a file resource
	resource = new FileResource(new File(basepath +
		                        "/WEB-INF/images/SemChessLogo.png"));

	// Shows the image in the application
	image = new Image("Image from file", resource);
	
	
	setSizeFull();
     
    addComponent(image,0,0);
    setComponentAlignment(image, Alignment.MIDDLE_CENTER);
		

	}
	
	

}
