package model;

import presenter.*;
import utils.DataBaseManager.DataBase;
import views.BaseView;
import views.ScoreView;
import views.SearchView;
import views.StorageView;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        try {
                // Set System L&F
                UIManager.put("nimbusSelection", new Color(247,248,250));
        //UIManager.put("nimbusBase", new Color(51,98,140)); //This is redundant!

        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                UIManager.setLookAndFeel(info.getClassName());
                break;
            }
        }
    }
    catch (Exception e) {
        System.out.println("Something went wrong with UI!");
    }
        WikiSearchModel wikiSearchModel = new WikiSearchModel();
        WikiPageModel wikiPageModel = new WikiPageModel();
        RetrieveSeriesModel retrieveSeriesModel = new RetrieveSeriesModel();
        SaveChangesModel saveChangesModel = new SaveChangesModel();
        DeleteSeriesModel deleteSeriesModel = new DeleteSeriesModel();
        SaveSeriesModel saveSeriesModel = new SaveSeriesModel();
        SeriesContentModel seriesContentModel = new SeriesContentModel();
        ScoreModel scoreModel = new ScoreModel();
        RankingModel rankingModel = new RankingModel();

        SearchPresenter searchPresenter = new SearchPresenter(wikiSearchModel,wikiPageModel);
        SaveSeriesPresenter saveSeriesPresenter = new SaveSeriesPresenter(saveSeriesModel);
        StoragePresenter storagePresenter = new StoragePresenter(retrieveSeriesModel, saveChangesModel, deleteSeriesModel, seriesContentModel);
        ScorePresenter scorePresenter = new ScorePresenter(scoreModel);
        RankingPresenter rankingPresenter = new RankingPresenter(rankingModel);

        storagePresenter.setSaveChangesModel(saveChangesModel);
        storagePresenter.setDeleteSeriesModel(deleteSeriesModel);

        BaseView baseView = new BaseView();
        SearchView searchView = baseView.getSearchView();
        StorageView storageView = baseView.getStorageView();
        ScoreView scoreView = baseView.getScoreView();
        searchView.setSearchPresenter(searchPresenter);
        searchView.setSaveSeriesPresenter(saveSeriesPresenter);
        searchView.setScorePresenter(scorePresenter);
        storageView.setStoragePresenter(storagePresenter);
        scoreView.setRankingPresenter(rankingPresenter);
        baseView.showView();

        searchPresenter.setSearchView(searchView);
        saveSeriesPresenter.setSearchView(searchView);
        saveSeriesPresenter.setSearchPresenter(searchPresenter);
        saveSeriesPresenter.setStoragePresenter(storagePresenter);

        scorePresenter.setSearchView(searchView);
        scorePresenter.setRankingPresenter(rankingPresenter);
        scorePresenter.setSearchPresenter(searchPresenter);
        rankingPresenter.setScoreView(scoreView);


        storagePresenter.setStorageView(storageView);

        DataBase localDataBase = new DataBase();
        localDataBase.loadDatabase();
        storageView.requestSavedSeries();
    }
}
