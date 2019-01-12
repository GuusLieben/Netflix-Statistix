package com.netflix.entities;

import java.sql.*;
import java.util.*;

import static com.netflix.Netflix.database;

public class Serie extends MediaObject { // MediaObject extends Entity

  public static final Set<Serie> series = new HashSet<>();
  public static List<String> serieTitles = new ArrayList<>();
  public int watchedEpisodes = 0;
  private int episodeCount;
  private int seasonCount;
  private List<Season> seasons = new ArrayList<>();

  public Serie(
      MediaCommons.Genre genre,
      MediaCommons.Language lang,
      String title,
      MediaCommons.AgeRating rating,
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
          MediaCommons.Genre.getByName((String) map.get("Genre")),
          MediaCommons.Language.getByCode((String) map.get("LanguageCode")),
          (String) map.get("Title"),
          MediaCommons.AgeRating.getByAge((int) map.get("Rating")),
          (int) map.get("SerieId"),
          (String) map.get("LijktOp"));
    }
  }

  // Getters
  public List<Season> getSeasons() {
    return seasons;
  }

  public Serie getSimilarObject() {
    return series.stream()
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

    public static class Season extends Entity {

      protected static final List<Season> seasons = new ArrayList<>();
      private Serie serie;
      private String title;
      private int seaonNumber;
      private int amountOfEpisodes;
      private Set<Episode> episodes = new HashSet<>();

      public Season(Serie serie, String title, int seaonNumber, int databaseId) {
        super.databaseId = databaseId;
        this.serie = serie;
        this.title = title;
        this.seaonNumber = seaonNumber;
        amountOfEpisodes = 0;
        seasons.add(this);
        serie.setSeasonCount(serie.getSeasonCount() + 1);
        serie.addSeason(this);
      }

      // Get season by database id
      public static Season getByDbId(int id) {
        return seasons.stream().filter(ent -> ent.databaseId == id).findFirst().orElse(null);
      }

      // Get seasons from database
      public static void getFromDatabase() {
        for (HashMap<String, Object> map :
            database.executeSql("SELECT SeasonId, SerieId, Title, SeasonNumber FROM Season")) {
          new Season(
              Serie.getByDbId((int) map.get("SerieId")),
              (String) map.get("Title"),
              (int) map.get("SeasonNumber"),
              (int) map.get("SeasonId"));
        }
      }

      // Getters
      public Set<Episode> getEpisodes() {
        return episodes;
      }

      public Serie getSerie() {
        return serie;
      }

      public String getTitle() {
        return title;
      }

      public int getSeaonNumber() {
        return seaonNumber;
      }

      public int getAmountOfEpisodes() {
        return amountOfEpisodes;
      }

      // Add an episode to the season
      public void addEpisode(Episode episode) {
        this.episodes.add(episode);
      }

      @Override
      public String toString() {
        return title;
      }
    }

    public static class Episode extends Entity {

      public static final Set<Episode> episodes = new HashSet<>();
      private Season season;
      private String title;
      private Serie serie;
      private Time duration;
      private int episodeNumber;

      public Episode(
              Season season, String title, Serie serie, Time duration, int episodeNumber, int databaseId) {
        super.databaseId = databaseId;
        this.season = season;
        this.title = title;
        this.serie = serie;
        this.duration = duration;
        this.episodeNumber = episodeNumber;
        serie.setEpisodeCount(serie.getEpisodeCount() + 1);
        episodes.add(this);
        season.addEpisode(this);
      }

      // Get episode by database ID
      public static Episode getByDbId(int id) {
        return episodes.stream().filter(ent -> ent.databaseId == id).findFirst().orElse(null);
      }

      // Get all episodes from database
      public static void getFromDatabase() {
        for (HashMap<String, Object> map :
            database.executeSql(
                "SELECT EpisodeId, SeasonId, Title, Duration, EpisodeNumber FROM Episode")) {
          new Episode(
              Season.getByDbId((int) map.get("SeasonId")),
              (String) map.get("Title"),
              Season.getByDbId((int) map.get("SeasonId")).getSerie(),
              (Time) map.get("Duration"),
              (int) map.get("EpisodeNumber"),
              (int) map.get("EpisodeId"));
        }
      }

      // Get all watched episodes and the profile that watched it
      public static void getViewData() {
        for (HashMap<String, Object> map :
            database.executeSql("SELECT UserId, EpisodeId FROM WatchedEpisodes")) {
          Account.Profile prof = Account.Profile.getByDbId((int) map.get("UserId")); // Breaks
          Episode epi = Episode.getByDbId((int) map.get("EpisodeId"));
          prof.viewEpisodeNoDB(epi);
        }
      }

      // Getters
      public int getEpisodeNumber() {
        return episodeNumber;
      }

      public Season getSeason() {
        return season;
      }

      public String getTitle() {
        return title;
      }

      public Serie getSerie() {
        return serie;
      }

      public Time getDuration() {
        return duration;
      }

      // Use the Stream API to find out if the current profile watched this episode
      public Boolean watchedByProfile() {
        return Account.Profile.currentUser.getEpisodesWatched().stream().anyMatch(epi -> epi == this);
      }
    }
}
