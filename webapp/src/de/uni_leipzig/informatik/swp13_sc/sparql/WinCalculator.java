package de.uni_leipzig.informatik.swp13_sc.sparql;

/**
 * Created with IntelliJ IDEA.
 * User: makisi
 * Date: 06.05.13 (19)
 * Time: 15:42
 * To change this template use File | Settings | File Templates.
 */

import com.hp.hpl.jena.*;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;

import java.util.Iterator;
import java.util.List;

public class WinCalculator {

    private String resultQueryWithFen= "";
    private String fen = "";
    private double winRateWhite;
    private double winRateBlack;
    private double drawRate;
    private int totalWWin=0;
    private int totalBWin=0;
    private int totalDraw=0;
    private int totalGames = 0;
     /*
     QUERY: ?
     SELECT ?gr COUNT(DISTINCT ?g)
WHERE {
GRAPH ?gr{
?g a cont:ChessGame.
?g cont:moves ?m.
?m cont:fen ?fen.
}}


//NEW
SELECT
distinct ?w ?fen
WHERE{ GRAPH <http://localhost:1358/millionbase_fen>{
?w a cont:ChessGame.
?w cont:result "0-1".
?w cont:moves ?m.
?m cont:fen ?fen
}}

SELECT ?w ?fen
WHERE{ GRAPH <http://localhost:1358/millionbase_fen>{
?w a cont:ChessGame.
?w cont:result "1-0".
?w cont:moves ?m.
?m cont:fen ?fen
}
FILTER(regex ( ?fen,"6k1/4r3/1p2P1q1/p1pQ4/P4P2/1P4Pp/3R3P/7K"))
}
      */
    QuerySearch qs;

    @SuppressWarnings("unchecked")
	public ResultSet calculateChances()
    {
        totalWWin=0;
        totalBWin=0;
        totalDraw=0;
        totalGames = 0;
        qs = new QuerySearch(resultQueryWithFen);
        qs.sendQuery();
        ResultSet resultSet = qs.getResultSet();
        int j = qs.getNumberOfResults();
        System.out.println(j);
//        List<String> resultVars = resultSet.getResultVars();
//        int j = 0;
//        while (resultSet.hasNext()){
//        	j++;
//        }
        
//        QuerySolution querySolution = null;
//        while(resultSet.hasNext()){
//        	String querySolution2 = resultSet.toString(); //TODO: NullPointerException
//        }
        
        //TODO: TESTING
//        totalWWin = Integer.parseInt(querySolution.get("whiteWin").toString());
//        totalBWin = Integer.parseInt(querySolution.get("blackWin").toString());
//        totalDraw = Integer.parseInt(querySolution.get("draw").toString());
//        winRateBlack = (double) totalBWin / totalGames;
//        winRateWhite = (double) totalWWin / totalGames;
//        drawRate = (double) totalDraw / totalGames;
        return resultSet;
    }
    public ResultSet calculateChances(String fen)
    {
        setFen(fen);
        ResultSet result = calculateChances();
        return result;
    }
    public void setFen(String fen)
    {
        this.fen = fen;
        updateQuery();
    }

    private void updateQuery()
    {
    	
    	resultQueryWithFen = "SELECT *" +
    						"WHERE" + 
    						"{?g cont:fen ?fen ." + 
    						"FILTER regex(?fen, '" + fen + "')" + 
    						"?m cont:result '1-0'}"; //1/2-1/2 & 0-1
    }
    public double getWinWhite()
    {
        return winRateWhite;
    }
    public double getWinBlack()
    {
        return winRateBlack;
    }

    public double getDraw()
    {
        return drawRate;
    }
    public int getTotalGames()
    {
        return totalGames;
    }
    public int getTotalWWin()
    {
        return totalWWin;
    }
    public int getTotalBWin()
    {
        return  totalBWin;
    }
    public  int getTotalDraw()
    {
        return totalDraw;
    }

    public void setRandomTestRates()
    {
        winRateWhite = Math.random();
        winRateBlack = Math.random();
        drawRate = Math.random();
    }


}
