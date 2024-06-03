package model.APIs;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WikipediaSearchAPI {

  //@GET("api.php?action=query&list=search&utf8=1&format=json&srlimit=5")
    @GET("api.php?action=query&list=search&utf8=1&format=json")
    Call<String> searchForTerm(@Query("srsearch") String term, @Query("srlimit") int limit);

}
