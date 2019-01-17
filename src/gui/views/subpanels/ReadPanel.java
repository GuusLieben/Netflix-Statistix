package com.netflix.gui.views.subpanels;

import com.netflix.commons.Commons;
import com.netflix.entities.MediaObject;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.NORTH;

public abstract class ReadPanel {

  protected static JComboBox<Object> comboBox;
  protected static String type;
  protected static int mediaType;
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

    JPanel mediaSwitch = new JPanel(new GridLayout());
    mediaSwitch.add(comboBox);
    mediaSwitch.add(new JButton("Bewerken"));

    selectMedia.add(mediaSwitch);

    selectMedia.setBackground(Color.WHITE);

    return selectMedia;
  }
}
