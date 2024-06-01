package model;

import utils.DataBaseManager.DataBase;
import model.listeners.SaveChangesModelListener;

public class SaveChangesModel implements Model{
    private SaveChangesModelListener saveChangesModelListener;
    private DataBase localDataBase;

    public SaveChangesModel() {

    }
    public void setListener(SaveChangesModelListener saveChangesModelListener) {
        this.saveChangesModelListener = saveChangesModelListener;
    }
    public void saveChanges(String selectedSeriesTitle, String modifiedExtract) {
        localDataBase.saveSeriesContent(selectedSeriesTitle, modifiedExtract);
        saveChangesModelListener.saveChangesHasFinished();
    }
}
