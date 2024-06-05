package model;

import utils.DataBaseManager.DataBase;
import model.listeners.SaveChangesModelListener;

import java.sql.SQLException;
import java.util.ArrayList;

public class SaveChangesModel implements Model{
    private ArrayList<SaveChangesModelListener> saveChangesModelListeners = new ArrayList<>();
    private DataBase localDataBase;

    public SaveChangesModel() {

    }
    public void setLocalDataBase(DataBase localDataBase) {
        this.localDataBase = localDataBase;
    }
    public void setListener(SaveChangesModelListener saveChangesModelListener) {
        this.saveChangesModelListeners.add(saveChangesModelListener);
    }
    public void saveChanges(String selectedSeriesTitle, String modifiedExtract) throws SQLException{
        try {
            localDataBase.saveSeriesContent(selectedSeriesTitle, modifiedExtract);
            notifySaveChangesHasFinished();
        } catch (Exception e) {
            throw new SQLException();
        }
    }
    private void notifySaveChangesHasFinished() {
        for (SaveChangesModelListener listener : saveChangesModelListeners) {
            listener.saveChangesHasFinished();
        }
    }
}
