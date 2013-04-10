package de.uni_leipzig.informatik.swp13_sc.datamodel.rdf;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;

import de.uni_leipzig.informatik.swp13_sc.datamodel.ChessMove;
import de.uni_leipzig.informatik.swp13_sc.datamodel.ChessPlayer;

/**
 * <p>Vocabulary definitions for <strong>ChessOntology</strong>:<br />
 * http://pcai042.informatik.uni-leipzig.de/~swp13-sc/ChessOntology
 * </p>
 *
 * @author  Erik
 */
public class ChessRDFVocabulary
{
    /**
     * Internal model for creating static {@link
     *  com.hp.hpl.jena.rdf.model.Resource} and {@link
     *  com.hp.hpl.jena.rdf.model.Property}s.
     */
    private final static Model model;
    
    /**
     * The namespace of this ChessOntology vocabulary.
     */
    public final static String Namespace;
    /**
     * The separation character of the Prefix. Used to separate namespace and
     * Resource/Property name.
     */
    public final static String Anchor;
    /**
     * The whole prefix of the defined Resources and Properties.
     */
    public final static String Prefix;
    /**
     * The version of this vocabulary.
     */
    public final static String Version;
    /**
     * ResourceURI is the URI used to define resources with this vocabulary.
     */
    public final static String ResourceURI;
    
    // Class Types / Resource Types
    /**
     * Resource as type for new {@link ChessGame} {@link
     *  com.hp.hpl.jena.rdf.model.Resource}s.
     */
    public final static Resource ChessGame;
    /**
     * Resource as type for new {@link ChessPlayer} {@link
     *  com.hp.hpl.jena.rdf.model.Resource}s.
     */
    public final static Resource ChessPlayer;
    /**
     * Resource as type for new {@link ChessMove} {@link
     *  com.hp.hpl.jena.rdf.model.Resource}s.
     */
    public final static Resource ChessMove;
    
    // Properties of ChessGame
    /**
     * blackPlayer property of a {@link ChessGame}. The player is an entity.
     */
    public final static Property blackPlayer;
    /**
     * whitePlayer property of a {@link ChessGame}.
     */
    public final static Property whitePlayer;
    /**
     * moves property of a {@link ChessGame}. Lists all moves of a chess game.
     */
    public final static Property moves;
    
    // Properties of ChessPlayer
    /**
     * Is the name of a chess player.
     */
    public final static Property name;
    
    // Properties of ChessGame
    /**
     * The event the chess game was played at.
     */
    public final static Property event;
    /**
     * The site/location of the chess game.
     */
    public final static Property site;
    /**
     * The round of the chess game.
     */
    public final static Property round;
    /**
     * The result of the chess game.
     */
    public final static Property result;
    /**
     * The date the chess game was held on.
     */
    public final static Property date;
    
    // Properties of ChessMove
    /**
     * Possible comment of a player for this move.
     */
    public final static Property comment;
    /**
     * The FEN String of the move. It shows information about the positioning,
     * possible moves and so on.
     */
    public final static Property fen;
    /**
     * The actual move. It's in algebraic notation.
     */
    public final static Property move;
    /**
     * The number of this move.
     */
    public final static Property moveNr;
    
    
    /**
     * Returns the URI of the defined resources and properties of this
     * vocabulary.
     * 
     * @return  String with vocabulary URI
     */
    public static String getURI()
    {
        return Prefix;
    }
    
    /**
     * Returns the URI that is used as a standard location for new resources.
     * 
     * @return  String with resource URI
     */
    public static String getResourceURI()
    {
        return ResourceURI;
    }
    
    /**
     * Returns the versioning String of this vocabulary.
     * 
     * @return  Versioning String
     */
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
        //black = model.createProperty(Prefix + "black");
        //white = model.createProperty(Prefix + "white");
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
