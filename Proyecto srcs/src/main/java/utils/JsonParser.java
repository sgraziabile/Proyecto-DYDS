package utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import model.entities.Series;
import retrofit2.Response;

import java.util.Iterator;

public class JsonParser {
    public JsonArray getJsonResults(Response<String> lastSearchResult) {
        Gson gson = new Gson();
        JsonObject jobj = gson.fromJson(lastSearchResult.body(), JsonObject.class);
        JsonObject query = jobj.get("query").getAsJsonObject();
        Iterator<JsonElement> resultIterator = query.get("search").getAsJsonArray().iterator();
        JsonArray jsonResults = query.get("search").getAsJsonArray();
        return jsonResults;
    }
    public Series buildSeriesFromJson(JsonElement jasonResult) {
        JsonObject jsonSearchResult = jasonResult.getAsJsonObject();
        String searchResultTitle = jsonSearchResult.get("title").getAsString();
        String searchResultPageId = jsonSearchResult.get("pageid").getAsString();
        String searchResultSnippet = jsonSearchResult.get("snippet").getAsString();
        Series series = new Series(searchResultTitle, searchResultPageId, searchResultSnippet);

        return series;
    }
}
