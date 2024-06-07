package tests.unitTests;

import model.RankingModel;
import model.entities.RatedSeries;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import utils.DataBaseManager.DataBase;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RankingModelTests {
    private RankingModel rankingModelToTest;

    @Mock
    private DataBase dataBaseMock;

    @Before
    public void setUp() {
        rankingModelToTest = new RankingModel();
        dataBaseMock = mock(DataBase.class);
        rankingModelToTest.setLocalDataBase(dataBaseMock);
    }
    @Test
    public void testRankingSortingWith3Elements() {
        ArrayList<RatedSeries> ranking = new ArrayList<>();
        RatedSeries series1 = new RatedSeries("Breaking Bad", 10, "2024-06-03T10:35:48Z");
        RatedSeries series2 = new RatedSeries("The Wire", 7, "2024-06-03T10:35:48Z");
        RatedSeries series3 = new RatedSeries("The Sopranos", 9, "2024-06-03T10:35:48Z");
        ranking.add(series1); ranking.add(series2); ranking.add(series3);
        ArrayList<RatedSeries> expectedRanking = new ArrayList<>();
        expectedRanking.add(series2); expectedRanking.add(series3); expectedRanking.add(series1);
        try {
            when(dataBaseMock.getRankedSeries()).thenReturn(ranking);
            rankingModelToTest.updateRanking();
        }catch (Exception e) {
            e.printStackTrace();
        }
        ArrayList<RatedSeries> sortedRanking = rankingModelToTest.getLastUpdatedRanking();
        assertEquals(expectedRanking, sortedRanking);
    }
    @Test
    public void testRankingSortingWithEmptyList() {
        ArrayList<RatedSeries> ranking = new ArrayList<>();
        ArrayList<RatedSeries> expectedRanking = new ArrayList<>();
        try {
            when(dataBaseMock.getRankedSeries()).thenReturn(ranking);
            rankingModelToTest.updateRanking();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ArrayList<RatedSeries> sortedRanking = rankingModelToTest.getLastUpdatedRanking();
        assertEquals(expectedRanking, sortedRanking);
    }
}
