package com.netflix.gui.panes;

import com.netflix.commons.Commons;
import com.netflix.objects.Film;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.NORTH;

@SuppressWarnings("deprecation")
public class Films {

  private static JComboBox comboBox = new JComboBox<>(Commons.filmTitles.toArray());
  private static Overview filmView = new Overview();

  public static JPanel pane() {
    // Create panel with 10px padding
    JPanel mainPanel = new JPanel(new BorderLayout());
    mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
    mainPanel.setBackground(Color.WHITE);

    // Add sub-panels
    mainPanel.add(selectFilm(), NORTH);

    Film film = Film.getFilmByName(comboBox.getSelectedItem().toString());

    mainPanel.add(filmView.getOverview(film, null), CENTER);

    return mainPanel;
  }

  private static JPanel selectFilm() {
    // Create dropdown with sample values
    JPanel selectFilms = new JPanel();
    JLabel selectFilm = new JLabel("Selecteer een film : ");

    comboBox.addActionListener(
        e -> {
          filmView.clearOverview();
          Commons.logger.info(comboBox.getSelectedItem().toString());
          filmView.getOverview(Film.getFilmByName(comboBox.getSelectedItem().toString()), null);
        });

    comboBox.setVisible(true);

    // Add label + dropdown
    selectFilms.add(selectFilm);
    selectFilms.add(comboBox);

    selectFilms.setBackground(Color.WHITE);

    return selectFilms;
  }
}
