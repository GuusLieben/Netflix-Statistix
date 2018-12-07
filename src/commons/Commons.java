package com.netflix.commons;

import com.netflix.objects.*;

import java.util.*;

import static java.lang.System.*;

public class Commons {

  public static ArrayList<Serie> series = new ArrayList<>();
  public HashMap<Season, Serie> seasons = new HashMap<>();
  public static HashMap<Episode, Season> episodes = new HashMap<>();

  public static ArrayList<Film> films = new ArrayList<>();

  public ArrayList<Genre> genres = new ArrayList<>();
  public ArrayList<Language> langs = new ArrayList<>();

  public static void exception(Exception ex) {
    out.println(ex.getMessage());
    out.println(ex.getCause());
    out.println(Arrays.toString(ex.getStackTrace()));
  }
}
