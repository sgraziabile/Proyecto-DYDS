package model;

import model.listeners.ScoreModelListener;
import utils.DataBaseManager.DataBase;

import java.util.Date;

public class ScoreModel implements Model {
    private DataBase localDataBase;
    private ScoreModelListener scoreModelListener;

    public ScoreModel() {};

    public void setLocalDataBase(DataBase localDataBase) {
        this.localDataBase = localDataBase;
    }
    public void setListener(ScoreModelListener scoreModelListener) {
        this.scoreModelListener = scoreModelListener;
    }
    public void updateSeriesScore(String seriesTitle, String seriesScore) {
        localDataBase.updateSeriesScore(seriesTitle.replace("'", "`"), seriesScore);
        scoreModelListener.scoreHasChanged();
    }
}
