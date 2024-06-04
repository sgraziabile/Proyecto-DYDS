package utils.DataBaseManager;

import model.entities.RatedSeries;

import java.sql.*;
import java.util.ArrayList;

public class DataBase {
  private DBDelete dbDelete;
  private DBInsert dbInsert;
  private DBSelect dbSelect;

  public DataBase () {
    dbDelete = new DBDelete();
    dbInsert = new DBInsert();
    dbSelect = new DBSelect();
  }
  public static void loadDatabase() {
    String url = "jdbc:sqlite:./dictionary.db";

    try (Connection connection = DriverManager.getConnection(url)) {
      if (connection != null) {

        DatabaseMetaData meta = connection.getMetaData();

        Statement statement = connection.createStatement();
        statement.setQueryTimeout(30);  // set timeout to 30 sec.
        statement.executeUpdate("create table if not exists catalog (id INTEGER, title string PRIMARY KEY, extract string, source integer)");
        statement.executeUpdate("create table if not exists ranking(title string PRIMARY KEY, score integer, lastUpdate date)");
      }

    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }
  public ArrayList<String> getSavedSeriesTitles() {
    return dbSelect.getSavedSeriesTitles();
  }
  public void saveSeriesContent(String title, String extract) {
    dbInsert.saveSeriesContent(title, extract);
  }
  public  String getSavedSeriesExctract(String title) {
    return dbSelect.getSavedSeriesExctract(title);
  }
  public void deleteSavedSeries(String title) {
    dbDelete.deleteSavedSeries(title);
  }
  public void updateSeriesScore(String title, String score) {
    dbInsert.updateSeriesScore(title, score);
  }
  public String getSeriesScore(String title) {
    return dbSelect.getSeriesScore(title);
  }
  public ArrayList<RatedSeries> getRankedSeries() {
    return dbSelect.getRankedSeries();
  }
}