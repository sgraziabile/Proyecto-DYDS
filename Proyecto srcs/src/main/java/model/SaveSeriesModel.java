package model;

import utils.DataBaseManager.DataBase;
import model.listeners.SaveSeriesModelListener;

public class SaveSeriesModel {
    private DataBase localDataBase;
    private SaveSeriesModelListener saveSeriesModelListener;

    public SaveSeriesModel() {

    }
    public void setListener(SaveSeriesModelListener saveSeriesModelListener) {
        this.saveSeriesModelListener = saveSeriesModelListener;
    }
    public void saveSeries(String seriesTitle, String seriesExctract) {
        DataBase.saveSeriesContent(seriesTitle.replace("'", "`"),seriesExctract );
        saveSeriesModelListener.saveSeriesHasFinished();
    }
}
