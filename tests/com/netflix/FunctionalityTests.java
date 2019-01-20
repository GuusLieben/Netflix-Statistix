package com.netflix;

import com.netflix.commons.*;
import com.netflix.entities.Account;
import org.junit.jupiter.api.Test;

import java.io.Reader;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.*;

class FunctionalityTests {

  @Test
  void HashPassWithCorrectSHA256Hash() {
    assertEquals(
        "d74ff0ee8da3b9806b18c877dbf29bbde50b5bd8e4dad7a3a725000feb82e8f1",
        Commons.hashSHA256("pass"),
        "Returned SHA256 hash for 'pass' does not match expected hash");
  }

  @Test
  void HashPassWithIncorrectSHA256Hash() {
    assertNotEquals(
        "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855",
        Commons.hashSHA256("pass"),
        "Returned SHA256 hash for 'pass' returned hash for null");
  }

  @Test
  void testPropertyReadingWithCorrectValue() {
    Reader stream = new StringReader("value=true");
    String actual = DataHandle.parseProperties(stream, "value");

    assertEquals("true", actual, "Returned value does not match expected value");
  }

  @Test
  void testPropertyReadingWithIncorrectValue() {
    Reader stream = new StringReader("value=true");
    String actual = DataHandle.parseProperties(stream, "value");

    assertNotEquals(null, actual, "Value returned is incorrect");
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
