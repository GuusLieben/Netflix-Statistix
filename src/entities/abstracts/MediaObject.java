/*
 * Copyright © 2018. Guus Lieben.
 * All rights reserved.
 */

package com.netflix.entities.abstracts;

import com.netflix.entities.*;

import java.util.HashSet;
import java.util.Set;

public abstract class MediaObject extends Entity {

  public static int type;
  public int mediaType;
  public String title;
  public Genre genre;
  public Language lang;
  public AgeRating rating;
  private Set<Profile> watchedBy;

  public MediaObject() {
    watchedBy = new HashSet<>();
  }

  public static MediaObject getObjectByName(String name, int mediaType) {
    switch (mediaType) {
      case 2:
        for (Serie serie : Serie.series) if (serie.getTitle().equals(name)) return serie;
        return null;
      case 1:
        for (Film film : Film.films) if (film.getTitle().equals(name)) return film;
        return null;
      default:
        return null;
    }
  }

  public int getWatchedByAmount() {
    return watchedBy.size();
  }

  public void setWatchedBy(Profile profile) {
    watchedBy.add(profile);
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    MediaObject.type = type;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Genre getGenre() {
    return genre;
  }

  public void setGenre(Genre genre) {
    this.genre = genre;
  }

  public Language getLang() {
    return lang;
  }

  public void setLang(Language lang) {
    this.lang = lang;
  }

  public AgeRating getRating() {
    return rating;
  }

  public void setRating(AgeRating rating) {
    this.rating = rating;
  }

  public int getMediaType() {
    return mediaType;
  }

  public void setMediaType(int mediaType) {
    this.mediaType = mediaType;
  }
}
