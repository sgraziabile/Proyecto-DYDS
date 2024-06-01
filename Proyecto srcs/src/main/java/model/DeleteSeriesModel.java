package model;

import dyds.tvseriesinfo.fulllogic.DataBase;
import model.listeners.DeleteSeriesModelListener;

public class DeleteSeriesModel implements Model{
    private DeleteSeriesModelListener deleteSeriesModelListener;
    private DataBase localDataBase;

    public DeleteSeriesModel() {

    }
    public void setListener(DeleteSeriesModelListener deleteSeriesModelListener) {
        this.deleteSeriesModelListener = deleteSeriesModelListener;
    }
    public void deleteSeries(String seriesTitle) {
        localDataBase.deleteEntry(seriesTitle);
        deleteSeriesModelListener.deleteSeriesHasFinished();

    }
    private void notifyDeleteSeriesHasFinished() {
        deleteSeriesModelListener.deleteSeriesHasFinished();
    }


}
