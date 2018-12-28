package com.netflix.handles;

import com.netflix.Netflix;
import com.netflix.commons.Commons;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LoadData {

  DatabaseHandle con = Netflix.database;

  void loadRatings() {
    ResultSet seasonSet = con.executeSql("SELECT * FROM Ratings");
    try {
      while (seasonSet.next()) {
        //              Commons.ratings.add(...)
      }
    } catch (SQLException ex) {
      Commons.exception(ex);
    }
  }

  void loadFilms() {
    ResultSet filmSet = con.executeSql("SELECT * FROM FilmMediaView");
    try {
      while (filmSet.next()) {
        //              Commons.films.add(...)
      }
    } catch (SQLException ex) {
      Commons.exception(ex);
    }
  }

  void loadSeries() {
    ResultSet serieSet = con.executeSql("SELECT * FROM SerieMediaView");
    try {
      while (serieSet.next()) {
        //              Commons.series.add(...)
      }
    } catch (SQLException ex) {
      Commons.exception(ex);
    }
  }

  void loadSeasons() {
    ResultSet seasonSet = con.executeSql("SELECT * FROM Seasons");
    try {
      while (seasonSet.next()) {
        //              Commons.seasons.add(...)
      }
    } catch (SQLException ex) {
      Commons.exception(ex);
    }
  }

  void loadEpisodes() {
    ResultSet episodeSet = con.executeSql("SELECT * FROM Episodes");
    try {
      while (episodeSet.next()) {
        //              Commons.episodes.add(...)
      }
    } catch (SQLException ex) {
      Commons.exception(ex);
    }
  }

  void loadGenres() {
    ResultSet genreSet = con.executeSql("SELECT * FROM Genres");
    try {
      while (genreSet.next()) {
        //              Commons.episodes.add(...)
      }
    } catch (SQLException ex) {
      Commons.exception(ex);
    }
  }

  void loadLangs() {
    ResultSet langSet = con.executeSql("SELECT * FROM Languages");
    try {
      while (langSet.next()) {
        //              Commons.episodes.add(...)
      }
    } catch (SQLException ex) {
      Commons.exception(ex);
    }
  }

  void loadUsers() {
    ResultSet userSet = con.executeSql("SELECT * FROM Users");
    try {
      while (userSet.next()) {
        //              Commons.episodes.add(...)
      }
    } catch (SQLException ex) {
      Commons.exception(ex);
    }
  }

  void loadProfiles() {
    ResultSet profileSet = con.executeSql("SELECT * FROM Profiles");
    try {
      while (profileSet.next()) {
        //              Commons.episodes.add(...)
      }
    } catch (SQLException ex) {
      Commons.exception(ex);
    }
  }
}
