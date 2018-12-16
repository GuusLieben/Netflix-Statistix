package com.netflix.entities;

import com.netflix.commons.Commons;

public class Genre {

  private String genreName;

  public Genre(String genre) {
    this.genreName = genre;
    Commons.genres.add(this);
  }

  public String getGenre() {
    return genreName;
  }

  @Override
  public String toString() {
    return genreName;
  }
}
