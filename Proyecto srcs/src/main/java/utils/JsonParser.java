package utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import model.entities.Series;
import retrofit2.Response;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

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
    public JsonElement getSearchResultExctract(Response<String> lastRetrievedSeries) {
        Gson gson = new Gson();
        JsonObject jobj2 = gson.fromJson(lastRetrievedSeries.body(), JsonObject.class);
        JsonObject query2 = jobj2.get("query").getAsJsonObject();
        JsonObject pages = query2.get("pages").getAsJsonObject();
        Set<Map.Entry<String, JsonElement>> pagesSet = pages.entrySet();
        Map.Entry<String, JsonElement> first = pagesSet.iterator().next();
        JsonObject page = first.getValue().getAsJsonObject();
        JsonElement searchResultExtract2 = page.get("extract");
        return searchResultExtract2;
    }
}
