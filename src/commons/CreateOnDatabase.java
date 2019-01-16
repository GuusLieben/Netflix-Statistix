package com.netflix.commons;

import com.netflix.entities.*;

import java.sql.*;
import java.util.HashMap;

import static com.netflix.Netflix.database;

public class CreateOnDatabase {

  public void createAccount(
      boolean isAdmin,
      String email,
      String street,
      int houseNumber,
      String addition,
      String city,
      String password) {
    String qr =
        "INSERT INTO Account (isAdmin, Email, Straatnaam, Huisnummer, Toevoeging, Woonplaats, Wachtwoord) VALUES (?, ?, ?, ?, ?, ?, ?)";
    Object[] arr = {
      isAdmin, email, street, houseNumber, addition, city, Commons.hashSHA256(password)
    };

    if (database.executeSqlNoResult(qr, arr) == DataHandle.SQLResults.PASS) {
      for (HashMap<String, Object> map :
          database.executeSql(
              "SELECT AccountID FROM Account WHERE Email=?", new Object[] {email})) {
        new Account(
            (int) map.get("AccountID"),
            isAdmin,
            email,
            street,
            houseNumber,
            addition,
            city,
            password);
      }
    }
  }

  public void createSerie(
      MediaCommons.Genre genre,
      MediaCommons.Language lang,
      String title,
      MediaCommons.AgeRating rating,
      String similarMedia) {

    String qr =
        "INSERT INTO Serie (Title, AmountOfSeasons, LijktOp, LanguageCode, Rating) VALUES ('Title', [int AmountOfSeasons], 'LijktOp', 'LanguageCode', 'Rating')";
    Object[] arr = {title, 0, similarMedia, lang.getLangCode(), rating.getAgeCode()};

    if (database.executeSqlNoResult(qr, arr) == DataHandle.SQLResults.PASS) {
      for (HashMap<String, Object> map :
          database.executeSql(
              "SELECT SerieId FROM Serie WHERE Title=? AND AmountOfSeasons=0",
              new Object[] {title})) {

        new Serie(genre, lang, title, rating, (int) map.get("SerieId"), similarMedia);

        String qr2 = "INSERT INTO Koppeltabel_Serie_Genre (SerieId, GenreId) VALUES (?, ?)";
        Object[] arr2 = {map.get("SerieId"), genre.databaseId};

        database.executeSql(qr2, arr2);
      }
    }
  }

  public void createSeason(String serie, String title, int seasonNumber) {
    int serieId = Serie.getSerieByName(serie).databaseId;

    String qr = "INSERT INTO Season (SerieId, Title, SeasonNumber) VALUES (?, ?, ?)";
    Object[] arr = {serieId, title, seasonNumber};

    if (database.executeSqlNoResult(qr, arr) == DataHandle.SQLResults.PASS) {
      for (HashMap<String, Object> map :
          database.executeSql(
              "SELECT SeasonId FROM Season WHERE SerieId=? AND SeasonNumber=?",
              new Object[] {serieId, seasonNumber})) {
        new Serie.Season(
            Serie.getSerieByName(serie), title, seasonNumber, (int) map.get("SeasonId"));
      }
    }
  }

  public void createEpisode(
      String title, String serie, String season, Time duration, int episodeNumber) {

    int SeasonId = Serie.Season.getSeason(Serie.getSerieByName(serie), season).databaseId;

    String qr =
        "INSERT INTO Episode (SeasonId, Title, Duration, EpisodeNumber) VALUES (?, ?, ?, ?)";
    Object[] arr = {SeasonId, title, duration, episodeNumber};

    if (database.executeSqlNoResult(qr, arr) == DataHandle.SQLResults.PASS) {
      for (HashMap<String, Object> map :
          database.executeSql(
              "SELECT EpisodeId FROM Episode WHERE SeasonId=? AND EpisodeNumber=?",
              new Object[] {SeasonId, episodeNumber})) {
        new Serie.Episode(
            Serie.Season.getSeason(Serie.getSerieByName(serie), season),
            title,
            Serie.getSerieByName(serie),
            duration,
            episodeNumber,
            (int) map.get("EpisodeId"));
      }
    }
  }

  public void createFilm(
      String title,
      Time duration,
      String director,
      MediaCommons.Genre genre,
      MediaCommons.Language lang,
      MediaCommons.AgeRating rating,
      String similarMedia) {
    String qr =
        "INSERT INTO Film (Rating, LijktOp, LanguageCode, Title, Duration, Director) VALUES (?, ?, ?, ?, ?, ?)";
    Object[] arr = {
      rating.getMinimumAge(), similarMedia, lang.getLangCode(), title, duration, director
    };
    if (database.executeSqlNoResult(qr, arr) == DataHandle.SQLResults.PASS) {
      for (HashMap<String, Object> map :
          database.executeSql(
              "SELECT FilmId FROM Film WHERE Title=? AND Director=?",
              new Object[] {title, director})) {
        new Film(
            rating, genre, lang, title, duration, director, (int) map.get("FilmId"), similarMedia);

        String qr2 = "INSERT INTO Koppeltabel_GenreId_Film (FilmId, GenreId) VALUES (?, ?)";
        Object[] arr2 = {map.get("FilmId"), genre.databaseId};

        database.executeSqlNoResult(qr2, arr2);
      }
    }
  }

  public void createGenre(String name) {
    String qr = "INSERT INTO Genre (Genre) VALUES (?)";
    Object[] arr = {name};
    if (database.executeSqlNoResult(qr, arr) == DataHandle.SQLResults.PASS) {
      for (HashMap<String, Object> map :
          database.executeSql("SELECT GenreId FROM Genre WHERE Genre=?", new Object[] {name})) {
        new MediaCommons.Genre(name, (int) map.get("GenreId"));
      }
    }
  }

  public void createLanguage(String language, String langCode) {
    String qr = "INSERT INTO Language (LanguageCode, Language) VALUES (?, ?)";
    Object[] arr = {langCode, language};
    if (database.executeSqlNoResult(qr, arr) == DataHandle.SQLResults.PASS) {
      new MediaCommons.Language(langCode, language);
    }
  }

  public void createRating(String code, int age) {
    String qr = "INSERT INTO Rating (MPAA, Rating) VALUES (?, ?)";
    Object[] arr = {code, age};
    if (database.executeSqlNoResult(qr, arr) == DataHandle.SQLResults.PASS) {
      new MediaCommons.AgeRating(code, age);
    }
  }
}
