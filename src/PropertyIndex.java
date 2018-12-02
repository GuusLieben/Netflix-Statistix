package com.netflix;

import java.io.*;
import java.util.*;

import static com.netflix.Commons.*;

class PropertyIndex {
    private static Properties properties = new Properties();
    private static ThreadLocal<InputStream> inputStream = new ThreadLocal<>();

    static String get(String property) {
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