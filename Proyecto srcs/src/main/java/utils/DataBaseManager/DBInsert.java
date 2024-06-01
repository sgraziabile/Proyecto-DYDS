package utils.DataBaseManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBInsert {
    Connection connection;
    public static void saveSeriesContent(String title, String extract) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:./dictionary.db");
            Statement statement = setDataBaseConnection(connection);
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
                // connection close failed.
                System.err.println( e);
            }
        }
    }
    public static void saveSeriesScore(String title, int score, java.sql.Date lastUpdateDate) {
        Connection connection = null;
        try {
            Statement statement = setDataBaseConnection(connection);
            System.out.println("INSERT  " + title + "', '"+ score + "', '"+ lastUpdateDate);
            statement.executeUpdate("replace into ranking values('"+ title + "', '"+ score + "', '"+ lastUpdateDate + "')");
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
                System.err.println(e);
            }
        }
    }
    private static Statement setDataBaseConnection(Connection connection) {
        try {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:./dictionary.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.
            return statement;
        }
        catch(SQLException e) {
            System.err.println("Error saving " + e.getMessage());
        }
        return null;
    }
}
