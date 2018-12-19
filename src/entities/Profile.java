package com.netflix.entities;

import com.netflix.gui.NetflixGUI;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Profile {

  private Account account;
  private String name;
  private ArrayList<Episode> episodesWatched;
  private ArrayList<Film> filmsWatched;
  private int age;

  public Profile(Account account, String name, int age) {
    if (account.getProfiles().size() < 5) { // Make sure there are less than 5 profiles attached
      this.account = account;
      this.name = name;
      this.age = age;
      episodesWatched = new ArrayList<>();
      filmsWatched = new ArrayList<>();
      account.addProfile(this);
    } else {
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

  public String getName() {
    return name;
  }

  public List<Episode> getEpisodesWatched() {
    return episodesWatched;
  }

  public List<Film> getFilmsWatched() {
    return filmsWatched;
  }

  public void viewFilm(Film film) {
    filmsWatched.add(film);
  }

  public void viewEpisode(Episode episode) {
    episodesWatched.add(episode);
  }
}
