package com.netflix.objects;

import com.netflix.commons.Commons;

import java.sql.Time;

@SuppressWarnings("deprecation")
public class Film {

  private double rating;
  private Genre genre;
  private Language lang;
  private String title;
  private Time duration;
  private String director;

  public Film(
      double rating, Genre genre, Language lang, String title, Time duration, String director) {
    this.rating = rating;
    this.genre = genre;
    this.lang = lang;
    this.title = title;
    this.duration = duration;
    this.director = director;
    Commons.films.add(this);
    Commons.filmTitles.add(title);
  }

  public static Film getFilmByName(String title) {
    return Commons.films
        .stream()
        .filter(film -> film.getTitle().equals(title))
        .findFirst()
        .orElse(null);
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

  public String getDuration() {
    return duration.toString();
  }

  public String getDirector() {
    return director;
  }
}
