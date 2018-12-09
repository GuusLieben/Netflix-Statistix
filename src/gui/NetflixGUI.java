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
    // Use lambda to handle button pressing to switch panes
    button.addActionListener(
        (ActionEvent e) -> {
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
            default:
              break;
          }
        });
  }

  private void setFrame(int width, int height) {
    // Set defaults for frame
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
    // Background gradient
    GradientPanel gradientPanel = new GradientPanel();

    // Set panels
    JPanel main = new JPanel(new BorderLayout());
    JPanel loginbox = gradientPanel.getGradientPanel();
    loginbox.setLayout(new GridBagLayout());

    // GridBagLayout constraints to throw each new item on the appropriate y level
    GridBagConstraints constraints = new GridBagConstraints();

    // Title styling
    JLabel loginTitle = new JLabel("Log in");
    loginTitle.setFont(
        new Font(loginTitle.getFont().getName(), loginTitle.getFont().getStyle(), 18));

    // Basic text boxes
    JTextField usernameBox = new JTextField("Username ...", 20);
    JPasswordField passwordBox = new JPasswordField(20);

    // Button
    JButton login = new JButton("Login");

    // Make sure all text in passwordBox is obscured with a specific character
    passwordBox.setEchoChar('⚬');

    // Set minimum sizes for the input boxes, to prevent them from being too small
    usernameBox.setMinimumSize(
        new Dimension(
            usernameBox.getPreferredSize().width + 30, usernameBox.getPreferredSize().height));
    passwordBox.setMinimumSize(
        new Dimension(
            passwordBox.getPreferredSize().width + 20, passwordBox.getPreferredSize().height));

    // Add borders to create extra spacing
    loginTitle.setBorder(new EmptyBorder(0, 10, 20, 10));
    login.setBorder(new EmptyBorder(15, 0, 0, 0));

    // Sample login, will be grabbed from database later
    Commons.users.put("guuslieben", "pass");

    // If someone presses the button..
    login.addActionListener(
        (ActionEvent e) ->
            Commons.users.forEach(
                (key, value) -> { // Loop through the users
                  // and check if they match the input
                  if (usernameBox.getText().equals(key) && passwordBox.getText().equals(value)) {
                    // Clear the mainPanel (removing login panel), set loggedIn status to true and
                    // load the media panels
                    Overview.clearPane(mainPanel);
                    loggedIn = true;
                    loadPanels();

                  } else {
                    // If it doesn't match, show an error
                    JOptionPane.showMessageDialog(
                        NetflixGUI.frame,
                        "Incorrect credentials, please try again",
                        null,
                        JOptionPane.WARNING_MESSAGE);
                  }
                }));

    // If someone presses enter on the passwordBox, simulate a button click
    passwordBox.addKeyListener(
        new KeyListener() {
          @Override
          public void keyTyped(KeyEvent e) {
            if (e.getKeyChar() == KeyEvent.VK_ENTER) login.doClick();
          }

          @Override
          public void keyPressed(KeyEvent e) {
            // Ignored
          }

          @Override
          public void keyReleased(KeyEvent e) {
            // Ignored
          }
        });

    // If someone types in the usernameBox
    usernameBox.addKeyListener(
        new KeyListener() {
          @Override
          public void keyTyped(KeyEvent e) {
            // If the text is "" then add out placeholder
            if (usernameBox.getText().equals("")) usernameBox.setText("Username ...");
          }

          @Override
          public void keyPressed(KeyEvent e) {
            // Ignored
          }

          @Override
          public void keyReleased(KeyEvent e) {
            // Ignored
          }
        });

    addHoverEffect(login);

    // Lazy spacing method, as the emptyborder added later will be colored
    JPanel spacer = new JPanel();

    // Add items in order
    constraints.gridy = 1;
    loginbox.add(loginTitle, constraints);

    constraints.gridy++; // 2
    loginbox.add(usernameBox, constraints);

    constraints.gridy++; // 3
    loginbox.add(spacer, constraints);

    constraints.gridy++; // 4
    loginbox.add(passwordBox, constraints);

    constraints.gridy++; // 5
    loginbox.add(login, constraints);

    // Styling
    loginbox.setBackground(new Color(43, 43, 43));
    loginTitle.setForeground(Color.LIGHT_GRAY);
    login.setForeground(Color.LIGHT_GRAY);
    usernameBox.setForeground(Color.LIGHT_GRAY);
    passwordBox.setForeground(Color.LIGHT_GRAY);
    usernameBox.setCaretColor(Color.LIGHT_GRAY);
    passwordBox.setCaretColor(Color.LIGHT_GRAY);

    usernameBox.setBorder(new EmptyBorder(8, 8, 8, 8));
    passwordBox.setBorder(new EmptyBorder(8, 8, 8, 8));

    usernameBox.setBackground(new Color(20, 20, 20));
    spacer.setOpaque(false);
    passwordBox.setBackground(new Color(20, 20, 20));

    // Add all the things
    main.add(logo(), BorderLayout.NORTH);
    main.add(loginbox, BorderLayout.CENTER);
    main.add(Common.bottomPane(), SOUTH);

    return main;
  }
}
