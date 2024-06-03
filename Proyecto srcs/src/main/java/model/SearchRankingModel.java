package model;

import model.APIs.WikipediaSearchAPI;
import model.listeners.SearchRankingModelListener;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class SearchRankingModel {
    private WikipediaSearchAPI searchAPI;
    private SearchRankingModelListener searchRankingModelListener;
    private Response<String> lastSearchResult;

    public SearchRankingModel() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://en.wikipedia.org/w/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        searchAPI = retrofit.create(WikipediaSearchAPI.class);
    }
    public void searchTerm(String termToSearch, int limit) {
        Response<String> callForSearchResponse = null;
        try {
            callForSearchResponse = searchAPI.searchForTerm(termToSearch + " (Tv series) articletopic:\"television\"",limit).execute();
        } catch(Exception e) {
            System.out.println("No result found for term."); //crear una ventana que avise del error
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
