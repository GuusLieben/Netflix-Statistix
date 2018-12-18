package com.netflix.commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class CommonsTest {

  @Test
  void HashPassWithCorrectMD5Hash() {
      String expectedHash = "1a1dc91c907325c69271ddf0c944bc72";
      String actualHash = Commons.hashMD5("pass");
      assertEquals(expectedHash, actualHash);
  }

    @Test
    void HashPassWithIncorrectMD5Hash() {
        String expectedHash = "d41d8cd98f00b204e9800998ecf8427e";
        String actualHash = Commons.hashMD5("pass");
        assertNotEquals(expectedHash, actualHash);
    }
}