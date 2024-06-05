package model;

import model.interfaces.SearchModelInterface;
import model.listeners.SearchRankingModelListener;
import retrofit2.Response;
import utils.APIConsumer.SearchApiConsumer;

public class SearchRankingModel implements SearchModelInterface {
    private SearchRankingModelListener searchRankingModelListener;
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
        this.searchRankingModelListener = searchRankingModelListener;
    }
    private void notifySearchFromRankingHasFinishedListener() {
        searchRankingModelListener.searchFromRankingHasFinished();
    }
    public Response<String> getLastSearchResult() {return lastSearchResult;}
}
