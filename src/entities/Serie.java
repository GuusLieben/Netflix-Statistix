package com.netflix.entities;

import com.netflix.entities.abstracts.MediaObject;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static com.netflix.Netflix.database;

public class Serie extends MediaObject { // MediaObject extends Entity

  public static Set<Serie> series = new HashSet<>();
  public static Set<String> serieTitles = new HashSet<>();
  public int watchedEpisodes = 0;
  private int episodeCount;
  private int seasonCount;
  private Set<Season> seasons = new HashSet<>();

  public Serie(
      Genre genre,
      Language lang,
      String title,
      AgeRating rating,
      int databaseId,
      String similarMedia) {
    super.similarMedia = similarMedia;
    super.databaseId = databaseId;
    super.genre = genre;
    super.lang = lang;
    super.title = title;
    super.rating = rating;
    mediaType = 2;
    this.seasonCount = 0;
    this.episodeCount = 0;
    serieTitles.add(title);
    series.add(this);
  }

  // Get a serie by name
  public static Serie getSerieByName(String title) {
    return series.stream().filter(serie -> serie.getTitle().equals(title)).findFirst().orElse(null);
  }

  // Get serie by database id
  public static Serie getByDbId(int id) {
    return series.stream().filter(ent -> ent.databaseId == id).findFirst().orElse(null);
  }

  // Get series from database
  public static void getFromDatabase() {
    for (HashMap<String, Object> map :
        database.executeSql(
            "SELECT Serie.SerieId, Title, AmountOfSeasons, LijktOp, LanguageCode, Rating, Genre FROM Serie JOIN Koppeltabel_Serie_Genre ON Serie.SerieId = Koppeltabel_Serie_Genre.SerieId JOIN Genre ON Koppeltabel_Serie_Genre.GenreId = Genre.GenreId")) {
      new Serie(
          Genre.getByName((String) map.get("Genre")),
          Language.getByCode((String) map.get("LanguageCode")),
          (String) map.get("Title"),
          AgeRating.getByAge((int) map.get("Rating")),
          (int) map.get("SerieId"),
          (String) map.get("LijktOp"));
    }
  }

  // Getters
  public Set<Season> getSeasons() {
    return seasons;
  }

  public Serie getSimilarObject() {
    return series
        .stream()
        .filter(serie -> serie.getTitle().equals(similarMedia))
        .findFirst()
        .orElse(null);
  }

  public int getSeasonCount() {
    return seasonCount;
  }

  public void setSeasonCount(int seasons) {
    this.seasonCount = seasons;
  }

  public int getEpisodeCount() {
    return episodeCount;
  }

  // Set the episode count
  public void setEpisodeCount(int episodes) {
    this.episodeCount = episodes;
  }

  // Add a season to the serie
  public void addSeason(Season season) {
    seasons.add(season);
  }
}
