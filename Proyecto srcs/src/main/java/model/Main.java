package model;

import utils.DataBaseManager.DataBase;
import presenter.SaveSeriesPresenter;
import presenter.SearchPresenter;
import presenter.StoragePresenter;
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
        DataBaseModel dataBaseModel = new DataBaseModel();

        SearchPresenter searchPresenter = new SearchPresenter(wikiSearchModel,wikiPageModel);
        SaveSeriesPresenter saveSeriesPresenter = new SaveSeriesPresenter(saveSeriesModel);
        StoragePresenter storagePresenter = new StoragePresenter(retrieveSeriesModel, saveChangesModel, deleteSeriesModel, seriesContentModel);

        storagePresenter.setDataBaseModel(dataBaseModel);
        storagePresenter.setSaveChangesModel(saveChangesModel);
        storagePresenter.setDeleteSeriesModel(deleteSeriesModel);

        BaseView baseView = new BaseView();
        SearchView searchView = baseView.getSearchView();
        StorageView storageView = baseView.getStorageView();
        ScoreView scoreView = baseView.getScoreView();
        searchView.setSearchPresenter(searchPresenter);
        searchView.setSaveSeriesPresenter(saveSeriesPresenter);
        storageView.setStoragePresenter(storagePresenter);
        baseView.showView();

        searchPresenter.setSearchView(searchView);
        saveSeriesPresenter.setSearchView(searchView);
        saveSeriesPresenter.setSearchPresenter(searchPresenter);
        saveSeriesPresenter.setStoragePresenter(storagePresenter);

        storagePresenter.setStorageView(storageView);

        DataBase localDataBase = new DataBase();
        localDataBase.loadDatabase();
        localDataBase.saveSeriesContent("test", "sarasa");

        storageView.requestSavedSeries();
    }
}
