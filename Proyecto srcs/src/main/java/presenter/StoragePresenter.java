package presenter;

import model.*;
import model.entities.HtmlHandler;
import model.listeners.DeleteSeriesModelListener;
import model.listeners.RetrieveSeriesModelListener;
import model.listeners.SaveChangesModelListener;
import model.listeners.SeriesContentModelListener;
import views.StorageView;

import java.util.ArrayList;

public class StoragePresenter implements Presenter{

    private StorageView storageView;
    private Thread taskThread;
    private DataBaseModel dataBaseModel;
    private SaveChangesModel saveChangesModel;
    private DeleteSeriesModel deleteSeriesModel;
    private RetrieveSeriesModel retrieveSeriesModel;
    private SeriesContentModel seriesContentModel;
    private HtmlHandler htmlHandler;



    public StoragePresenter(RetrieveSeriesModel retrieveSeriesModel, SaveChangesModel saveChangesModel, DeleteSeriesModel deleteSeriesModel, SeriesContentModel seriesContentModel) {
        this.retrieveSeriesModel = retrieveSeriesModel;
        this.saveChangesModel = saveChangesModel;
        this.deleteSeriesModel = deleteSeriesModel;
        this.seriesContentModel = seriesContentModel;
        this.htmlHandler = htmlHandler; //podria hacerse singleton
        initListeners();
    }
    private void initListeners() {
        retrieveSeriesModel.setListener(new RetrieveSeriesModelListener() {
            @Override
            public void retrieveSeriesHasFinished() {
                showSavedSeries();
            }
        });
        seriesContentModel.setListener(new SeriesContentModelListener() {
            @Override
            public void showSeriesContentHasFinished() {
                showSelectedSeriesContent();
            }
        });
        deleteSeriesModel.setListener(new DeleteSeriesModelListener() {
            @Override
            public void deleteSeriesHasFinished() {
                updateSavedSeriesContent();
            }
        });
        saveChangesModel.setListener(new SaveChangesModelListener() {
            @Override
            public void saveChangesHasFinished() {
                //mostrar una ventana de que se guardó correctamente
            }
        });
    }
    public void setDataBaseModel(DataBaseModel dataBaseModel) {
        this.dataBaseModel = dataBaseModel;
    }
    public void setSaveChangesModel(SaveChangesModel saveChangesModel) {
        this.saveChangesModel = saveChangesModel;
    }
    public void setDeleteSeriesModel(DeleteSeriesModel deleteSeriesModel) {
        this.deleteSeriesModel = deleteSeriesModel;
    }
    public void setRetrieveSeriesModel(RetrieveSeriesModel retrieveSeriesModel) {
        this.retrieveSeriesModel = retrieveSeriesModel;
    }
    public void setShowSeriesContentModel(SeriesContentModel seriesContentModel) {
        this.seriesContentModel = seriesContentModel;
    }

    public void setStorageView(StorageView storageView) {
        this.storageView = storageView;
    }
    public  void getSavedSeries() {
        taskThread = new Thread(() -> {
            retrieveSeriesModel.getSavedSeries();
        });
        taskThread.start();
    }
    private void showSavedSeries() {
        Object[] formattedSavedSeries = getSavedSeriesTitles();
        storageView.showSavedSeries(formattedSavedSeries);
    }
    private Object[] getSavedSeriesTitles() {
        Object[] formattedSavedSeries;
        formattedSavedSeries = formatSavedSeries(retrieveSeriesModel.getSavedSeriesTitles());
        return formattedSavedSeries;
    }
    private Object[] formatSavedSeries(ArrayList<String> savedSeriesTitles) {
        return savedSeriesTitles.stream().sorted().toArray();
    }
    public void updateSavedSeriesContent() {
        showSavedSeries();
        storageView.clearSavedSeriesContent();
    }
    public void onSaveChangesClick() {
        saveChanges();
    }
    private void saveChanges() {
        Object selectedSeriesTitle = storageView.getSelectedSavedSeries();
        String modifiedExtract  = storageView.getSelectedSeriesContent();
        taskThread = new Thread(() -> {
            saveChangesModel.saveChanges(selectedSeriesTitle.toString(), modifiedExtract);
        });
        taskThread.start();
    }
    public void onDeleteClick() {
        if(storageView.isSelectedOption()) {
            deleteSelectedSeries();
        }
    }
    private void deleteSelectedSeries() {
        Object selectedSeriesTitle = storageView.getSelectedSavedSeries();
        taskThread = new Thread(() -> {
            deleteSeriesModel.deleteSeries(selectedSeriesTitle.toString());
        });
        taskThread.start();
    }
    public void onSavedSeriesSelected() {
        Object selectedSeriesTitle = storageView.getSelectedSavedSeries();
        taskThread = new Thread(() -> {
            seriesContentModel.showSeriesContent(selectedSeriesTitle.toString());
        });
        taskThread.start();
    }
    private void showSelectedSeriesContent() {
        String formattedSeriesContent = htmlHandler.textToHtml(seriesContentModel.getLastSeriesContent());
        storageView.showSavedSeriesContent(formattedSeriesContent);
    }
}
