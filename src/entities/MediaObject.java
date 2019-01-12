package com.netflix.entities;

import java.util.HashSet;
import java.util.Set;

public abstract class MediaObject extends Entity {

  public static int type;
  public static final Set<MediaObject> objectIds = new HashSet<>();
  protected int mediaType;
  public String title;
  protected MediaCommons.Genre genre;
  protected MediaCommons.Language lang;
  protected MediaCommons.AgeRating rating;
  public int objectId;
  private Set<Account.Profile> watchedBy;
  protected String similarMedia;

  protected MediaObject() {
    watchedBy = new HashSet<>();
    objectId = objectIds.size() + 1;
    objectIds.add(this);
  }

  // Get Object by name, works for films and series
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

  // Watched by statistics
  private int getWatchedByAmount() {
    return watchedBy.size();
  }

  public void removeWatchedBy(Account.Profile profile) {
    watchedBy.remove(profile);
  }

  public void setWatchedBy(Account.Profile profile) {
    watchedBy.add(profile);
  }

  public double getWatchedPercentage() {
    int profileCount = Account.accounts.stream().mapToInt(acc -> acc.getProfiles().size()).sum();
      return (double) this.getWatchedByAmount() / profileCount * 100;
  }

  // Getters and setters
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

  public MediaCommons.Genre getGenre() {
    return genre;
  }

  public void setGenre(MediaCommons.Genre genre) {
    this.genre = genre;
  }

  public MediaCommons.Language getLang() {
    return lang;
  }

  public void setLang(MediaCommons.Language lang) {
    this.lang = lang;
  }

  public MediaCommons.AgeRating getRating() {
    return rating;
  }

  public void setRating(MediaCommons.AgeRating rating) {
    this.rating = rating;
  }

  public int getMediaType() {
    return mediaType;
  }

  public void setMediaType(int mediaType) {
    this.mediaType = mediaType;
  }
}
