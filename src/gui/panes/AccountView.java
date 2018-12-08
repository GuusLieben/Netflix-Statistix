package com.netflix.gui.panes;

import com.netflix.objects.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.Time;

@SuppressWarnings("deprecation")
public class AccountView {

  public static JPanel pane() {
    JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    panel.setBorder(new EmptyBorder(10, 10, 10, 10));

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

    JLabel profileLabel = new JLabel("<html>");
    for (Profile prof : accountView.getProfiles()) {
      profileLabel.setText(profileLabel.getText() + ".Profile name : " + prof.getName());

      for (Film film : prof.getFilmsWatched())
        profileLabel.setText(profileLabel.getText() + "<br>...Film watched: " + film.getTitle());

      for (Serie ser : prof.getSeriesWatched())
        profileLabel.setText(profileLabel.getText() + "<br>...Series watched: " + serie.getTitle());

      profileLabel.setText(profileLabel.getText() + "<br><br>");
    }
    profileLabel.setText(profileLabel.getText() + "</html>");

    JLabel labelBoi =
        new JLabel(
            String.format(
                "<html><h1>Account overzicht</h1>"
                    + "<br>Account name : %s<br>Is admin : %s<br>E-mail : %s<br><br><b>Profile(s) attached :</b> <br>%s</html>",
                accountView.getName(),
                accountView.isAdmin(),
                accountView.getEmail(),
                profileLabel.getText()));
    panel.add(labelBoi);
    panel.setBackground(Color.WHITE);
    return panel;
  }
}
