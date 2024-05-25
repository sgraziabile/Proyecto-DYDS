package presenter;

import model.WikiPageModel;
import model.WikiSearchModel;
import model.listeners.WikiPageModelListener;
import model.listeners.WikiSearchModelListener;
import views.SearchView;

public class SearchPresenter {
    private WikiSearchModel searchModel;
    private WikiPageModel pageModel;
    private SearchView searchView;
    private Thread taskThread;

    public SearchPresenter(WikiSearchModel searchModel, WikiPageModel pageModel, SearchView searchView) {
        this.searchModel = searchModel;
        this.pageModel = pageModel;
        this.searchView = searchView;
        initListeners();
    }
    public void start() {
        searchView.showView();
    }
    private void initListeners() {
        searchModel.setListener(new WikiSearchModelListener() {
            @Override
            public void searchHasFinished() {
                //showSearchResult()
            }
        });
        pageModel.setListener(new WikiPageModelListener() {
            @Override
            public void seriesRetrieved() {
                //showSeriesRetrieved
            }
        });
    }
    public void setSearchView(SearchView searchView) {
        this.searchView = searchView;
    }
}
