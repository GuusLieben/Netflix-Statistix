package com.netflix.commons;

import com.netflix.objects.*;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class Commons {

  public static final Logger logger = Logger.getLogger("Netflix");
  public static Map<String, String> users = new HashMap<>();
  protected static List<Episode> episodes = new ArrayList<>();
  public static List<Serie> series = new ArrayList<>();
  public static List<Film> films = new ArrayList<>();
  public static List<String> filmTitles = new ArrayList<>();
    public static List<String> serieTitles = new ArrayList<>();
  public static List<Season> seasons = new ArrayList<>();
  protected static List<Genre> genres = new ArrayList<>();
  protected static List<Language> langs = new ArrayList<>();
  public static Profile currentUser;

  // Exception handle
  public static void exception(Exception ex) {
    logger.severe(ex.getMessage());
    logger.severe("Suspected cause : " + ex.getCause());
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
