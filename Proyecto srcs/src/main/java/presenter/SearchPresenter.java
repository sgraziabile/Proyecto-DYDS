package presenter;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import model.WikiPageModel;
import model.WikiSearchModel;
import model.entities.SearchResult;
import model.listeners.WikiPageModelListener;
import model.listeners.WikiSearchModelListener;
import retrofit2.Response;
import views.SearchView;

import javax.swing.*;
import java.util.Iterator;

public class SearchPresenter implements Presenter{
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
                showSearchResult();
            }
        });
        pageModel.setListener(new WikiPageModelListener() {
            @Override
            public void seriesRetrieved() {
                //showSeriesRetrieved
            }
        });
    }
    public void onSearchButtonClicked() {
        searchView.setWaitingStatus();
        requestSearch(searchView.getSearchFieldText());
    }
    public void onSeriesMenuSelect() {
        try {
            searchView.setWorkingStatus();
            //FALTA SEGUIR ESTO
        }catch(Exception e) {
            System.out.println("It was not possible to retrieve series data.");
        }
    }
    private void requestSearch(String termToSearch) {
        taskThread = new Thread(() -> {
            searchModel.searchTerm(termToSearch);
        });
        taskThread.start();
    }
    private void showSearchResult() {
        //factorizar
        Response<String> lastSearchResult = searchModel.getLastSearchResult();
        Gson gson = new Gson();
        JsonObject jobj = gson.fromJson(lastSearchResult.body(), JsonObject.class);
        JsonObject query = jobj.get("query").getAsJsonObject();
        Iterator<JsonElement> resultIterator = query.get("search").getAsJsonArray().iterator();
        JsonArray jsonResults = query.get("search").getAsJsonArray();
        //factorizar
        //toAlberto: shows each result in the JSonArry in a Popupmenu
        JPopupMenu searchOptionsMenu = new JPopupMenu("Search Results");
        searchView.setSearchOptionsMenu(searchOptionsMenu);
        for (JsonElement jasonResult : jsonResults) {
            JsonObject jsonSearchResult = jasonResult.getAsJsonObject();
            String searchResultTitle = jsonSearchResult.get("title").getAsString();
            String searchResultPageId = jsonSearchResult.get("pageid").getAsString();
            String searchResultSnippet = jsonSearchResult.get("snippet").getAsString();

            SearchResult searchResult = new SearchResult(searchResultTitle, searchResultPageId, searchResultSnippet);
            addSeriesToSearchOptionsMenu(searchOptionsMenu,searchResult);
        }
        searchView.showSearchOptionsMenu();
    }
    public void addSeriesToSearchOptionsMenu(JPopupMenu searchOptionsMenu,SearchResult searchResult) {
        searchOptionsMenu.add(searchResult);
        searchView.initSearchOptionListener(searchResult);
    }

    public void setSearchView(SearchView searchView) {
        this.searchView = searchView;
    }
}
