package com.netflix.entities;

import com.netflix.*;
import com.netflix.entities.abstracts.*;

import java.util.*;

public class Genre extends Entity {

  public static Set<Genre> genres = new HashSet<>();
  private String genreName;

  public Genre(String genre, int databaseId) {
    this.genreName = genre;
    this.databaseId = databaseId;
    genres.add(this);
  }

  public static Genre getByName(String name) {
    for (Genre genre : genres) if (genre.getGenre().equals(name)) return genre;
    return null;
  }

  public String getGenre() {
    return genreName;
  }

  @Override
  public String toString() {
    return genreName;
  }

  public static void getFromDatabase() {
    for (HashMap<String, Object> map :
        Netflix.database.executeSql("SELECT GenreId, Genre FROM Genre")) {
      new Genre((String) map.get("Genre"), (int) map.get("GenreId"));
    }
  }
}
