package utils.DataBaseManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBDelete {

    public static void deleteSavedSeries(String title) throws SQLException{
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:./dictionary.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            statement.executeUpdate("DELETE FROM catalog WHERE title = '" + title + "'" );
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
