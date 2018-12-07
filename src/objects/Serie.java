package com.netflix.objects;

public class Serie {

  private String title;
  private int episodes;
  private Genre genre;
  private Language lang;
  private double rating;
  private int seasons;

  public Serie(Genre genre, Language lang, String title, double rating) {
    this.genre = genre;
    this.lang = lang;
    this.title = title;
    this.rating = rating;
    this.seasons = 0;
    this.episodes = 0;
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

  public double getRating() {
    return rating;
  }

  public int getSeasons() {
    return seasons;
  }

  public void setSeasons(int seasons) {
    this.seasons = seasons;
  }

  public int getEpisodes() {
    return episodes;
  }

  public void setEpisodes(int episodes) {
    this.episodes = episodes;
  }
}
