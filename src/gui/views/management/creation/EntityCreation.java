package com.netflix.gui.views.management.creation;

import com.netflix.*;
import com.netflix.entities.*;
import com.netflix.handles.*;

import java.util.*;

public class EntityCreation {

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
    Object[] arr = {isAdmin, email, street, houseNumber, addition, city, password};

    if (Netflix.database.executeSqlNoResult(qr, arr) == SQLResults.PASS) {
      for (HashMap<String, Object> map :
          Netflix.database.executeSql(
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

  public void createSerie() {
      String qr = "";
      Object[] arr = {};
      if (Netflix.database.executeSqlNoResult(qr, arr) == SQLResults.PASS) {
          for (HashMap<String, Object> map : Netflix.database.executeSql("SELECT id FROM entity WHERE something=?", new Object[] {})) {

          }
      }
  }

  public void createSeason() {
      String qr = "";
      Object[] arr = {};
      if (Netflix.database.executeSqlNoResult(qr, arr) == SQLResults.PASS) {
          for (HashMap<String, Object> map : Netflix.database.executeSql("SELECT id FROM entity WHERE something=?", new Object[] {})) {

          }
      }
  }

  public void createEpisode() {
      String qr = "";
      Object[] arr = {};
      if (Netflix.database.executeSqlNoResult(qr, arr) == SQLResults.PASS) {
          for (HashMap<String, Object> map : Netflix.database.executeSql("SELECT id FROM entity WHERE something=?", new Object[] {})) {

          }
      }
  }

  public void createMovie() {
      String qr = "";
      Object[] arr = {};
      if (Netflix.database.executeSqlNoResult(qr, arr) == SQLResults.PASS) {
          for (HashMap<String, Object> map : Netflix.database.executeSql("SELECT id FROM entity WHERE something=?", new Object[] {})) {

          }
      }
  }
}
