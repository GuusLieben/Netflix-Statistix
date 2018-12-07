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
    inputStream.set(PropertyIndex.class.getClassLoader().getResourceAsStream("package.properties"));
    Objects.requireNonNull(inputStream);
    // Assume none found if exception throws
    try {
      properties.load(inputStream.get());
      return properties.getProperty(property);
    } catch (IOException e) {
      exception(e);
    }
    return null;
  }
}
