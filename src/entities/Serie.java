package com.netflix.entities;

import com.netflix.*;
import com.netflix.entities.abstracts.*;

import java.util.*;

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
    for (HashMap<String, Object> map :
        Netflix.database.executeSql(
            "SELECT SerieId, Title, AmountOfSeasons, LijktOp, LanguageCode, Rating FROM Serie")) {
      new Serie(
          Genre.getByName((String) map.get("Genre")),
          Language.getByCode((String) map.get("Languagecode")),
          (String) map.get("Title"),
          AgeRating.getByAge((int) map.get("Rating")),
          (int) map.get("SerieId"));
    }
  }
}
