package model;

import model.APIs.WikipediaPageAPI;
import model.entities.SearchResult;
import model.listeners.WikiPageModelListener;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class WikiPageModel implements Model{

    private WikipediaPageAPI pageAPI;
    private WikiPageModelListener pageModelListener;
    private Response<String> lastRetrievedSeries;


    public WikiPageModel() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://en.wikipedia.org/w/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        pageAPI = retrofit.create(WikipediaPageAPI.class);
    }
    private void notifySeriesRetrievedListener(SearchResult searchResult) {pageModelListener.seriesRetrieved(searchResult);};
    public void setListener(WikiPageModelListener wikiPageModelListener) {
        this.pageModelListener = wikiPageModelListener;
    }
    public void retrieveSeries(SearchResult searchResult) {
        Response<String> callForPageResponse = null;
        try {
            callForPageResponse = pageAPI.getExtractByPageID(searchResult.pageID).execute();
        } catch(Exception e) {
            System.out.println("No result found for term."); //crear una ventana que avise del error
        }
        lastRetrievedSeries = callForPageResponse;
        notifySeriesRetrievedListener(searchResult);
    }
    public Response<String> getLastRetrievedSeries() {
        return lastRetrievedSeries;
    }

}
