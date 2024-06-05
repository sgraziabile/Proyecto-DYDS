package model;

import model.APIs.WikipediaSearchAPI;
import model.listeners.WikiSearchModelListener;
import retrofit2.Response;
import utils.APIConsumer.SearchApiCosnumer;
import utils.DataBaseManager.DataBase;

import java.sql.SQLException;

public class WikiSearchModel implements Model{
    private WikipediaSearchAPI searchAPI;
    private WikiSearchModelListener searchModelListener;
    private Response<String> lastSearchResult;
    private DataBase localDataBase;
    private SearchApiCosnumer searchApiCosnumer;

    public WikiSearchModel() {

    }
    public void setLocalDataBase(DataBase localDataBase) {
        this.localDataBase = localDataBase;
    }
    public void setSearchApiConsumer(SearchApiCosnumer searchApiCosnumer) {
        this.searchApiCosnumer = searchApiCosnumer;
    }
    public void searchTerm(String termToSearch, int limit) throws Exception {
        Response<String> callForSearchResponse = null;
        try {
            callForSearchResponse = searchApiCosnumer.searchTerm(termToSearch, limit);
        } catch(Exception e) {
            throw new Exception();
        }
        lastSearchResult = callForSearchResponse;
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
