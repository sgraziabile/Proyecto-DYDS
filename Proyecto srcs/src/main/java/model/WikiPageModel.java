package model;

import model.APIs.WikipediaPageAPI;
import model.entities.Series;
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
    private void notifySeriesRetrievedListener(Series series) {pageModelListener.seriesRetrieved(series);};
    public void setListener(WikiPageModelListener wikiPageModelListener) {
        this.pageModelListener = wikiPageModelListener;
    }
    public void retrieveSeries(Series series) {
        Response<String> callForPageResponse = null;
        try {
            callForPageResponse = pageAPI.getExtractByPageID(series.getPageID()).execute();
        } catch(Exception e) {
            System.out.println("No result found for term."); //crear una ventana que avise del error
        }
        lastRetrievedSeries = callForPageResponse;
        notifySeriesRetrievedListener(series);
    }
    public Response<String> getLastRetrievedSeries() {
        return lastRetrievedSeries;
    }

}
