package com.netflix.gui.views;

import com.netflix.entities.Film;

import javax.swing.*;

@SuppressWarnings("deprecation")
public class FilmMediaView extends MediaView {

  public static JPanel pane() {
    comboBox = new JComboBox<>(Film.filmTitles.toArray());
    type = "film";
    mediaType = 1;
    return MediaView.panel();
  }
}
