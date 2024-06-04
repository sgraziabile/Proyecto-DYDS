package model;

import utils.DataBaseManager.DataBase;
import model.listeners.DeleteSeriesModelListener;

public class DeleteSeriesModel implements Model{
    private DeleteSeriesModelListener deleteSeriesModelListener;
    private DataBase localDataBase;

    public DeleteSeriesModel() {

    }
    public void setLocalDataBase(DataBase localDataBase) {
        this.localDataBase = localDataBase;
    }
    public void setListener(DeleteSeriesModelListener deleteSeriesModelListener) {
        this.deleteSeriesModelListener = deleteSeriesModelListener;
    }
    public void deleteSeries(String seriesTitle) {
        localDataBase.deleteSavedSeries(seriesTitle);
        deleteSeriesModelListener.deleteSeriesHasFinished();
    }
    private void notifyDeleteSeriesHasFinished() {
        deleteSeriesModelListener.deleteSeriesHasFinished();
    }


}
