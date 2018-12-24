package com.netflix.entities;

import java.util.HashSet;
import java.util.Set;

public class Season {

  public static Set<Season> seasons = new HashSet<>();
  private Serie serie;
  private String title;
  private int seaonNumber;
  private int amountOfEpisodes;
  private Set<Episode> episodes = new HashSet<>();

  public Season(Serie serie, String title, int seaonNumber) {
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

  @Override
  public String toString() {
    return title;
  }
}
