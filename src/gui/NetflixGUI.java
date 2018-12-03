package com.netflix.gui;

import com.netflix.gui.panes.*;
import com.raphaellevy.fullscreen.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static com.netflix.commons.Commons.*;
import static java.awt.BorderLayout.*;
import static javax.swing.JFrame.*;

public class NetflixGUI {

  public static JFrame frame;

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
    frame.add(Series.pane(), CENTER);

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

  public static void showOnClick(JButton button, String pane) {
    button.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            if (!(pane.equals("Series"))) frame.remove(Series.pane());
            if (!(pane.equals("Films"))) frame.remove(Films.pane());
            if (!(pane.equals("Account"))) frame.remove(Account.pane());

            if ((pane.equals("Series"))) frame.add(Series.pane(), CENTER);
            if ((pane.equals("Films"))) frame.add(Films.pane(), CENTER);
            if ((pane.equals("Account"))) frame.add(Account.pane(), CENTER);
          }
        });
  }
}
