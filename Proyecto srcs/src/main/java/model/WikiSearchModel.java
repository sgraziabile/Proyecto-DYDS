package model;

import model.APIs.WikipediaSearchAPI;
import model.interfaces.SearchModelInterface;
import model.listeners.WikiSearchModelListener;
import retrofit2.Response;
import utils.APIConsumer.SearchApiConsumer;
import utils.DataBaseManager.DataBase;

import java.sql.SQLException;

public class WikiSearchModel implements SearchModelInterface {
    private WikipediaSearchAPI searchAPI;
    private WikiSearchModelListener searchModelListener;
    private Response<String> lastSearchResult;
    private DataBase localDataBase;
    private SearchApiConsumer searchApiConsumer;

    public WikiSearchModel() {

    }
    public void setLocalDataBase(DataBase localDataBase) {
        this.localDataBase = localDataBase;
    }
    public void setSearchApiConsumer(SearchApiConsumer searchApiConsumer) {
        this.searchApiConsumer = searchApiConsumer;
    }
    public void searchTerm(String termToSearch, int limit) throws Exception {
        Response<String> callForSearchResponse = null;
        try {
            callForSearchResponse = searchApiConsumer.searchTerm(termToSearch, limit);
        } catch(Exception e) {
            throw new Exception();
        }
        lastSearchResult = callForSearchResponse;
        System.out.println(lastSearchResult.body());
        notifySearchHasFinishedListener();
    }
    public void setSearchAPI(WikipediaSearchAPI searchAPI) {
        this.searchAPI = searchAPI;
    }
    public String getSeriesScore(String title) throws SQLException{
        try {
            return localDataBase.getSeriesScore(title);
    } catch (SQLException e) {
            throw new SQLException();
        }
    }
    public Response<String> getLastSearchResult() {
        return lastSearchResult;
    }
    private void notifySearchHasFinishedListener() {
        searchModelListener.searchHasFinished();
    }
    public void setListener(WikiSearchModelListener searchModelListener) {
        this.searchModelListener = searchModelListener;
    }

}
