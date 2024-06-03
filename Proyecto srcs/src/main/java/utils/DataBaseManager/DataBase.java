package utils.DataBaseManager;

import model.entities.RatedSeries;

import java.sql.*;
import java.util.ArrayList;

public class DataBase {

  public static void loadDatabase() {
    //If the database doesnt exists we create it
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
  public static ArrayList<String> getSavedSeriesTitles() {
    ArrayList<String> titles = new ArrayList<>();
    Connection connection = null;
    try {
      // create a database connection
      connection = DriverManager.getConnection("jdbc:sqlite:./dictionary.db");
      Statement statement = connection.createStatement();
      statement.setQueryTimeout(30);  // set timeout to 30 sec.

      ResultSet rs = statement.executeQuery("select * from catalog");
      while(rs.next()) titles.add(rs.getString("title"));
    }
    catch(SQLException e) {
      // if the error message is "out of memory",
      // it probably means no database file is found
      System.err.println(e.getMessage());
    }
    finally {
      try {
        if(connection != null)
          connection.close();
      }
      catch(SQLException e) {
        // connection close failed.
        System.err.println(e);
      }
      return titles;
    }
  }

  public static void saveSeriesContent(String title, String extract) {
    Connection connection = null;
    try {
      // create a database connection
      connection = DriverManager.getConnection("jdbc:sqlite:./dictionary.db");

      Statement statement = connection.createStatement();
      statement.setQueryTimeout(30);  // set timeout to 30 sec.

      System.out.println("INSERT  " + title + "', '"+ extract);

      statement.executeUpdate("replace into catalog values(null, '"+ title + "', '"+ extract + "', 1)");
    }
    catch(SQLException e) {
      System.err.println("Error saving " + e.getMessage());
    }
    finally {
      try {
        if(connection != null)
          connection.close();
      }
      catch(SQLException e) {
        System.err.println( e);
      }
    }
  }

  public static String getSavedSeriesExctract(String title) {

    Connection connection = null;
    try {
      // create a database connection
      connection = DriverManager.getConnection("jdbc:sqlite:./dictionary.db");
      Statement statement = connection.createStatement();
      statement.setQueryTimeout(30);  // set timeout to 30 sec.

      ResultSet rs = statement.executeQuery("select * from catalog WHERE title = '" + title + "'" );
      rs.next();
      return rs.getString("extract");
    }
    catch(SQLException e) {
      // if the error message is "out of memory",
      // it probably means no database file is found
      System.err.println("Get title error " + e.getMessage());
    }
    finally {
      try {
        if(connection != null)
          connection.close();
      }
      catch(SQLException e) {
        // connection close failed.
        System.err.println(e);
      }
    }
    return null;
  }

  public static void deleteSavedSeries(String title) {
    Connection connection = null;
    try {
      // create a database connection
      connection = DriverManager.getConnection("jdbc:sqlite:./dictionary.db");
      Statement statement = connection.createStatement();
      statement.setQueryTimeout(30);  // set timeout to 30 sec.

      statement.executeUpdate("DELETE FROM catalog WHERE title = '" + title + "'" );
    }
    catch(SQLException e) {
      // if the error message is "out of memory",
      // it probably means no database file is found
      System.err.println("Get title error " + e.getMessage());
    }
    finally {
      try {
        if(connection != null)
          connection.close();
      }
      catch(SQLException e) {
        // connection close failed.
        System.err.println(e);
      }
    }
  }
  public static void updateSeriesScore(String title, String score) {
    Connection connection = null;
    try {
      // create a database connection
      connection = DriverManager.getConnection("jdbc:sqlite:./dictionary.db");
      Statement statement = connection.createStatement();
      statement.setQueryTimeout(30);  // set timeout to 30 sec.

      statement.executeUpdate("replace into ranking values('"+ title + "', "+ score + ", datetime('now','-3 hours'))");
    }
    catch(SQLException e) {
      System.err.println("Error saving " + e.getMessage());
    }
    finally {
      try {
        if(connection != null)
          connection.close();
      }
      catch(SQLException e) {
        // connection close failed.
        System.err.println( e);
      }
    }
  }
  public static String getSeriesScore(String title) {
    Connection connection = null;
    try {
      connection = DriverManager.getConnection("jdbc:sqlite:./dictionary.db");
      Statement statement = connection.createStatement();
      statement.setQueryTimeout(30);  // set timeout to 30 sec.

      ResultSet rs = statement.executeQuery("select * from ranking WHERE title = '" + title + "'" );
      rs.next();
      if(rs.getString("score") != null)
        return rs.getString("score");
      else
        return "0";
    }
    catch(SQLException e) {
      System.err.println("Get title error " + e.getMessage());
    }
    finally {
      try {
        if(connection != null)
          connection.close();
      }
      catch(SQLException e) {
        System.err.println(e);
      }
    }
    return null;
  }
  public static ArrayList<RatedSeries> getRankedSeries() {
    Connection connection = null;
    try {
      connection = DriverManager.getConnection("jdbc:sqlite:./dictionary.db");
      Statement statement = connection.createStatement();
      statement.setQueryTimeout(30);  // set timeout to 30 sec.

      ResultSet rs = statement.executeQuery("select * from ranking ORDER BY score ASC");
      StringBuilder sb = new StringBuilder();
      ArrayList<RatedSeries> ranking = new ArrayList<>();
      while(rs.next()) {
        String title = rs.getString("title");
        String score = rs.getString("score");
        String lastUpdate = rs.getString("lastUpdate");
        RatedSeries ratedSeries = new RatedSeries(title, Integer.parseInt(score), lastUpdate);
        ranking.add(ratedSeries);
      }
      return ranking;
    }
    catch(SQLException e) {
      System.err.println("Get title error " + e.getMessage());
    }
    finally {
      try {
        if(connection != null)
          connection.close();
      }
      catch(SQLException e) {
        System.err.println(e);
      }
    }
    return null;
  }
}