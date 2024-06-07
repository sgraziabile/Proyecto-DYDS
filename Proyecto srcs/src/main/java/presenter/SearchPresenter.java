package presenter;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
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
import java.sql.SQLException;
import java.util.ArrayList;

public class SearchPresenter implements Presenter{
    private WikiSearchModel searchModel;
    private WikiPageModel pageModel;
    private SearchView searchView;
    private Thread taskThread;
    private HtmlHandler htmlHandler;
    private JPopupMenu searchOptionsMenu;
    private String lastSeriesTitle;
    private JsonParser jsonParser;
    private ArrayList<Series> lastSearchResultsList = new ArrayList<>();


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
        try {
            searchView.setWaitingStatus();
            requestSearch(searchView.getSearchFieldText(), 5);
        }catch(Exception exception) {
            notifySearchError();
        }
    }
    public void onSeriesMenuSelect(Series series) {
        searchView.setWorkingStatus();
        requestRetrieveSeries(series);
    }
    public void findRankingSeriesID(String seriesTitle, Response<String> lastSearchResult) {
        try {
            JsonArray jsonResults = jsonParser.getJsonResults(lastSearchResult);
            for (JsonElement jasonResult : jsonResults) {
                Series series = jsonParser.buildSeriesFromJson(jasonResult);
                if (series.getTitle().equals(seriesTitle)) {
                    requestRetrieveSeries(series);
                    break;
                    }
                }
        }catch(Exception e) {
            notifySearchError();
        }
    }

    public void requestSearch(String termToSearch, int limit) throws Exception {
        try {
            searchModel.searchTerm(termToSearch, limit);
        }catch(Exception e) {
            notifySearchError();
        }
    }
    private void requestRetrieveSeries(Series selectedSeries) {
            taskThread = new Thread(() -> {
                try {
                    pageModel.retrieveSeries(selectedSeries);
                }catch(Exception e) {
                    notifySearchError();
                }
            });
            taskThread.start();
    }
    private void showSearchResult() {
        lastSearchResultsList = new ArrayList<>();
        Response<String> lastSearchResult = searchModel.getLastSearchResult();
        JsonArray jsonResults = jsonParser.getJsonResults(lastSearchResult);
        this.searchOptionsMenu = new JPopupMenu("Search Results");
        searchView.setSearchOptionsMenu(searchOptionsMenu);
        if(jsonResults.isEmpty()) {
            searchView.showEventNotifier("No results found for term");
        } else {
            try {
                for (JsonElement jasonResult : jsonResults) {
                    Series series = jsonParser.buildSeriesFromJson(jasonResult);
                    int seriesScore = Integer.parseInt(searchModel.getSeriesScore(series.getTitle()));
                    series.setScore(seriesScore);
                    lastSearchResultsList.add(series);
                    addSeriesToSearchOptionsMenu(series);
                }
            } catch (SQLException e) {
                searchView.showEventNotifier("Error getting series scores from database");
            }
        }
        searchView.showSearchOptionsMenu();
    }
    private void showRetrievedSeries(Series series) {
        try {
            Response<String> lastRetrievedSeries = pageModel.getLastRetrievedSeries();
            String retrievedSeriesExtract = "";
            JsonElement searchResultExtract2 = jsonParser.getSearchResultExctract(lastRetrievedSeries);
            if (searchResultExtract2 == null) {
                retrievedSeriesExtract = "No Results";
                searchView.showEventNotifier("No extract found");
            } else {
                retrievedSeriesExtract = "<h1>" + series.getTitle() + "</h1>";
                lastSeriesTitle = series.getTitle();
                retrievedSeriesExtract += searchResultExtract2.getAsString().replace("\\n", "\n");
                retrievedSeriesExtract = htmlHandler.textToHtml(retrievedSeriesExtract);
                searchView.showSelectedSeries(retrievedSeriesExtract);
                searchView.setSearchTextField(series.getTitle());
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
    public void clearLastSeriesTitle(){
        lastSeriesTitle = "";
    }
    public void notifySearchError() {
        searchView.showEventNotifier("Search couldn't be made");
    }
    public ArrayList<Series> getLastSearchResultsList() {
        return lastSearchResultsList;
    }

}
