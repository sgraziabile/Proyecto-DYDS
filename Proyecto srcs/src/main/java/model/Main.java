package model;

import presenter.*;
import utils.APIConsumer.PageApiConsumer;
import utils.APIConsumer.SearchApiConsumer;
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
        SearchRankingModel searchRankingModel = new SearchRankingModel();

        SearchPresenter searchPresenter = new SearchPresenter(wikiSearchModel,wikiPageModel);
        SaveSeriesPresenter saveSeriesPresenter = new SaveSeriesPresenter(saveSeriesModel);
        StoragePresenter storagePresenter = new StoragePresenter(retrieveSeriesModel, saveChangesModel, deleteSeriesModel, seriesContentModel);
        ScorePresenter scorePresenter = new ScorePresenter(scoreModel);
        RankingPresenter rankingPresenter = new RankingPresenter(rankingModel,searchRankingModel);

        storagePresenter.setSaveChangesModel(saveChangesModel);
        storagePresenter.setDeleteSeriesModel(deleteSeriesModel);

        BaseView baseView = new BaseView();
        SearchView searchView = baseView.getSearchView();
        StorageView storageView = baseView.getStorageView();
        ScoreView scoreView = baseView.getScoreView();
        scoreView.setBaseView(baseView);
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
        rankingPresenter.setSearchPresenter(searchPresenter);


        storagePresenter.setStorageView(storageView);

        DataBase localDataBase = new DataBase();
        localDataBase.loadDatabase();
        wikiSearchModel.setLocalDataBase(localDataBase);
        deleteSeriesModel.setLocalDataBase(localDataBase);
        saveChangesModel.setLocalDataBase(localDataBase);
        saveSeriesModel.setLocalDataBase(localDataBase);
        seriesContentModel.setLocalDataBase(localDataBase);
        scoreModel.setLocalDataBase(localDataBase);
        rankingModel.setLocalDataBase(localDataBase);
        retrieveSeriesModel.setLocalDataBase(localDataBase);

        SearchApiConsumer searchApiConsumer = new SearchApiConsumer();
        PageApiConsumer pageApiConsumer = new PageApiConsumer();
        wikiSearchModel.setSearchApiConsumer(searchApiConsumer);
        searchRankingModel.setSearchApiConsumer(searchApiConsumer);
        wikiPageModel.setPageApiConsumer(pageApiConsumer);

        storageView.requestSavedSeries();
        scoreView.showSeriesRanking();

    }
}
