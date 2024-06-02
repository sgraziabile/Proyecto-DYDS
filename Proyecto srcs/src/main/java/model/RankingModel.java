package model;

import model.listeners.RankingModelListener;
import utils.DataBaseManager.DataBase;

public class RankingModel implements Model{
    private RankingModelListener rankingModelListener;
    private DataBase localDataBase;
    private String lastUpdatedRanking;

    public RankingModel() {};

    public void setListener(RankingModelListener rankingModelListener) {
        this.rankingModelListener = rankingModelListener;
    }
    public void updateRanking() {
        lastUpdatedRanking = localDataBase.getRankedSeries();
        rankingModelListener.rankingHasChanged();
    }
    private void sortRanking() {
        //sort table
    }
    public String getLastUpdatedRanking() {
        return lastUpdatedRanking;
    }
}
