package com.netflix.entities;

import java.sql.Time;
import java.util.*;

import static com.netflix.Netflix.database;

public class Film extends MediaObject { // MediaObject extends Entity

  public static final Set<Film> films = new HashSet<>();
  public static List<String> filmTitles = new ArrayList<>();
  private Time duration;
  private String director;

  public Film(Object primaryId, Object secondaryId) {
    super(primaryId, secondaryId);
  }

  public Film(
      MediaCommons.AgeRating rating,
      MediaCommons.Genre genre,
      MediaCommons.Language lang,
      String title,
      Time duration,
      String director,
      int databaseId,
      String similarMedia) {
    super(title, null);
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

  public void setDuration(Time duration) {
    this.duration = duration;
  }

  public void setDirector(String director) {
    this.director = director;
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
          MediaCommons.AgeRating.getByAge(map.get("Rating")),
          MediaCommons.Genre.getByName((String) map.get("Genre")),
          MediaCommons.Language.getByCode(map.get("LanguageCode")),
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
        database.executeSql("SELECT UserId, FilmId, TimeWatched FROM WatchedFilms")) {
      Account.Profile prof = Account.Profile.getByDbId((int) map.get("UserId")); // Breaks
      Film film = Film.getByDbId((int) map.get("FilmId"));
      Time time = (Time) map.get("TimeWatched");
      prof.viewFilmNoDB(film, time);
    }
  }

  // Get the movie object from the similarMedia title
  public Film getSimilarObject() {
    return Film.getFilmByName(similarMedia);
  }

  // Getters
  public Time getDuration() {
    return duration;
  }

  public double getAverageWatchedTime() {
    double totalSeconds = 0;

    if (getWatchedBy().size() > 0) {

      for (Map.Entry<Account.Profile, Time> entry : getWatchedBy().entrySet()) {
        totalSeconds +=
            ((entry.getValue().getHours() * 3600)
                + (entry.getValue().getMinutes() * 60)
                + entry.getValue().getSeconds());
      }

      double averageSeconds = totalSeconds / getWatchedByAmount();

      double totalDuration =
          (duration.getHours() * 3600) + (duration.getMinutes() * 60) + duration.getSeconds();

      return averageSeconds / totalDuration * 100;
    }
    return 0;
  }

  public String getDirector() {
    return director;
  }
}
