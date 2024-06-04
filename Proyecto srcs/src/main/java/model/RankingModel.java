package model;

import model.entities.RatedSeries;
import model.listeners.RankingModelListener;
import utils.DataBaseManager.DataBase;

import java.lang.reflect.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;

public class RankingModel implements Model{
    private RankingModelListener rankingModelListener;
    private DataBase localDataBase;
    private ArrayList<RatedSeries> lastUpdatedRanking;

    public RankingModel() {};

    public void setLocalDataBase(DataBase localDataBase) {
        this.localDataBase = localDataBase;
    }
    public void setListener(RankingModelListener rankingModelListener) {
        this.rankingModelListener = rankingModelListener;
    }
    public void updateRanking() throws SQLException{
        try {
             lastUpdatedRanking = localDataBase.getRankedSeries();
             sortRankingByScore();
             rankingModelListener.rankingHasChanged();
        } catch (SQLException e) {
            throw new SQLException();
        }
    }
    public ArrayList<RatedSeries> getLastUpdatedRanking() {
        return lastUpdatedRanking;
    }
    private void sortRankingByScore() {
        Comparator<RatedSeries> comparator = new Comparator<RatedSeries>() {
            @Override
            public int compare(RatedSeries series1, RatedSeries series2) {
                return Integer.compare(series1.getRating(),series2.getRating());
            }
        };
        lastUpdatedRanking.sort(comparator);
    }
}
