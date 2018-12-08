package com.netflix.commons;

public class DatabaseHandle {

  // Use the package.properties file to generate a connection string
  public static String generateConnectionString() {
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

  // Connect to the database with the generated string
  public static void connectDatabase() {
    SqlConnection connection = new SqlConnection();
    connection.connectDatabase(generateConnectionString());
  }

  public void loadSeries() {
    //    Commons.series.add(Serie);
  }

  public void loadSeasons() {
    //    Commons.seasons.add(Season);
  }

  public void loadEpisodes() {
    //    Commons.episodes.add(Episode);
  }

  public void loadFilms() {
    //    Commons.films.add(Film);
  }

  public void loadGenres() {
    //    Commons.genres.add(Genre);
  }

  public void loadLangs() {
    //    Commons.langs.add(Language);
  }

  public void loadUsers() {
    //    Commons.users.put(AccountMail, Password);
  }
}
