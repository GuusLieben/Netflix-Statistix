package com.netflix.gui.views;

import com.netflix.commons.Commons;
import com.netflix.entities.Serie;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

import static java.awt.BorderLayout.NORTH;

@SuppressWarnings("deprecation")
public class SerieView {

  private static JComboBox<Object> comboBox = new JComboBox<>(Commons.serieTitles.toArray());
  private static MediaView serieView = new MediaView();

  public static JPanel pane() {
    // Create panel with 10px padding
    JPanel mainPanel = new JPanel(new BorderLayout());
    mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
    mainPanel.setBackground(Color.WHITE);

    // Add sub-panels
    mainPanel.add(selectSeries(), NORTH);

    Serie serie = Serie.getSerieByName(comboBox.getSelectedItem().toString());

    mainPanel.add(serieView.getOverview(null, serie), BorderLayout.CENTER);

    return mainPanel;
  }

  private static JPanel selectSeries() {
    // Create dropdown with sample values
    JPanel selectSeries = new JPanel();
    JLabel selectSerie = new JLabel("Selecteer een serie : ");

    comboBox.addActionListener(
        (ActionEvent e) -> {
          serieView.clearOverview();
          Commons.logger.info("User selected serie: " + comboBox.getSelectedItem().toString());
          serieView.getOverview(null, Serie.getSerieByName(comboBox.getSelectedItem().toString()));
        });

    comboBox.setVisible(true);

    // Add label + dropdown
    selectSeries.add(selectSerie);
    selectSeries.add(comboBox);

    selectSeries.setBackground(Color.WHITE);

    return selectSeries;
  }
}
