package com.netflix;

import com.netflix.commons.Commons;
import com.netflix.entities.Account;
import com.netflix.handles.PropertiesHandle;
import org.junit.jupiter.api.Test;

import java.io.Reader;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.*;

class FunctionalityTests {

    /*
     All tests are constructed using the AAA format (Arrange, Act, Assert).
     */

  @Test
  void HashPassWithCorrectMD5Hash() {
    String expectedHash = "1a1dc91c907325c69271ddf0c944bc72"; // MD5 Hash for 'pass'
    String actualHash = Commons.hashMD5("pass");
    assertEquals(expectedHash, actualHash);
  }

  @Test
  void HashPassWithIncorrectMD5Hash() {
    String expectedHash = "d41d8cd98f00b204e9800998ecf8427e"; // MD5 Hash for null
    String actualHash = Commons.hashMD5("pass");
    assertNotEquals(expectedHash, actualHash);
  }

  @Test
  void testPropertyReadingWithCorrectValue() {
    String expected = "1";
    Reader stream = new StringReader("value=1");
    String actual = PropertiesHandle.parseProperties(stream, "value");
    assertEquals(expected, actual);
  }

  @Test
  void testPropertyReadingWithIncorrectValue() {
    String expected = "1";
    Reader stream = new StringReader("value=2");
    String actual = PropertiesHandle.parseProperties(stream, "value");
    assertNotEquals(expected, actual);
  }

  @Test
  void ProperEmailRegexReturnsTrue() {
    String email = "bob@example.com";
    boolean isvalid = Account.emailIsValid(email);
    assertTrue(isvalid);
  }

  @Test
  void InproperEmailRegexReturnsFalse() {
    String email = "bob#example,com";
    boolean isvalid = Account.emailIsValid(email);
    assertFalse(isvalid);
  }
}
