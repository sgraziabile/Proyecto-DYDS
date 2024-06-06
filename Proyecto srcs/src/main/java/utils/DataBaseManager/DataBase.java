package utils.DataBaseManager;

import model.entities.RatedSeries;

import java.sql.*;
import java.util.ArrayList;

public class DataBase implements DataBaseInterface{
  private DBDelete dbDelete;
  private DBInsert dbInsert;
  private DBSelect dbSelect;

  public DataBase () {
    dbDelete = new DBDelete();
    dbInsert = new DBInsert();
    dbSelect = new DBSelect();
  }
  public void loadDatabase() {
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
  public ArrayList<String> getSavedSeriesTitles() throws SQLException{
    try {
    return dbSelect.getSavedSeriesTitles();
    } catch (SQLException e) {
      throw new SQLException(e.getMessage());
    }
  }
  public void saveSeriesContent(String title, String extract) throws SQLException {
    try {
      dbInsert.saveSeriesContent(title, extract);
    } catch (SQLException e) {
      throw new SQLException();
    }
  }
  public  String getSavedSeriesExtract(String title) throws SQLException {
      try {
      return dbSelect.getSavedSeriesExctract(title);
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
  }
  public void deleteSavedSeries(String title) throws SQLException{
    try {
      dbDelete.deleteSavedSeries(title);
    }catch (SQLException e) {
      throw new SQLException();
    }
  }
  public void updateSeriesScore(String title, String score) throws  SQLException {
    try {
      dbInsert.updateSeriesScore(title, score);
    } catch (SQLException e) {
      throw new SQLException();
    }
  }
  public String getSeriesScore(String title) throws SQLException {
    try {
      return dbSelect.getSeriesScore(title);
    } catch (SQLException e) {
      throw new SQLException();
    }
  }
  public ArrayList<RatedSeries> getRankedSeries() throws SQLException {
    try {
      return dbSelect.getRankedSeries();
    } catch (SQLException e) {
        throw new SQLException();
    }
  }
}