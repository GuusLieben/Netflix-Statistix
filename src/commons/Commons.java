package com.netflix.commons;

import java.awt.*;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import static javax.xml.bind.DatatypeConverter.printHexBinary;

public class Commons {

  // Instantiate a logger for the entire project
  public static final Logger logger = Logger.getLogger("Netflix");

  static {
    try {
      String dateTime =
          String.format("d.%s-t.%s", LocalDate.now().toString(), LocalTime.now().toString());
      dateTime.replace(" ", ".");
      FileHandler handler = new FileHandler("logs/log-" + dateTime + ".log", true);
      Commons.logger.addHandler(handler);
    } catch (IOException e) {
      Commons.exception(e);
    }
  }

  // Exception handle
  public static void exception(Exception ex) {
    // Print the error message
    logger.severe(ex.getMessage());
    // Print the suspected cause
    logger.severe("Suspected cause : " + ex.getCause());
    // Print the stacktrace
    logger.severe(Arrays.toString(ex.getStackTrace()));
  }

  public static String hashSHA256(String password) {
    try {
      // Set digest to use SHA-256
      MessageDigest md = MessageDigest.getInstance("SHA-256");
      // Set digest to convert given password
      md.update(password.getBytes());

      // Return it as a string
      return printHexBinary(md.digest()).toLowerCase();
    } catch (NoSuchAlgorithmException ex) {
      // In case SHA-256 is somehow not found
      Commons.exception(ex);
    }
    return null;
  }

  public static void clearPane(Container con) {
    // Clears a container
    con.removeAll();
    con.repaint();
    con.revalidate();
  }
}
