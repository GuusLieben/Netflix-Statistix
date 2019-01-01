package com.netflix.entities;

import com.netflix.*;
import com.netflix.commons.*;
import com.netflix.entities.abstracts.*;
import com.netflix.gui.*;

import javax.swing.*;
import java.sql.*;
import java.util.*;
import java.util.regex.*;

public class Account extends Entity {

  // Stores current account
  public static Account currentAccount;
  // Stores all accounts loaded from the database
  public static Set<Account> accounts = new HashSet<>();

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
    // If the email check is valid, create the object, store the user
    if (emailIsValid(email)) {
      this.databaseId = databaseId;
      this.isAdmin = isAdmin;
      this.email = email;
      this.street = street;
      this.houseNumber = houseNumber;
      this.addition = addition;
      this.city = city;
      // Make sure to hash the password before storing it. Not the most secure method, but at least
      // it isn't plain text
      this.password = Commons.hashSHA256(password);
      // HashSet so each profile is unique
      profiles = new HashSet<>();
      // Add it to the Set
      accounts.add(this);

    } else {
      // If the email wasn't valid, show a popup and don't create the object
      JOptionPane.showMessageDialog(
          NetflixFrame.frame, "E-mail is incorrect : " + email, null, JOptionPane.ERROR_MESSAGE);
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

  public Set<Profile> getProfiles() {
    return profiles;
  }

  public void addProfile(Profile profile) {
    profiles.add(profile);
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getLocation() {
    return street + " " + houseNumber + addition + ", " + city;
  }

  public String getPassword() {
    return password;
  }

  public static Account getByDbId(int id) {
    return accounts.stream().filter(ent -> ent.databaseId == id).findFirst().orElse(null);
  }

  public static void getFromDatabase() {
    if (Netflix.database.connectDatabase()) {
      String sqlQuery =
          "SELECT AccountID, isAdmin, Email, Straatnaam, Huisnummer, Toevoeging, Woonplaats, Wachtwoord FROM Account";
      ResultSet results = null;
      try (Statement statement = Netflix.database.connection.createStatement()) {
        // Make sure the results are passed
        results = statement.executeQuery(sqlQuery);
        System.out.println("Query passed : " + results.toString());
        while (results.next()) {
          System.out.println(results.getString("AccountID"));
          new Account(
              results.getInt("AccountID"),
              results.getBoolean("isAdmin"),
              results.getString("Email"),
              results.getString("Straatnaam"),
              results.getInt("Huisnummer"),
              results.getString("Toevoeging"),
              results.getString("Woonplaats"),
              results.getString("Wachtwoord"));
        }
      } catch (SQLException ex) {
        Commons.exception(ex);
        System.out.println("Query did not pass");
      }
      Netflix.database.disconnectDatabase();
      for (Account acc : Account.accounts) {
        System.out.println(acc);
      }
    }
  }

  @Override
  public String toString() {
    return "Account{"
        + "databaseId="
        + databaseId
        + ", isAdmin="
        + isAdmin
        + ", profiles="
        + profiles
        + ", email='"
        + email
        + '\''
        + ", street='"
        + street
        + '\''
        + ", houseNumber="
        + houseNumber
        + ", addition='"
        + addition
        + '\''
        + ", city='"
        + city
        + '\''
        + ", password='"
        + password
        + '\''
        + ", entityId="
        + entityId
        + '}';
  }
}
