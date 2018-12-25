package com.netflix.entities;

import com.netflix.entities.abstracts.Entity;

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
    return new AgeRating(ageCode, minAge);
  }

  public String getAgeCode() {
    return ageCode;
  }

  public int getMinimumAge() {
    return minimumAge;
  }

  @Override
  public String toString() {
    // Dutch translations
    return getAgeCode() + " (" + getMinimumAge() + " jaar en ouder)";
  }
}
