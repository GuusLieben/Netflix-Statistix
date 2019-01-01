package com.netflix.entities;

import com.netflix.*;
import com.netflix.entities.abstracts.*;

import java.sql.*;
import java.util.*;

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
    for (HashMap<String, Object> map :
        Netflix.database.executeSql(
            "SELECT FilmId, Rating, LijktOp, LanguageCode, Title, Duration, Director FROM Film")) {
      new Film(
          AgeRating.getByAge((int) map.get("Rating")),
          Genre.getByName((String) map.get("Genre")),
          Language.getByCode((String) map.get("LanguageCode")),
          (String) map.get("Title"),
          (Time) map.get("Duration"),
          (String) map.get("Director"),
          (int) map.get("FilmId"));
    }
  }
}
