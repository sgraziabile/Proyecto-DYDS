package model;

import dyds.tvseriesinfo.fulllogic.DataBase;
import presenter.SaveSeriesPresenter;
import presenter.SearchPresenter;
import presenter.StoragePresenter;
import views.BaseView;
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
        DataBaseModel dataBaseModel = new DataBaseModel();

        SearchPresenter searchPresenter = new SearchPresenter(wikiSearchModel,wikiPageModel);
        SaveSeriesPresenter saveSeriesPresenter = new SaveSeriesPresenter(dataBaseModel);
        StoragePresenter storagePresenter = new StoragePresenter(dataBaseModel);

        BaseView baseView = new BaseView();
        SearchView searchView = baseView.getSearchView();
        StorageView storageView = baseView.getStorageView();
        searchView.setSearchPresenter(searchPresenter);
        searchView.setSaveSeriesPresenter(saveSeriesPresenter);
        storageView.setStoragePresenter(storagePresenter);
        baseView.showView();
        storageView.showSavedSeries();

        searchPresenter.setSearchView(searchView);
        saveSeriesPresenter.setSearchView(searchView);
        saveSeriesPresenter.setSearchPresenter(searchPresenter);

        storagePresenter.setStorageView(storageView);

        DataBase.loadDatabase();
        DataBase.saveInfo("test", "sarasa");
    }
}
