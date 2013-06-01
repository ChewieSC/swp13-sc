package de.uni_leipzig.informatik.swp13_sc.ui;

import java.io.File;

import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Image;

public class SemChessLogo extends GridLayout
{
    private static final long serialVersionUID = 1L;
    
    String basepath;
    FileResource resource;
    Image image;

    public SemChessLogo(int x, int y)
    {
        super(x, y);
        
        // Find the application directory
        basepath = VaadinService.getCurrent().getBaseDirectory()
                .getAbsolutePath();

        // Image as a file resource
        System.out.println();
        resource = new FileResource(new File(basepath
                + "/WEB-INF/images/SemChessLogo.png"));

        // Shows the image in the application
        //image = new Image("Image from file", resource);
        image = new Image();
        image.setSource(resource);

        setSizeFull();

        addComponent(image, 0, 0);
        setComponentAlignment(image, Alignment.MIDDLE_CENTER);
    }
}
