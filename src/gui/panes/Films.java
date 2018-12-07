package com.netflix.gui.panes;

import com.netflix.objects.*;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

import static java.awt.BorderLayout.*;

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
            57.2,
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
