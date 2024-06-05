package utils.APIConsumer;

import model.APIs.WikipediaSearchAPI;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class SearchApiCosnumer {
    private WikipediaSearchAPI searchAPI;

    public SearchApiCosnumer() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://en.wikipedia.org/w/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        searchAPI = retrofit.create(WikipediaSearchAPI.class);
    }
    public WikipediaSearchAPI getSearchAPI() {
        return searchAPI;
    }
    public void setSearchAPI(WikipediaSearchAPI searchAPI) {
        this.searchAPI = searchAPI;
    }
    public Response<String> searchTerm(String termToSearch, int limit) throws Exception {
        Response<String> callForSearchResponse = null;
        try {
            callForSearchResponse = searchAPI.searchForTerm(termToSearch + " (Tv series) articletopic:\"television\"",limit).execute();
        } catch(Exception e) {
            throw new Exception();
        }
        return callForSearchResponse;
    }

}
