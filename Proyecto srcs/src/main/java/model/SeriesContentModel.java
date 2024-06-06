package model;

import utils.DataBaseManager.DataBase;
import model.listeners.SeriesContentModelListener;

import java.sql.SQLException;
import java.util.ArrayList;

public class SeriesContentModel implements Model{
    private ArrayList<SeriesContentModelListener> seriesContentModelListeners = new ArrayList<>();
    private DataBase localDataBase;
    private String lastSeriesContent;

    public SeriesContentModel() {

    }
    public void setLocalDataBase(DataBase localDataBase) {
        this.localDataBase = localDataBase;
    }
    public void setListener(SeriesContentModelListener seriesContentModelListener) {
        this.seriesContentModelListeners.add(seriesContentModelListener);
    }
    public void showSeriesContent(String seriesTitle) throws SQLException {
        try {
            lastSeriesContent = localDataBase.getSavedSeriesExtract(seriesTitle);
            notifyShowSeriesContentHasFinished();
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
    public void notifyShowSeriesContentHasFinished() {
        for(SeriesContentModelListener seriesContentModelListener: seriesContentModelListeners)
            seriesContentModelListener.showSeriesContentHasFinished();
    }
    public String getLastSeriesContent() {
        return lastSeriesContent;
    }

}
