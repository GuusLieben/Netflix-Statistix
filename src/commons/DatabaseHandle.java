package com.netflix.commons;

import com.netflix.objects.*;

import java.sql.Time;
import java.util.Arrays;

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

  public static void loadFilms() {
    String[] films = {
      "Twilight",
      "Narnia",
      "The Avengers",
      "The Lord of the Rings",
      "Wildlife Pictured",
      "Whatever Movie"
    };
    Commons.filmTitles.addAll(Arrays.asList(films));
  }

  @SuppressWarnings("deprecation")
  public static void loadSampleData() {
    //////////// USER SAMPLE DATA

    // Sample login, will be grabbed from database later
    Commons.users.put("guuslieben", "d41d8cd98f00b204e9800998ecf8427e");

    // Create a sample account with profile and additional data
    Account account =
        new Account(true, "g.lieben@avans.student.nl", "Steur", 358, "", "Hendrik-Ido-Ambacht");
    Profile profile = new Profile(account, "Guus", 18);
    profile.viewFilm(
        new Film(
            8.3,
            new Genre("Romance"),
            new Language("nl_NL", "Dutch"),
            "The Avengers",
            new Time(1, 57, 38),
            "Bob"));

    Profile profile2 = new Profile(account, "Sarah", 19);

    Serie serie =
        new Serie(new Genre("Drama"), new Language("nl_NL", "Dutch"), "House of Cards", 8.6);
    Season season = new Season(serie, "newSeason", 1);
    Episode episode = new Episode(season, "Pilot", serie, 16.57);
    Episode episode2 = new Episode(season, "Pilot Continued", serie, 12.35);

    profile2.viewEpisode(episode);
    profile2.viewEpisode(episode2);

    Commons.currentUser = profile2;

    //////////// FILM SAMPLE DATA
    Commons.films.add(
        new Film(
            8.0,
            new Genre("Romance"),
            new Language("nl_NL", "Dutch"),
            "Twilight",
            new Time(0, 57, 38),
            "The Milkman"));

    Commons.films.add(
        new Film(
            9.0,
            new Genre("Action"),
            new Language("nl_NL", "Dutch"),
            "Narnia",
            new Time(0, 57, 38),
            "The Cartoonman"));

    //////////// FILM SAMPLE DATA
    Commons.series.add(
        new Serie(new Genre("Romance"), new Language("nl_NL", "Dutch"), "House of Cards", 8.0));
    Commons.series.add(
        new Serie(new Genre("Action"), new Language("de_DE", "German"), "Daredevil", 9.0));
  }

  public static void loadSeries() {
    String[] series = {
      "House of Cards",
      "Daredevil",
      "Stranger Things",
      "Orange Is the New Black",
      "Narcos",
      "The Crown"
    };
    Commons.serieTitles.addAll(Arrays.asList(series));
  }

  // Connect to the database with the generated string
  public void connectDatabase() {
    connection.connectDatabase(connectionString());
  }

  public void disconnectDatabase() {
    connection.disconnectDatabase();
  }

  // TODO : add database handles
  public void loadSeasons() {
    throw new UnsupportedOperationException();
  }

  public void loadEpisodes() {
    throw new UnsupportedOperationException();
  }

  public void loadGenres() {
    throw new UnsupportedOperationException();
  }

  public void loadLangs() {
    throw new UnsupportedOperationException();
  }

  public void loadUsers() {
    throw new UnsupportedOperationException();
  }

  public void loadProfiles() {
    throw new UnsupportedOperationException();
  }

  public void registerAccount(Account account) {
    throw new UnsupportedOperationException();
  }
}
