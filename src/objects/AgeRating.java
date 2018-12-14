/*
 * Copyright © 2018. Guus Lieben.
 * All rights reserved.
 */

package com.netflix.objects;

import com.netflix.commons.Commons;

public class AgeRating {

  private String ageCode;
  private int minimumAge;

  public AgeRating(String ageCode, int minimumAge) {
    this.ageCode = ageCode;
    this.minimumAge = minimumAge;
    Commons.ratings.add(this);
  }

  public String getAgeCode() {
    return ageCode;
  }

  public int getMinimumAge() {
    return minimumAge;
  }

    @Override
    public String toString() {
        return getAgeCode() + " (" + getMinimumAge() + " and up)";
    }
}
