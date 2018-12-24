package com.netflix.entities;

import java.util.HashSet;
import java.util.Set;

public class Genre {

    public static Set<Genre> genres = new HashSet<>();
    private String genreName;

  public Genre(String genre) {
    this.genreName = genre;
    genres.add(this);
  }

  public String getGenre() {
    return genreName;
  }

  @Override
  public String toString() {
    return genreName;
  }
}
