package model;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import model.APIs.WikipediaSearchAPI;
import model.listeners.WikiSearchModelListener;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import utils.DataBaseManager.DataBase;

import javax.xml.crypto.Data;
import java.util.Iterator;

public class WikiSearchModel implements Model{
    private WikipediaSearchAPI searchAPI;
    private WikiSearchModelListener searchModelListener;
    private Response<String> lastSearchResult;
    private DataBase localDataBase;

    public WikiSearchModel() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://en.wikipedia.org/w/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        searchAPI = retrofit.create(WikipediaSearchAPI.class);
    }
    public void searchTerm(String termToSearch, int limit) throws Exception {
        Response<String> callForSearchResponse = null;
        try {
            callForSearchResponse = searchAPI.searchForTerm(termToSearch + " (Tv series) articletopic:\"television\"",limit).execute();
        } catch(Exception e) {
            throw new Exception();
        }
        lastSearchResult = callForSearchResponse;
        notifySearchHasFinishedListener();
    }
    public String getSeriesScore(String title) {
        return localDataBase.getSeriesScore(title);
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
