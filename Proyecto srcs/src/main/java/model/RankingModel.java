package model;

import model.listeners.RankingModelListener;
import utils.DataBaseManager.DataBase;

public class RankingModel implements Model{
    private RankingModelListener rankingModelListener;
    private DataBase localDataBase;

    public RankingModel() {};

    public void setListener(RankingModelListener rankingModelListener) {
        this.rankingModelListener = rankingModelListener;
    }
    public void sortRanking() {
        //get table and sort it
        rankingModelListener.rankingHasChanged();
    }
}
