package tests.integrationTests;

import model.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import presenter.SearchPresenter;
import presenter.StoragePresenter;
import utils.DataBaseManager.DataBase;
import views.StorageView;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class DeleteSeriesIntegrationTest {
    private StoragePresenter presenterToTest;
    private StorageView storageView;
    private DeleteSeriesModel deleteSeriesModel;

    @Mock
    private DataBase dataBaseMock;

    @Before
    public void setUp() {
        storageView = new StorageView();
        deleteSeriesModel = new DeleteSeriesModel();
        RetrieveSeriesModel retrieveSeriesModel = new RetrieveSeriesModel();
        SeriesContentModel seriesContentModel = new SeriesContentModel();
        seriesContentModel.setLastSeriesContent("Breaking Bad");
        presenterToTest = new StoragePresenter(retrieveSeriesModel, new SaveChangesModel(), deleteSeriesModel,seriesContentModel);
        presenterToTest.setStorageView(storageView);
        presenterToTest.setSearchPresenter(new SearchPresenter(new WikiSearchModel(), new WikiPageModel()));
        storageView.setStoragePresenter(presenterToTest);
        dataBaseMock = mock(DataBase.class);
        deleteSeriesModel.setLocalDataBase(dataBaseMock);
        seriesContentModel.setLocalDataBase(dataBaseMock);
        retrieveSeriesModel.setLocalDataBase(dataBaseMock);
    }
    @Test
    public void testDeleteSeries() {
        ArrayList<String> seriesList = new ArrayList<>();
        seriesList.add("Breaking Bad");
        Object[] formattedSeries = seriesList.stream().sorted().toArray();
        storageView.showSavedSeries(formattedSeries);
        storageView.setSelectedOption(0);
        String titleToDelete = storageView.getSelectedSavedSeriesTitle();
        try {
            when(dataBaseMock.getSavedSeriesTitles()).thenReturn(seriesList);
            doAnswer(invocation -> {
                seriesList.remove(titleToDelete);
                System.out.println(seriesList.size());
                return null;
            }).when(dataBaseMock).deleteSavedSeries(titleToDelete);
            //deleteSeriesModel.deleteSeries(titleToDelete);
            presenterToTest.onDeleteClick();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(seriesList.size());
        assertEquals(0, seriesList.size());
    }
}
