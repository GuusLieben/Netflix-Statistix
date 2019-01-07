package com.netflix.entities;

import com.netflix.*;
import com.netflix.entities.abstracts.*;
import com.netflix.gui.*;
import com.netflix.handles.*;

import javax.swing.*;
import java.sql.Date;
import java.time.*;
import java.util.*;
import java.util.stream.*;

public class Profile extends Entity {

  public static Profile currentUser;
  private static Set<Profile> profiles = new HashSet<>();
  private Account account;
  private String name;
  private Set<Episode> episodesWatched;
  private Set<Film> filmsWatched;
  private Date birthdate;

  public Profile(Account account, String name, Date birthday, int databaseId) {
    super.databaseId = databaseId;
    if (account.getProfiles().size() < 5) { // Make sure there are less than 5 profiles attached
      this.account = account;
      this.name = name;
      this.birthdate = birthday;
      episodesWatched = new HashSet<>();
      filmsWatched = new HashSet<>();
      account.addProfile(this);
      profiles.add(this);
    } else {
      // If the account already has 5 (or more) profiles, show a popup
      JOptionPane.showMessageDialog(
          NetflixFrame.frame,
          String.format("Dit account heeft al %d profielen", account.getProfiles().size()),
          null,
          JOptionPane.ERROR_MESSAGE);
    }
  }

  // Getters
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
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(birthdate);

    LocalDate date =
        LocalDate.of(
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH));

    return Period.between(date, LocalDate.now()).getYears();
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

  // View media Film
  public void viewFilm(Film film) {
    // Seperate method as viewfilm is also invoked when pulling from the database
    viewFilmNoDB(film);

    String qr = "INSERT INTO WatchedFilms (FilmId, UserId, FilmsWatched) VALUES (?, ?, ?)";
    Object[] arr = {film.databaseId, databaseId, filmsWatched.size()};

    if (Netflix.database.executeSqlNoResult(qr, arr) == SQLResults.PASS)
      JOptionPane.showMessageDialog(NetflixFrame.frame, "Succes!");
  }

  // View media episode
  public void viewEpisode(Episode episode) {
    // Seperate method as viewepisode is also invoked when pulling from the database
    viewEpisodeNoDB(episode);

    String qr = "INSERT INTO WatchedEpisodes (EpisodeId, UserId, EpisodesWatched) VALUES (?, ?, ?)";

    Object[] arr = {episode.databaseId, databaseId, episodesWatched.size()};

    if (Netflix.database.executeSqlNoResult(qr, arr) == SQLResults.PASS)
      JOptionPane.showMessageDialog(NetflixFrame.frame, "Succes!");
  }

  // Get profile by database id
  public static Profile getByDbId(int id) {
    return profiles.stream().filter(ent -> ent.databaseId == id).findFirst().orElse(null);
  }

  // Get profiles from database
  @SuppressWarnings("deprecation")
  public static void getFromDatabase() {
    for (HashMap<String, Object> map :
        Netflix.database.executeSql(
            "SELECT UserId, AccountId, Name, EpisodesWatched, FilmsWatched, Birthday FROM Users")) {

      new Profile(
          Account.getByDbId((int) map.get("AccountId")),
          (String) map.get("Name"),
          (Date) map.get("Birthday"),
          (int) map.get("UserId"));
    }
  }

  // View media episode
  public void viewEpisodeNoDB(Episode epi) {
    // Statistics for the parent Serie
    epi.getSerie().setWatchedBy(this);
    epi.getSerie().watchedEpisodes++;
    // Statistics for the profile
    episodesWatched.add(epi);
  }

  // View media film
  public void viewFilmNoDB(Film film) {
    // Statistics for the film
    film.setWatchedBy(this);
    // Statistics for the profile
    filmsWatched.add(film);
  }

  // Remove viewed episode (unwatch)
  public void unviewEpisode(Episode epi) {
    if (epi.getSerie().watchedEpisodes == 1) epi.getSerie().removeWatchedBy(this);
    epi.getSerie().watchedEpisodes--;
    episodesWatched.remove(epi);
  }

  // Remove viewed film (unwatch)
  public void unviewFilm(Film film) {
    film.removeWatchedBy(this);
    filmsWatched.remove(film);
  }
}
