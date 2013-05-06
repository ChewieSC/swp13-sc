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

import java.util.List;

public class WinCalculator {

    private String resultQueryWithFen= "";
    private String fen = "";
    private double winRateWhite;
    private double winRateBlack;
    private double drawRate;
    private double totalWWin=0;
    private double totalBWin=0;
    private double totalDraw=0;
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
      */
    QuerySearch qs;

    public boolean calculateChances()
    {
        totalWWin=0;
        totalBWin=0;
        totalDraw=0;
        totalGames = 0;
        qs = new QuerySearch(resultQueryWithFen);
        ResultSet resultSet = qs.getResultSet();
        QuerySolution querySolution = resultSet.next();
        totalWWin = Integer.parseInt(querySolution.get("whiteWin").toString());
        totalBWin = Integer.parseInt(querySolution.get("blackWin").toString());
        totalDraw = Integer.parseInt(querySolution.get("draw").toString());
        winRateBlack = totalBWin / totalGames;
        winRateWhite = totalWWin / totalGames;
        drawRate = totalDraw / totalGames;
        return true;
    }
    public double calculateChances(String fen)
    {
        setFen(fen);
        calculateChances();
    }
    public void setFen(String fen)
    {
        this.fen = fen;
        updateQuery();
    }

    private void updateQuery()
    {
        resultQueryWithFen = ""; //todo Query einbauen.
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

}
