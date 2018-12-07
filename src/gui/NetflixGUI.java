package com.netflix.gui;

import com.netflix.gui.panes.AccountView;
import com.netflix.gui.panes.Films;
import com.netflix.gui.panes.Overview;
import com.netflix.gui.panes.Series;
import com.raphaellevy.fullscreen.FullScreenException;
import com.raphaellevy.fullscreen.FullScreenMacOS;

import javax.swing.*;
import java.awt.*;

import static com.netflix.commons.Commons.exception;
import static java.awt.BorderLayout.*;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

public class NetflixGUI {

  public static JFrame frame;
  private static JPanel lpane = new JPanel(new BorderLayout());

  // Basic constructor
  public NetflixGUI(int width, int height) {
    frame = new JFrame();
    setFrame(width, height);
  }

  public static void switchPane(JButton button, String pane) {
    // Lambda : actionListener
    button.addActionListener(
        e -> {
          Overview.clearPane(lpane);

          if ((pane.equals("Series"))) lpane.add(Series.pane());
          if ((pane.equals("Films"))) lpane.add(Films.pane());
          if ((pane.equals("Account"))) lpane.add(AccountView.pane());
        });
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

    // Add to LayeredPane
    lpane.add(Series.pane());

    // Add all panes
    frame.add(Common.bottomPane(), SOUTH);
    frame.add(Common.logo(), NORTH);
    frame.add(Common.menu(), WEST);
    frame.add(lpane, CENTER);

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
}
