package model;

import dyds.tvseriesinfo.fulllogic.DataBase;
import model.listeners.DataBaseModelListener;

import java.util.ArrayList;

public class DataBaseModel implements Model {

    private ArrayList<DataBaseModelListener> dataBaseModelListener;
    private DataBase localDataBase;

    public DataBaseModel() {

    }
    public void setListener(DataBaseModelListener dataBaseModelListener) {
        this.dataBaseModelListener.add(dataBaseModelListener);
    }
    public ArrayList<String> getSavedSeriesTitles() {
        return localDataBase.getTitles();
    }
    public void saveSeries(String seriesTitle, String seriesExctract) {
        DataBase.saveInfo(seriesTitle.replace("'", "`"),seriesExctract );

    }
}
