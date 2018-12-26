/*
 * Copyright © 2018. Guus Lieben.
 * All rights reserved.
 */

package com.netflix.handles;

import com.netflix.commons.Commons;
import com.netflix.entities.*;

import java.sql.*;

public class DatabaseHandle {

  private Connection connection = null;

  public DatabaseHandle() {
    connectDatabase();
    collectData();
  }

  // Use the package.properties file to generate a connection string
  private static String connectionString() {
    return "jdbc:sqlserver://"
        + PropertiesHandle.get("jdbc.server")
        + ":"
        + PropertiesHandle.get("jdbc.port")
        + ";database="
        + PropertiesHandle.get("jdbc.database")
        + ";user="
        + PropertiesHandle.get("jdbc.user")
        + ";password="
        + PropertiesHandle.get("jdbc.password");
  }

  @SuppressWarnings("deprecation")
  public static void loadSampleData() {
    Commons.logger.info("Loading sample data");

    //////////// USER SAMPLE DATA

    // Create a sample account with profile and additional data
    Account account =
        new Account(false, "guus@xendox.com", "Steur", 358, "", "Hendrik-Ido-Ambacht", "pass");
    System.out.println(account.getEntityId());
    Profile profile = new Profile(account, "Guus", 18);
    System.out.println(profile.getEntityId());
    Profile profile2 = new Profile(account, "Niet Guus", 19);
    System.out.println(profile2.getEntityId());

    Account admin =
        new Account(true, "admin@admin.admin", "Lovensdijkstraat", 63, "", "Breda", "admin");
    Profile adminProfile = new Profile(admin, "Guus Lieben", 150);

    Account docent =
        new Account(true, "docent@avans.nl", "Lovensdijkstraat", 65, "", "Breda", "1234");
    Profile ruud = new Profile(docent, "Ruud Hermans", 33);
    Profile erik = new Profile(docent, "Erik Kuiper", 49);

    // Sample series
    Serie HouseOfCards =
        new Serie(
            new Genre("Drama"),
            new Language("nl_NL", "Dutch"),
            "House of Cards",
            new AgeRating("PG-13", 13));

    Serie Daredevil =
        new Serie(
            new Genre("Action"),
            new Language("de_DE", "German"),
            "Daredevil",
            new AgeRating("NC-17", 18));

    Season DaredevilSeason = new Season(Daredevil, "Season 1", 1);
    Season Season1 = new Season(HouseOfCards, "Season 1", 1);
    Season Season2 = new Season(HouseOfCards, "Season 2", 2);
    Episode episode1 = new Episode(Season1, "Pilot", HouseOfCards, 16.57, 1);
    Episode episode2 = new Episode(Season1, "Pilot Continued", HouseOfCards, 12.35, 2);
    Episode episode3 = new Episode(Season1, "Episode 3", HouseOfCards, 12.35, 3);
    Episode episode4 = new Episode(Season1, "Episode 4", HouseOfCards, 12.35, 4);
    Episode episode5 = new Episode(Season1, "Episode 5", HouseOfCards, 12.35, 5);
    Episode episode6 = new Episode(Season2, "Episode 6", HouseOfCards, 12.35, 1);
    Episode episode7 = new Episode(Season2, "Episode 7", HouseOfCards, 12.35, 2);
    Episode episode8 = new Episode(Season2, "Episode 8", HouseOfCards, 12.35, 3);
    Episode episode9 = new Episode(Season2, "Episode 9", HouseOfCards, 12.35, 4);
    Episode episode10 = new Episode(DaredevilSeason, "Episode 10", Daredevil, 12.35, 1);

    // Sample films

    Film Avengers =
        new Film(
            new AgeRating("NC-17", 18),
            new Genre("Romance"),
            new Language("nl_NL", "Dutch"),
            "The Avengers",
            new Time(1, 57, 38),
            "Bob");

    Film Twilight =
        new Film(
            new AgeRating("PG-13", 13),
            new Genre("Romance"),
            new Language("nl_NL", "Dutch"),
            "Twilight",
            new Time(0, 57, 38),
            "The Milkman");

    Film Narnia =
        new Film(
            new AgeRating("R", 17),
            new Genre("Action"),
            new Language("nl_NL", "Dutch"),
            "Narnia",
            new Time(0, 57, 38),
            "The Cartoonman");

    // Sample viewdata
    profile2.viewEpisode(episode1);
    profile2.viewEpisode(episode2);
    profile.viewFilm(Avengers);

    //////////// FILM SAMPLE DATA
    Film.films.add(Twilight);
    Film.films.add(Narnia);
    Film.films.add(Avengers);

    //////////// SERIE SAMPLE DATA
    Serie.series.add(HouseOfCards);
    Serie.series.add(Daredevil);
  }

