package utils.DataBaseManager;

import java.sql.*;
import java.util.ArrayList;

public class DBInfo {
    public static ArrayList<String> getSavedSeriesTitles() {
        ArrayList<String> titles = new ArrayList<>();
        Connection connection = null;
        try {
            Statement statement = setDataBaseConnection(connection);
            ResultSet rs = statement.executeQuery("select * from catalog");
            while(rs.next()) titles.add(rs.getString("title"));
        }
        catch(SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        }
        finally {
            try
            {
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
    public static String getSavedSeriesExctract(String title) {
        Connection connection = null;
        try {
            Statement statement = setDataBaseConnection(connection);
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
