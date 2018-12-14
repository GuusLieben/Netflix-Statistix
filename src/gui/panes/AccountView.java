package com.netflix.gui.panes;

import com.netflix.commons.Commons;
import com.netflix.objects.Film;
import com.netflix.objects.Serie;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

@SuppressWarnings("deprecation")
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
    Commons.currentUser
        .getAccount()
        .getProfiles()
        .forEach(
            prof -> {
              profileLabel.setText(
                  profileLabel.getText()
                      + ".Profile name : "
                      + prof.getName()); // Show the profile name

              for (Film film : prof.getFilmsWatched()) { // If they have any
                profileLabel.setText(
                    profileLabel.getText()
                        + "<br>...Film watched: "
                        + film.getTitle()); // Show what films they watched lately
              }
              for (Serie ser : prof.getSeriesWatched()) { // If they have any
                profileLabel.setText(
                    profileLabel.getText()
                        + "<br>...Series watched: "
                        + ser.getTitle()); // Show what series they watched lately
              }
              profileLabel.setText(profileLabel.getText() + "<br><br>"); // Add spacing
            });
    profileLabel.setText(profileLabel.getText() + "</html>"); // End html

    JLabel
        accountLabel = // Basic string formatting to include all basic account information, and the
            // profile label
            new JLabel(
                String.format(
                    "<html><h1>Account overzicht</h1>"
                        + "<br>Is admin : %s<br>E-mail : %s<br><br><b>Profile(s) attached :</b> <br>%s</html>",
                    Commons.currentUser.getAccount().isAdmin(),
                    Commons.currentUser.getAccount().getEmail(),
                    profileLabel.getText()));

    // Add all the things
    constraints.gridy++;
    inner.add(accountLabel, constraints);

    constraints.gridy++;
    inner.add(new JButton("Uitloggen"), constraints);

    panel.add(inner);
    // Styling
    panel.setBackground(Color.WHITE);
      inner.setBackground(Color.WHITE);

    return panel;
  }
}
