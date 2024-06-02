package presenter;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import model.WikiPageModel;
import model.WikiSearchModel;
import utils.HtmlHandler;
import model.entities.Series;
import model.listeners.WikiPageModelListener;
import model.listeners.WikiSearchModelListener;
import retrofit2.Response;
import views.SearchView;

import javax.swing.*;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class SearchPresenter implements Presenter{
    private WikiSearchModel searchModel;
    private WikiPageModel pageModel;
    private SearchView searchView;
    private Thread taskThread;
    private HtmlHandler htmlHandler;
    private JPopupMenu searchOptionsMenu;
    private String lastSeriesTitle;


    public SearchPresenter(WikiSearchModel searchModel, WikiPageModel pageModel) {
        this.searchModel = searchModel;
        this.pageModel = pageModel;
        this.htmlHandler = new HtmlHandler();
        initListeners();
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
            public void seriesRetrieved(Series series) {showRetrievedSeries(series); }
        });
    }
    public void setSearchModel(WikiSearchModel searchModel) {
        this.searchModel = searchModel;
    }
    public void setPageModel(WikiPageModel pageModel) {
        this.pageModel = pageModel;
    }
    public void setSearchView(SearchView searchView) {
        this.searchView = searchView;
    }

    public void onSearchButtonClicked() {
        searchView.setWaitingStatus();
        requestSearch(searchView.getSearchFieldText());
    }
    public void onSeriesMenuSelect(Series series) {
        try {
            searchView.setWorkingStatus();
            requestRetrieveSeries(series);
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
    private void requestRetrieveSeries(Series selectedSeries) {
        taskThread = new Thread(() -> {
            pageModel.retrieveSeries(selectedSeries) ;
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
        this.searchOptionsMenu = new JPopupMenu("Search Results");
        searchView.setSearchOptionsMenu(searchOptionsMenu);

        for (JsonElement jasonResult : jsonResults) {
            JsonObject jsonSearchResult = jasonResult.getAsJsonObject();
            String searchResultTitle = jsonSearchResult.get("title").getAsString();
            String searchResultPageId = jsonSearchResult.get("pageid").getAsString();
            String searchResultSnippet = jsonSearchResult.get("snippet").getAsString();

            int seriesScore = Integer.parseInt(searchModel.getSeriesScore(searchResultTitle));
            Series series = new Series(searchResultTitle, searchResultPageId, searchResultSnippet);
            series.setScore(seriesScore);
            addSeriesToSearchOptionsMenu(series);
        }
        searchView.showSearchOptionsMenu();
    }
    private void showRetrievedSeries(Series series) {
        try {
            Response<String> lastRetrievedSeries = pageModel.getLastRetrievedSeries();
            String retrievedSeriesExtract = "";
            Gson gson = new Gson();
            JsonObject jobj2 = gson.fromJson(lastRetrievedSeries.body(), JsonObject.class);
            JsonObject query2 = jobj2.get("query").getAsJsonObject();
            JsonObject pages = query2.get("pages").getAsJsonObject();
            Set<Map.Entry<String, JsonElement>> pagesSet = pages.entrySet();
            Map.Entry<String, JsonElement> first = pagesSet.iterator().next();
            JsonObject page = first.getValue().getAsJsonObject();
            JsonElement searchResultExtract2 = page.get("extract");
            if (searchResultExtract2 == null) {
                retrievedSeriesExtract = "No Results";
                //generar ventana de error
            } else {
                retrievedSeriesExtract = "<h1>" + series.title + "</h1>";
                lastSeriesTitle = series.title;
                retrievedSeriesExtract += searchResultExtract2.getAsString().replace("\\n", "\n");
                retrievedSeriesExtract = htmlHandler.textToHtml(retrievedSeriesExtract);
                searchView.showSelectedSeries(retrievedSeriesExtract);
                searchView.setWaitingStatus();
                searchView.allowScoreUpdate();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    private void addSeriesToSearchOptionsMenu(Series series) {
        if(series.isRated()) {
            series.setIcon(new ImageIcon("rated_icon.png"));
        }
        searchOptionsMenu.add(series);
        searchView.initSearchOptionListener(series);
    }
    public String getLastSeriesTitle() {
        return lastSeriesTitle;
    }

}
