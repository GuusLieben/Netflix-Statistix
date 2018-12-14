package com.netflix.commons;

import com.netflix.objects.*;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.logging.Logger;

public class Commons {

  public static final Logger logger = Logger.getLogger("Netflix");
  public static Set<Language> langs = new HashSet<>();
  public static Map<String, String> users = new HashMap<>();
  public static Set<Serie> series = new HashSet<>();
  public static Set<Film> films = new HashSet<>();
  public static Set<String> filmTitles = new HashSet<>();
  public static Set<String> serieTitles = new HashSet<>();
  public static Set<Season> seasons = new HashSet<>();
  public static Profile currentUser;
  public static Set<Episode> episodes = new HashSet<>();
  public static Set<AgeRating> ratings = new HashSet<>();
  public static Set<Genre> genres = new HashSet<>();

  // Exception handle
  public static void exception(Exception ex) {
    logger.severe(ex.getMessage());
    logger.severe("Suspected cause : " + ex.getCause());
    logger.severe(Arrays.toString(ex.getStackTrace()));
  }

  public static String hashMD5(String string) {
    MessageDigest md = null;

    String hash = "";
    try {
      md = MessageDigest.getInstance("MD5");
      md.update(string.getBytes());
      hash = DatatypeConverter.printHexBinary(md.digest());
    } catch (NoSuchAlgorithmException ex) {
      Commons.exception(ex);
    }

    return hash.toLowerCase();
  }
}
