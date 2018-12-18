package com.netflix.entities;

import com.netflix.commons.Commons;
import com.netflix.gui.NetflixGUI;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Account {

  private boolean isAdmin;
  private ArrayList<Profile> profiles;
  private String email;
  private String street;
  private int houseNumber;
  private String addition;
  private String city;

  public Account(
      boolean isAdmin, String email, String street, int houseNumber, String addition, String city, String password) {
    if (emailIsValid(email)) {
      this.isAdmin = isAdmin;
      this.email = email;
      this.street = street;
      this.houseNumber = houseNumber;
      this.addition = addition;
      this.city = city;
      profiles = new ArrayList<>();
      Commons.users.put(email, password);
    } else {
      JOptionPane.showMessageDialog(
          NetflixGUI.frame, "Invalid email found : " + email, null, JOptionPane.ERROR_MESSAGE);
    }
  }

  public static boolean emailIsValid(String email) {
    // Check that the email matches a proper format (using Regex)
    String emailRegex =
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    Pattern pat = Pattern.compile(emailRegex);
    if (email == null) return false;
    return pat.matcher(email).matches();
  }

  public boolean isAdmin() {
    return isAdmin;
  }

  public List<Profile> getProfiles() {
    return profiles;
  }

  public void addProfile(Profile profile) {
    profiles.add(profile);
  }

  public String getEmail() {
    return email;
  }
}
