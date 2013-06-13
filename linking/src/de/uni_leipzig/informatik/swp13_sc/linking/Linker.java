/**
 * Linker.java
 */

package de.uni_leipzig.informatik.swp13_sc.linking;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import java.util.List;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;

import de.uni_leipzig.informatik.swp13_sc.datamodel.rdf.ChessRDFVocabulary;
import de.uni_leipzig.simba.controller.PPJoinController;
import de.uni_leipzig.simba.data.Mapping;

/**
 * links the Data of DBPedia with the semantic chess poject use Linker link =
 * new Linker();link.makeNTOutputFile(); you have to upload your file manually
 * 
 * @author Lasse
 */
public class Linker
// Daten aus mapping extrahieren-->klappt
// Daten aus DBPEDIA abfragen-->klappt nicht für Union-Abfragen
// (sind weniger, die gleiche Informationen liefern,
// perfomanter, aber nicht abarbeitbar)
// Daten setzen--> Output-Format *.nt, Strukturierung durch Modulo-Zuweisung,
// muss manuell dann geschehen
{

    /**
     * constructs
     */
    public Linker()
    {

    }

    /**
     * generates a whole query
     * 
     * @param uri
     *            of the resource you wish to get a query
     * @param attribut
     *            a property to the resource
     * @param i
     *            to differ the result variable and remember the kind of attribut
     *            if you wish to set attributes at the same
     * @return SPARQL-query
     */
    public String generateQuery(String uri, String attribut, int i)
    {
        String query = "PREFIX dbpedia-owl: <http://dbpedia.org/ontology/> \n"
                + "PREFIX dbpprop: <http://dbpedia.org/property/> \nselect * where{<"
                + uri + "> " + attribut + " ?slct" + i + ".}";
        return query;
    }

    /**
     * generates just one little query part which can build to an union-clause
     * 
     * @param uri
     *            of the resource you wish to get a query
     * @param attribut
     *            a property to the resource
     * @param i
     *            to differ the resultvariable
     * @return a String which can be part of an UNION-clause
     */

    private String generateUnionPart(String uri, String attribut, int i)
    {
        String query = " <" + uri + "> " + attribut + " ?slct" + i + ".";
        return query;
    }

    /**
     * generates WHERE-clause of a query with UNION elements
     * 
     * @param uri
     *            of the resource you wish to get a query
     * @return WHERE-clause
     */
    private String generateWhere(String uri)
    {
        String query = "{" + generateUnionPart(uri, "dbpedia-owl:birthDate", 1)
                + "}UNION{" + generateUnionPart(uri, "dbpprop:dateOfBirth", 2)
                + "}UNION{"
                + generateUnionPart(uri, "dbpedia-owl:birthDate", 3)
                + "}UNION{" + generateUnionPart(uri, "dbpprop:dateOfDeath", 4)
                + "}UNION{" + generateUnionPart(uri, "dbpprop:deathDate", 5)
                + "}UNION{" + generateUnionPart(uri, "dbpedia-owl:title", 6)
                + "}UNION{" + generateUnionPart(uri, "dbpprop:title", 7)
                + "}UNION{"
                + generateUnionPart(uri, "dbpedia-owl:sportCountry", 8)
                + "}UNION{" + generateUnionPart(uri, "dbpprop:sportCountry", 9)
                + "}UNION{" + generateUnionPart(uri, "dbpprop:country", 10)
                + "}UNION{" + generateUnionPart(uri, "dbpedia-owl:country", 11)
                + "}UNION{" + generateUnionPart(uri, "dbpedia-owl:ranking", 12)
                + "}UNION{" + generateUnionPart(uri, "dbpprop:ranking", 13)
                + "}UNION{" + generateUnionPart(uri, "dbpedia-owl:elo", 14)
                + "}UNION{" + generateUnionPart(uri, "dbpprop:elo", 15)
                + "}UNION{"
                + generateUnionPart(uri, "dbpedia-owl:eloRecord", 16)
                + "}UNION{" + generateUnionPart(uri, "dbpprop:eloRecord", 17)
                + "}UNION{" + generateUnionPart(uri, "dbpprop:peakrating", 18)
                + "}";

        return query;
    }

    /**
     * generates an UNION-query
     * 
     * @param uri
     * @return whole query
     */
    public String generateUnionQuery(String uri)
    {
        String query = "PREFIX dbpedia-owl:<http://dbpedia.org/ontology/> \n "
                + "PREFIX dbpprop: <http://dbpedia.org/property/> \n select * where{"
                + generateWhere(uri) + "}";

        return query;
    }

    /**
     * alternative to askResources, more efficient, doesnt work yet
     * (nullpointer, because the relation has "gaps")
     * 
     * @param uri
     * @param sourceUri
     * @return
     */
    private List<String> getRessources(String uri, String sourceUri)
    {
        // should call UNionquery
        List<String> stringls = new ArrayList<String>();
        return stringls;
    }

    /**
     * generates many Queries, sparqls dbpediaendpoint and gives a nt-Syntax
     * list of found attributes
     * 
     * @param uri
     *            of the resource you wish to get information
     * @param sourceUri
     *            the uri of the source which gets the new data
     * @return list of the information which is found
     */
    public List<String> askRessources(String uri, String sourceUri)
    {
        /* expandable with other %x */
        String[] queryString =
        // Birthdate %10=0
        {
                generateQuery(uri, "dbpedia-owl:birthDate", 0),
                generateQuery(uri, "dbpprop:dateOfBirth", 10),
                generateQuery(uri, "dbpedia-owl:birthDate", 20),
                // Deathdate %10=1
                generateQuery(uri, "dbpprop:dateOfDeath", 1),
                generateQuery(uri, "dbpprop:deathDate", 11),

                // Titel %10=2
                generateQuery(uri, "dbpedia-owl:title", 2),
                generateQuery(uri, "dbpprop:title", 12),

                // Nation %10=3
                generateQuery(uri, "dbpedia-owl:sportCountry", 3),
                generateQuery(uri, "dbpprop:sportCountry", 13),
                generateQuery(uri, "dbpprop:country", 23),
                generateQuery(uri, "dbpedia-owl:country", 33),

                // Elo %10=4
                generateQuery(uri, "dbpedia-owl:ranking", 4),
                generateQuery(uri, "dbpprop:ranking", 14),
                generateQuery(uri, "dbpedia-owl:elo", 24),
                generateQuery(uri, "dbpprop:elo", 34),
                generateQuery(uri, "dbpedia-owl:eloRecord", 44),
                generateQuery(uri, "dbpprop:eloRecord", 54),
                generateQuery(uri, "dbpprop:peakrating", 64),
                generateQuery(uri, "dbpprop:peakRating", 74),
                // Geburtsort %10=5
                generateQuery(uri, "dbpprop:placeOfBirth", 5),
                generateQuery(uri, "dbpprop:birthPlace", 15),
                generateQuery(uri, "dbpedia-owl:birthPlace", 25),
                // thumbnail %10=6
                generateQuery(uri, "dbpedia-owl:thumbnail", 6),
                // peakRanking %10=7
                generateQuery(uri, "dbpprop:peakranking", 7),
                generateQuery(uri, "dbpprop:peakRanking", 17) };

        List<String> resVar = new ArrayList<String>();
        Set<String> stringls = new HashSet<String>();
        /* for each querystring */
        for (int i = 0; i < queryString.length; i++)
        {
            Query query = QueryFactory.create(queryString[i]);
            /* initializing queryExecution factory with remote service */
            QueryExecution qexec = QueryExecutionFactory.sparqlService(
                    "http://dbpedia.org/sparql", query);

            /* get the Results */
            try
            {
                ResultSet results = qexec.execSelect();

                while (results.hasNext())
                {
                    QuerySolution qs = results.nextSolution();

                    resVar = results.getResultVars();

                    /* extract the categorizationNumber */
                    int identifier = Integer.parseInt(resVar.get(0).toString()
                            .substring(4));
                    String attribute = "unknown";
                    String value = qs.get(resVar.get(0)).toString();
                    
                    /* gives URIs <> and Non-URIs "" */
                    if ((value.startsWith("http://")))
                    {
                        // case: http://dbpedia.org/resource/London
                        value = "<" + value + "> ";
                    }
                    else
                    {
                        int j = value.indexOf("^^");
                        if (j == -1)
                        {
                            j = value.indexOf('@');                            
                            if (j == -1)
                            {
                                // default "value" or better <value> ?
                                value = "\"" + value + "\" ";
                            }
                            else
                            {
                                // case: London, England@en
                                String first = value.substring(0, j);
                                if (! first.startsWith("\""))
                                {
                                    first = "\"" + first + "\"";
                                }
                                String second = value.substring(j + 1);
                                value = first + '@' + second;
                            }
                        }
                        else
                        {
                            // case: 1963-08-28^^http://www.w3.org/2001/XMLSchema#date
                            //       2650^^http://www.w3.org/2001/XMLSchema#int
                            String first = value.substring(0, j);
                            if (! first.startsWith("\""))
                            {
                                first = "\"" + first + "\"";
                            }
                            String second = value.substring(j+2);
                            if (second.startsWith("http://"))
                            {
                                second = "<" + second + ">";
                            }
                            value = first + "^^" + second;
                        }
                    }

                    /* sets the attribute by the categorizationNumber */
                    if (identifier % 10 == 0)
                    {
                        attribute = ChessRDFVocabulary.birthDate.toString();
                    }
                    if (identifier % 10 == 1)
                    {
                        attribute = ChessRDFVocabulary.deathDate.toString();
                    }
                    if (identifier % 10 == 2)
                    {
                        attribute = ChessRDFVocabulary.title.toString();
                    }
                    if (identifier % 10 == 3)
                    {
                        attribute = ChessRDFVocabulary.nation.toString();
                    }
                    if (identifier % 10 == 4)
                    {
                        attribute = ChessRDFVocabulary.elo.toString();
                    }
                    if (identifier % 10 == 5)
                    {
                        attribute = ChessRDFVocabulary.birthPlace.toString();
                    }
                    if (identifier % 10 == 6)
                    {
                        attribute = ChessRDFVocabulary.thumbnail.toString();
                    }
                    if (identifier % 10 == 7)
                    {
                        attribute = ChessRDFVocabulary.peakRanking.toString();
                    }

                    String comp = "<" + sourceUri + "> <" + attribute + "> "
                            + value + ".";
                    
                    stringls.add(comp);
                    //System.out.println(comp);
                }
            }
            finally
            {
                qexec.close();
            }
        }
        return new ArrayList<String>(stringls);
    }

    /**
     * extracts a tupel source-URI target-URI from a map given as list
     * 
     * @param index
     *            the position in list
     * @param list
     *            of a map
     * @return array with length 2 for the source and the target URIs
     */
    public String[] extractTupelFromMapping(int index, List list)
    {

        String source = "", target = "", temp;
        boolean srcflag = true, trgtflag = false;

        temp = list.get(index).toString();

        // splitts the Inputstring as char comparision
        for (int i = 0; i < temp.length(); i++)
        {
            if (temp.charAt(i) == '=')
            {
                if (trgtflag == true)
                {
                    break;
                }
                srcflag = false;
                trgtflag = true;
                i += 2;
            }
            if (trgtflag == true)
            {
                target += temp.charAt(i);
            }
            if (srcflag == true)
            {
                source += temp.charAt(i);
            }
        }

        String[] result = { source, target };

        return result;
    }

    /**
     * combines important functions to model the linking
     */
    public List<String> link()
    {
        Mapping m = PPJoinController.getMapping("sc1.xml");
        List mappingList = new ArrayList(m.map.entrySet());
        List<String> n3Liste = new ArrayList<String>();
        System.out
                .println("Getting Data, this takes its time (aprx. 20 minutes), \n"
                        + "you will have a file 'LinkedData.nt' in the programmfolder, \n"
                        + "which you should upload to your TripleStore");

        for (int i = 0; i < mappingList.size(); i++)
        {
            // System.out.println("Player"+i);
            String uri = extractTupelFromMapping(i, mappingList)[1];
            String source = extractTupelFromMapping(i, mappingList)[0];
            List<String> resListe = new ArrayList<String>();
            resListe = askRessources(uri, source);
            n3Liste.addAll(resListe);
            if (i % 10 == 0)
            {
                System.out.print("\rProgress: " + Float.toString((float) i
                        * 100 / mappingList.size()) + "% (" + i + ")");
            }
        }
        System.out.println("Finished!");
        /*
         * for(int k=0; k<n3Liste.size();k++) {
         * 
         * System.out.println(k + " :" + n3Liste.get(k));
         * 
         * }
         */
        return n3Liste;
    }

    /**
     * makes an *.nt file
     */
    public void makeNTOutputFile()
    {
        File output = new File("linkedData.nt");
        try
        {
            FileWriter writer = new FileWriter(output, true);
            List<String> data = link();
            for (int i = 0; i < data.size(); i++)
            {
                writer.write(data.get(i));
                writer.write(System.getProperty("line.separator"));
            }
            writer.flush();
            writer.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * uploads a file to Virtuoso, unready
     * 
     * @param filename
     *            name of the File you wish to upload
     */
    public void uploadToVirtuoso(String filename)
    {

    }

    /**
     * main function, used to test the functions
     * 
     * @param args
     */
    public static void main(String[] args)
    {
        /*
         * Mapping m = PPJoinController.getMapping("sc1.xml");
         * 
         * ArrayList lis =new ArrayList(m.map.entrySet());
         */
        // System.out.println(lis.get(1).toString());
        // System.out.println(m.map.entrySet().toString());
        Linker link = new Linker();
        // String[] str =link.extractFromMapping();
        // System.out.println(str[1]);
        // System.out.println(link.generateQuery("http://dbpedia.org/resource/Jaime_Sunye_Neto",
        // "dbpedia-owl:birthDate"));

        // System.out.println(link.generateQuery("www.bsp.de", "exam:prtot"));
        // List<String> liste =new ArrayList<String>();
        // liste=link.askRessources("http://dbpedia.org/resource/Davor_Palo");

        // liste=link.askRessources("http://dbpedia.org/resource/Davor_Palo","http://dbpedia.org/resource/Davor_Palo");
        /*
         * for (int i=0;i<liste.size();i++) {System.out.println(liste.get(i));}
         */
        // System.out.println(link.generateUnionQuery("http://dbpedia.org/resource/Peter_Heine_Nielsen"));
        /*
         * for (int i=0;i<liste.size();i++) System.out.println(liste.get(i));
         */
        // System.out.println(link.extractTupelFromMapping(0, lis)[1]);
        // link.link();
        // link.getRessources("http://dbpedia.org/resource/Davor_Palo",
        // "sourceUri");

        // call this after construct the instance
        link.makeNTOutputFile();
    }
}
