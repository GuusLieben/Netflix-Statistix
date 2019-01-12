package com.netflix.gui.views;

import com.netflix.commons.Commons;
import com.netflix.entities.MediaObject;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.Color;

import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.NORTH;

public abstract class ReadPanel {

  static JComboBox<Object> comboBox;
  static String type;
  static int mediaType;
  private static ReadObject readObject = new ReadObject();

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
    panel.add(readObject.getOverview(media), CENTER);

    return panel;
  }

  private static JPanel selectMedia() {
    // Use 'type' (film or serie) in selection label
    JPanel selectMedia = new JPanel();
    JLabel selectLabel = new JLabel(String.format("Selecteer een %s : ", type));

    // Reload panels when a new media object is selected
    comboBox.addActionListener(
        e -> {
          readObject.clearOverview();
          readObject.getOverview(
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
