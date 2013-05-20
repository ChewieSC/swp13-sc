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
    
    /**
     * Possible Ontology Prefix. Can be used to shorten the URI of Ontologies.
     */
    public final static String OntologyPrefixName;
    /**
     * Possible Resource Prefix. Used to shorten the URI of Resources.
     */
    public final static String ResourcePrefixName;
    
    // Class Types / Resource Types
    /**
     * Resource as type for new {@link
     * de.uni_leipzig.informatik.swp13_sc.datamodel.ChessGame}
     * {@link com.hp.hpl.jena.rdf.model.Resource}s.
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
    /**
     * The opening of a chess game.
     */
    public final static Resource ChessOpening;
    
    // Properties of ChessGame
    /**
     * blackPlayer property of a {@link
     * de.uni_leipzig.informatik.swp13_sc.datamodel.ChessGame}.
     * The player is an entity.
     */
    public final static Property blackPlayer;
    /**
     * whitePlayer property of a {@link
     * de.uni_leipzig.informatik.swp13_sc.datamodel.ChessGame}.
     */
    public final static Property whitePlayer;
    /**
     * moves property of a {@link
     * de.uni_leipzig.informatik.swp13_sc.datamodel.ChessGame}.
     * Lists all moves of a chess game.
     */
    public final static Property moves;
    
    // Properties of ChessPlayer
    /**
     * Is the name of a chess player.
     */
    public final static Property name;
    /**
     * The birth place of the chess player.
     */
    public final static Property birthPlace;
    /**
     * The birth date of the chess player..
     */
    public final static Property birthDate;
    /**
     * The date of death of the chess player
     */
    public final static Property deathDate;
    /**
     * The ELO ranking of the chess player. It shows the player's ability.
     */
    public final static Property elo;
    /**
     * The player's nation/nationality.
     */
    public final static Property nation;
    /**
     * Peak ranking of a chess player.
     */
    public final static Property peakRanking;
    /**
     * A link to a thumbnail of the chess player.
     */
    public final static Property thumbnail;
    /**
     * The title of the chess player.
     */
    public final static Property title;
    
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
    /**
     * The opening code name of this game.
     */
    public final static Property eco;
    
    /**
     * The code name of the opening.
     */
    public final static Property openingCode;
    /**
     * The 'human-readable' name of the opening.
     */
    public final static Property openingName;
    
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
     * Returns a possible prefix for the ontology URI (or IRI).
     * 
     * @return  String with Ontology prefix name
     */
    public static String getOntologyPrefixName()
    {
        return OntologyPrefixName;
    }
    
    /**
     * Returns a possible prefix for the resource URI (or IRI) of chess
     * resources.
     * 
     * @return  String with Resource prefix name
     */
    public static String getResourcePrefixName()
    {
        return ResourcePrefixName;
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
        
        OntologyPrefixName = "cont";
        ResourcePrefixName = "cres";
        
        ChessGame = model.createResource(Prefix + "ChessGame");
        ChessPlayer = model.createResource(Prefix + "ChessPlayer");
        ChessMove = model.createResource(Prefix + "ChessMove");
        ChessOpening = model.createResource(Prefix + "ChessOpening");
        
        blackPlayer = model.createProperty(Prefix + "blackPlayer");
        whitePlayer = model.createProperty(Prefix + "whitePlayer");
        moves = model.createProperty(Prefix + "moves");
        
        name = model.createProperty(Prefix + "name");
        birthPlace = model.createProperty(Prefix + "birthPlace");
        birthDate = model.createProperty(Prefix + "birthDate");
        deathDate = model.createProperty(Prefix + "deathDate");
        elo = model.createProperty(Prefix + "elo");
        nation = model.createProperty(Prefix + "nation");
        peakRanking = model.createProperty(Prefix + "peakRanking");
        thumbnail = model.createProperty(Prefix + "thumbnail");
        title = model.createProperty(Prefix + "title");
        
        //black = model.createProperty(Prefix + "black");
        //white = model.createProperty(Prefix + "white");
        event = model.createProperty(Prefix + "event");
        site = model.createProperty(Prefix + "site");
        round = model.createProperty(Prefix + "round");
        result = model.createProperty(Prefix + "result");
        date = model.createProperty(Prefix + "date");
        eco = model.createProperty(Prefix + "eco");
        
        comment = model.createProperty(Prefix + "comment");
        fen = model.createProperty(Prefix + "fen");
        move = model.createProperty(Prefix + "move");
        moveNr = model.createProperty(Prefix + "moveNr");  
        
        openingName = model.createProperty(Prefix + "openingName");
        openingCode = model.createProperty(Prefix + "openingCode");
    }    
}
