package com.netflix;

import com.netflix.commons.Commons;
import com.netflix.entities.Account;
import com.netflix.handles.PropertiesHandle;
import org.junit.jupiter.api.Test;

import java.io.Reader;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.*;

class FunctionalityTests {

  @Test
  void HashPassWithCorrectMD5Hash() {
    assertEquals(
        "1a1dc91c907325c69271ddf0c944bc72",
        Commons.hashMD5("pass"),
        "Returned md5 hash for 'pass' does not match expected hash");
  }

  @Test
  void HashPassWithIncorrectMD5Hash() {
    assertNotEquals(
        "d41d8cd98f00b204e9800998ecf8427e",
        Commons.hashMD5("pass"),
        "Returned md5 hash for 'pass' returned hash for null");
  }

  @Test
  void testPropertyReadingWithCorrectValue() {
    Reader stream = new StringReader("value=true");
    String actual = PropertiesHandle.parseProperties(stream, "value");

    assertEquals("true", actual, "Returned value does not match expected value");
  }

  @Test
  void testPropertyReadingWithIncorrectValue() {
    Reader stream = new StringReader("value=false");
    String actual = PropertiesHandle.parseProperties(stream, "value");

    assertNotEquals("true", actual, "Value returned is incorrect");
  }

  @Test
  void ProperEmailRegexReturnsTrue() {
    assertTrue(
        Account.emailIsValid("bob@example.com"),
        "Returned boolean marked correct email as invalid");
  }

  @Test
  void InproperEmailRegexReturnsFalse() {
    assertFalse(
        Account.emailIsValid("bob#example,com"),
        "Returned boolean marked incorrect email as valid");
  }
}
