package model;

import model.listeners.ScoreModelListener;
import utils.DataBaseManager.DataBase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class ScoreModel implements Model {
    private DataBase localDataBase;
    private ArrayList<ScoreModelListener> scoreModelListeners = new ArrayList<>();

    public ScoreModel() {};

    public void setLocalDataBase(DataBase localDataBase) {
        this.localDataBase = localDataBase;
    }
    public void setListener(ScoreModelListener scoreModelListener) {
        this.scoreModelListeners.add(scoreModelListener);
    }
    public void updateSeriesScore(String seriesTitle, String seriesScore) throws SQLException {
        try {
            localDataBase.updateSeriesScore(seriesTitle.replace("'", "`"), seriesScore);
            notifyScoreHasChanged();
        } catch (Exception e) {
            throw new SQLException();
        }
    }
    private void notifyScoreHasChanged() {
        for (ScoreModelListener scoreModelListener : scoreModelListeners) {
            scoreModelListener.scoreHasChanged();
        }
    }
}
