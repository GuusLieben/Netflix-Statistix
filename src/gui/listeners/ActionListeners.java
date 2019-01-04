package com.netflix.gui.listeners;

import com.netflix.*;
import com.netflix.commons.*;
import com.netflix.entities.*;
import com.netflix.gui.*;
import com.netflix.gui.views.*;
import com.netflix.gui.views.management.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ActionListeners {

  private static String usernameBoxValue = "";
  private static String passwordBoxValue = "";
  private static int width;
  private static int height;

  public static void loginClickEvent(JButton login) {
    login.addActionListener(
        (ActionEvent e) -> {
          // For all accounts in the list
          for (Account account : Account.accounts) {

            // Equal the name and password to the key and value
            String key = account.getEmail();
            String value = account.getPassword();

            if ((usernameBoxValue.equals(key)) // If it equals, login
                && (Commons.hashSHA256(passwordBoxValue)
                    .equals(
                        value))) { // Only check the hashes, don't compare plain text (we don't have
                                   // it anyway)

              // A strange artefact appears, Easter Egg <3
              Commons.logger.info(
                  String.format(
                      "\n\n   .----.\n"
                          + "   |C>_ |\n"
                          + " __|____|__\n"
                          + "|  ______--|\n"
                          + "`-/.::::.\\-'   %s logged in\n"
                          + " `--------'     Hash %s\n",
                      usernameBoxValue, Commons.hashSHA256(passwordBoxValue)));

              // Clear the mainPanel (removing login panel), set loggedIn status to true and
              // load the media panels
              Commons.clearPane(NetflixFrame.mainPanel);
              NetflixFrame.loggedIn = true;
              Account.currentAccount = account;
              NetflixFrame.mainPanel.add(LoginView.ProfileLogin.profileSelection());
              break;
            } else {
              flashMyField(
                  LoginView.passwordBox, new Color(75, 20, 20), new Color(20, 20, 20), 100, 300);
              LoginView.passwordBox.setText("");
            }
          }
        });
  }

  private static void flashMyField(
      final JTextField field,
      Color flashColor,
      Color originalColor,
      final int timerDelay,
      int totalTime) {
    final int totalCount = totalTime / timerDelay;
    javax.swing.Timer timer =
        new javax.swing.Timer(
            timerDelay,
            new ActionListener() {
              int count = 0;

              public void actionPerformed(ActionEvent evt) {
                if (count % 2 == 0) field.setBackground(flashColor);
                else {
                  field.setBackground(originalColor);
                  if (count >= totalCount) ((Timer) evt.getSource()).stop();
                }
                count++;
              }
            });
    timer.start();
  }

  public static void profileSelectionEvent(JButton button, Profile profile) {
    button.addActionListener(
        (ActionEvent e) -> {
          // Clear the main panel
          Commons.clearPane(NetflixFrame.mainPanel);
          // Set the currentUser
          Profile.currentUser = profile;

          // Load the panels
          NetflixFrame.loadPanels();
        });
  }

  public static void backtoLogin(JButton button) {
    button.addActionListener(
        (ActionEvent e) -> {
          // Clear the main panel
          Commons.clearPane(NetflixFrame.mainPanel);
          // Add the login pane
          NetflixFrame.mainPanel.add(LoginView.AccountLogin.login());
          // Restore the previous size (defined by switchRegisterPane)
          NetflixFrame.frame.setSize(width, height);
          // Make the frame resizable again
          NetflixFrame.frame.setResizable(true);
          // Center the frame
          Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
          NetflixFrame.frame.setLocation(
              dim.width / 2 - NetflixFrame.frame.getSize().width / 2,
              dim.height / 2 - NetflixFrame.frame.getSize().height / 2);
        });
  }

  @SuppressWarnings("deprecation")
  public static void simulateClickOnEnter(JPasswordField textField, JButton button) {
    textField.addKeyListener(
        new KeyListener() {
          @Override
          // does what it says, clicks a button if you press Enter
          public void keyTyped(KeyEvent e) {
            if (e.getKeyChar() == KeyEvent.VK_ENTER) button.doClick();
          }

          public void keyPressed(KeyEvent e) {
            // Ignored
          }

          // Stores the password in a String here. Prevents
          public void keyReleased(KeyEvent e) {
            passwordBoxValue = textField.getText();
          }
        });
  }

  public static void updateString(JTextField textField) {
    textField.addKeyListener(
        new KeyListener() {
          @Override
          public void keyTyped(KeyEvent e) {
            // Ignored
          }

          public void keyPressed(KeyEvent e) {
            // Ignored
          }

          @Override
          // Stores the email in a String here. Prevents a weird bug where the getText doesn't
          // pass as a parameter when you use the Enter key rather than the button
          public void keyReleased(KeyEvent e) {
            usernameBoxValue = textField.getText();
          }
        });
  }

  public static void switchRegisterPane(JButton register) {
    register.addActionListener(
        (ActionEvent e) -> {
          // Clears the main panel
          Commons.clearPane(NetflixFrame.mainPanel);

          // Stores the current size
          width = NetflixFrame.frame.getWidth();
          height = NetflixFrame.frame.getHeight();

          // Adds the registration panel
          NetflixFrame.mainPanel.add(RegistrationView.registerPanel(NetflixFrame.frame));
        });
  }

  public static void switchPaneOnClick(JButton button, String pane) {
    // Use lambda to handle button pressing to switch views
    button.addActionListener(
        (ActionEvent e) -> {
          Commons.clearPane(NetflixFrame.lpane);

          // Switch case
          switch (pane) {
            case "Series":
              NetflixFrame.lpane.add(SerieMediaView.pane());
              break;
            case "Films":
              NetflixFrame.lpane.add(FilmMediaView.pane());
              break;
            case "Account":
              NetflixFrame.lpane.add(AccountView.pane());
              break;
            case "Manager":
              Commons.clearPane(accountListTable.tablePanel);
              Commons.clearPane(AdminView.panel());
              NetflixFrame.lpane.add(AdminView.panel());
              break;
            default:
              break;
          }
        });
  }

  public static void logoutClickEvent(JButton logoutButton) {
    logoutButton.addActionListener(
        (ActionEvent e) -> {
          // Clear all the things, to prevent anything from being stored by the session
          Commons.clearPane(NetflixFrame.mainPanel);
          Commons.clearPane(AccountView.pane());
          Commons.clearPane(FilmMediaView.pane());
          Commons.clearPane(SerieMediaView.pane());
          Commons.clearPane(NetflixFrame.lpane);
          NetflixFrame.loggedIn = false;
          LoginView.usernameBox.setText("");
          LoginView.passwordBox.setText("");
          usernameBoxValue = "";
          passwordBoxValue = "";

          // Reload everything
          Netflix.gui.setFrame(Netflix.width, Netflix.height);
        });
  }

  public static void showFrame(JButton button, JFrame frame) {
    button.addActionListener(
        (ActionEvent e) -> {
          frame.setVisible(true);
        });
  }
}
