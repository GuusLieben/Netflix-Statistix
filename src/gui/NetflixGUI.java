package com.netflix.gui;

import com.netflix.commons.Commons;
import com.netflix.gui.listeners.ActionListeners;
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
import static com.netflix.gui.Common.logo;
import static java.awt.BorderLayout.*;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

public class NetflixGUI {

  public static JFrame frame;
  public static JPanel mainPanel = new JPanel(new BorderLayout());
  public static boolean loggedIn;
  public static JPanel lpane = new JPanel(new BorderLayout());

  // Basic constructor
  public NetflixGUI(int width, int height) {
    frame = new JFrame();
    setFrame(width, height);
  }

  public static void loadPanels() {
    // Add to LayeredPane
    lpane.add(Series.pane());

    // Add all panes
    mainPanel.add(Common.bottomPane(), SOUTH);
    mainPanel.add(logo(), NORTH);
    mainPanel.add(Common.menu(), WEST);
    mainPanel.add(lpane, CENTER);
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
    JButton register = new JButton("Register");
    JPanel buttonFrame = new JPanel(new BorderLayout());

    buttonFrame.add(login, BorderLayout.WEST);
    buttonFrame.add(register, BorderLayout.EAST);

    buttonFrame.setOpaque(false);

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
    login.setBorder(new EmptyBorder(15, 0, 0, 10));
    register.setBorder(new EmptyBorder(15, 10, 0, 0));

    // Sample login, will be grabbed from database later
    Commons.users.put("guuslieben", "1a1dc91c907325c69271ddf0c944bc72");

    // If someone presses the button..
    ActionListeners.loginClickEvent(login, usernameBox.getText(), passwordBox.getText());

    ActionListeners.switchRegisterPane(register);

    // If someone presses enter on the passwordBox, simulate a button click
    ActionListeners.simulateClickOnEnter(passwordBox, login);

    // If someone types in the usernameBox
    ActionListeners.onEmptyFieldSet(usernameBox, "Username...");

    ActionListeners.mouseEventUnderline(login);
    ActionListeners.mouseEventUnderline(register);

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
    loginbox.add(buttonFrame, constraints);

    // Styling
    loginbox.setBackground(new Color(43, 43, 43));
    loginTitle.setForeground(Color.LIGHT_GRAY);
    login.setForeground(Color.LIGHT_GRAY);
    register.setForeground(Color.LIGHT_GRAY);
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
