package presenter;

import model.DataBaseModel;
import views.SearchView;

public class SaveSeriesPresenter implements Presenter {
    private SearchView searchView;
    private DataBaseModel dataBaseModel;
    private Thread taskThread;

    public SaveSeriesPresenter(DataBaseModel dataBaseModel, SearchView searchView) {
        this.dataBaseModel = dataBaseModel;
        this.searchView = searchView;
        initListeners();
    }
    public void start() {
        searchView.showView();
    }
    private void initListeners() {

    }
    public void setSearchView(SearchView searchView) {
        this.searchView = searchView;
    }
    public void onSavedLocallyButtonClicked() {
        //DBModel.saveSeries()
    }

}
