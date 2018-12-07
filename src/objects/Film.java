package com.netflix.objects;

public class Film {

  double rating;
  Genre genre;
  Language lang;
  String title;
  double duration;
  String director;

  public Film(
      double rating, Genre genre, Language lang, String title, double duration, String director) {
    this.rating = rating;
    this.genre = genre;
    this.lang = lang;
    this.title = title;
    this.duration = duration;
    this.director = director;
  }

  public double getRating() {
    return rating;
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

  public double getDuration() {
    return duration;
  }

  public String getDirector() {
    return director;
  }
}
