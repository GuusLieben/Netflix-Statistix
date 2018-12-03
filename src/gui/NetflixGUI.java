package com.netflix.gui;

import com.raphaellevy.fullscreen.*;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

import static com.netflix.commons.Commons.*;
import static java.awt.BorderLayout.*;
import static javax.swing.JFrame.*;

public class NetflixGUI {

  private JFrame frame;

  public NetflixGUI(int width, int height) {
    frame = new JFrame();
    setFrame(width, height);
  }

  private void setFrame(int width, int height) {
    // Set defaults for frame
    frame.setLayout(new BorderLayout());
    frame.setDefaultCloseOperation(EXIT_ON_CLOSE);

    // Make sure the given sizes don't exceed the minimum frame size
    if (width < 650) width = 650;
    if (height < 500) height = 500;

    // Set sizes for frame
    frame.setMinimumSize(new Dimension(650, 400));
    frame.setSize(width, height);

    // Add all panes
    frame.add(Common.bottomPane(), SOUTH);
    frame.add(Common.menu(), NORTH);
    frame.add(mainPane(), CENTER);

    // Make sure the application can be used full-screen on MacOS devices
    try {
      if (System.getProperty("os.name").startsWith("Mac"))
        FullScreenMacOS.setFullScreenEnabled(frame, true);
    } catch (FullScreenException ex) {
      exception(ex);
    }

    // Make the frame visible
    frame.setVisible(true);
  }

  private JPanel mainPane() {
    // Create panel with 10px padding
    JPanel mainPanel = new JPanel(new BorderLayout());
    mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
    mainPanel.setBackground(Color.WHITE);

    // Add sub-panels
    mainPanel.add(MainGUI.selectSeries(), NORTH);
    mainPanel.add(MainGUI.seriesOverview(), CENTER);

    return mainPanel;
  }
}
