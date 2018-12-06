package com.netflix.objects;

public class Genre {

  String genre;

  public Genre(String genre) {
    this.genre = genre;
  }

  public String getGenre() {
    return genre;
  }

    @Override
    public String toString() {
        return genre;
    }
}
