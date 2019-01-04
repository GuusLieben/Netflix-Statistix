package com.netflix.gui.views;

import com.netflix.commons.Commons;
import com.netflix.entities.abstracts.MediaObject;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.NORTH;

public abstract class MediaView {

  static JComboBox<Object> comboBox;
  static String type;
  static int mediaType;
  private static ObjectView objectView = new ObjectView();

  public static JPanel panel() {
    JPanel panel = new JPanel(new BorderLayout());
    panel.setBorder(new EmptyBorder(10, 10, 10, 10));
    panel.setBackground(Color.WHITE);

    panel.add(selectMedia(), NORTH);

    // Get object using the name and mediaType
    MediaObject media =
        MediaObject.getObjectByName(comboBox.getSelectedItem().toString(), mediaType);

    MediaObject.type = media.getMediaType();

    // Log it to the file and console
    Commons.logger.info(
        String.format("Loading new view with type %d::%d", media.getType(), media.getMediaType()));

    // Get the overview, add it
    panel.add(objectView.getOverview(media), CENTER);

    return panel;
  }

  private static JPanel selectMedia() {
    // Use 'type' (film or serie) in selection label
    JPanel selectMedia = new JPanel();
    JLabel selectLabel = new JLabel(String.format("Selecteer een %s : ", type));

    // Reload panels when a new media object is selected
    comboBox.addActionListener(
        (ActionEvent e) -> {
          objectView.clearOverview();
          objectView.getOverview(
              MediaObject.getObjectByName(comboBox.getSelectedItem().toString(), mediaType));
        });

    comboBox.setVisible(true);

    // Add all things
    selectMedia.add(selectLabel);
    selectMedia.add(comboBox);

    selectMedia.setBackground(Color.WHITE);

    return selectMedia;
  }
}
