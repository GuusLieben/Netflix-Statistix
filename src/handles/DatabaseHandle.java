/*
 * Copyright © 2018. Guus Lieben.
 * All rights reserved.
 */

package com.netflix.handles;

import com.netflix.*;
import com.netflix.commons.*;
import com.netflix.entities.*;

import java.sql.*;
import java.util.*;

public class DatabaseHandle {

  public Connection connection = null;

  // Use the package.properties file to generate a connection string
  private static String connectionString() {
    //        String connectionUrl =
    // "jdbc:sqlserver://localhost\\SQLEXPRESS;databaseName=Bibliotheek;integratedSecurity=true;";

    return "jdbc:sqlserver://"
        + PropertiesHandle.get("jdbc.server")
        + "\\SQLEXPRESS:"
        + PropertiesHandle.get("jdbc.port")
        + ";databasename="
        + PropertiesHandle.get("jdbc.database")
        + ";user="
        + PropertiesHandle.get("jdbc.user")
        + ";password="
        + PropertiesHandle.get("jdbc.password")
        + ";";
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
  }

  // Connect to the database with the generated string
  public boolean connectDatabase() {
    try {
      // Use MS Sql server
      Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
      // Use the connectionUrl to connect (jdbc connection string)
      connection = DriverManager.getConnection(connectionString());
      System.out.println("Connected to database '" + PropertiesHandle.get("jdbc.server") + "'");
      return true;
    } catch (ClassNotFoundException | SQLException e) {
      Commons.exception(e);
      connection = null;
      System.out.println(
          "Failed to connect to database '" + PropertiesHandle.get("jdbc.server") + "'");
      return false;
    }
  }

  public void registerAccount(Account account) {
    throw new UnsupportedOperationException();
  }

  public void disconnectDatabase() {
    // Check if it isn't already disconnected
    if (connection != null)
      try {
        connection.close();
        System.out.println(
            "Disconnected from database '" + PropertiesHandle.get("jdbc.server") + "'");
      } catch (SQLException e) {
        Commons.exception(e);
        System.out.println(
            "Failed to disconnect from database '" + PropertiesHandle.get("jdbc.server") + "'");
      }
    // Set connection to null, if it's already disconnected it'd be the same anyway
    connection = null;
  }

  public boolean executeSqlNoResult(String sqlQuery) {
    // Return true if the query succeeded, even if it has no resultset
    try (Statement statement = this.connection.createStatement()) {
      return statement.execute(sqlQuery);
    } catch (Exception ex) {
      Commons.exception(ex);
    }
    return false;
  }

  public List<HashMap<String, Object>> executeSql(String sqlQuery) {
    if (Netflix.database.connectDatabase()) {
      ResultSet results = null;
      try (Statement statement = Netflix.database.connection.createStatement()) {
        // Make sure the results are passed
        results = statement.executeQuery(sqlQuery);
        System.out.println("Query passed : " + results.toString() + " (" + sqlQuery + ")");

        ResultSetMetaData md = results.getMetaData();
        int columns = md.getColumnCount();
        List<HashMap<String, Object>> list = new ArrayList<>();

        while (results.next()) {
          HashMap<String, Object> row = new HashMap<>(columns);
          for (int i = 1; i <= columns; ++i) row.put(md.getColumnName(i), results.getObject(i));
          list.add(row);
        }

        return list;

      } catch (SQLException ex) {
        Commons.exception(ex);
        System.out.println("Query did not pass");
      } finally {
        Netflix.database.disconnectDatabase();
      }
    }
    return new ArrayList<>();
  }
}
