/*
 * Copyright © 2018. Guus Lieben.
 * All rights reserved.
 */

package com.netflix.gui.views;

import com.netflix.commons.Commons;
import com.netflix.entities.MediaObject;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.NORTH;

public class MediaView {

  static JComboBox<Object> comboBox;
  static String type;
  static int mediaType;
  private static ObjectView objectView = new ObjectView();

  public static JPanel panel() {
    JPanel panel = new JPanel(new BorderLayout());
    panel.setBorder(new EmptyBorder(10, 10, 10, 10));
    panel.setBackground(Color.WHITE);

    panel.add(selectMedia(), NORTH);

    MediaObject media =
        MediaObject.getObjectByName(comboBox.getSelectedItem().toString(), mediaType);

    MediaObject.type = media.getMediaType();

    Commons.logger.info(
        "Loading new view with type " + media.getType() + "::" + media.getMediaType());

    panel.add(objectView.getOverview(media), CENTER);

    return panel;
  }

  private static JPanel selectMedia() {
    JPanel selectMedia = new JPanel();
    JLabel selectLabel = new JLabel("Selecteer een " + type + " : ");

    comboBox.addActionListener(
        (ActionEvent e) -> {
          objectView.clearOverview();
          objectView.getOverview(
              MediaObject.getObjectByName(comboBox.getSelectedItem().toString(), mediaType));
        });

    comboBox.setVisible(true);

    selectMedia.add(selectLabel);
    selectMedia.add(comboBox);

    selectMedia.setBackground(Color.WHITE);

    return selectMedia;
  }
}
