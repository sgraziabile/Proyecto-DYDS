package model;

import utils.DataBaseManager.DataBase;
import model.listeners.DeleteSeriesModelListener;

import java.sql.SQLException;

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
    public void deleteSeries(String seriesTitle) throws SQLException {
        try {
        localDataBase.deleteSavedSeries(seriesTitle);
        deleteSeriesModelListener.deleteSeriesHasFinished();
        } catch (SQLException e) {
            throw new SQLException();
        }
    }
    private void notifyDeleteSeriesHasFinished() {
        deleteSeriesModelListener.deleteSeriesHasFinished();
    }


}
