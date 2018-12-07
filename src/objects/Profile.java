package com.netflix.objects;

import java.util.ArrayList;

public class Profile {

  private Account account;
  private String name;
  private ArrayList<Episode> episodesWatched;
  private ArrayList<Film> filmsWatched;

  public Profile(Account account, String name) {
    this.account = account;
    this.name = name;
    episodesWatched = new ArrayList<>();
    filmsWatched = new ArrayList<>();
    account.addProfile(this);
  }

  public Account getAccount() {
    return account;
  }

  public String getName() {
    return name;
  }

  public ArrayList<Episode> getEpisodesWatched() {
    return episodesWatched;
  }

  public ArrayList<Film> getFilmsWatched() {
    return filmsWatched;
  }

  public void viewFilm(Film film) {
    filmsWatched.add(film);
  }
}
