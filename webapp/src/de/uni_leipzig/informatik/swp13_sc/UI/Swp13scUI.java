package de.uni_leipzig.informatik.swp13_sc.UI;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.vaadin.server.FileDownloader;
import com.vaadin.server.FileResource;
import com.vaadin.server.Resource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.UI;
import com.vaadin.ui.Upload;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.VerticalSplitPanel;

import de.uni_leipzig.informatik.swp13_sc.converter.PGNToRDFConverterStream;
import de.uni_leipzig.informatik.swp13_sc.util.FileUtils;



/**
 * Hauptklasse der semChess UI
 * @author LingLong
 *
 */
@SuppressWarnings("serial")
public class Swp13scUI extends UI
{
	//---------------Game-Explorer Instanzen---------------//
	                       //TODO

	//---------------Suchfenster Instanzen-----------------//
	   
	private SearchView searchView = new SearchView();  

	//--------------Konverterfenster Instazen-------------//
	/**Layout fuer Konverterfenster*/
	private VerticalLayout converterLayout = new VerticalLayout();
	/**Inneres KonverterFensterLayout*/
	private HorizontalLayout converterLayoutInner = new HorizontalLayout();
	/**Label Ueberschrift*/
	private Label lbConverter = new Label("Konvertierung PGN - RDF");
	/**Textfeld f�r pgn-upload*/
	//private TextField tfToPars = new TextField();
	/**
	 * Textfeld F�r zu Parsende PGN 
	 */
	private TextArea taToPars = new TextArea();

	/**Button fur Konvertierung*/    
    private Button btnDownload = new Button("Download");
    
    private String fileString = "";
    
    private File file;
    
    private String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath(); //TODO: zu verwenden
    
