package com.netflix.commons;

import com.netflix.objects.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static java.lang.System.out;

public class Commons {

  // Holds data from the database
  public static ArrayList<Serie> series = new ArrayList<>();
  public static ArrayList<Episode> episodes = new ArrayList<>();
  public static ArrayList<Film> films = new ArrayList<>();
  public static ArrayList<Season> seasons = new ArrayList<>();
  public static ArrayList<Genre> genres = new ArrayList<>();
  public static ArrayList<Language> langs = new ArrayList<>();
  public static HashMap<String, String> users = new HashMap<>();

  // Exception handle
  public static void exception(Exception ex) {
    out.println(ex.getMessage());
    out.println(ex.getCause());
    out.println(Arrays.toString(ex.getStackTrace()));
  }
}
