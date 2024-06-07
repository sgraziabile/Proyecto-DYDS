package model;

import utils.DataBaseManager.DataBase;
import model.listeners.RetrieveSeriesModelListener;
import utils.DataBaseManager.DataBaseInterface;

import java.sql.SQLException;
import java.util.ArrayList;

public class RetrieveSeriesModel implements Model {
    private DataBaseInterface localDataBase;
    private ArrayList<RetrieveSeriesModelListener> retrieveSeriesModelListeners = new ArrayList<>();
    private ArrayList<String> savedSeriesTitles;

    public RetrieveSeriesModel() {

    }
    public void setLocalDataBase(DataBaseInterface localDataBase) {
        this.localDataBase = localDataBase;
    }
    public void setListener(RetrieveSeriesModelListener retrieveSeriesModelListener) {
        this.retrieveSeriesModelListeners.add(retrieveSeriesModelListener);
    }
    public void getSavedSeries() throws SQLException{
        try {
            savedSeriesTitles = getSavedSeriesTitles();
            notifyRetrieveSeriesHasFinished();
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
    public ArrayList<String> getSavedSeriesTitles() throws SQLException {
        try {
            return localDataBase.getSavedSeriesTitles();
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
    private void notifyRetrieveSeriesHasFinished() {
        for (RetrieveSeriesModelListener retrieveSeriesModelListener : retrieveSeriesModelListeners) {
            retrieveSeriesModelListener.retrieveSeriesHasFinished();
        }
    }
}