    //private FileDownloader fileDownloader; 
    /**Button zum Suchen von Uploads*/  
	 //TODO: Upload checken
    Upload upload = new Upload("Upload RDF-File Here.", new Upload.Receiver() {
    	@Override
    	public OutputStream receiveUpload(String filename, String mimeType) {
    		
	    	/* Here, we'll stored the uploaded file as a temporary file. No doubt there's
	    	a way to use a ByteArrayOutputStream, a reader around it, use ProgressListener (and
	    	a progress bar) and a separate reader thread to populate a container *during*
	    	the update.
	    	 
	    	This is quick and easy example, though.
	    	*/
    		System.out.println(basepath);
    		FileOutputStream fos = null; // Stream to write to
            try {
                // Open the file for writing.
            	System.out.println(mimeType);
            	setFileString(filename);
            	// TODO: check file for ending .pgn
            		file = new File(basepath + filename);
                    fos = new FileOutputStream(file);
                    //process                
            } catch (final java.io.FileNotFoundException e) {
                Notification.show(
                        "Could not open file", e.getMessage(),
                        Notification.Type.ERROR_MESSAGE);
                return null;
            } 
            return fos; // Return the output stream to write to
    	}
	   });
    	
    
    /**
     * OutputLabel
     */
    private Label lblOut = new Label();
    /**
     * AnzeigeLabel
     */
    private Label lblInfo = new Label();

//------------------------------Seitenaufbau-------------------------------------//
	/**
	 * Methode wird zum erstellen der GUI und ihrer Komponenten aufgerufen
	 * -konstruiert die Seite
	 */
	@Override
	protected void init(VaadinRequest request) {

		searchView.initFunktion();
		initConverter();
		initLayout();
		initButtons();
		initUpload();
	}
//------------------------------Methodenteil--------------------------//	

/**
 * Methode erstellt das Layout der Website	
 */
    private void initLayout()
     {
       
      //Hauptfenster 	2geteilt
	  HorizontalSplitPanel splitPanel = new HorizontalSplitPanel();
      setContent(splitPanel);
      
      //Rechts, Unterfenster 2geteilt
      VerticalSplitPanel splitPanelInner = new VerticalSplitPanel();
      splitPanel.addComponent(splitPanelInner);
            
      splitPanelInner.addComponent(searchView); 
      splitPanelInner.addComponent(converterLayout);
      
           
      
      
      //--------------Aufbau Konverterfenster---------------//
      converterLayout.addComponent(lbConverter);
      converterLayout.addComponent(converterLayoutInner);
      //converterLayoutInner.addComponent(tfToPars);
      converterLayout.addComponent(upload);
      converterLayout.addComponent(taToPars);
      converterLayout.addComponent(btnDownload);
      
      converterLayout.addComponent(lblOut);
      converterLayout.addComponent(lblInfo);
      
      //-------------Eigenschaften der GUIkomponenten----------//
        
      //Anpassen Searchfenster
      searchView.setMargin(true);
      searchView.setVisible(true);
      
     
      taToPars.setWidth("100%");
      
          
      
//      searchLayoutInner.setMargin(true);
//      searchLayoutInner.setVisible(true);
      
      //Anpassen Konverterfenster
      converterLayoutInner.setSizeFull();
      converterLayoutInner.setWidth("100%");
      converterLayout.setWidth("100%");
      
      //tfToPars.setWidth("100%");
      taToPars.setWidth("100%");
      
      //converterLayoutInner.setExpandRatio(tfToPars, 1);
      converterLayout.setExpandRatio(taToPars, 1);
      
      converterLayout.setMargin(true);
      converterLayout.setVisible(true);
      
//      converterLayoutInner.setMargin(true);
//      converterLayoutInner.setVisible(true);  
   
      
     }

  
 private void initUpload(){
  //final RdfUploader uploader = new RdfUploader(); //TODO
     
  //upload.setReceiver(uploader);
	  upload.addFinishedListener(new Upload.FinishedListener() {
	    	@Override
	    	public void uploadFinished(Upload.FinishedEvent finishedEvent) {
	    		File fileTemp = new File(basepath + "test.rdf");
	    		if(fileTemp.exists())
	    			fileTemp.delete();
	    		postPgn(basepath + getFileString(), basepath + "test.rdf");
	    	}
	    	});

 }
  
  
 /**
  * Methode initialiesiert Buttons und ihre Listener 
  * bzw. funktionalitaeten
  */
  private void initButtons()
  {

  //------------------Buttons Konverterfenster-------------------//

	  //delete test.rdf every time
		System.out.println(basepath + getFileString());

		Resource res = new FileResource(new File(basepath + "test.rdf"));
		FileDownloader fd = new FileDownloader(res);
		fd.extend(btnDownload);




//	StreamResource myResource = createResource();
//    fileDownloader = new FileDownloader(myResource);

//   		 System.out.println(file.getPath());

	 //------------------Buttons Suchfenster-------------------// 


  }
  
  //*------------------Anbindung der Komponenten an das Datenmodell------------//
  
  public boolean postPgn( String pathIn, String pathOut) {

	  FileInputStream is = FileUtils.openInputStream(pathIn);
	  FileOutputStream os = FileUtils.openOutputStream(pathOut);
	  PGNToRDFConverterStream p = new PGNToRDFConverterStream(is, os);
	  boolean bValue = p.start();
	  try {
		is.close();
		os.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  lblInfo.setValue("Parsen...Konvertieren...");

	  if(bValue){
		  lblInfo.setValue("PGN wurde erfolgreich Konvertiert! Die RDF steht jetzt zum Donwload bereit.");
	  }

	  else			
		  lblInfo.setValue("Fehler beim Parsen/Konvertieren!");

	return true;  
  }
  
  
  
public boolean postText( String text) {

	//Parser parser = new Parser(text, "test.txt" );
	  //Post text
	return true;  
  }

public void setFileString(String fileString){
	this.fileString = fileString; 
}
public String getFileString(){
	return fileString; 
}

  
  /**
   * Methode baut Knverterfenster und dessen Funktionen auf
   */
  private void initConverter()
  {


  }

  private void initEngine()
  {
      //splitPanel.addcomp
  }

}