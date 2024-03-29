package com.netflix.gui;

import com.netflix.commons.*;
import com.netflix.gui.views.FilmReadPanel;
import com.netflix.gui.views.LoginView;
import com.raphaellevy.fullscreen.FullScreenException;
import com.raphaellevy.fullscreen.FullScreenMacOS;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import static com.netflix.commons.Commons.exception;
import static com.netflix.commons.Commons.logo;
import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.NORTH;
import static java.awt.BorderLayout.SOUTH;
import static java.awt.BorderLayout.WEST;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

public class NetflixFrame {

  public static JFrame frame;
  public static JPanel mainPanel = new JPanel(new BorderLayout());
  public static boolean loggedIn;
  public static JPanel lpane = new JPanel(new BorderLayout());

  // Basic constructor
  public NetflixFrame(int width, int height) {
    Commons.logger.info("Instantiating GUI");
    frame = new JFrame();
    setFrame(width, height);
  }

  public static void loadPanels() {
    // Add to LayeredPane
    lpane.add(FilmReadPanel.pane());

    // Add all views
    mainPanel.add(Commons.credits(), SOUTH);
    mainPanel.add(logo(), NORTH);
    mainPanel.add(Commons.menu(), WEST);
    mainPanel.add(lpane, CENTER);
  }

  public void setFrame(int width, int height) {
    // Set defaults for frame
    frame.setDefaultCloseOperation(EXIT_ON_CLOSE);

    // Make sure the given sizes don't exceed the minimum frame size
    if (width < 700) width = 700;
    if (height < 450) height = 450;

    // Set sizes for frame
    frame.setMinimumSize(
        new Dimension(
            Integer.parseInt(DataHandle.get("window.width")),
            Integer.parseInt(DataHandle.get("window.height"))));
    frame.setSize(width, height);

    if (loggedIn) loadPanels();
    else mainPanel.add(LoginView.AccountLogin.login());

    // Make sure the application can be used full-screen on MacOS devices. If it's not a MacOS
    // device, don't do anything
    try {
      if (System.getProperty("os.name").startsWith("Mac"))
        FullScreenMacOS.setFullScreenEnabled(frame, true);
    } catch (FullScreenException ex) {
      exception(ex);
    }

    // Add the mainPanel containing either all media panels or the login panel
    frame.add(mainPanel);

    // Center frame
    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    frame.setLocation(
        dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);

    // Make the frame visible
    frame.pack();
    frame.setVisible(true);
  }
}
