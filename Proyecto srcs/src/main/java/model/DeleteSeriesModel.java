package model;

import model.listeners.DeleteSeriesModelListener;
import utils.DataBaseManager.DataBaseInterface;

import java.sql.SQLException;
import java.util.ArrayList;

public class DeleteSeriesModel implements Model{
    private ArrayList<DeleteSeriesModelListener> deleteSeriesModelListeners = new ArrayList<>();
    private DataBaseInterface localDataBase;

    public DeleteSeriesModel() {

    }
    public void setLocalDataBase(DataBaseInterface localDataBase) {
        this.localDataBase = localDataBase;
    }
    public void setListener(DeleteSeriesModelListener deleteSeriesModelListener) {
        this.deleteSeriesModelListeners.add(deleteSeriesModelListener);
    }
    public void deleteSeries(String seriesTitle) throws SQLException {
        try {
            localDataBase.deleteSavedSeries(seriesTitle);
            notifyDeleteSeriesHasFinished();
        } catch (SQLException e) {
            throw new SQLException();
        }
    }
    private void notifyDeleteSeriesHasFinished() {
        for (DeleteSeriesModelListener deleteSeriesModelListener : deleteSeriesModelListeners) {
            deleteSeriesModelListener.deleteSeriesHasFinished();
        }
    }


}
