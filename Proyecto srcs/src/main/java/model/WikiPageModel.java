package model;

import model.entities.Series;
import model.listeners.WikiPageModelListener;
import retrofit2.Response;
import utils.APIConsumer.PageApiConsumer;
import java.util.ArrayList;

public class WikiPageModel implements Model {

    private PageApiConsumer pageApiConsumer;
    private ArrayList<WikiPageModelListener> pageModelListeners = new ArrayList<>();
    private Response<String> lastRetrievedSeries;


    public WikiPageModel() {

    }
    public void setPageApiConsumer(PageApiConsumer pageApiConsumer) {this.pageApiConsumer = pageApiConsumer;}
    private void notifySeriesRetrievedListener(Series series) {
        for(WikiPageModelListener pageModelListener : pageModelListeners)
            pageModelListener.seriesRetrieved(series);
    }
    public void setListener(WikiPageModelListener wikiPageModelListener) {
        this.pageModelListeners.add(wikiPageModelListener);
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
