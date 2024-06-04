package utils.DataBaseManager;

import model.entities.RatedSeries;

import java.sql.*;
import java.util.ArrayList;

public class DBSelect {
    public String getSeriesScore(String title) throws SQLException{
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
            throw new SQLException();
        }
        finally {
            try {
                if(connection != null)
                    connection.close();
            }
            catch(SQLException e) {
               throw new SQLException();
            }
        }
    }
    public ArrayList<RatedSeries> getRankedSeries() throws SQLException{
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
            throw new SQLException();
        }
        finally {
            try {
                if(connection != null)
                    connection.close();
            }
            catch(SQLException e) {
                throw new SQLException();
            }
        }
    }
    public String getSavedSeriesExctract(String title) throws SQLException {
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
            throw new SQLException(e.getMessage());
        }
        finally {
            try {
                if(connection != null)
                    connection.close();
            }
            catch(SQLException e) {
                // connection close failed.
                throw new SQLException(e.getMessage());
            }
        }
    }
    public ArrayList<String> getSavedSeriesTitles() throws SQLException {
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
            throw new SQLException(e.getMessage());
        }
        finally {
            try {
                if(connection != null)
                    connection.close();
            }
            catch(SQLException e) {
                throw new SQLException(e.getMessage());
            }
            return titles;
        }
    }
}
