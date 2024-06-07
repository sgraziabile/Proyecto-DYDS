package tests.integrationTests;

import model.DeleteSeriesModel;
import model.RetrieveSeriesModel;
import model.SaveChangesModel;
import model.SeriesContentModel;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import presenter.StoragePresenter;
import utils.DataBaseManager.DataBaseInterface;
import views.StorageView;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class SaveChangesIntegrationTest {
    private StoragePresenter presenterToTest;
    private StorageView storageView;
    private SaveChangesModel saveChangesModel;
    @Mock
    DataBaseInterface dataBaseMock;

    @Before
    public void setUp() {
        storageView = new StorageView();
        saveChangesModel = new SaveChangesModel();
        presenterToTest = new StoragePresenter(new RetrieveSeriesModel(), saveChangesModel, new DeleteSeriesModel(), new SeriesContentModel());
        presenterToTest.setStorageView(storageView);
        storageView.setStoragePresenter(presenterToTest);
        dataBaseMock = mock(DataBaseInterface.class);
        saveChangesModel.setLocalDataBase(dataBaseMock);
    }
    /*
    @Test
    public void testSaveChanges() {
        ArrayList<String> seriesList = new ArrayList<>();
        seriesList.add("Breaking Bad");
        Object[] formattedSeries = seriesList.stream().sorted().toArray();
        String[] actualContent = new String[1];
        actualContent[0] = "This is the original extract";
        storageView.showSavedSeries(formattedSeries);
        storageView.setSelectedSeriesContent("This is the original extract but with some changes");
        String seriesTitle = storageView.getSelectedSavedSeriesTitle();
        String newExtract = storageView.getSelectedSeriesContent();
        try {
            doAnswer(invocation -> {
                actualContent[0] = "This is the original extract but with some changes";
                return null;
            }).when((dataBaseMock).saveSeriesContent(seriesTitle, newExtract));
            saveChangesModel.saveChanges(seriesTitle, newExtract);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(actualContent,"This is the original extract but with some changes");
    }
    */


}
