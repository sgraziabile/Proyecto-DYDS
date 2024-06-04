package model;

import utils.DataBaseManager.DataBase;
import model.listeners.SaveSeriesModelListener;

import java.sql.SQLException;

public class SaveSeriesModel {
    private DataBase localDataBase;
    private SaveSeriesModelListener saveSeriesModelListener;

    public SaveSeriesModel() {}

    public void setLocalDataBase(DataBase localDataBase) {
        this.localDataBase = localDataBase;
    }
    public void setListener(SaveSeriesModelListener saveSeriesModelListener) {
        this.saveSeriesModelListener = saveSeriesModelListener;
    }
    public void saveSeries(String seriesTitle, String seriesExctract) throws SQLException {
        try {
        localDataBase.saveSeriesContent(seriesTitle.replace("'", "`"),seriesExctract );
        saveSeriesModelListener.saveSeriesHasFinished();
        } catch (Exception e) {
            throw new SQLException();
        }
    }
}
