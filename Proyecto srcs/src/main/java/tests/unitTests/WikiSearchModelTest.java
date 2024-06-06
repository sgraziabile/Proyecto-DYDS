package tests.unitTests;

import model.WikiSearchModel;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import retrofit2.Response;
import utils.APIConsumer.SearchApiConsumer;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class WikiSearchModelTest {
    private WikiSearchModel searchModelToTest;
   @Mock
    private SearchApiConsumer searchApiConsumer;
    @Before
    public void setUp() {
        searchModelToTest = new WikiSearchModel();
        searchApiConsumer = mock(SearchApiConsumer.class);
        searchModelToTest.setSearchApiConsumer(searchApiConsumer);
    }
    @Test
    public void testSearchValidTermWithLimit1() {
        Response<String> response = Response.success("Simulated response for term: Breaking Bad with limit: 1");
        try {
            when(searchApiConsumer.searchTerm("Breaking Bad", 1)).thenReturn(response);
            searchModelToTest.searchTerm("Breaking Bad", 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals("Simulated response for term: Breaking Bad with limit: 1", searchModelToTest.getLastSearchResult().body());
    }
    @Test
    public void testSearchEmptyTermWithLimit5() {
        Response<String> response = Response.success("Default search for empty term with limit: 5");
        try {
            when(searchApiConsumer.searchTerm("", 5)).thenReturn(response);
            searchModelToTest.searchTerm("", 5);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals("Default search for empty term with limit: 5", searchModelToTest.getLastSearchResult().body());
    }
}
