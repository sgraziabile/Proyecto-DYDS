package model;

import dyds.tvseriesinfo.fulllogic.DataBase;
import model.listeners.DataBaseModelListener;

import java.util.ArrayList;

public class DataBaseModel {

    private DataBaseModelListener dataBaseModelListener;
    private DataBase localDataBase;

    public DataBaseModel() {

    }
    public void setListener(DataBaseModelListener dataBaseModelListener) {
        this.dataBaseModelListener = dataBaseModelListener;
    }
    public ArrayList<String> getSavedSeriesTitles() {
        return localDataBase.getTitles();
    }
}
