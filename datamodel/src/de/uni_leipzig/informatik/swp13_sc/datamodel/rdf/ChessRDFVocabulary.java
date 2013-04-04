package de.uni_leipzig.informatik.swp13_sc.datamodel.rdf;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;

public class ChessRDFVocabulary
{
    private final static Model model;
    
    public final static String Namespace;
    public final static String Anchor;
    public final static String Prefix;
    public final static String Version;
    public final static String ResourceURI;
    
    // Class Types / Resource Types
    public final static Resource ChessGame;
    public final static Resource ChessPlayer;
    public final static Resource ChessMove;
    
    // Properties of ChessGame
    public final static Property blackPlayer;
    public final static Property whitePlayer;
    public final static Property moves;
    
    // Properties of ChessPlayer
    public final static Property name;
    
    // Properties of ChessGame
    public final static Property event;
    public final static Property site;
    public final static Property round;
    public final static Property result;
    public final static Property date;
    
    // Properties of ChessMove
    public final static Property comment;
    public final static Property fen;
    public final static Property move;
    public final static Property moveNr;
    
    
    public static String getURI()
    {
        return Prefix;
    }
    
    public static String getResourceURI()
    {
        return ResourceURI;
    }
    
    public static String getVersion()
    {
        return Version;
    }
    
    // Initialization
    static
    {
        model = ModelFactory.createDefaultModel();
        
        Namespace = "http://pcai042.informatik.uni-leipzig.de/~swp13-sc/ChessOntology";
        Anchor = "#";
        Prefix = Namespace + Anchor;
        Version = Namespace; // ?
        ResourceURI = Namespace + "/" + "Resources" + "/";
        
        ChessGame = model.createResource(Prefix + "ChessGame");
        ChessPlayer = model.createResource(Prefix + "ChessPlayer");
        ChessMove = model.createResource(Prefix + "ChessMove");
        
        blackPlayer = model.createProperty(Prefix + "blackPlayer");
        whitePlayer = model.createProperty(Prefix + "whitePlayer");
        moves = model.createProperty(Prefix + "moves");
        
        name = model.createProperty(Prefix + "name");
        
        event = model.createProperty(Prefix + "event");
        site = model.createProperty(Prefix + "site");
        round = model.createProperty(Prefix + "round");
        result = model.createProperty(Prefix + "result");
        date = model.createProperty(Prefix + "date");
        
        comment = model.createProperty(Prefix + "comment");
        fen = model.createProperty(Prefix + "fen");
        move = model.createProperty(Prefix + "move");
        moveNr = model.createProperty(Prefix + "moveNr");        
    }    
}
