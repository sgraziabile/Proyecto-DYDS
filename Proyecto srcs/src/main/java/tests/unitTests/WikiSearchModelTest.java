package tests.unitTests;

import model.WikiSearchModel;
import org.junit.Before;
import utils.APIConsumer.SearchApiConsumer;

public class WikiSearchModelTest {
    WikiSearchModel searchModelToTest;

    @Before
    public void setUp() {
        searchModelToTest = new WikiSearchModel();
    }

}
