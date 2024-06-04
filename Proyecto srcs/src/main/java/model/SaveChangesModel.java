package model;

import utils.DataBaseManager.DataBase;
import model.listeners.SaveChangesModelListener;

import java.sql.SQLException;

public class SaveChangesModel implements Model{
    private SaveChangesModelListener saveChangesModelListener;
    private DataBase localDataBase;

    public SaveChangesModel() {

    }
    public void setLocalDataBase(DataBase localDataBase) {
        this.localDataBase = localDataBase;
    }
    public void setListener(SaveChangesModelListener saveChangesModelListener) {
        this.saveChangesModelListener = saveChangesModelListener;
    }
    public void saveChanges(String selectedSeriesTitle, String modifiedExtract) throws SQLException{
        try {
            localDataBase.saveSeriesContent(selectedSeriesTitle, modifiedExtract);
            saveChangesModelListener.saveChangesHasFinished();
        } catch (Exception e) {
            throw new SQLException();
        }
    }
}
