package model;

import model.APIs.WikipediaSearchAPI;
import model.listeners.SearchRankingModelListener;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import utils.APIConsumer.SearchApiCosnumer;

public class SearchRankingModel {
    private SearchRankingModelListener searchRankingModelListener;
    private SearchApiCosnumer searchApiCosnumer;
    private Response<String> lastSearchResult;

    public SearchRankingModel() {

    }
   public void setSearchApiConsumer(SearchApiCosnumer searchApiCosnumer) {this.searchApiCosnumer = searchApiCosnumer;}
    public void searchTerm(String termToSearch, int limit) throws Exception{
        Response<String> callForSearchResponse = null;
        try {
            callForSearchResponse = searchApiCosnumer.searchTerm(termToSearch, limit);
        } catch(Exception e) {
            throw new Exception();
        }
        lastSearchResult = callForSearchResponse;
        notifySearchFromRankingHasFinishedListener();
    }
    public void setListener(SearchRankingModelListener searchRankingModelListener) {
        this.searchRankingModelListener = searchRankingModelListener;
    }
    private void notifySearchFromRankingHasFinishedListener() {
        searchRankingModelListener.searchFromRankingHasFinished();
    }
    public Response<String> getLastSearchResult() {return lastSearchResult;}
}
