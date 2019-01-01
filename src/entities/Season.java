package com.netflix.entities;

import com.netflix.*;
import com.netflix.commons.*;
import com.netflix.entities.abstracts.Entity;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class Season extends Entity {

  public static Set<Season> seasons = new HashSet<>();
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
    serie.setSeasonCount(serie.getSeasonCount() + 1);
    serie.addSeason(this);
  }

  public Set<Episode> getEpisodes() {
    return episodes;
  }

  public void addEpisode(Episode episode) {
    this.episodes.add(episode);
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

    public static Season getByDbId(int id) {
        return seasons.stream().filter(ent -> ent.databaseId == id).findFirst().orElse(null);
    }

  public static void getFromDatabase() {
    if (Netflix.database.connectDatabase()) {
      String sqlQuery = "SELECT SeasonId, SerieId, Title, SeasonNumber FROM Season";
      ResultSet results = null;
      try (Statement statement = Netflix.database.connection.createStatement()) {
        // Make sure the results are passed
        results = statement.executeQuery(sqlQuery);
        System.out.println("Query passed : " + results.toString());
        while (results.next())
          new Season(
              Serie.getByDbId(results.getInt("SerieId")),
              results.getString("Title"),
              results.getInt("SeasonNumber"),
              results.getInt("SeasonId"));

      } catch (SQLException ex) {
        Commons.exception(ex);
        System.out.println("Query did not pass");
      }
      Netflix.database.disconnectDatabase();
    }
  }

  @Override
  public String toString() {
    return title;
  }
}
