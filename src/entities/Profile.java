package com.netflix.entities;

import com.netflix.gui.NetflixGUI;

import javax.swing.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Profile {

  public static Profile currentUser;
  private Account account;
  private String name;
  private Set<Episode> episodesWatched;
  private Set<Film> filmsWatched;
  private int age;

  public Profile(Account account, String name, int age) {
    if (account.getProfiles().size() < 5) { // Make sure there are less than 5 profiles attached
      this.account = account;
      this.name = name;
      this.age = age;
      episodesWatched = new HashSet<>();
      filmsWatched = new HashSet<>();
      account.addProfile(this);
    } else {
      // If the account already has 5 (or more) profiles, show a popup
      JOptionPane.showMessageDialog(
          NetflixGUI.frame,
          "Dit account heeft al " + account.getProfiles().size() + " profielen",
          null,
          JOptionPane.ERROR_MESSAGE);
    }
  }

  public Account getAccount() {
    return account;
  }

  public Set<Serie> getSeriesWatched() {
    return episodesWatched
        .stream()
        .map(episode -> episode.getSeason().getSerie())
        .collect(Collectors.toSet());
  }

  public int getAge() {
    return age;
  }

  public String getName() {
    return name;
  }

  public Set<Episode> getEpisodesWatched() {
    return episodesWatched;
  }

  public Set<Film> getFilmsWatched() {
    return filmsWatched;
  }

  public void viewFilm(Film film) {
    filmsWatched.add(film);
  }

  public void viewEpisode(Episode episode) {
    episodesWatched.add(episode);
  }
}
