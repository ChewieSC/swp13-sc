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
import com.vaadin.ui.VerticalLayout;

import de.uni_leipzig.informatik.swp13_sc.converter.PGNToRDFConverterStream;
import de.uni_leipzig.informatik.swp13_sc.util.FileUtils;

/**
 * KLasse enhaelt das Layout sowie Funktionen des Upload und Konvertierens
 * 
 * @author LingLong
 * 
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
    private TextArea taToPars = new TextArea();

    /** Button fur Konvertierung */
    private Button btnDownload = new Button("Download");

    private String fileString = "";

    private File file;

    private String basepath = VaadinService.getCurrent().getBaseDirectory()
            .getAbsolutePath(); // TODO: zu verwenden

    private Upload upload;

    // private FileDownloader fileDownloader;
    /** Button zum Suchen von Uploads */
    // TODO: Upload checken

    /**
     * Konstruktor fuer UploadView
     */
    public ConverterView()
    {
        upload = new Upload("Upload RDF-File Here.", new Upload.Receiver()
        {
            private static final long serialVersionUID = 1L;

            @Override
            public OutputStream receiveUpload(String filename, String mimeType)
            {
                /*
                 * Here, we'll stored the uploaded file as a temporary file. No
                 * doubt there's a way to use a ByteArrayOutputStream, a reader
                 * around it, use ProgressListener (and a progress bar) and a
                 * separate reader thread to populate a container *during* the
                 * update.
                 * 
                 * This is quick and easy example, though.
                 */
                System.out.println(basepath);
                FileOutputStream fos = null; // Stream to write to
                try
                {
                    // Open the file for writing.
                    System.out.println(mimeType);

                    setFileString(filename);
                    // TODO: check file for ending .pgn
                    file = new File(basepath + filename);
                    fos = new FileOutputStream(file);
                    // process
                }
                catch (final java.io.FileNotFoundException e)
                {
                    Notification.show("Could not open file", e.getMessage(),
                            Notification.Type.ERROR_MESSAGE);
                    return null;
                }
                return fos; // Return the output stream to write to
            }
        });

        // --------------Aufbau Konverterfenster---------------//

        addComponent(lbConverter);
        addComponent(converterLayoutInner);

        addComponent(upload);
        addComponent(taToPars);
        addComponent(btnDownload);

        addComponent(lblOut);
        addComponent(lblInfo);

        // Anpassen Konverterfenster
        converterLayoutInner.setSizeFull();
        converterLayoutInner.setWidth("100%");
        setWidth("100%");

        // tfToPars.setWidth("100%");
        taToPars.setWidth("100%");

        // converterLayoutInner.setExpandRatio(tfToPars, 1);
        setExpandRatio(taToPars, 1);

        setMargin(true);
        setVisible(true);

        // converterLayoutInner.setMargin(true);
        // converterLayoutInner.setVisible(true);
    }

    public void initUpload()
    {
        // final RdfUploader uploader = new RdfUploader(); //TODO

        // upload.setReceiver(uploader);
        upload.addFinishedListener(new Upload.FinishedListener()
        {
            private static final long serialVersionUID = 1L;

            @Override
            public void uploadFinished(Upload.FinishedEvent finishedEvent)
            {
                File fileTemp = new File(basepath + "test.rdf");

                if (fileTemp.exists())
                {
                    fileTemp.delete();
                }

                postPgn(basepath + getFileString(), basepath + "test.rdf");
            }
        });

        // delete test.rdf every time
        System.out.println(basepath + getFileString());

        Resource res = new FileResource(new File(basepath + "test.rdf"));

        FileDownloader fd = new FileDownloader(res);
        fd.extend(btnDownload);

        // StreamResource myResource = createResource();
        // fileDownloader = new FileDownloader(myResource);

        // System.out.println(file.getPath());
    }

    // *------------------Anbindung der Komponenten an das
    // Datenmodell------------//

    public boolean postPgn(String pathIn, String pathOut)
    {
        FileInputStream is = FileUtils.openInputStream(pathIn);
        FileOutputStream os = FileUtils.openOutputStream(pathOut);
        PGNToRDFConverterStream p = new PGNToRDFConverterStream(is, os);
        boolean bValue = p.start();

        try
        {
            is.close();
            os.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }

        lblInfo.setValue("Parsen...Konvertieren...");

        if (bValue)
        {
            lblInfo.setValue("PGN wurde erfolgreich Konvertiert! Die RDF steht jetzt zum Donwload bereit.");
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
