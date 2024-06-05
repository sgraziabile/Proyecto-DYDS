package utils.APIConsumer;

import model.APIs.WikipediaPageAPI;
import model.entities.Series;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class PageApiConsumer {
    private WikipediaPageAPI pageAPI;

    public PageApiConsumer() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://en.wikipedia.org/w/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        pageAPI = retrofit.create(WikipediaPageAPI.class);
    }
    public WikipediaPageAPI getPageAPI() {
        return pageAPI;
    }
    public void setPageAPI(WikipediaPageAPI pageAPI) {
        this.pageAPI = pageAPI;
    }
    public Response<String> retrieveSeries(Series series ) throws Exception {
        Response<String> callForPageResponse = null;
        try {
            callForPageResponse = pageAPI.getExtractByPageID(series.getPageID()).execute();
        } catch(Exception e) {
            throw new Exception();
        }
        return callForPageResponse;
    }
}
