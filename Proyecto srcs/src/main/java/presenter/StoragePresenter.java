package presenter;

import model.DataBaseModel;
import views.StorageView;

import java.util.ArrayList;

public class StoragePresenter implements Presenter{

    private StorageView storageView;
    private Thread taskThread;
    private DataBaseModel dataBaseModel;

    public StoragePresenter(DataBaseModel dataBaseModel) {
        this.dataBaseModel = dataBaseModel;
        initListeners();
    }
    private void initListeners() {

    }
    public void setDataBaseModel(DataBaseModel dataBaseModel) {
        this.dataBaseModel = dataBaseModel;
    }
    public void setStorageView(StorageView storageView) {
        this.storageView = storageView;
    }
    public Object[] getSavedSeries() {
        Object[] formattedSavedSeries;
        formattedSavedSeries = formatSavedSeries(dataBaseModel.getSavedSeriesTitles());
        return formattedSavedSeries;
    }
    private Object[] formatSavedSeries(ArrayList<String> savedSeriesTitles) {
        return savedSeriesTitles.stream().sorted().toArray();
    }
    public void onSaveChangesClick() {
        //DBModel saveChanges()
    }
    public void onDeleteClick() {
        //DBModel deleteSeries()
    }
    public void onSavedSeriesSelected() {
        //DBModel getSeriesInfo()
    }
}
