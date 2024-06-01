package model;

import dyds.tvseriesinfo.fulllogic.DataBase;
import model.listeners.SaveChangesModelListener;
import model.listeners.SaveSeriesModelListener;

public class SaveChangesModel implements Model{
    private SaveChangesModelListener saveChangesModelListener;
    private DataBase localDataBase;

    public SaveChangesModel() {

    }
    public void setListener(SaveChangesModelListener saveChangesModelListener) {
        this.saveChangesModelListener = saveChangesModelListener;
    }
    public void saveChanges(String selectedSeriesTitle, String modifiedExtract) {
        localDataBase.saveInfo(selectedSeriesTitle, modifiedExtract);
        saveChangesModelListener.saveChangesHasFinished();
    }
}
