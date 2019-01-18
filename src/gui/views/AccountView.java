package com.netflix.gui.views;

import com.netflix.commons.*;
import com.netflix.entities.*;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.*;

public class AccountView {

  private static Random random = new Random();

  public static JPanel pane() {
    // Make sure content is aligned to the left and add a basic margin
    JPanel wrapper = new JPanel(new BorderLayout());
    wrapper.setBorder(new EmptyBorder(10, 10, 10, 10));

    int randomNum =
        random.nextInt((7 - 1) + 1)
            + 1; // Random profile picture, can show duplicate icons, intended behavior
    Image image =
        new ImageIcon(String.format("resources/profiles/profile%d.png", randomNum)).getImage();
    // Scale icon to fit labels, then add it to the label
    ImageIcon icon = new ImageIcon(image.getScaledInstance(100, 100, Image.SCALE_SMOOTH));

    String labelText = Account.Profile.currentUser.getName();
    JLabel label = new JLabel();

    // Close the html tags
    label.setText(String.format("<html><center>%s</center></html>", labelText));

    // Set icon and basic styling
    label.setBorder(new EmptyBorder(0, 10, 0, 20));
    label.setIcon(icon);
    label.setIconTextGap(5);
    label.setOpaque(false);
    label.setHorizontalTextPosition(JLabel.CENTER);
    label.setVerticalTextPosition(JLabel.BOTTOM);
    label.setVerticalAlignment(JLabel.TOP);

    JPanel inner = new JPanel(new BorderLayout());

    // Label for profiles
    JLabel profileLabel = new JLabel("<html><ol>"); // Use html because we can
    // For all profiles
    for (Account.Profile prof : Account.Profile.currentUser.getAccount().getProfiles()) {
      profileLabel.setText(
          String.format(
              "%s<li>%s", profileLabel.getText(), prof.getName())); // Show the profile name

      if (prof == Account.Profile.currentUser)
        profileLabel.setText(profileLabel.getText() + " <b><i>(Huidig)</b></i>");

      profileLabel.setText(profileLabel.getText() + "<br>Bekeken films: <ul>");
      for (Film film : prof.getFilmsWatched()) { // If they have any
        profileLabel.setText(
            String.format(
                "%s<li>%s</li>",
                profileLabel.getText(), film.getTitle())); // Show what films they watched lately
      }
      if (prof.getFilmsWatched().isEmpty())
        profileLabel.setText(profileLabel.getText() + "<li>Geen</li>");

      profileLabel.setText(profileLabel.getText() + "</ul><br>Bekeken series: <ul>");
      for (Serie ser : prof.getSeriesWatched()) { // If they have any
        profileLabel.setText(
            String.format(
                "%s<ul>%s</ul>",
                profileLabel.getText(), ser.getTitle())); // Show what series they watched lately
      }
      if (prof.getSeriesWatched().isEmpty())
        profileLabel.setText(profileLabel.getText() + "<li>Geen</li>");

      profileLabel.setText(profileLabel.getText() + "</li></ul><br><br>"); // Add spacing
    }
    profileLabel.setText(profileLabel.getText() + "</ol></html>"); // End html

    JLabel
        accountLabel = // Basic string formatting to include all basic account information, and the
            // profile label
            new JLabel(
                String.format(
                    "<html><br>Administrator : %s<br>E-mail : %s<br>Locatie : %s<br><br><b>Profielen :</b> <br>%s</html>",
                    Account.Profile.currentUser.getAccount().isAdmin(),
                    Account.Profile.currentUser.getAccount().getEmail(),
                    Account.currentAccount.getLocation(),
                    profileLabel.getText()));

    accountLabel.setHorizontalAlignment(JLabel.LEFT);
    // Add all the things
    inner.add(accountLabel, BorderLayout.WEST);

    JPanel actions = new JPanel(new GridLayout(2, 1));
    JButton logoutButton = new Commons.NButton("Uitloggen");
    ActionListeners.logoutClickEvent(logoutButton);

    JButton passwordButton = new Commons.NButton("Wijzig wachtwoord");

    actions.setOpaque(false);
    actions.add(passwordButton);
    actions.add(logoutButton);

    JPanel sidebar = new JPanel(new BorderLayout());
    sidebar.add(actions, BorderLayout.SOUTH);
    sidebar.add(label, BorderLayout.NORTH);

    wrapper.add(
        new JLabel(
            String.format(
                "<html><h1>Account overzicht voor %s</h1></html>",
                Account.Profile.currentUser.getName())),
        BorderLayout.NORTH);

    wrapper.add(sidebar, BorderLayout.WEST);

    JScrollPane pane = new JScrollPane(inner);
    wrapper.add(pane, BorderLayout.CENTER);
    pane.setBorder(new EmptyBorder(0, 0, 0, 0));

    // Styling
    sidebar.setBackground(Color.WHITE);
    wrapper.setBackground(Color.WHITE);
    inner.setBackground(Color.WHITE);

    return wrapper;
  }
}
