package utils.DataBaseManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBDelete {

    public static void deleteSavedSeries(String title) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:./dictionary.db");
            Statement statement = setDataBaseConnection(connection);
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
    private static Statement setDataBaseConnection(Connection connection) {
        try {
            // create a database connection
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
