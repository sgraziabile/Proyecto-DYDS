package model;

import utils.DataBaseManager.DataBase;
import model.listeners.RetrieveSeriesModelListener;

import java.sql.SQLException;
import java.util.ArrayList;

public class RetrieveSeriesModel implements Model {
    private DataBase localDataBase;
    private RetrieveSeriesModelListener retrieveSeriesModelListener;
    private ArrayList<String> savedSeriesTitles;

    public RetrieveSeriesModel() {

    }
    public void setLocalDataBase(DataBase localDataBase) {
        this.localDataBase = localDataBase;
    }
    public void setListener(RetrieveSeriesModelListener retrieveSeriesModelListener) {
        this.retrieveSeriesModelListener = retrieveSeriesModelListener;
    }
    public void getSavedSeries() throws SQLException{
        try {
            savedSeriesTitles = getSavedSeriesTitles();
            retrieveSeriesModelListener.retrieveSeriesHasFinished();
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
}
