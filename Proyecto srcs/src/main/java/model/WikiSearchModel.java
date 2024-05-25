package model;

import model.APIs.WikipediaSearchAPI;
import model.listeners.WikiSearchModelListener;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class WikiSearchModel {
    private WikipediaSearchAPI searchAPI;
    private WikiSearchModelListener searchModelListener;

    public WikiSearchModel() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://en.wikipedia.org/w/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        searchAPI = retrofit.create(WikipediaSearchAPI.class);
    }
    public void searchSeries(String termToSearch) {

    }
    private void notifySearchHasFinishedListener() {
        searchModelListener.searchHasFinished();
    }
    public void setListener(WikiSearchModelListener searchModelListener) {
        this.searchModelListener = searchModelListener;
    }
}
