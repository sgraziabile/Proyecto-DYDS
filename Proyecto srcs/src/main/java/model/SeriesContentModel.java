package model;

import utils.DataBaseManager.DataBase;
import model.listeners.SeriesContentModelListener;

public class SeriesContentModel implements Model{
    private SeriesContentModelListener seriesContentModelListener;
    private DataBase localDataBase;
    private String lastSeriesContent;

    public SeriesContentModel() {

    }
    public void setListener(SeriesContentModelListener seriesContentModelListener) {
        this.seriesContentModelListener = seriesContentModelListener;
    }
    public void showSeriesContent(String seriesTitle) {
        lastSeriesContent = localDataBase.getSavedSeriesExctract(seriesTitle);
        seriesContentModelListener.showSeriesContentHasFinished();
    }
    public String getLastSeriesContent() {
        return lastSeriesContent;
    }

}
