package com.netflix.objects;

public class Genre {

  private String genreName;

  public Genre(String genre) {
    this.genreName = genre;
  }

  public String getGenre() {
    return genreName;
  }

  @Override
  public String toString() {
    return genreName;
  }
}
