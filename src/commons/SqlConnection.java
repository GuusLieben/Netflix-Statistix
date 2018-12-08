package com.netflix.commons;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SqlConnection {

  private Connection connection = null;

  public boolean connectDatabase(String connectionUrl) {
    try {
      Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
      connection = DriverManager.getConnection(connectionUrl);
      return true;
    } catch (Exception e) {
      Commons.exception(e);
      connection = null;
      return false;
    }
  }

  public void disconnectDatabase() {
    if (connection != null)
      try {
        connection.close();
      } catch (Exception e) {
        Commons.exception(e);
      }
    connection = null;
  }

  public ResultSet executeSql(String sqlQuery) {
    ResultSet rs = null;
    try {
      Statement statement = this.connection.createStatement();
      rs = statement.executeQuery(sqlQuery);
    } catch (Exception e) {
      Commons.exception(e);
    }
    return rs;
  }

  public boolean executeSqlNoResult(String sqlQuery) {
    try {
      Statement statement = this.connection.createStatement();
      return statement.execute(sqlQuery);
    } catch (Exception e) {
      Commons.exception(e);
    }
    return false;
  }
}
