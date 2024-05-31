package model;

import dyds.tvseriesinfo.fulllogic.DataBase;
import model.listeners.RetrieveSeriesModelListener;

import java.util.ArrayList;

public class RetrieveSeriesModel implements Model {
    private DataBase localDataBase;
    private RetrieveSeriesModelListener retrieveSeriesModelListener;
    private ArrayList<String> savedSeriesTitles;

    public RetrieveSeriesModel() {

    }
    public void setListener(RetrieveSeriesModelListener retrieveSeriesModelListener) {
        this.retrieveSeriesModelListener = retrieveSeriesModelListener;
    }
    public void getSavedSeries() {
        System.out.print("Getting saved series");
        savedSeriesTitles = getSavedSeriesTitles();
        retrieveSeriesModelListener.retrieveSeriesHasFinished();
    }
    public ArrayList<String> getSavedSeriesTitles() {
        return localDataBase.getTitles();
    }
}