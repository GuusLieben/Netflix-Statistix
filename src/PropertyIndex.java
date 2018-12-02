package com.netflix;

import java.io.*;
import java.util.*;

import static com.netflix.Commons.*;

public class PropertyIndex {
    private Properties properties;
    private ThreadLocal<InputStream> inputStream;
    private String file;

    public PropertyIndex(String filename) {
        file = filename;
        inputStream = new ThreadLocal<>();
        inputStream.set(getClass().getClassLoader().getResourceAsStream(file));
        properties = new Properties();
    }

    public String get(String property) {
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

    public void put(String key, String value) {
        try {
            FileOutputStream out = new FileOutputStream("resources/" + file);
            properties.setProperty(key, value);
            properties.store(out, null);
            out.close();
        } catch (IOException e) {
            exception(e);
        }
    }

    public HashMap<String, Object> getHashMap() {
        HashMap<String, Object> propertyHash = new HashMap<>();
        try {
            properties.load(inputStream.get());
            properties.forEach((key, value) -> propertyHash.put((String) key, value));
        } catch (IOException e) {
            exception(e);
        }
        return propertyHash;
    }

    public ArrayList<String> getKeys() {
        ArrayList<String> keyList = new ArrayList<>();
        try {
            properties.load(inputStream.get());
            properties.forEach((key, value) -> keyList.add((String) key));
        } catch (IOException e) {
            exception(e);
        }
        return keyList;
    }

    public ArrayList<Object> getValues() {
        ArrayList<Object> valueList = new ArrayList<>();
        try {
            properties.load(inputStream.get());
            properties.forEach((key, value) -> valueList.add(value));
        } catch (IOException e) {
            exception(e);
        }
        return valueList;
    }
}