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
    for (HashMap<String, Object> map :
        Netflix.database.executeSql("SELECT MPAA, Rating FROM Rating")) {
      new AgeRating((String) map.get("MPAA"), (int) map.get("Rating"));
    }
  }

  @Override
  public String toString() {
    // Dutch translations
    return getAgeCode() + " (" + getMinimumAge() + " jaar en ouder)";
  }
}
