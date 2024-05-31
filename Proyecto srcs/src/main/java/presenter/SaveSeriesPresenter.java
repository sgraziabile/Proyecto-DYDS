package presenter;

import model.DataBaseModel;
import views.SearchView;

public class SaveSeriesPresenter implements Presenter {
    private SearchView searchView;
    private DataBaseModel dataBaseModel;
    private Thread taskThread;
    private SearchPresenter searchPresenter;

    public SaveSeriesPresenter(DataBaseModel dataBaseModel) {
        this.dataBaseModel = dataBaseModel;
        initListeners();
    }
    private void initListeners() {

    }
    public void setSearchPresenter(SearchPresenter searchPresenter) {
        this.searchPresenter = searchPresenter;
    }
    public void setSearchView(SearchView searchView) {
        this.searchView = searchView;
    }
    public void setDataBaseModel(DataBaseModel dataBaseModel) {
        this.dataBaseModel = dataBaseModel;
    }
    public void onSavedLocallyButtonClicked() {
        String seriesTitle = searchPresenter.getLastSeriesTitle();
        String seriesExctract = searchView.getSelectedSeriesPaneText();
        if(seriesExctract != null) {
            requestSaveSeries(seriesTitle, seriesExctract);
        }
    }
    private void requestSaveSeries(String seriesTitle, String seriesExctract) {
        taskThread = new Thread(() -> {
            dataBaseModel.saveSeries(seriesTitle, seriesExctract);
        });
        taskThread.start();
    }

}
