package com.netflix.gui.views;

import com.netflix.entities.Serie;

import javax.swing.*;

@SuppressWarnings("deprecation")
public class SerieMediaView extends MediaView {

  public static JPanel pane() {
    comboBox = new JComboBox<>(Serie.serieTitles.toArray());
    type = "serie";
    mediaType = 2;
    return MediaView.panel();
  }
}
