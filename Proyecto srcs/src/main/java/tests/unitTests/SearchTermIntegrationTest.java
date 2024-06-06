package tests.unitTests;

import model.WikiPageModel;
import model.WikiSearchModel;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import presenter.SearchPresenter;
import retrofit2.Response;
import utils.APIConsumer.SearchApiConsumer;
import views.SearchView;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SearchTermIntegrationTest {
    private SearchPresenter presenterToTest;
    private SearchView viewToTest;
    @Mock
    private SearchApiConsumer searchApiConsumer;

    @Before
    public void setUp() {
        WikiSearchModel searchModel = new WikiSearchModel();
        WikiPageModel pageModel = new WikiPageModel();
        viewToTest = new SearchView();
        presenterToTest = new SearchPresenter(searchModel, pageModel);
        presenterToTest.setSearchView(viewToTest);
        viewToTest.setSearchPresenter(presenterToTest);
        searchApiConsumer = mock(SearchApiConsumer.class);
        searchModel.setSearchApiConsumer(searchApiConsumer);
    }
    @Test
    public void testSearchTermWithLimit1() throws InterruptedException {
        String jsonResponse = "{\"batchcomplete\":\"\",\"continue\":{\"sroffset\":1,\"continue\":\"-||\"},\"query\":{\"searchinfo\":{\"totalhits\":8212},\"search\":[{\"ns\":0,\"title\":\"Breaking Bad\",\"pageid\":14426270,\"size\":198656,\"wordcount\":17633,\"snippet\":\"Drama <span class=\\\"searchmatch\\\">Series</span> twice. In 2013, <span class=\\\"searchmatch\\\">Breaking</span> <span class=\\\"searchmatch\\\">Bad</span> entered the Guinness World Records as the most critically acclaimed <span class=\\\"searchmatch\\\">TV</span> show of all time. In 2023, <span class=\\\"searchmatch\\\">Breaking</span> <span class=\\\"searchmatch\\\">Bad</span> was\",\"timestamp\":\"2024-06-03T10:35:48Z\"}]}}";
        Response<String> response = Response.success(jsonResponse);
        viewToTest.setSearchTextField("breaking bad");
        String termToSearch = viewToTest.getSearchFieldText();
        try {
            when(searchApiConsumer.searchTerm(termToSearch, 1)).thenReturn(response);
            presenterToTest.requestSearch(termToSearch, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals("Breaking Bad", presenterToTest.getLastSearchResultsList().get(0).getTitle());
    }
    private String mockJsonResponse() {
        return "";
    }
}
