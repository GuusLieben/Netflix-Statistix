package com.netflix.commons;

import com.netflix.objects.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Commons {

  private static final Logger logger = Logger.getLogger(Commons.class.getName());

  public static Map<String, String> users = new HashMap<>();
  protected static List<Episode> episodes = new ArrayList<>();
  // Holds data from the database
  protected static List<Serie> series = new ArrayList<>();
  protected static List<Film> films = new ArrayList<>();
  protected static List<Season> seasons = new ArrayList<>();
  protected static List<Genre> genres = new ArrayList<>();
  protected static List<Language> langs = new ArrayList<>();

  // Exception handle
  public static void exception(Exception ex) {
    logger.log(Level.SEVERE, ex.getMessage());
    logger.log(Level.SEVERE, "Suspected cause : {0}", ex.getCause());
  }
}
