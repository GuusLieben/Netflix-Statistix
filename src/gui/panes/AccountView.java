package com.netflix.gui.panes;

import com.netflix.objects.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.Time;

@SuppressWarnings("deprecation")
public class AccountView {

  public static JPanel pane() {
    // Make sure content is aligned to the left and add a basic margin
    JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    panel.setBorder(new EmptyBorder(10, 10, 10, 10));

    // Create a sample account with profile and additional data
    Account accountView = new Account("Guus", true, "g.lieben@avans.student.nl");
    Profile profile = new Profile(accountView, "Profiel 1");
    profile.viewFilm(
        new Film(
            8.3,
            new Genre("Romance"),
            new Language("nl_NL", "Dutch"),
            "The Avengers",
            new Time(1, 57, 38),
            "Bob"));

    Profile profile2 = new Profile(accountView, "Profiel 2");

    Serie serie =
        new Serie(new Genre("Drama"), new Language("nl_NL", "Dutch"), "House of Cards", 8.6);
    Season season = new Season(serie, "newSeason", 1, 1);
    Episode episode = new Episode(season, "Pilot", serie, 16.57);
    Episode episode2 = new Episode(season, "Pilot Continued", serie, 12.35);

    profile2.viewEpisode(episode);
    profile2.viewEpisode(episode2);

    //////////// End the sample data

    // Label for profiles
    JLabel profileLabel = new JLabel("<html>"); // Use html because we can
    // For all profiles
    accountView
        .getProfiles()
        .forEach(
            prof -> {
              profileLabel.setText(
                  profileLabel.getText()
                      + ".Profile name : "
                      + prof.getName()); // Show the profile name

              for (Film film : prof.getFilmsWatched()) // If they have any
              profileLabel.setText(
                    profileLabel.getText()
                        + "<br>...Film watched: "
                        + film.getTitle()); // Show what films they watched lately
              for (Serie ser : prof.getSeriesWatched()) // If they have any
              profileLabel.setText(
                    profileLabel.getText()
                        + "<br>...Series watched: "
                        + serie.getTitle()); // Show what series they watched lately
              profileLabel.setText(profileLabel.getText() + "<br><br>"); // Add spacing
            });
    profileLabel.setText(profileLabel.getText() + "</html>"); // End html

    JLabel
        accountLabel = // Basic string formatting to include all basic account information, and the
                       // profile label
        new JLabel(
                String.format(
                    "<html><h1>Account overzicht</h1>"
                        + "<br>Account name : %s<br>Is admin : %s<br>E-mail : %s<br><br><b>Profile(s) attached :</b> <br>%s</html>",
                    accountView.getName(),
                    accountView.isAdmin(),
                    accountView.getEmail(),
                    profileLabel.getText()));

    // Add all the things
    panel.add(accountLabel);

    // Styling
    panel.setBackground(Color.WHITE);

    return panel;
  }
}
