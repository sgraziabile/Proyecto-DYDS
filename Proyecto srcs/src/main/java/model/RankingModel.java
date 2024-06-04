package model;

import model.entities.RatedSeries;
import model.listeners.RankingModelListener;
import utils.DataBaseManager.DataBase;

import java.lang.reflect.Array;
import java.util.ArrayList;

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
    public void updateRanking() {
        lastUpdatedRanking = localDataBase.getRankedSeries();
        rankingModelListener.rankingHasChanged();
    }
    public ArrayList<RatedSeries> getLastUpdatedRanking() {
        return lastUpdatedRanking;
    }
}
