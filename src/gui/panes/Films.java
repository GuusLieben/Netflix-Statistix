package com.netflix.gui.panes;

import com.netflix.objects.Film;
import com.netflix.objects.Genre;
import com.netflix.objects.Language;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.Time;

import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.NORTH;

public class Films {
  private static String title = "House of Cards";
  private static int episodes = 73;
  private static String[] choices = {
    "Twilight",
    "Narnia",
    "The Avengers",
    "The Lord of the Rings",
    "Wildlife Pictured",
    "Whatever Movie"
  };

  public static JPanel pane() {
    // Create panel with 10px padding
    JPanel mainPanel = new JPanel(new BorderLayout());
    mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
    mainPanel.setBackground(Color.WHITE);

    Film film =
        new Film(
            8.6,
            new Genre("Action"),
            new Language("de_DE", "German"),
            "Twilight",
            new Time(2,16,48),
            "Bob Bobber");

    // Add sub-panels
    mainPanel.add(selectSeries(), NORTH);
    mainPanel.add(Overview.newOverview(film, null), CENTER);

    return mainPanel;
  }

  static JPanel selectSeries() {
    // Create dropdown with sample values
    JPanel selectSeries = new JPanel();
    JLabel selectSerie = new JLabel("Selecteer een film : ");
    JComboBox<String> comboBox = new JComboBox<>(choices);
    comboBox.setVisible(true);

    // Add label + dropdown
    selectSeries.add(selectSerie);
    selectSeries.add(comboBox);

    selectSeries.setBackground(Color.WHITE);

    return selectSeries;
  }
}
