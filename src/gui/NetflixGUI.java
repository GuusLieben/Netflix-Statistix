package com.netflix.gui;

import com.netflix.commons.Commons;
import com.netflix.gui.panes.*;
import com.raphaellevy.fullscreen.FullScreenException;
import com.raphaellevy.fullscreen.FullScreenMacOS;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Map;

import static com.netflix.commons.Commons.exception;
import static com.netflix.gui.Common.addHoverEffect;
import static com.netflix.gui.Common.logo;
import static java.awt.BorderLayout.*;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

public class NetflixGUI {

  public static JFrame frame;
  private static JPanel lpane = new JPanel(new BorderLayout());
  private static JPanel mainPanel = new JPanel(new BorderLayout());
  private boolean loggedIn;

  // Basic constructor
  public NetflixGUI(int width, int height) {
    frame = new JFrame();
    setFrame(width, height);
  }

  static void switchPane(JButton button, String pane) {
    // Lambda : actionListener
    button.addActionListener(
        e -> {
          Overview.clearPane(lpane);

          switch (pane) {
            case "Series":
              lpane.add(Series.pane());
              break;
            case "Films":
              lpane.add(Films.pane());
              break;
            case "Account":
              lpane.add(AccountView.pane());
              break;
          }
        });
  }

  private void setFrame(int width, int height) {
    // Set defaults for frame

    //    frame.setLayout(new BorderLayout());
    frame.setDefaultCloseOperation(EXIT_ON_CLOSE);

    // Make sure the given sizes don't exceed the minimum frame size
    if (width < 650) width = 650;
    if (height < 400) height = 400;

    // Set sizes for frame
    frame.setMinimumSize(new Dimension(650, 400));
    frame.setSize(width, height);

    if (loggedIn) loadPanels();
    else mainPanel.add(login());

    // Make sure the application can be used full-screen on MacOS devices
    try {
      if (System.getProperty("os.name").startsWith("Mac"))
        FullScreenMacOS.setFullScreenEnabled(frame, true);

    } catch (FullScreenException ex) {
      exception(ex);
    }

    frame.add(mainPanel);

    // Center frame
    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    frame.setLocation(
        dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);

    // Make the frame visible
    frame.pack();
    frame.setVisible(true);
  }

  private void loadPanels() {
    // Add to LayeredPane
    lpane.add(Series.pane());

    // Add all panes
    mainPanel.add(Common.bottomPane(), SOUTH);
    mainPanel.add(logo(), NORTH);
    mainPanel.add(Common.menu(), WEST);
    mainPanel.add(lpane, CENTER);
  }

  @SuppressWarnings("deprecation")
  private JPanel login() {

    GradientPanel gradientPanel = new GradientPanel();

    JPanel main = new JPanel(new BorderLayout());
    JPanel loginbox = gradientPanel.getGradientPanel();
    loginbox.setLayout(new GridBagLayout());

    GridBagConstraints constraints = new GridBagConstraints();

    JLabel loginTitle = new JLabel("Log in");
    loginTitle.setFont(
        new Font(loginTitle.getFont().getName(), loginTitle.getFont().getStyle(), 18));

    JTextField usernameBox = new JTextField("Username ...", 20);
    JPasswordField passwordBox = new JPasswordField(20);

    JButton login = new JButton("Login");

    passwordBox.setEchoChar('⚬');

    usernameBox.setMinimumSize(
        new Dimension(
            usernameBox.getPreferredSize().width + 30, usernameBox.getPreferredSize().height));
    passwordBox.setMinimumSize(
        new Dimension(
            passwordBox.getPreferredSize().width + 20, passwordBox.getPreferredSize().height));

    loginTitle.setBorder(new EmptyBorder(0, 10, 20, 10));
    login.setBorder(new EmptyBorder(15, 0, 0, 0));

    Commons.users.put("guuslieben", "pass");

    login.addActionListener(
        (ActionEvent e) -> {
          for (Map.Entry<String, String> entry : Commons.users.entrySet()) {

            String key = entry.getKey();
            String value = entry.getValue();

            if (usernameBox.getText().equals(key) && passwordBox.getText().equals(value)) {
              Overview.clearPane(mainPanel);
              loggedIn = true;
              loadPanels();

            } else
              JOptionPane.showMessageDialog(
                  NetflixGUI.frame,
                  "Incorrect credentials, please try again",
                  null,
                  JOptionPane.WARNING_MESSAGE);
          }
        });

    passwordBox.addKeyListener(
        new KeyListener() {
          @Override
          public void keyTyped(KeyEvent e) {
            if (e.getKeyChar() == KeyEvent.VK_ENTER) login.doClick();
          }

          @Override
          public void keyPressed(KeyEvent e) {}

          @Override
          public void keyReleased(KeyEvent e) {}
        });

    usernameBox.addKeyListener(
        new KeyListener() {
          @Override
          public void keyTyped(KeyEvent e) {
              if (usernameBox.getText().equals(""))
                  usernameBox.setText("Username ...");
          }

          @Override
          public void keyPressed(KeyEvent e) {}

          @Override
          public void keyReleased(KeyEvent e) {}
        });

    addHoverEffect(login);

    JPanel spacer = new JPanel();

    constraints.gridy = 1;
    loginbox.add(loginTitle, constraints);

    constraints.gridy = 2;
    loginbox.add(usernameBox, constraints);

    constraints.gridy = 3;
    loginbox.add(spacer, constraints);

    constraints.gridy = 4;
    loginbox.add(passwordBox, constraints);

    constraints.gridy = 5;
    loginbox.add(login, constraints);

    loginbox.setBackground(new Color(43, 43, 43));
    loginTitle.setForeground(Color.LIGHT_GRAY);
    login.setForeground(Color.LIGHT_GRAY);
    usernameBox.setForeground(Color.LIGHT_GRAY);
    passwordBox.setForeground(Color.LIGHT_GRAY);

    usernameBox.setBorder(new EmptyBorder(8, 8, 8, 8));
    passwordBox.setBorder(new EmptyBorder(8, 8, 8, 8));

    usernameBox.setBackground(new Color(20, 20, 20));
    spacer.setOpaque(false);
    passwordBox.setBackground(new Color(20, 20, 20));

    main.add(logo(), BorderLayout.NORTH);
    main.add(loginbox, BorderLayout.CENTER);
    main.add(Common.bottomPane(), SOUTH);

    return main;
  }
}
