package presenter;

import model.*;
import utils.HtmlHandler;
import model.listeners.DeleteSeriesModelListener;
import model.listeners.RetrieveSeriesModelListener;
import model.listeners.SaveChangesModelListener;
import model.listeners.SeriesContentModelListener;
import views.StorageView;

import java.sql.SQLException;
import java.util.ArrayList;

public class StoragePresenter implements Presenter{

    private StorageView storageView;
    private Thread taskThread;
    private SaveChangesModel saveChangesModel;
    private DeleteSeriesModel deleteSeriesModel;
    private RetrieveSeriesModel retrieveSeriesModel;
    private SeriesContentModel seriesContentModel;
    private SearchPresenter searchPresenter;
    private HtmlHandler htmlHandler;

    public StoragePresenter(RetrieveSeriesModel retrieveSeriesModel, SaveChangesModel saveChangesModel, DeleteSeriesModel deleteSeriesModel, SeriesContentModel seriesContentModel) {
        this.retrieveSeriesModel = retrieveSeriesModel;
        this.saveChangesModel = saveChangesModel;
        this.deleteSeriesModel = deleteSeriesModel;
        this.seriesContentModel = seriesContentModel;
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
                storageView.showEventNotifier("Series deleted successfully!");
            }
        });
        saveChangesModel.setListener(new SaveChangesModelListener() {
            @Override
            public void saveChangesHasFinished() {
                storageView.showEventNotifier("Changes saved successfully!");
            }
        });
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
    public void setSearchPresenter(SearchPresenter searchPresenter) {
        this.searchPresenter = searchPresenter;
    }

    public void setStorageView(StorageView storageView) {
        this.storageView = storageView;
    }
    public  void getSavedSeries() {
        taskThread = new Thread(() -> {
            try {
                retrieveSeriesModel.getSavedSeries();
            }catch(SQLException e) {
                storageView.showEventNotifier("Error retrieving saved series");
            }
        });
        taskThread.start();
    }
    private void showSavedSeries() {
        Object[] formattedSavedSeries = getSavedSeriesTitles();
        storageView.showSavedSeries(formattedSavedSeries);
    }
    private Object[] getSavedSeriesTitles() {
        Object[] formattedSavedSeries = new Object[0];
        try {
            formattedSavedSeries = formatSavedSeries(retrieveSeriesModel.getSavedSeriesTitles());
        } catch(SQLException e) {
            storageView.showEventNotifier(e.getMessage());
        }
        return formattedSavedSeries;
    }
    private Object[] formatSavedSeries(ArrayList<String> savedSeriesTitles) {
        return savedSeriesTitles.stream().sorted().toArray();
    }
    public void updateSavedSeriesContent() {
        showSavedSeries();
        storageView.clearSavedSeriesContent();
        searchPresenter.clearLastSeriesTitle();
    }
    public void onSaveChangesClick() {
        saveChanges();
    }
    private void saveChanges() {
        Object selectedSeriesTitle = storageView.getSelectedSavedSeries();
        String modifiedExtract  = storageView.getSelectedSeriesContent();
        try {
            saveChangesModel.saveChanges(selectedSeriesTitle.toString(), modifiedExtract);
        }catch(SQLException e) {
            storageView.showEventNotifier("Error saving changes");
        }
    }
    public void onDeleteClick() {
        if(storageView.isSelectedOption()) {
            deleteSelectedSeries();
        }
    }
    private void deleteSelectedSeries() {
        Object selectedSeriesTitle = storageView.getSelectedSavedSeries();
            try {
                deleteSeriesModel.deleteSeries(selectedSeriesTitle.toString());
            }catch(SQLException e) {
                storageView.showEventNotifier("Error deleting series");
            }
    }
    public void onSavedSeriesSelected() {
        Object selectedSeriesTitle = storageView.getSelectedSavedSeries();
        taskThread = new Thread(() -> {
            try {
                seriesContentModel.showSeriesContent(selectedSeriesTitle.toString());
            }catch(SQLException e) {
                storageView.showEventNotifier(e.getMessage());
            }
        });
        taskThread.start();
    }
    private void showSelectedSeriesContent() {
        String formattedSeriesContent = htmlHandler.textToHtml(seriesContentModel.getLastSeriesContent());
        storageView.showSavedSeriesContent(formattedSeriesContent);
    }
}
