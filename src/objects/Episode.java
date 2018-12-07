package com.netflix.objects;

public class Episode {

  private Season season;
  private String title;
  private Serie serie;
  private double duration;

  public Episode(Season season, String title, Serie serie, double duration) {
    this.season = season;
    this.title = title;
    this.serie = serie;
    this.duration = duration;
    serie.setEpisodes(serie.getEpisodes() + 1);
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
