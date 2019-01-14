package com.netflix.commons;

import com.netflix.Netflix;
import com.netflix.entities.Account;
import com.netflix.gui.NetflixFrame;
import com.netflix.gui.views.AccountView;
import com.netflix.gui.views.FilmReadPanel;
import com.netflix.gui.views.LoginView;
import com.netflix.gui.views.RegistrationPanel;
import com.netflix.gui.views.SerieReadPanel;
import com.netflix.gui.views.AdminView;

import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

import static com.netflix.Netflix.database;
import static com.netflix.Netflix.gui;

public class ActionListeners {

  private static String usernameBoxValue = "";
  private static String passwordBoxValue = "";
  private static int width;
  private static int height;

  public static void loginClickEvent(JButton login) {
    login.addActionListener(
        e -> {
          // Equal the name and password to the key and value
          String value = "";

          for (HashMap<String, Object> map :
              database.executeSql(
                  "SELECT Wachtwoord FROM Account WHERE email=?",
                  new Object[] {usernameBoxValue})) {
            value = (String) map.get("Wachtwoord");
          }

          if (Commons.hashSHA256(passwordBoxValue)
              .equals(value)) { // Only check the hashes, don't compare plain text (we don't have
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
            Account.currentAccount = Account.getUserByEmail(usernameBoxValue);
            NetflixFrame.mainPanel.add(LoginView.ProfileLogin.profileSelection());
          } else {
            flashMyField(
                LoginView.passwordBox, new Color(75, 20, 20), new Color(20, 20, 20), 100, 300);
            LoginView.passwordBox.setText("");
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

  public static void profileSelectionEvent(JButton button, Account.Profile profile) {
    button.addActionListener(
        e -> {
          // Clear the main panel
          Commons.clearPane(NetflixFrame.mainPanel);
          // Set the currentUser
          Account.Profile.currentUser = profile;

          // Load the panels
          NetflixFrame.loadPanels();
        });
  }

  public static void backtoLogin(JButton button) {
    button.addActionListener(
        e -> {
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
        e -> {
          // Clears the main panel
          Commons.clearPane(NetflixFrame.mainPanel);

          // Stores the current size
          width = NetflixFrame.frame.getWidth();
          height = NetflixFrame.frame.getHeight();

          // Adds the registration panel
          NetflixFrame.mainPanel.add(RegistrationPanel.registerPanel(NetflixFrame.frame));
        });
  }

  public static void switchPaneOnClick(JButton button, String pane) {
    // Use lambda to handle button pressing to switch views
    button.addActionListener(
        e -> {
          Commons.clearPane(NetflixFrame.lpane);

          // Switch case
          switch (pane) {
            case "Series":
              NetflixFrame.lpane.add(SerieReadPanel.pane());
              break;
            case "Films":
              NetflixFrame.lpane.add(FilmReadPanel.pane());
              break;
            case "Account":
              NetflixFrame.lpane.add(AccountView.pane());
              break;
            case "Manager":
              Commons.clearPane(AdminView.accountListTable.tablePanel);
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
        e -> {
          // Clear all the things, to prevent anything from being stored by the session
          Commons.clearPane(NetflixFrame.mainPanel);
          Commons.clearPane(AccountView.pane());
          Commons.clearPane(FilmReadPanel.pane());
          Commons.clearPane(SerieReadPanel.pane());
          Commons.clearPane(NetflixFrame.lpane);
          NetflixFrame.loggedIn = false;
          LoginView.usernameBox.setText("");
          LoginView.passwordBox.setText("");
          usernameBoxValue = "";
          passwordBoxValue = "";

          // Reload everything
          gui.setFrame(Netflix.width, Netflix.height);
        });
  }
}
