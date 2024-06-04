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
    public void setPageAPI(WikipediaPageAPI pageAPI) {
        this.pageAPI = pageAPI;
    }
    private void notifySeriesRetrievedListener(Series series) {pageModelListener.seriesRetrieved(series);};
    public void setListener(WikiPageModelListener wikiPageModelListener) {
        this.pageModelListener = wikiPageModelListener;
    }
    public void retrieveSeries(Series series) throws Exception {
        Response<String> callForPageResponse = null;
        try {
            callForPageResponse = pageAPI.getExtractByPageID(series.getPageID()).execute();
        } catch(Exception e) {
            throw new Exception();
        }
        lastRetrievedSeries = callForPageResponse;
        notifySeriesRetrievedListener(series);
    }
    public Response<String> getLastRetrievedSeries() {
        return lastRetrievedSeries;
    }

}
