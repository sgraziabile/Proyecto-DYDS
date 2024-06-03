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
import utils.JsonParser;
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
    private JsonParser jsonParser;


    public SearchPresenter(WikiSearchModel searchModel, WikiPageModel pageModel) {
        this.searchModel = searchModel;
        this.pageModel = pageModel;
        this.htmlHandler = new HtmlHandler();
        this.jsonParser = new JsonParser();
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
    public void searchSeriesFromRanking(String seriesTitle) {
        searchView.setWaitingStatus();
        requestSearch(seriesTitle);
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
        Response<String> lastSearchResult = searchModel.getLastSearchResult();
        JsonArray jsonResults = jsonParser.getJsonResults(lastSearchResult);
        this.searchOptionsMenu = new JPopupMenu("Search Results");
        searchView.setSearchOptionsMenu(searchOptionsMenu);

        for (JsonElement jasonResult : jsonResults) {
            Series series = jsonParser.buildSeriesFromJson(jasonResult);
            int seriesScore = Integer.parseInt(searchModel.getSeriesScore(series.getTitle()));
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
                retrievedSeriesExtract = "<h1>" + series.getTitle() + "</h1>";
                lastSeriesTitle = series.getTitle();
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
