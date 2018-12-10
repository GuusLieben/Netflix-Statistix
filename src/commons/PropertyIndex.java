package com.netflix.commons;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

import static com.netflix.commons.Commons.exception;

public class PropertyIndex {

  private static Properties properties = new Properties();
  private static ThreadLocal<InputStream> inputStream = new ThreadLocal<>();

  public static String get(String property) {
    // Read the properties file
    inputStream.set(PropertyIndex.class.getClassLoader().getResourceAsStream("package.properties"));

    // Make sure we're not reading null
    Objects.requireNonNull(inputStream);

    // Assume none found if exception throws
    try {
      // Load the properties from the file
      properties.load(inputStream.get());
      // Grab the property requested
      return properties.getProperty(property);
    } catch (IOException e) {
      exception(e);
    }
    return null;
  }
}
