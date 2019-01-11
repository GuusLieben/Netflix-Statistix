package com.netflix.gui.views;

import com.netflix.entities.Film;
import com.netflix.entities.Profile;
import com.netflix.entities.Serie;
import com.netflix.gui.commons.NButton;
import com.netflix.gui.listeners.ActionListeners;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

public class AccountView {

  public static JPanel pane() {
    // Make sure content is aligned to the left and add a basic margin
    JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    panel.setBorder(new EmptyBorder(10, 10, 10, 10));

    JPanel inner = new JPanel(new GridBagLayout());
    GridBagConstraints constraints = new GridBagConstraints();
    constraints.gridy = 0;

    // Label for profiles
    JLabel profileLabel = new JLabel("<html>"); // Use html because we can
    // For all profiles
    for (Profile prof : Profile.currentUser.getAccount().getProfiles()) {
      profileLabel.setText(
              String.format("%s.Profiel : %s", profileLabel.getText(), prof.getName())); // Show the profile name

      if (prof == Profile.currentUser)
        profileLabel.setText(profileLabel.getText() + " <b><i>(Huidig)</b></i>");

      for (Film film : prof.getFilmsWatched()) { // If they have any
        profileLabel.setText(
            profileLabel.getText()
                + "<br>...Bekeken films: "
                + film.getTitle()); // Show what films they watched lately
      }
      for (Serie ser : prof.getSeriesWatched()) { // If they have any
        profileLabel.setText(
            profileLabel.getText()
                + "<br>...Bekeken series: "
                + ser.getTitle()); // Show what series they watched lately
      }
      profileLabel.setText(profileLabel.getText() + "<br><br>"); // Add spacing
    }
    profileLabel.setText(profileLabel.getText() + "</html>"); // End html

    JLabel
        accountLabel = // Basic string formatting to include all basic account information, and the
            // profile label
            new JLabel(
                String.format(
                    "<html><h1>Account overzicht</h1>"
                        + "<br>Administrator? : %s<br>E-mail : %s<br><br><b>Profielen :</b> <br>%s</html>",
                    Profile.currentUser.getAccount().isAdmin(),
                    Profile.currentUser.getAccount().getEmail(),
                    profileLabel.getText()));

    // Add all the things
    constraints.gridy++;
    inner.add(accountLabel, constraints);

    JButton logoutButton = new NButton("Uitloggen");

    constraints.gridy++;
    inner.add(logoutButton, constraints);

    ActionListeners.logoutClickEvent(logoutButton);

    panel.add(inner);
    // Styling
    panel.setBackground(Color.WHITE);
    inner.setBackground(Color.WHITE);

    return panel;
  }
}
