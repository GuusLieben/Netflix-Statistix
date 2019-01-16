package com.netflix.handles;

import com.netflix.commons.Commons;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Properties;

import static com.netflix.commons.Commons.exception;

public class PropertiesHandle {

  private static Properties properties = new Properties();
  private static ThreadLocal<InputStream> inputStream = new ThreadLocal<>();

  public static String get(String property) {
    Commons.logger.config(String.format("Requesting property '%s' from package.properties", property));

    // Read the properties file
    inputStream.set(
        PropertiesHandle.class.getClassLoader().getResourceAsStream("package.properties"));

    Reader stream = new InputStreamReader(inputStream.get());

    // Assume none found if exception throws
    return parseProperties(stream, property);
  }

  public static String parseProperties(Reader stream, String property) {
    try {
      // Load the properties from the stream
      properties.load(stream);
      // Grab the property requested
      return properties.getProperty(property);
    } catch (IOException e) {
      exception(e);
      return null;
    }
  }
}
