package com.netflix.entities;

import com.netflix.entities.abstracts.Entity;

import java.util.HashSet;
import java.util.Set;

public class Episode extends Entity {

  public static Set<Episode> episodes = new HashSet<>();
  private Season season;
  private String title;
  private Serie serie;
  private double duration;
  private int episodeNumber;

  public Episode(Season season, String title, Serie serie, double duration, int episodeNumber) {
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

  public double getDuration() {
    return duration;
  }
}
