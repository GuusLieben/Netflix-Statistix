package com.netflix.entities;

import com.netflix.*;
import com.netflix.commons.*;
import com.netflix.entities.abstracts.MediaObject;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("deprecation")
public class Film extends MediaObject { // MediaObject extends Entity

  public static Set<Film> films = new HashSet<>();
  public static Set<String> filmTitles = new HashSet<>();
  private Time duration;
  private String director;

  public Film(
      AgeRating rating,
      Genre genre,
      Language lang,
      String title,
      Time duration,
      String director,
      int databaseId) {
    super.databaseId = databaseId;
    super.rating = rating;
    super.genre = genre;
    super.lang = lang;
    super.title = title;
    mediaType = 1;
    this.duration = duration;
    this.director = director;
    films.add(this);
    filmTitles.add(title);
  }

  public static Film getFilmByName(String title) {
    // Use Lambda to go through all films and check if it equals the given title, else return null
    return films.stream().filter(film -> film.getTitle().equals(title)).findFirst().orElse(null);
  }

  public String getDuration() {
    return duration.toString();
  }

  public String getDirector() {
    return director;
  }

    public static Film getByDbId(int id) {
        return films.stream().filter(ent -> ent.databaseId == id).findFirst().orElse(null);
    }

  public static void getFromDatabase() {
    if (Netflix.database.connectDatabase()) {
      String sqlQuery =
          "SELECT FilmId, Rating, LijktOp, LanguageCode, Title, Duration, Director FROM Film";
      ResultSet results = null;
      try (Statement statement = Netflix.database.connection.createStatement()) {
        // Make sure the results are passed
        results = statement.executeQuery(sqlQuery);
        System.out.println("Query passed : " + results.toString());
        while (results.next())
          new Film(
              AgeRating.getByAge(18),
              Genre.getByName(""),
              Language.getByCode(results.getString("LanguageCode")),
              results.getString("Title"),
              results.getTime("Duration"),
              results.getString("Director"),
              results.getInt("FilmId"));
      } catch (SQLException ex) {
        Commons.exception(ex);
        System.out.println("Query did not pass");
      }
      Netflix.database.disconnectDatabase();
    }
  }
}
