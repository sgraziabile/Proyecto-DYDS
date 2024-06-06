package utils.DataBaseManager;

import model.entities.RatedSeries;

import java.sql.*;
import java.util.ArrayList;

public class DBInsert {
    public void saveSeriesContent(String title, String extract) throws SQLException {
        Connection connection = null;
        try {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:./dictionary.db");

            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.


            statement.executeUpdate("replace into catalog values(null, '"+ title + "', '"+ extract + "', 1)");
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
    public void updateSeriesScore(String title, String score) throws SQLException {
        Connection connection = null;
        try {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:./dictionary.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            statement.executeUpdate("replace into ranking values('"+ title + "', "+ score + ", datetime('now','-3 hours'))");
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


}
