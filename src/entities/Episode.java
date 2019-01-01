package com.netflix.entities;

import com.netflix.*;
import com.netflix.entities.abstracts.*;

import java.sql.*;
import java.util.*;

public class Episode extends Entity {

  public static Set<Episode> episodes = new HashSet<>();
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
    for (HashMap<String, Object> map :
        Netflix.database.executeSql(
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
}