package com.netflix.entities;

import com.netflix.commons.Commons;

public class AgeRating {

  private String ageCode;
  private int minimumAge;

  public AgeRating(String ageCode, int minimumAge) {
    this.ageCode = ageCode;
    this.minimumAge = minimumAge;
    Commons.ratings.add(this);
  }

  public static AgeRating getRating(String ageCode, int minAge) {
    for (AgeRating rating : Commons.ratings) if (rating.getAgeCode().equals(ageCode)) return rating;
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
    return getAgeCode() + " (" + getMinimumAge() + " jaar en ouder)";
  }
}
