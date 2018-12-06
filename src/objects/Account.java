package com.netflix.objects;

import com.netflix.gui.*;

import javax.swing.*;
import java.util.*;
import java.util.regex.*;

public class Account {

  private String name;
  private boolean isAdmin;
  private ArrayList<Profile> profiles;
  private String email;

  public Account(String name, boolean isAdmin, String email) {
    if (emailIsValid(email)) {
      this.name = name;
      this.isAdmin = isAdmin;
      this.email = email;
      profiles = new ArrayList<>();
    } else {
      JOptionPane.showMessageDialog(
          NetflixGUI.frame, "Invalid email found : " + email, null, JOptionPane.ERROR_MESSAGE);
    }
  }

  public boolean emailIsValid(String email) {
    String emailRegex =
        "^[a-zA-Z0-9_+&*-]+(?:\\."
            + "[a-zA-Z0-9_+&*-]+)*@"
            + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
            + "A-Z]{2,7}$";

    Pattern pat = Pattern.compile(emailRegex);
    if (email == null) return false;
    return pat.matcher(email).matches();
  }

  public String getName() {
    return name;
  }

  public boolean isAdmin() {
    return isAdmin;
  }

  public ArrayList<Profile> getProfiles() {
    return profiles;
  }

  public void addProfile(Profile profile) {
    profiles.add(profile);
  }

  public String getEmail() {
    return email;
  }
}
