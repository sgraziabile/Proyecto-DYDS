package model;

import model.listeners.SearchRankingModelListener;
import retrofit2.Response;
import utils.APIConsumer.SearchApiConsumer;

import java.util.ArrayList;

public class SearchRankingModel implements Model{
    private ArrayList<SearchRankingModelListener> searchRankingModelListeners = new ArrayList<>();
    private SearchApiConsumer searchApiConsumer;
    private Response<String> lastSearchResult;

    public SearchRankingModel() {

    }
   public void setSearchApiConsumer(SearchApiConsumer searchApiConsumer) {this.searchApiConsumer = searchApiConsumer;}
    public void searchTerm(String termToSearch, int limit) throws Exception{
        Response<String> callForSearchResponse = null;
        try {
            callForSearchResponse = searchApiConsumer.searchTerm(termToSearch, limit);
        } catch(Exception e) {
            throw new Exception();
        }
        lastSearchResult = callForSearchResponse;
        notifySearchFromRankingHasFinishedListener();
    }
    public void setListener(SearchRankingModelListener searchRankingModelListener) {
        this.searchRankingModelListeners.add(searchRankingModelListener);
    }
    private void notifySearchFromRankingHasFinishedListener() {
        for(SearchRankingModelListener searchRankingModelListener : searchRankingModelListeners) {
            searchRankingModelListener.searchFromRankingHasFinished();
        }
    }
    public Response<String> getLastSearchResult() {return lastSearchResult;}
}
