package model;

import dyds.tvseriesinfo.fulllogic.DataBase;
import model.APIs.WikipediaPageAPI;
import model.listeners.WikiPageModelListener;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class WikiPageModel {

    private WikipediaPageAPI pageAPI;
    private WikiPageModelListener pageModelListener;


    public WikiPageModel() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://en.wikipedia.org/w/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        pageAPI = retrofit.create(WikipediaPageAPI.class);
    }
    private void notifySeriesRetrievedListener() {pageModelListener.seriesRetrieved();};
    public void setListener(WikiPageModelListener wikiPageModelListener) {
        this.pageModelListener = wikiPageModelListener;
    }
}
