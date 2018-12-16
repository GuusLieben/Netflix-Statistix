package com.netflix.entities;

import com.netflix.commons.Commons;

import java.util.ArrayList;
import java.util.List;

public class Serie {

  private String title;
  private int episodeCount;
  private Genre genre;
  private Language lang;
  private AgeRating rating;
  private int seasonCount;
  private List<Season> seasons = new ArrayList<>();

  public Serie(Genre genre, Language lang, String title, AgeRating rating) {
    this.genre = genre;
    this.lang = lang;
    this.title = title;
    this.rating = rating;
    this.seasonCount = 0;
    this.episodeCount = 0;
    Commons.serieTitles.add(title);
    Commons.series.add(this);
  }

  public static Serie getSerieByName(String title) {
    return Commons.series
        .stream()
        .filter(serie -> serie.getTitle().equals(title))
        .findFirst()
        .orElse(null);
  }

  public List<Season> getSeasons() {
    return seasons;
  }

  public void addSeason(Season season) {
    seasons.add(season);
  }

  public Genre getGenre() {
    return genre;
  }

  public Language getLang() {
    return lang;
  }

  public String getTitle() {
    return title;
  }

  public AgeRating getRating() {
    return rating;
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
}