  private void collectData() {
    // First load items that do not require others entities
    loadGenres();
    loadLangs();
    loadRatings();

    // Load films
    loadFilms();

    // Load all serie entities in order
    loadSeries();
    loadSeasons();
    loadEpisodes();

    // Load all users in order
    loadUsers();
    loadProfiles();
  }

  // Connect to the database with the generated string
  public boolean connectDatabase() {
    try {
      // Use MS Sql server
      Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
      // Use the connectionUrl to connect (jdbc connection string)
      connection = DriverManager.getConnection(connectionString());
      return true;

    } catch (ClassNotFoundException | SQLException e) {
      Commons.exception(e);
      connection = null;
      return false;
    }
  }

  private void loadRatings() {
    ResultSet seasonSet = executeSql("SELECT * FROM Ratings");
    try {
      while (seasonSet.next()) {
        //              Commons.ratings.add(...)
      }
    } catch (SQLException ex) {
      Commons.exception(ex);
    }
  }

  private void loadFilms() {
    ResultSet filmSet = executeSql("SELECT * FROM FilmMediaView");
    try {
      while (filmSet.next()) {
        //              Commons.films.add(...)
      }
    } catch (SQLException ex) {
      Commons.exception(ex);
    }
  }

  private void loadSeries() {
    ResultSet serieSet = executeSql("SELECT * FROM SerieMediaView");
    try {
      while (serieSet.next()) {
        //              Commons.series.add(...)
      }
    } catch (SQLException ex) {
      Commons.exception(ex);
    }
  }

  private void loadSeasons() {
    ResultSet seasonSet = executeSql("SELECT * FROM Seasons");
    try {
      while (seasonSet.next()) {
        //              Commons.seasons.add(...)
      }
    } catch (SQLException ex) {
      Commons.exception(ex);
    }
  }

  private void loadEpisodes() {
    ResultSet episodeSet = executeSql("SELECT * FROM Episodes");
    try {
      while (episodeSet.next()) {
        //              Commons.episodes.add(...)
      }
    } catch (SQLException ex) {
      Commons.exception(ex);
    }
  }

  private void loadGenres() {
    ResultSet genreSet = executeSql("SELECT * FROM Genres");
    try {
      while (genreSet.next()) {
        //              Commons.episodes.add(...)
      }
    } catch (SQLException ex) {
      Commons.exception(ex);
    }
  }

  private void loadLangs() {
    ResultSet langSet = executeSql("SELECT * FROM Languages");
    try {
      while (langSet.next()) {
        //              Commons.episodes.add(...)
      }
    } catch (SQLException ex) {
      Commons.exception(ex);
    }
  }

  private void loadUsers() {
    ResultSet userSet = executeSql("SELECT * FROM Users");
    try {
      while (userSet.next()) {
        //              Commons.episodes.add(...)
      }
    } catch (SQLException ex) {
      Commons.exception(ex);
    }
  }

  private void loadProfiles() {
    ResultSet profileSet = executeSql("SELECT * FROM Profiles");
    try {
      while (profileSet.next()) {
        //              Commons.episodes.add(...)
      }
    } catch (SQLException ex) {
      Commons.exception(ex);
    }
  }

  public void registerAccount(Account account) {
    throw new UnsupportedOperationException();
  }

  public void disconnectDatabase() {
    // Check if it isn't already disconnected
    if (connection != null)
      try {
        connection.close();
      } catch (SQLException e) {
        Commons.exception(e);
      }
    // Set connection to null, if it's already disconnected it'd be the same anyway
    connection = null;
  }

  public ResultSet executeSql(String sqlQuery) {
    ResultSet results = null;
    try (Statement statement = this.connection.createStatement()) {
      // Make sure the results are passed
      results = statement.executeQuery(sqlQuery);
    } catch (SQLException ex) {
      Commons.exception(ex);
    }
    return results;
  }

  public boolean executeSqlNoResult(String sqlQuery) {
    // Return true if the query succeeded, even if it has no resultset
    try (Statement statement = this.connection.createStatement()) {
      return statement.execute(sqlQuery);
    } catch (Exception ex) {
      Commons.exception(ex);
    }
    return false;
  }
}
