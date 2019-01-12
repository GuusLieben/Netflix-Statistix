package com.netflix.entities;

import java.sql.Time;
import java.util.*;

import static com.netflix.Netflix.database;

public class Film extends MediaObject { // MediaObject extends Entity

  public static final Set<Film> films = new HashSet<>();
  public static List<String> filmTitles = new ArrayList<>();
  private Time duration;
  private String director;

  public Film(
      MediaCommons.AgeRating rating,
      MediaCommons.Genre genre,
      MediaCommons.Language lang,
      String title,
      Time duration,
      String director,
      int databaseId,
      String similarMedia) {
    super.similarMedia = similarMedia;
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

  // Get a film by the name
  public static Film getFilmByName(String title) {
    // Use Lambda to go through all films and check if it equals the given title, else return null
    return films.stream().filter(film -> film.getTitle().equals(title)).findFirst().orElse(null);
  }

  // Get film from database ID
  public static Film getByDbId(int id) {
    return films.stream().filter(ent -> ent.databaseId == id).findFirst().orElse(null);
  }

  // Get all films from the database
  public static void getFromDatabase() {
    for (HashMap<String, Object> map :
        database.executeSql(
            "SELECT Film.FilmId, Rating, LijktOp, LanguageCode, Title, Duration, Director, Genre FROM Film JOIN Koppeltabel_GenreId_Film ON Film.FilmId = Koppeltabel_GenreId_Film.FilmId JOIN Genre ON Koppeltabel_GenreId_Film.GenreId = Genre.GenreId")) {

      new Film(
          MediaCommons.AgeRating.getByAge((int) map.get("Rating")),
          MediaCommons.Genre.getByName((String) map.get("Genre")),
          MediaCommons.Language.getByCode((String) map.get("LanguageCode")),
          (String) map.get("Title"),
          (Time) map.get("Duration"),
          (String) map.get("Director"),
          (int) map.get("FilmId"),
          (String) map.get("LijktOp"));
    }
  }

  // Get all watched films and the profile that watched it
  public static void getViewData() {
    for (HashMap<String, Object> map :
        database.executeSql("SELECT UserId, FilmId FROM WatchedFilms")) {
      Account.Profile prof = Account.Profile.getByDbId((int) map.get("UserId")); // Breaks
      Film film = Film.getByDbId((int) map.get("FilmId"));
      prof.viewFilmNoDB(film);
    }
  }

  // Get the movie object from the similarMedia title
  public Film getSimilarObject() {
    return Film.getFilmByName(similarMedia);
  }

  // Getters
  public String getDuration() {
    return duration.toString();
  }

  public String getDirector() {
    return director;
  }
}
