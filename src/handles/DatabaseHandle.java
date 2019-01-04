package com.netflix.handles;

import com.netflix.*;
import com.netflix.commons.*;
import com.netflix.entities.*;

import java.sql.*;
import java.util.*;

public class DatabaseHandle {

  public Connection connection = null;
  private String database;

  // Use the package.properties file to generate a connection string
  private String connectionString =
      "jdbc:sqlserver://"
          + PropertiesHandle.get("jdbc.server")
          + "\\SQLEXPRESS:"
          + PropertiesHandle.get("jdbc.port")
          + ";databasename="
          + PropertiesHandle.get("jdbc.database")
          + ";";

  {
    if (PropertiesHandle.get("jdbc.user").contains("\\"))
      connectionString += "integratedSecurity=true;";
  }

  public void collectData() {
    // First load items that do not require others entities
    Genre.getFromDatabase();
    Language.getFromDatabase();
    AgeRating.getFromDatabase();

    // Load films
    Film.getFromDatabase();

    // Load all serie entities in order
    Serie.getFromDatabase();
    Season.getFromDatabase();
    Episode.getFromDatabase();

    // Load all users in order
    Account.getFromDatabase();
    Profile.getFromDatabase();

    // Load all view data
    Episode.getViewData();
    Film.getViewData();
  }

  // Connect to the database with the generated string
  public SQLResults connectDatabase() {
    try {
      // Use MS Sql server
      Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
      // Use the connectionUrl to connect (jdbc connection string)
      connection =
          DriverManager.getConnection(
              connectionString,
              PropertiesHandle.get("jdbc.user"),
              PropertiesHandle.get("jdbc.password")); // Could also be stored in the connection String
      database = connection.getCatalog();
      Commons.logger.info(String.format("Connected to database '%s'", database));

      return SQLResults.PASS;
    } catch (ClassNotFoundException | SQLException e) {
      Commons.exception(e);
      connection = null;
      Commons.logger.warning("Failed to connect to database");

      return SQLResults.PASS;
    }
  }

  public void disconnectDatabase() {
    // Check if it isn't already disconnected
    if (connection != null)
      try {
        connection.close();
        Commons.logger.info(String.format("Disconnected from database '%s'", database));
      } catch (SQLException e) {
        Commons.exception(e);
        Commons.logger.warning(String.format("Failed to disconnect from database '%s'", database));
      }
    // Set connection to null, if it's already disconnected it'd be the same anyway
    connection = null;
  }

  public SQLResults executeSqlNoResult(String sqlQuery, Object[] arr) {
    if (Netflix.database.connectDatabase() == SQLResults.PASS) {
      try (PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
        for (int i = 0; i < arr.length; i++)
          if (arr[i].equals(""))
            statement.setNull(
                i + 1, Types.NULL); // If it's an empty String, set the cell to SQL type null
          else statement.setObject(i + 1, arr[i]);
        statement.execute();
        Commons.logger.info(String.format("Query passed %s)", sqlQuery.replace("?", "$obscured")));
        return SQLResults.PASS;
      } catch (SQLException ex) {
        Commons.exception(ex);
        Commons.logger.warning("Query did not pass");
      }
    }
    return SQLResults.FAIL;
  }

  public List<HashMap<String, Object>> executeSql(String sqlQuery) {
    if (Netflix.database.connectDatabase() == SQLResults.PASS) {
      ResultSet results = null;
      try (Statement statement = Netflix.database.connection.createStatement()) {
        // Make sure the results are passed
        results = statement.executeQuery(sqlQuery);
        Commons.logger.info(
            String.format(
                // Safe modification of data, don't show cell content in the logs
                "Query passed : %s (%s)", results.toString(), sqlQuery.replace("?", "$obscured")));

        ResultSetMetaData md = results.getMetaData();
        int columns = md.getColumnCount();
        List<HashMap<String, Object>> list = new ArrayList<>();

        while (results.next()) {
          HashMap<String, Object> row = new HashMap<>(columns);

          for (int i = 1; i <= columns; ++i) {
            row.put(md.getColumnName(i), results.getObject(i));
          }

          list.add(row);
        }

        return list;

      } catch (SQLException ex) {
        Commons.exception(ex);
        Commons.logger.warning("Query did not pass");
      } finally {
        Netflix.database.disconnectDatabase();
      }
    }
    return new ArrayList<>();
  }
}
