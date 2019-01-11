package com.netflix.entities;

import com.netflix.entities.abstracts.Entity;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static com.netflix.Netflix.database;

public class AgeRating extends Entity {

  public static Set<AgeRating> ratings = new HashSet<>();
  private String ageCode;
  private int minimumAge;

  public AgeRating(String ageCode, int minimumAge) {
    this.ageCode = ageCode;
    this.minimumAge = minimumAge;
    ratings.add(this);
  }

  // Get a rating by the age, used in Database handles
  public static AgeRating getByAge(int age) {
    for (AgeRating rating : ratings) if (rating.getMinimumAge() == age) return rating;
    return null;
  }

  // Get all ratings from the database
  public static void getFromDatabase() {
    for (HashMap<String, Object> map : database.executeSql("SELECT MPAA, Rating FROM Rating")) {
      new AgeRating((String) map.get("MPAA"), (int) map.get("Rating"));
    }
  }

  // Getters
  public String getAgeCode() {
    return ageCode;
  }

  public int getMinimumAge() {
    return minimumAge;
  }

  // toString used for the MediaObject ObjectView
  @Override
  public String toString() {
    // Dutch translations
    return String.format("%s (%d jaar en ouder)", getAgeCode(), getMinimumAge());
  }
}
