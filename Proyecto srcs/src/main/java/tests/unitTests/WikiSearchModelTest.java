package tests.unitTests;

import model.WikiSearchModel;
import model.listeners.WikiSearchModelListener;
import org.junit.Before;
import org.junit.Test;
import utils.APIConsumer.SearchApiConsumer;
import utils.stubs.SearchApiConsumerSim;

import static org.junit.Assert.assertEquals;

public class WikiSearchModelTest {
    WikiSearchModel searchModelToTest;
    @Before
    public void setUp() {
        searchModelToTest = new WikiSearchModel();
        SearchApiConsumerSim searchApiConsumerSim = new SearchApiConsumerSim();
        searchModelToTest.setSearchApiConsumer(searchApiConsumerSim);
    }
    @Test
    public void testSearchValidTermWithLimit1() {
        try {
            searchModelToTest.searchTerm("Breaking Bad", 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals("Simulated response for term: Breaking Bad with limit: 1", searchModelToTest.getLastSearchResult().body());
    }
    @Test
    public void testSearchEmptyTermWithLimit2() {
        try {
            searchModelToTest.searchTerm("", 2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals("Default search for empty term with limit: 2", searchModelToTest.getLastSearchResult().body());
    }
}
