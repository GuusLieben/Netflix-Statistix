package com.netflix.entities;

import com.netflix.*;
import com.netflix.entities.abstracts.*;
import com.netflix.gui.*;

import javax.swing.*;
import java.time.*;
import java.util.*;
import java.util.stream.*;

public class Profile extends Entity {

  public static Profile currentUser;
  private Account account;
  private String name;
  private Set<Episode> episodesWatched;
  private Set<Film> filmsWatched;
  private int age;

  public Profile(Account account, String name, int age, int databaseId) {
    super.databaseId = databaseId;
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
          NetflixFrame.frame,
          "Dit account heeft al " + account.getProfiles().size() + " profielen",
          null,
          JOptionPane.ERROR_MESSAGE);
    }
  }

  public Account getAccount() {
    return account;
  }

  public Set<Serie> getSeriesWatched() {
    return episodesWatched.stream().map(episode -> episode.getSerie()).collect(Collectors.toSet());
  }

  public Set<MediaObject> getMediaWatched() {
    Set<MediaObject> mediaWatched = new HashSet<>();
    mediaWatched.addAll(filmsWatched);
    for (Episode epi : episodesWatched) mediaWatched.add(epi.getSerie());
    return mediaWatched;
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
    film.setWatchedBy(this);
    filmsWatched.add(film);
  }

  public void viewEpisode(Episode episode) {
    episode.getSerie().setWatchedBy(this);
    episodesWatched.add(episode);
  }

  @SuppressWarnings("deprecation")
  public static void getFromDatabase() {
    for (HashMap<String, Object> map :
        Netflix.database.executeSql(
            "SELECT UserId, AccountId, Name, EpisodesWatched, FilmsWatched, Birthday FROM Users")) {
      Date birthdate = (Date) map.get("Birthday");
      LocalDate date = LocalDate.of(birthdate.getYear(), birthdate.getMonth(), birthdate.getDay());

      new Profile(
          Account.getByDbId((int) map.get("AccountId")),
          (String) map.get("Name"),
          (int) Period.between(date, LocalDate.now()).getYears(),
          (int) map.get("UserId"));
    }
  }
}