package model;

import model.APIs.WikipediaSearchAPI;
import model.listeners.WikiSearchModelListener;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class WikiSearchModel implements Model{
    private WikipediaSearchAPI searchAPI;
    private WikiSearchModelListener searchModelListener;
    private Response<String> lastSearchResult;

    public WikiSearchModel() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://en.wikipedia.org/w/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        searchAPI = retrofit.create(WikipediaSearchAPI.class);
    }
    public void searchTerm(String termToSearch) {
        Response<String> callForSearchResponse = null;
        try {
            callForSearchResponse = searchAPI.searchForTerm(termToSearch + " (Tv series) articletopic:\"television\"").execute();
        } catch(Exception e) {
            System.out.println("No result found for term."); //crear una ventana que avise del error
        }
        lastSearchResult = callForSearchResponse;
        notifySearchHasFinishedListener();
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
