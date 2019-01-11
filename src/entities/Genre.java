package com.netflix.entities;

import com.netflix.entities.abstracts.Entity;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static com.netflix.Netflix.database;

public class Genre extends Entity {

  public static Set<Genre> genres = new HashSet<>();
  private String genreName;

  public Genre(String genre, int databaseId) {
    this.genreName = genre;
    this.databaseId = databaseId;
    genres.add(this);
  }

  // Find the genre with a specific name
  public static Genre getByName(String name) {
    for (Genre genre : genres) if (genre.getGenre().equals(name)) return genre;
    return null;
  }

  // Get Genre from database
  public static void getFromDatabase() {
    for (HashMap<String, Object> map : database.executeSql("SELECT GenreId, Genre FROM Genre")) {
      new Genre((String) map.get("Genre"), (int) map.get("GenreId"));
    }
  }

  // Getter
  public String getGenre() {
    return genreName;
  }

  @Override
  public String toString() {
    return genreName;
  }
}
