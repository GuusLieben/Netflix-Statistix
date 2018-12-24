package com.netflix.entities;

import java.util.HashSet;
import java.util.Set;

public class Episode {

  public static Set<Episode> episodes = new HashSet<>();
  private Season season;
  private String title;
  private Serie serie;
  private double duration;

  public Episode(Season season, String title, Serie serie, double duration) {
    this.season = season;
    this.title = title;
    this.serie = serie;
    this.duration = duration;
    serie.setEpisodeCount(serie.getEpisodeCount() + 1);
    episodes.add(this);
    season.addEpisode(this);
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

  public double getDuration() {
    return duration;
  }
}
