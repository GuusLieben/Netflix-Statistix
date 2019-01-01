package com.netflix.entities;

import com.netflix.*;
import com.netflix.commons.*;
import com.netflix.entities.abstracts.Entity;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class Genre extends Entity {

    public static Set<Genre> genres = new HashSet<>();
    private String genreName;

  public Genre(String genre, int databaseId) {
    this.genreName = genre;
    this.databaseId = databaseId;
    genres.add(this);
  }

    public static Genre getByName(String name) {
      for (Genre genre : genres) if (genre.getGenre().equals(name)) return genre;
      return null;
    }

    public String getGenre() {
    return genreName;
  }

  @Override
  public String toString() {
    return genreName;
  }

    public static void getFromDatabase() {
        if (Netflix.database.connectDatabase()) {
      String sqlQuery = "SELECT GenreId, Genre FROM Genre";
            ResultSet results = null;
            try (Statement statement = Netflix.database.connection.createStatement()) {
                // Make sure the results are passed
                results = statement.executeQuery(sqlQuery);
                System.out.println("Query passed : " + results.toString());
                while (results.next())
                    new Genre(results.getString("Genre"), results.getInt("GenreId"));

            } catch (SQLException ex) {
                Commons.exception(ex);
                System.out.println("Query did not pass");
            }
            Netflix.database.disconnectDatabase();
        }
    }

}
