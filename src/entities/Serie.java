package com.netflix.entities;

import com.netflix.*;
import com.netflix.commons.*;
import com.netflix.entities.abstracts.MediaObject;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class Serie extends MediaObject { // MediaObject extends Entity

  public static Set<Serie> series = new HashSet<>();
  public static Set<String> serieTitles = new HashSet<>();
  private int episodeCount;
  private int seasonCount;
  private Set<Season> seasons = new HashSet<>();

  public Serie(Genre genre, Language lang, String title, AgeRating rating, int databaseId) {
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

  public static Serie getSerieByName(String title) {
    return series.stream().filter(serie -> serie.getTitle().equals(title)).findFirst().orElse(null);
  }

  public Set<Season> getSeasons() {
    return seasons;
  }

  public void addSeason(Season season) {
    seasons.add(season);
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

  public void setEpisodeCount(int episodes) {
    this.episodeCount = episodes;
  }

  public static Serie getByDbId(int id) {
    return series.stream().filter(ent -> ent.databaseId == id).findFirst().orElse(null);
  }

  public static void getFromDatabase() {
    if (Netflix.database.connectDatabase()) {
      String sqlQuery =
          "SELECT SerieId, Title, AmountOfSeasons, LijktOp, LanguageCode, Rating FROM Serie";
      ResultSet results = null;
      try (Statement statement = Netflix.database.connection.createStatement()) {
        // Make sure the results are passed
        results = statement.executeQuery(sqlQuery);
        System.out.println("Query passed : " + results.toString());
        while (results.next())
          new Serie(
              new Genre("Drama", 1), // TODO : get Genre by ID
              (Language) Language.getByCode(results.getString("LanguageCode")),
              results.getString("Title"),
              (AgeRating) AgeRating.getByAge(results.getInt("Rating")),
              results.getInt("SerieId"));

      } catch (SQLException ex) {
        Commons.exception(ex);
        System.out.println("Query did not pass");
      }
      Netflix.database.disconnectDatabase();
    }
  }
}
