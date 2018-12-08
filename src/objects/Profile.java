package com.netflix.objects;

import com.netflix.gui.NetflixGUI;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Profile {

  private Account account;
  private String name;
  private ArrayList<Episode> episodesWatched;
  private ArrayList<Film> filmsWatched;

  public Profile(Account account, String name) {
    if (!(account.getProfiles().size() >= 5)) {
      this.account = account;
      this.name = name;
      episodesWatched = new ArrayList<>();
      filmsWatched = new ArrayList<>();
      account.addProfile(this);
    } else
      JOptionPane.showMessageDialog(
          NetflixGUI.frame,
          "Profile limit reached for account '"
              + account
              + "' ("
              + account.getProfiles().size()
              + ")",
          null,
          JOptionPane.ERROR_MESSAGE);
  }

  public Account getAccount() {
    return account;
  }

  public Set<Serie> getSeriesWatched() {
    Set<Serie> serieSet = new HashSet<>();

    for (Episode episode : episodesWatched) serieSet.add(episode.getSeason().getSerie());

    return serieSet;
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

  public void viewEpisode(Episode episode) {
    episodesWatched.add(episode);
  }
}
