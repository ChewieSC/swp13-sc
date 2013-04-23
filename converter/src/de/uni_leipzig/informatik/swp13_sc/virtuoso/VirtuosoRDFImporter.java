/**
 * VirtuosoRDFImporter.java
 */
package de.uni_leipzig.informatik.swp13_sc.virtuoso;

import java.io.IOException;
import java.io.InputStream;
import virtuoso.jena.driver.VirtGraph;
import virtuoso.jena.driver.VirtModel;

import com.hp.hpl.jena.graph.Graph;
import com.hp.hpl.jena.graph.GraphUtil;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.StmtIterator;

import de.uni_leipzig.informatik.swp13_sc.converter.ChessDataModelToRDFConverter;
import de.uni_leipzig.informatik.swp13_sc.converter.PGNToChessDataModelConverter;
import de.uni_leipzig.informatik.swp13_sc.util.FileUtils;

/**
 * 
 *
 * @author Erik
 *
 */
public class VirtuosoRDFImporter
{
    private VirtGraph virtuosoGraph;
    
    public VirtuosoRDFImporter(String dbConnectionString, String username, String password)
    {
        this.virtuosoGraph = new VirtGraph(dbConnectionString, username, password);
    }
    
    public boolean importFromZipArchive(String filename)
    {
        InputStream is = FileUtils.openInputStream(filename);
        boolean ok = this.importFromZipArchive(filename);
        try
        {
            is.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return ok;
    }
    
    public boolean importFromZipArchive(InputStream inputStream)
    {
        // TODO:
        return false;
    }
    
    public boolean importFromRDFFileStream(InputStream inputStream)
    {
        // TODO:
        
        //virtuosoGraph.read(url, type)
        
        VirtModel m = new VirtModel(virtuosoGraph);
        
        
        return false;
    }
    
    
    // TODO: can i use a virtModel ??
    public boolean convertModelToGraph(Model m, Graph g)
    {
        if (m == null || m.isClosed())
        {
            return false;
        }
        if (g == null || g.isClosed())
        {
            return false;
        }
        
        boolean transActionStarted = false;
        if (g.getTransactionHandler().transactionsSupported())
        {
            try
            {
                g.getTransactionHandler().begin();
                transActionStarted = true;
            }
            catch (UnsupportedOperationException e)
            {
                e.printStackTrace();
            }
        }
        
        // better way to add all statements?
        StmtIterator stmtIter = m.listStatements();
        while(stmtIter.hasNext())
        {
            g.add(stmtIter.nextStatement().asTriple());
            stmtIter.remove();
        }
        
        // or -----
        //GraphUtil.addInto(g, m.getGraph());
        
        // or -----
        //VirtModel vm = new VirtModel((VirtGraph) g);
        //vm.add(m);
        
        if (g.getTransactionHandler().transactionsSupported() && transActionStarted)
        {
            g.getTransactionHandler().commit();
        }
        
        return true;
    }
    
    public boolean convertAndImportFromPGNFile(String inputFilename)
    {
        InputStream is = FileUtils.openInputStream(inputFilename);
        boolean ok = this.convertAndImportFromPGNFile(is);
        try
        {
            is.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return ok;
    }
    
    public boolean convertAndImportFromPGNFile(InputStream inputStream)
    {
        PGNToChessDataModelConverter pgn2cdm = new PGNToChessDataModelConverter();
        pgn2cdm.setInputStream(inputStream);
        ChessDataModelToRDFConverter cdm2rdf = new ChessDataModelToRDFConverter();
        
        boolean ok = true;
        while(ok)
        {
            // see if finished input
            if (pgn2cdm.finishedInputFile())
            {
                break;
            }
            
            // parse
            ok = ok && pgn2cdm.parse(500);
            if (! ok)
            {
                // parsing error
                break;
            }
            
            // convert
            ok = ok && cdm2rdf.convert(pgn2cdm.getGames());
            if (! ok)
            {
                // converting error
                break;
            }
            
            // put into store
            Graph dataGraph = cdm2rdf.getTripelGraph();
            GraphUtil.addInto(this.virtuosoGraph, dataGraph);
            //dataGraph.clear();
            dataGraph.close();
            
            // access model or iterator ?
            //com.hp.hpl.jena.graph.GraphUtil.addInto(dstGraph, srcGraph);
        }
        
        return ok;
    }
    
    
}
