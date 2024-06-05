package model;

import model.APIs.WikipediaPageAPI;
import model.entities.Series;
import model.interfaces.PageModelInterface;
import model.listeners.WikiPageModelListener;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import utils.APIConsumer.PageApiConsumer;

public class WikiPageModel implements PageModelInterface {

    private PageApiConsumer pageApiConsumer;
    private WikiPageModelListener pageModelListener;
    private Response<String> lastRetrievedSeries;


    public WikiPageModel() {

    }
    public void setPageApiConsumer(PageApiConsumer pageApiConsumer) {this.pageApiConsumer = pageApiConsumer;}
    private void notifySeriesRetrievedListener(Series series) {pageModelListener.seriesRetrieved(series);};
    public void setListener(WikiPageModelListener wikiPageModelListener) {
        this.pageModelListener = wikiPageModelListener;
    }
    public void retrieveSeries(Series series) throws Exception {
        Response<String> callForPageResponse = null;
        try {
            callForPageResponse = pageApiConsumer.retrieveSeries(series);
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
