package model;

import model.listeners.SeriesContentModelListener;
import utils.DataBaseManager.DataBaseInterface;

import java.sql.SQLException;
import java.util.ArrayList;

public class SeriesContentModel implements Model {
    private ArrayList<SeriesContentModelListener> seriesContentModelListeners = new ArrayList<>();
    private DataBaseInterface localDataBase;
    private String lastSeriesContent;

    public SeriesContentModel() {

    }

    public void setLocalDataBase(DataBaseInterface localDataBase) {
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
        for (SeriesContentModelListener seriesContentModelListener : seriesContentModelListeners)
            seriesContentModelListener.showSeriesContentHasFinished();
    }

    public String getLastSeriesContent() {
        return lastSeriesContent;
    }

    public void setLastSeriesContent(String lastSeriesContent) {
        this.lastSeriesContent = lastSeriesContent;
    }
}