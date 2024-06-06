package tests.integrationTests;

import model.DeleteSeriesModel;
import model.RetrieveSeriesModel;
import model.SaveChangesModel;
import model.SeriesContentModel;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
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
        presenterToTest = new StoragePresenter(retrieveSeriesModel, new SaveChangesModel(), deleteSeriesModel, new SeriesContentModel());
        presenterToTest.setStorageView(storageView);
        storageView.setStoragePresenter(presenterToTest);
        dataBaseMock = mock(DataBase.class);
        deleteSeriesModel.setLocalDataBase(dataBaseMock);
        retrieveSeriesModel.setLocalDataBase(dataBaseMock);
    }
    @Test
    public void testDeleteSeries() {
        ArrayList<String> seriesList = new ArrayList<>();
        seriesList.add("Breaking Bad");
        Object[] formattedSeries = seriesList.stream().sorted().toArray();
        storageView.showSavedSeries(formattedSeries);
        String titleToDelete = storageView.getSelectedSavedSeriesTitle();
        try {
            when(dataBaseMock.getSavedSeriesTitles()).thenReturn(seriesList);
            doAnswer(invocation -> {
                seriesList.remove(titleToDelete);
                return null;
            }).when(dataBaseMock).deleteSavedSeries(titleToDelete);
            deleteSeriesModel.deleteSeries(titleToDelete);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(0, seriesList.size());
    }

}
