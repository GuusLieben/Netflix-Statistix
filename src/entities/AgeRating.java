package com.netflix.entities;

import com.netflix.*;
import com.netflix.commons.*;
import com.netflix.entities.abstracts.Entity;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class AgeRating extends Entity {

  public static Set<AgeRating> ratings = new HashSet<>();
  private String ageCode;
  private int minimumAge;

  public AgeRating(String ageCode, int minimumAge) {
    this.ageCode = ageCode;
    this.minimumAge = minimumAge;
    ratings.add(this);
  }

  public static AgeRating getRating(String ageCode, int minAge) {
    // Used for the DatabaseHandle to check if one already exists
    for (AgeRating rating : ratings) if (rating.getAgeCode().equals(ageCode)) return rating;
    return null;
  }

  public static AgeRating getByAge(int age) {
    for (AgeRating rating : ratings) if (rating.getMinimumAge() == age) return rating;
    return null;
  }

  public String getAgeCode() {
    return ageCode;
  }

  public int getMinimumAge() {
    return minimumAge;
  }

  public static void getFromDatabase() {
    if (Netflix.database.connectDatabase()) {
      String sqlQuery = "SELECT MPAA, Rating FROM Rating";
      ResultSet results = null;
      try (Statement statement = Netflix.database.connection.createStatement()) {
        // Make sure the results are passed
        results = statement.executeQuery(sqlQuery);
        System.out.println("Query passed : " + results.toString());
        while (results.next())
          new AgeRating(results.getString("MPAA"), 1); // TODO : Fix minimumAge in database!

      } catch (SQLException ex) {
        Commons.exception(ex);
        System.out.println("Query did not pass");
      }
      Netflix.database.disconnectDatabase();
    }
  }

  @Override
  public String toString() {
    // Dutch translations
    return getAgeCode() + " (" + getMinimumAge() + " jaar en ouder)";
  }
}
