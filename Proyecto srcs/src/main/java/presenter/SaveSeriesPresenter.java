package presenter;

import model.SaveSeriesModel;
import model.listeners.SaveSeriesModelListener;
import views.SearchView;

public class SaveSeriesPresenter implements Presenter {
    private SearchView searchView;
    private SaveSeriesModel saveSeriesModel;
    private Thread taskThread;
    private SearchPresenter searchPresenter;
    private StoragePresenter storagePresenter;

    public SaveSeriesPresenter(SaveSeriesModel saveSeriesModel) {
        this.saveSeriesModel = saveSeriesModel;
        initListeners();
    }
    private void initListeners() {
        saveSeriesModel.setListener(new SaveSeriesModelListener() {
            @Override
            public void saveSeriesHasFinished() {
                clearSearchView();
                storagePresenter.updateSavedSeriesContent();
                searchView.showEventNotifier("TV series saved successfully!");
            }
        });
    }
    public void setSearchPresenter(SearchPresenter searchPresenter) {
        this.searchPresenter = searchPresenter;
    }
    public void setStoragePresenter(StoragePresenter storagePresenter) {
        this.storagePresenter = storagePresenter;
    }
    public void setSearchView(SearchView searchView) {
        this.searchView = searchView;
    }
    public void setSaveSeriesModel(SaveSeriesModel saveSeriesModel) {this.saveSeriesModel = saveSeriesModel;}
    public void onSavedLocallyButtonClicked() {
        String seriesTitle = searchPresenter.getLastSeriesTitle();
        String seriesExtract = searchView.getSelectedSeriesPaneText();
        if(!searchView.getSearchFieldText().equals("")) {
            requestSaveSeries(seriesTitle, seriesExtract);
        }
        else {
            searchView.showEventNotifier("No series selected!");
        }
    }
    private void requestSaveSeries(String seriesTitle, String seriesExtract) {
        taskThread = new Thread(() -> {
            try {
            saveSeriesModel.saveSeries(seriesTitle, seriesExtract);
            } catch (Exception e) {
                searchView.showEventNotifier("Error saving TV series");
            }
        });
        taskThread.start();
    }
    private void clearSearchView() {
        searchView.clearSearchField();
        searchView.clearSelectedSeriesPane();
    }

}
