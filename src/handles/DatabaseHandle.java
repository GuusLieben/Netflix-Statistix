/*
 * Copyright © 2018. Guus Lieben.
 * All rights reserved.
 */

package com.netflix.handles;

import com.netflix.commons.*;
import com.netflix.entities.*;

import java.sql.*;

public class DatabaseHandle {

    public Connection connection = null;

    // Use the package.properties file to generate a connection string
    private static String connectionString() {
//        String connectionUrl = "jdbc:sqlserver://localhost\\SQLEXPRESS;databaseName=Bibliotheek;integratedSecurity=true;";

        return "jdbc:sqlserver://"
                + PropertiesHandle.get("jdbc.server")
                + "\\SQLEXPRESS:"
                + PropertiesHandle.get("jdbc.port")
                + ";databasename="
                + PropertiesHandle.get("jdbc.database")
                + ";user="
                + PropertiesHandle.get("jdbc.user")
                + ";password="
                + PropertiesHandle.get("jdbc.password")
                + ";";
    }

    @SuppressWarnings("deprecation")
    public static void loadSampleData() {
        Commons.logger.info("Loading sample data");

        //////////// USER SAMPLE DATA

        // Create a sample account with profile and additional data
//        Account account =
//                new Account(false, "guus@xendox.com", "Steur", 358, "", "Hendrik-Ido-Ambacht", "pass");
//
//        Profile profile = new Profile(account, "Guus", 18);
//        Profile profile2 = new Profile(account, "Niet Guus", 19);
//
//        Account admin =
//                new Account(true, "admin@admin.admin", "Lovensdijkstraat", 63, "", "Breda", "admin");
//        Profile adminProfile = new Profile(admin, "Guus Lieben", 150);
//
//        Account docent =
//                new Account(true, "docent@avans.nl", "Lovensdijkstraat", 65, "", "Breda", "1234");
//        Profile ruud = new Profile(docent, "Ruud Hermans", 33);
//        Profile erik = new Profile(docent, "Erik Kuiper", 49);
//        Profile bob = new Profile(docent, "Bob Bogger", 49);

        // Sample series
//        Serie HouseOfCards =
//                new Serie(
//                        new Genre("Drama"),
//                        new Language("nl_NL", "Nederlands"),
//                        "House of Cards",
//                        new AgeRating("PG-13", 13));
//
//        Serie Daredevil =
//                new Serie(
//                        new Genre("Erotiek"),
//                        new Language("de_DE", "Duits"),
//                        "Daredevil",
//                        new AgeRating("NC-17", 18));
//
//        Season DaredevilSeason = new Season(Daredevil, "Season 1", 1);
//        Season Season1 = new Season(HouseOfCards, "Season 1", 1);
//        Season Season2 = new Season(HouseOfCards, "Season 2", 2);
//        Episode episode1 = new Episode(Season1, "Pilot", HouseOfCards, 16.57, 1);
//        Episode episode2 = new Episode(Season1, "Pilot Continued", HouseOfCards, 12.35, 2);
//        Episode episode3 = new Episode(Season1, "Episode 3", HouseOfCards, 12.35, 3);
//        Episode episode4 = new Episode(Season1, "Episode 4", HouseOfCards, 12.35, 4);
//        Episode episode5 = new Episode(Season1, "Episode 5", HouseOfCards, 12.35, 5);
//        Episode episode6 = new Episode(Season2, "Episode 6", HouseOfCards, 12.35, 1);
//        Episode episode7 = new Episode(Season2, "Episode 7", HouseOfCards, 12.35, 2);
//        Episode episode8 = new Episode(Season2, "Episode 8", HouseOfCards, 12.35, 3);
//        Episode episode9 = new Episode(Season2, "Episode 9", HouseOfCards, 12.35, 4);
//        Episode episode10 = new Episode(DaredevilSeason, "Episode 10", Daredevil, 12.35, 1);

        // Sample films

//        Film Avengers =
//                new Film(
//                        new AgeRating("NC-17", 18),
//                        new Genre("Romantiek"),
//                        new Language("nl_NL", "Nederlands"),
//                        "The Avengers",
//                        new Time(1, 57, 38),
//                        "Bob");
//
//        Film Twilight =
//                new Film(
//                        new AgeRating("PG-13", 13),
//                        new Genre("Sci-fi"),
//                        new Language("nl_NL", "Nederlands"),
//                        "Twilight",
//                        new Time(0, 57, 38),
//                        "The Milkman");
//
//        Film Narnia =
//                new Film(
//                        new AgeRating("R", 17),
//                        new Genre("Misdaad"),
//                        new Language("nl_NL", "Nederlands"),
//                        "Narnia",
//                        new Time(0, 57, 38),
//                        "The Cartoonman");

        // Sample viewdata
//        profile2.viewEpisode(episode1);
//        profile2.viewEpisode(episode2);
//        profile2.viewEpisode(episode10);
//        profile.viewFilm(Avengers);
//        bob.viewFilm(Avengers);
//        bob.viewEpisode(episode10);
//        ruud.viewEpisode(episode10);

        //////////// FILM SAMPLE DATA
//        Film.films.add(Twilight);
//        Film.films.add(Narnia);
//        Film.films.add(Avengers);
//
//        //////////// SERIE SAMPLE DATA
//        Serie.series.add(HouseOfCards);
//        Serie.series.add(Daredevil);
    }

    public void collectData() {
        // First load items that do not require others entities
        Genre.getFromDatabase();
        Language.getFromDatabase();
        AgeRating.getFromDatabase();

        // Load films
        Film.getFromDatabase();

        // Load all serie entities in order
        Serie.getFromDatabase();
        Season.getFromDatabase();
        Episode.getFromDatabase();

        // Load all users in order
        Account.getFromDatabase();
        Profile.getFromDatabase();
    }

    // Connect to the database with the generated string
    public boolean connectDatabase() {
        try {
            // Use MS Sql server
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            // Use the connectionUrl to connect (jdbc connection string)
            connection = DriverManager.getConnection(connectionString());
            System.out.println("Connected to database '" + PropertiesHandle.get("jdbc.server") + "'");
            return true;
        } catch (ClassNotFoundException | SQLException e) {
            Commons.exception(e);
            connection = null;
            System.out.println("Failed to connect to database '" + PropertiesHandle.get("jdbc.server") + "'");
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
                System.out.println("Disconnected from database '" + PropertiesHandle.get("jdbc.server") + "'");
            } catch (SQLException e) {
                Commons.exception(e);
                System.out.println("Failed to disconnect from database '" + PropertiesHandle.get("jdbc.server") + "'");
            }
        // Set connection to null, if it's already disconnected it'd be the same anyway
        connection = null;
    }

    public ResultSet executeSql(String sqlQuery) {
        ResultSet results = null;
        if (
                connectDatabase()) {
            try (Statement statement = this.connection.createStatement()) {
                // Make sure the results are passed
                results = statement.executeQuery(sqlQuery);
                System.out.println("Query passed : " + results.toString());
                return results;
            } catch (SQLException ex) {
                Commons.exception(ex);
                System.out.println("Query did not pass");
            }
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
