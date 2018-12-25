package com.netflix.entities;

import com.netflix.entities.abstracts.MediaObject;

import java.sql.Time;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("deprecation")
public class Film extends MediaObject { // MediaObject extends Entity

  public static Set<Film> films = new HashSet<>();
  public static Set<String> filmTitles = new HashSet<>();
  private Time duration;
  private String director;

  public Film(
      AgeRating rating, Genre genre, Language lang, String title, Time duration, String director) {
    super.rating = rating;
    super.genre = genre;
    super.lang = lang;
    super.title = title;
    mediaType = 1;
    this.duration = duration;
    this.director = director;
    films.add(this);
    filmTitles.add(title);
  }

  public static Film getFilmByName(String title) {
    // Use Lambda to go through all films and check if it equals the given title, else return null
    return films.stream().filter(film -> film.getTitle().equals(title)).findFirst().orElse(null);
  }

  public String getDuration() {
    return duration.toString();
  }

  public String getDirector() {
    return director;
  }
}
