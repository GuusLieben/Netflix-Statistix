package com.netflix.gui.panes;

import com.netflix.objects.*;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

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
            57.10,
            "Bob"));

    Profile profile2 = new Profile(accountView, "Profiel 2");

    JLabel profileLabel = new JLabel("<html>");
    for (Profile prof : accountView.getProfiles()) {
      profileLabel.setText(profileLabel.getText() + ".Profile name : " + prof.getName());
      for (Film film : prof.getFilmsWatched()) {
        profileLabel.setText(profileLabel.getText() + "<br>...Film watched: " + film.getTitle());
      }
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
