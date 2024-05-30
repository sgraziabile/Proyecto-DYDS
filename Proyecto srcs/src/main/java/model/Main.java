package model;

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
    //loadDataBase
        BaseView baseView = new BaseView();
        baseView.showView();
        SearchView searchView = new SearchView();
        StorageView storageView = new StorageView();
        baseView.setSearchView(searchView);
        baseView.setStorageView(storageView);
    }
}
