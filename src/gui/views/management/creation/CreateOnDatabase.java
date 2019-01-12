package com.netflix.gui.views.management.creation;

import com.netflix.commons.Commons;
import com.netflix.entities.*;
import com.netflix.handles.SQLResults;

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

    if (database.executeSqlNoResult(qr, arr) == SQLResults.PASS) {
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
          MediaCommons.Genre genre, MediaCommons.Language lang, String title, MediaCommons.AgeRating rating, String similarMedia) {

    String qr =
        "INSERT INTO Serie (Title, AmountOfSeasons, LijktOp, LanguageCode, Rating) VALUES ('Title', [int AmountOfSeasons], 'LijktOp', 'LanguageCode', 'Rating')";
    Object[] arr = {title, 0, similarMedia, lang.getLangCode(), rating.getAgeCode()};

    if (database.executeSqlNoResult(qr, arr) == SQLResults.PASS) {
      for (HashMap<String, Object> map :
          database.executeSql("SELECT SerieId FROM Serie WHERE Title=? AND AmountOfSeasons=0")) {

        new Serie(genre, lang, title, rating, (int) map.get("SerieId"), similarMedia);

        String qr2 = "INSERT INTO Koppeltabel_Serie_Genre (SerieId, GenreId) VALUES (?, ?)";
        Object[] arr2 = {map.get("SerieId"), genre.databaseId};

        database.executeSql(qr2, arr2);
      }
    }
  }

  public void createSeason() {
    String qr = "";
    Object[] arr = {};
    if (database.executeSqlNoResult(qr, arr) == SQLResults.PASS) {
      for (HashMap<String, Object> map :
          database.executeSql("SELECT id FROM entity WHERE something=?", new Object[] {})) {
        throw new IllegalAccessError("2");
      }
    }
  }

  public void createEpisode() {
    String qr = "";
    Object[] arr = {};
    if (database.executeSqlNoResult(qr, arr) == SQLResults.PASS) {
      for (HashMap<String, Object> map :
          database.executeSql("SELECT id FROM entity WHERE something=?", new Object[] {})) {
        throw new IllegalAccessError("3");
      }
    }
  }

  public void createMovie() {
    String qr = "";
    Object[] arr = {};
    if (database.executeSqlNoResult(qr, arr) == SQLResults.PASS) {
      for (HashMap<String, Object> map :
          database.executeSql("SELECT id FROM entity WHERE something=?", new Object[] {})) {
        throw new IllegalAccessError("4");
      }
    }
  }
}
