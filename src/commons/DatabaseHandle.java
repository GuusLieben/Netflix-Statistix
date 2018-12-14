package com.netflix.commons;

import com.netflix.objects.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

public class DatabaseHandle {

  private final SqlConnection connection;

  public DatabaseHandle() {
    connection = new SqlConnection();
    connectDatabase();
  }

  // Use the package.properties file to generate a connection string
  private static String connectionString() {
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

  public static void loadFilms() {}

  @SuppressWarnings("deprecation")
  public static void loadSampleData() {
    //////////// USER SAMPLE DATA

    // Sample login, will be grabbed from database later
    Commons.users.put("guuslieben", "d41d8cd98f00b204e9800998ecf8427e");

    // Create a sample account with profile and additional data
    Account account =
        new Account(
            true, "g.lieben@avans.student.nl", "Steur", 358, "", "Hendrik-Ido-Ambacht", "pass");
    Profile profile = new Profile(account, "Guus", 18);
    Profile profile2 = new Profile(account, "Sarah", 19);

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

    Season Season1 = new Season(HouseOfCards, "Season 1", 1);
    Episode episode1 = new Episode(Season1, "Pilot", HouseOfCards, 16.57);
    Episode episode2 = new Episode(Season1, "Pilot Continued", HouseOfCards, 12.35);

    // Sample films

    Film Avengers =
        new Film(
            8.3,
            new Genre("Romance"),
            new Language("nl_NL", "Dutch"),
            "The Avengers",
            new Time(1, 57, 38),
            "Bob");

    Film Twilight =
        new Film(
            8.0,
            new Genre("Romance"),
            new Language("nl_NL", "Dutch"),
            "Twilight",
            new Time(0, 57, 38),
            "The Milkman");

    Film Narnia =
        new Film(
            9.0,
            new Genre("Action"),
            new Language("nl_NL", "Dutch"),
            "Narnia",
            new Time(0, 57, 38),
            "The Cartoonman");

    // Sample viewdata
    profile2.viewEpisode(episode1);
    profile2.viewEpisode(episode2);
    profile.viewFilm(Avengers);

    Commons.currentUser = profile2;

    //////////// FILM SAMPLE DATA
    Commons.films.add(Twilight);
    Commons.films.add(Narnia);
    Commons.films.add(Avengers);

    //////////// SERIE SAMPLE DATA
    Commons.series.add(HouseOfCards);
    Commons.series.add(Daredevil);
  }

  public static void loadSeries() {}

  // Connect to the database with the generated string
  public void connectDatabase() {
    connection.connectDatabase(connectionString());
  }

  public void disconnectDatabase() {
    connection.disconnectDatabase();
  }

  public void loadSeasons() {
    ResultSet seasonSet = connection.executeSql("SELECT * FROM Seasons");
    try {
      while (seasonSet.next()) {
        //              Commons.seasons.add(...)
      }
    } catch (SQLException ex) {
      Commons.exception(ex);
    }
  }

  public void loadEpisodes() {
    ResultSet episodeSet = connection.executeSql("SELECT * FROM Episodes");
    try {
      while (episodeSet.next()) {
        //              Commons.episodes.add(...)
      }
    } catch (SQLException ex) {
      Commons.exception(ex);
    }
  }

  public void loadGenres() {
    ResultSet genreSet = connection.executeSql("SELECT * FROM Genres");
    try {
      while (genreSet.next()) {
        //              Commons.episodes.add(...)
      }
    } catch (SQLException ex) {
      Commons.exception(ex);
    }
  }

  public void loadLangs() {
    ResultSet langSet = connection.executeSql("SELECT * FROM Languages");
    try {
      while (langSet.next()) {
        //              Commons.episodes.add(...)
      }
    } catch (SQLException ex) {
      Commons.exception(ex);
    }
  }

  public void loadUsers() {
    ResultSet userSet = connection.executeSql("SELECT * FROM Users");
    try {
      while (userSet.next()) {
        //              Commons.episodes.add(...)
      }
    } catch (SQLException ex) {
      Commons.exception(ex);
    }
  }

  public void loadProfiles() {
    ResultSet profileSet = connection.executeSql("SELECT * FROM Profiles");
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
}
