package model;

import utils.DataBaseManager.DataBase;
import model.listeners.SeriesContentModelListener;

import java.sql.SQLException;

public class SeriesContentModel implements Model{
    private SeriesContentModelListener seriesContentModelListener;
    private DataBase localDataBase;
    private String lastSeriesContent;

    public SeriesContentModel() {

    }
    public void setLocalDataBase(DataBase localDataBase) {
        this.localDataBase = localDataBase;
    }
    public void setListener(SeriesContentModelListener seriesContentModelListener) {
        this.seriesContentModelListener = seriesContentModelListener;
    }
    public void showSeriesContent(String seriesTitle) throws SQLException {
        try {
        lastSeriesContent = localDataBase.getSavedSeriesExctract(seriesTitle);
        seriesContentModelListener.showSeriesContentHasFinished();
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
    public String getLastSeriesContent() {
        return lastSeriesContent;
    }

}
