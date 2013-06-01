package de.uni_leipzig.informatik.swp13_sc.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.vaadin.server.FileDownloader;
import com.vaadin.server.FileResource;
import com.vaadin.server.Resource;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.VerticalLayout;

import de.uni_leipzig.informatik.swp13_sc.converter.PGNToRDFConverterStream;
import de.uni_leipzig.informatik.swp13_sc.util.FileUtils;

/**
 * KLasse enhaelt das Layout sowie Funktionen des Upload und Konvertierens
 * 
 * @author LingLong
 * @author Chewie
 */
public class ConverterView extends VerticalLayout
{
    private static final long serialVersionUID = 1L;

    /** Inneres KonverterFensterLayout */
    private HorizontalLayout converterLayoutInner = new HorizontalLayout();

    /** Label Ueberschrift */
    private Label lbConverter = new Label("Konvertierung PGN - RDF");

    /** OutputLabel */
    private Label lblOut = new Label();

    /** AnzeigeLabel */
    private Label lblInfo = new Label();

    /** Textfeld für pgn-upload */
    // private TextField tfToPars = new TextField();

    /** Textfeld Für zu Parsende PGN */
    // private TextArea taToPars = new TextArea(); //erstmal rausgenommen

    /** Button fur Konvertierung */
    private Button btnDownload = new Button("Download");

    private String fileString = "";

    private File file;

    private String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath(); 

	Upload upload = new Upload("Upload RDF-File Here.", new Upload.Receiver() {
    	@SuppressWarnings("deprecation")
		@Override
    	public OutputStream receiveUpload(String filename, String mimeType) {
    		
    		lblInfo.setValue("");
    		FileOutputStream fos = null; // Stream to write to
            try {
                // Open the file for writing.
            	System.out.println(mimeType);
            	if(!filename.equals(getFileString())){
            		setFileString(filename);
            	}	
            	// TODO: check file for ending .pgn
            		file = new File(basepath + filename);
                    fos = new FileOutputStream(file);
                    //process                
            } catch (final java.io.FileNotFoundException e) {
                Notification.show(
                        "Could not open file", e.getMessage(),
                        Notification.TYPE_ERROR_MESSAGE);
                return null;
            } 
            return fos; // Return the output stream to write to
    	}
	   });

    // private FileDownloader fileDownloader;
    /* Button zum Suchen von Uploads */
    // TODO: Upload checken

    /**
     * Konstruktor fuer UploadView
     */
    public ConverterView()
    {
        // --------------Aufbau Konverterfenster---------------//

        addComponent(lbConverter);
        addComponent(converterLayoutInner);

        addComponent(upload);
//        addComponent(taToPars);
        addComponent(btnDownload);

        addComponent(lblOut);
        addComponent(lblInfo);

        // Anpassen Konverterfenster
        converterLayoutInner.setSizeFull();
        converterLayoutInner.setWidth("100%");
        setWidth("100%");

        // tfToPars.setWidth("100%");
//        taToPars.setWidth("100%");

        // converterLayoutInner.setExpandRatio(tfToPars, 1);
//        setExpandRatio(taToPars, 1);

        setMargin(true);
        setVisible(true);

        // converterLayoutInner.setMargin(true);
        // converterLayoutInner.setVisible(true);
    }

    @SuppressWarnings("deprecation")
	public void initUpload()
    {
    	upload.addListener(new Upload.SucceededListener() {	
    		@Override
    		public void uploadSucceeded(SucceededEvent event) {
    			String[] tokens = getFileString().split("\\.(?=[^\\.]+$)");
    			File fileTemp = new File(basepath + tokens[0] + ".rdf");
        		if(fileTemp.exists())
        			fileTemp.delete();
        		System.out.println("File Name in UploadSucceded: " + getFileString());    		
        		postPgn(basepath + getFileString(), basepath + tokens[0] + ".rdf");
    		}
    	});
    
    }

    // *------------------Anbindung der Komponenten an das
    // Datenmodell------------//

    public boolean postPgn( String pathIn, String pathOut) {
  	  
  	  System.out.println("Check Check");
  	  lblInfo.setValue("Parsen...Konvertieren...");
  	  FileInputStream is = FileUtils.openInputStream(pathIn);
  	  FileOutputStream os = FileUtils.openOutputStream(pathOut);
  	  PGNToRDFConverterStream p = new PGNToRDFConverterStream(is, os);
  	  boolean bValue = p.start();
  	  try {
  		is.close();
  		os.close();
	  } catch (IOException e) {
	  		e.printStackTrace();
	  }
	  	  

  	  if(bValue){
  		  System.out.println("Check Check Check");
  		  lblInfo.setValue("PGN wurde erfolgreich Konvertiert! Die RDF steht jetzt zum Donwload bereit.");
  		  btnDownload.setEnabled(true);
  		  FileResource res = new FileResource(new File(pathOut));
  		  FileDownloader fd = new FileDownloader(res);
  		  fd.extend(btnDownload);
  	  }

  	  else			
  		  lblInfo.setValue("Fehler beim Parsen/Konvertieren!");

  	return bValue;  
    }

    public void setFileString(String fileString)
    {
        this.fileString = fileString;
    }

    public String getFileString()
    {
        return fileString;
    }

}
