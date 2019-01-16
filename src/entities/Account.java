package com.netflix.entities;

import com.netflix.commons.*;
import com.netflix.gui.NetflixFrame;

import javax.swing.JOptionPane;
import java.sql.Date;
import java.time.*;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.*;

import static com.netflix.Netflix.database;

public class Account extends Entity {

  // Stores current account
  public static Account currentAccount;
  // Stores all accounts loaded from the database
  public static final Set<Account> accounts = new HashSet<>();

  // Object attributes
  private boolean isAdmin;
  private Set<Profile> profiles;
  private String email;
  private String street;
  private int houseNumber;
  private String addition;
  private String city;
  private String password;

  public Account(
      int databaseId,
      boolean isAdmin,
      String email,
      String street,
      int houseNumber,
      String addition,
      String city,
      String password) {
    super(email, null);
    // If the email check is valid, create the object, store the user
    if (emailIsValid(email)) {
      this.databaseId = databaseId;
      this.isAdmin = isAdmin;
      this.email = email;
      this.street = street;
      this.houseNumber = houseNumber;
      this.addition = addition;
      this.city = city;
      // Password is hashed here
      this.password = Commons.hashSHA256(password);
      // HashSet so each profile is unique
      profiles = new HashSet<>();
      // Add it to the Set
      accounts.add(this);

    } else {
      // If the email wasn't valid, show a popup and don't create the object
      JOptionPane.showMessageDialog(
          NetflixFrame.frame,
          String.format("E-mail is incorrect : %s", email),
          null,
          JOptionPane.ERROR_MESSAGE);
    }
  }

  public static boolean emailIsValid(String email) {
    // Create the Regex pattern
    String emailRegex =
        "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    // Compile the Regex pattern
    Pattern pat = Pattern.compile(emailRegex);
    // If the email is null, it probably won't be valid, false
    if (email == null) return false;
    // Return whether or not the email matches the pattern
    return pat.matcher(email).matches();
  }

  public static Account getUserByEmail(String email) {
    // For all accounts, check their email, if it's equal return the account attached
    for (Account account : accounts) if (email.equals(account.getEmail())) return account;
    // Else return null
    return null;
  }

  /*
   * Getters and setters for all used attributes
   */
  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public int getHouseNumber() {
    return houseNumber;
  }

  public void setHouseNumber(int houseNumber) {
    this.houseNumber = houseNumber;
  }

  public String getAddition() {
    return addition;
  }

  public void setAddition(String addition) {
    this.addition = addition;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public boolean isAdmin() {
    return isAdmin;
  }

  public void setAdmin(boolean admin) {
    isAdmin = admin;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getLocation() {
    return String.format("%s %d%s, %s", street, houseNumber, addition, city);
  }

  public String getPassword() {
    return password;
  }

  public Set<Profile> getProfiles() {
    return profiles;
  }

  // Add a profile to the account, the >5 check is in the profile constructor
  public void addProfile(Profile profile) {
    profiles.add(profile);
  }

  // Get an account by its Database ID, this is always unique
  public static Account getByDbId(int id) {
    return accounts.stream().filter(ent -> ent.databaseId == id).findFirst().orElse(null);
  }

  // Get all accounts from the database
  public static void getFromDatabase() {
    for (HashMap<String, Object> map :
        database.executeSql(
            "SELECT AccountID, isAdmin, Email, Straatnaam, Huisnummer, Toevoeging, Woonplaats, Wachtwoord FROM Account")) {
      new Account(
          (int) map.get("AccountID"),
          (boolean) map.get("isAdmin"),
          (String) map.get("Email"),
          (String) map.get("Straatnaam"),
          (int) map.get("Huisnummer"),
          (String) map.get("Toevoeging"),
          (String) map.get("Woonplaats"),
          (String) map.get("Wachtwoord"));
    }
  }

  public static class Profile extends Entity {

    public static Profile currentUser;
    private static Set<Profile> profiles = new HashSet<>();
    private Account account;
    private String name;
    private Set<Serie.Episode> episodesWatched;
    private Set<Film> filmsWatched;
    private Date birthdate;

    public Profile(Account account, String name, Date birthday, int databaseId) {
        super(name, account.getEmail());
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

    // Get profile by database id
    public static Profile getByDbId(int id) {
      return profiles.stream().filter(ent -> ent.databaseId == id).findFirst().orElse(null);
    }

    // Get profiles from database
    @SuppressWarnings("deprecation")
    public static void getFromDatabase() {
      for (HashMap<String, Object> map :
          database.executeSql(
              "SELECT UserId, AccountId, Name, EpisodesWatched, FilmsWatched, Birthday FROM Users")) {

        new Profile(
            Account.getByDbId((int) map.get("AccountId")),
            (String) map.get("Name"),
            (Date) map.get("Birthday"),
            (int) map.get("UserId"));
      }
    }

    // Getters
    public Account getAccount() {
      return account;
    }

    public Set<Serie> getSeriesWatched() {
      return episodesWatched.stream().map(Serie.Episode::getSerie).collect(Collectors.toSet());
    }

    public Set<MediaObject> getMediaWatched() {
      Set<MediaObject> mediaWatched = new HashSet<>();
      mediaWatched.addAll(filmsWatched);
      for (Serie.Episode epi : episodesWatched) mediaWatched.add(epi.getSerie());
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

    public Set<Serie.Episode> getEpisodesWatched() {
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

      if (database.executeSqlNoResult(qr, arr) == DataHandle.SQLResults.PASS)
        JOptionPane.showMessageDialog(NetflixFrame.frame, "Succes!");
    }

    // View media episode
    public void viewEpisode(Serie.Episode episode) {
      // Seperate method as viewepisode is also invoked when pulling from the database
      viewEpisodeNoDB(episode);

      String qr =
          "INSERT INTO WatchedEpisodes (EpisodeId, UserId, EpisodesWatched) VALUES (?, ?, ?)";

      Object[] arr = {episode.databaseId, databaseId, episodesWatched.size()};

      if (database.executeSqlNoResult(qr, arr) == DataHandle.SQLResults.PASS)
        JOptionPane.showMessageDialog(NetflixFrame.frame, "Succes!");
    }

    // View media episode
    public void viewEpisodeNoDB(Serie.Episode epi) {
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
    public void unviewEpisode(Serie.Episode epi) {
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
}
