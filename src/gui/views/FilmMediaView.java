package com.netflix.gui.views;

import com.netflix.entities.Film;

import javax.swing.JComboBox;
import javax.swing.JPanel;

public class FilmMediaView extends MediaView {

  public static JPanel pane() {
      // Set the super.combobox to use film titles
    comboBox = new JComboBox<>(Film.filmTitles.toArray());
    // Make sure we are using films
    type = "film";
    mediaType = 1;
    // Return the panel in the super-class
    return MediaView.panel();
  }
}
