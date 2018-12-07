package com.netflix.commons;

public class DatabaseHandle {
  public String generateConnectionString() {
    return "jdbc:sqlserver://"
        + PropertyIndex.get("jdbc.server")
        + ":"
        + PropertyIndex.get("jdbc.port")
        + ";database="
        + PropertyIndex.get("jdbc.database")
        + ";user="
        + PropertyIndex.get("jdbc.user")
        + ";password="
        + PropertyIndex.get("jdbc.password");
  }

  public void loadSeries() {
    //        Commons.series.add(Serie);
  }

  public void loadSeasons() {
    //        Commons.seasons.put(Season, Season.getSerie());
  }

  public void loadEpisodes() {
    //        Commons.episodes.put(Episode, Episode.getSeason());
  }

  public void loadFilms() {
    //        Commons.films.add(Film);
  }

  public void loadGenres() {
    //        Commons.genres.add(Genre);
  }

  public void loadLangs() {
    //        Commons.langs.add(Language);
  }
}
