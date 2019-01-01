package com.netflix.entities;

import com.netflix.*;
import com.netflix.commons.*;
import com.netflix.entities.abstracts.Entity;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class Episode extends Entity {

  public static Set<Episode> episodes = new HashSet<>();
  private Season season;
  private String title;
  private Serie serie;
  private Time duration;
  private int episodeNumber;

  public Episode(
      Season season,
      String title,
      Serie serie,
      Time duration,
      int episodeNumber,
      int databaseId) {
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

    public static Episode getByDbId(int id) {
        return episodes.stream().filter(ent -> ent.databaseId == id).findFirst().orElse(null);
    }

  public static void getFromDatabase() {
    if (Netflix.database.connectDatabase()) {
      String sqlQuery = "SELECT EpisodeId, SeasonId, Title, Duration, EpisodeNumber FROM Episode";
      ResultSet results = null;
      try (Statement statement = Netflix.database.connection.createStatement()) {
        // Make sure the results are passed
        results = statement.executeQuery(sqlQuery);
        System.out.println("Query passed : " + results.toString());
        while (results.next()) {
          Season sn = (Season) Season.getByDbId(results.getInt("SeasonId"));
          new Episode(
              (Season) Season.getByDbId(results.getInt("SeasonId")),
              results.getString("Title"),
              sn.getSerie(),
              results.getTime("Duration"),
              results.getInt("EpisodeNumber"),
              results.getInt("EpisodeId"));
        }

      } catch (SQLException ex) {
        Commons.exception(ex);
        System.out.println("Query did not pass");
      }
      Netflix.database.disconnectDatabase();
    }
  }
}
