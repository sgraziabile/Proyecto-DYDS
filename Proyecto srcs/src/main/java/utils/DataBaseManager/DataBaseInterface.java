package utils.DataBaseManager;

import model.entities.RatedSeries;

import java.sql.SQLException;
import java.util.ArrayList;

public interface DataBaseInterface {
    void loadDatabase();
    ArrayList<String> getSavedSeriesTitles() throws SQLException;
    void saveSeriesContent(String title, String extract) throws SQLException;
    String getSavedSeriesExtract(String title) throws SQLException;
    void deleteSavedSeries(String title) throws SQLException;
    void updateSeriesScore(String title, String score) throws SQLException;
    String getSeriesScore(String title) throws SQLException;
    ArrayList<RatedSeries> getRankedSeries() throws SQLException;
}
