package model;

import utils.DataBaseManager.DataBase;
import model.listeners.SaveSeriesModelListener;
import utils.DataBaseManager.DataBaseInterface;

import java.sql.SQLException;
import java.util.ArrayList;

public class SaveSeriesModel {
    private DataBaseInterface localDataBase;
    private ArrayList<SaveSeriesModelListener> saveSeriesModelListeners = new ArrayList<>();

    public SaveSeriesModel() {}

    public void setLocalDataBase(DataBaseInterface localDataBase) {
        this.localDataBase = localDataBase;
    }
    public void setListener(SaveSeriesModelListener saveSeriesModelListener) {
        this.saveSeriesModelListeners.add(saveSeriesModelListener);
    }
    public void saveSeries(String seriesTitle, String seriesExtract) throws SQLException {
        try {
            localDataBase.saveSeriesContent(seriesTitle.replace("'", "`"),seriesExtract );
            notifySaveSeriesHasFinished();
        } catch (Exception e) {
            throw new SQLException();
        }
    }
    private void notifySaveSeriesHasFinished() {
        for (SaveSeriesModelListener listener : saveSeriesModelListeners) {
            listener.saveSeriesHasFinished();
        }
    }
}
