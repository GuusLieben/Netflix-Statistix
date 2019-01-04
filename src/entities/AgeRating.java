package com.netflix.entities;

import com.netflix.*;
import com.netflix.entities.abstracts.*;

import java.util.*;

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

  // Getters
  public String getAgeCode() {
    return ageCode;
  }

  public int getMinimumAge() {
    return minimumAge;
  }

  // Get all ratings from the database
  public static void getFromDatabase() {
    for (HashMap<String, Object> map :
        Netflix.database.executeSql("SELECT MPAA, Rating FROM Rating")) {
      new AgeRating((String) map.get("MPAA"), (int) map.get("Rating"));
    }
  }

  // toString used for the MediaObject ObjectView
  @Override
  public String toString() {
    // Dutch translations
    return String.format("%s (%d jaar en ouder)", getAgeCode(), getMinimumAge());
  }
}
