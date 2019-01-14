package com.netflix.gui.views;

import com.netflix.entities.Serie;
import com.netflix.gui.views.subpanels.*;

import javax.swing.*;

public class SerieReadPanel extends ReadPanel {

  public static JPanel pane() {
      // Set the super.combobox to use serie titles
    comboBox = new JComboBox<>(Serie.serieTitles.toArray());
    // Make sure we are using series
    type = "serie";
    mediaType = 2;
    // Return the panel in the super-class
    return ReadPanel.panel();
  }
}