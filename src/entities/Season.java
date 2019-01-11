package com.netflix.entities;

import com.netflix.entities.abstracts.Entity;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static com.netflix.Netflix.database;

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
