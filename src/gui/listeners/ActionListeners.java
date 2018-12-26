package com.netflix.gui.listeners;

import com.netflix.Netflix;
import com.netflix.commons.Commons;
import com.netflix.entities.Account;
import com.netflix.entities.Profile;
import com.netflix.gui.NetflixGUI;
import com.netflix.gui.views.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.TextAttribute;
import java.util.HashMap;

public class ActionListeners {

  private static String usernameBoxValue = "";
  private static String passwordBoxValue = "";
  private static int width;
  private static int height;

  public static void mouseEventUnderline(JComponent object) {
    // MouseOver effects for the menu (underline and cursor effect)
    HashMap<TextAttribute, Object> textAttrMap = new HashMap<>();

    // Listens for mouse events
    object.addMouseListener(
        new MouseAdapter() {
          // If you hover it ...
          @Override
          public void mouseEntered(MouseEvent evt) {
            // Underline the text...
            textAttrMap.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_LOW_GRAY);
            object.setFont(object.getFont().deriveFont(textAttrMap));
            // And change the cursor
            Cursor hoverCursor = new Cursor(Cursor.HAND_CURSOR);
            object.setCursor(hoverCursor);
          }

          // If you are no longer hovering it ...
          @Override
          public void mouseExited(MouseEvent evt) {
            // Remove the underline effect ...
            textAttrMap.put(TextAttribute.UNDERLINE, null);
            object.setFont(object.getFont().deriveFont(textAttrMap));
            // And reset the cursor ...
            Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
            object.setCursor(normalCursor);
          }
        });
  }

  public static void loginClickEvent(JButton login) {
    login.addActionListener(
        (ActionEvent e) -> {
          // For all accounts in the list
          for (Account account : Account.accounts) {

            // Equal the name and password to the key and value
            String key = account.getEmail();
            String value = account.getPassword();

            if ((usernameBoxValue.equals(key)) // If it equals, login
                && (Commons.hashSHA256(passwordBoxValue).equals(value))) {

              // A wild wizard appears, Easter Egg <3
              Commons.logger.info(
                  "\n               ________\n          _,.-Y  |  |  Y-._\n      .-~\"   ||  |  |  |   \"-.\n      I\" \"\"==\"|\" !\"\"! \"|\"[]\"\"|     _____\n      L__  [] |..------|:   _[----I\" .-{\"-.\n     I___|  ..| l______|l_ [__L]_[I_/r(=}=-P\n    [L______L_[________]______j~  '-=c_]/=-^\n     \\_I_j.--.\\==I|I==_/.--L_]\n       [_((==)[`-----\"](==)j\n          I--I\"~~\"\"\"~~\"I--I\n          |[]|         |[]|\n          l__j         l__j\n          |!!|         |!!|\n          |..|         |..|\n          ([])         ([])\n          ]--[         ]--[    !! Authorized login\n          [_L]         [_L]    User : "
                      + usernameBoxValue
                      + "\n         /|..|\\       /|..|\\   Password (encoded) :\n        `=}--{='     `=}--{='   "
                      + Commons.hashSHA256(passwordBoxValue)
                      + "\n       .-^--r-^-.   .-^--r-^-.\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");

              // Clear the mainPanel (removing login panel), set loggedIn status to true and
              // load the media panels
              Commons.clearPane(NetflixGUI.mainPanel);
              NetflixGUI.loggedIn = true;
              Account.currentAccount = account;
              NetflixGUI.mainPanel.add(LoginView.ProfileLogin.profileSelection());
              break;
            }
          }
        });
  }

  public static void profileSelectionEvent(JButton button, Profile profile) {
    button.addActionListener(
        (ActionEvent e) -> {
          // Clear the main panel
          Commons.clearPane(NetflixGUI.mainPanel);
          // Set the currentUser
          Profile.currentUser = profile;

          // Load the panels
          NetflixGUI.loadPanels();
        });
  }

  public static void backtoLogin(JButton button) {
    button.addActionListener(
        (ActionEvent e) -> {
          // Clear the main panel
          Commons.clearPane(NetflixGUI.mainPanel);
          // Add the login pane
          NetflixGUI.mainPanel.add(LoginView.AccountLogin.login());
          // Restore the previous size (defined by switchRegisterPane)
          NetflixGUI.frame.setSize(width, height);
          // Make the frame resizable again
          NetflixGUI.frame.setResizable(true);
          // Center the frame
          Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
          NetflixGUI.frame.setLocation(
              dim.width / 2 - NetflixGUI.frame.getSize().width / 2,
              dim.height / 2 - NetflixGUI.frame.getSize().height / 2);
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

          // Stores the password in a String here. Prevents a weird bug where the getText doesn't
          // pass as a parameter when you use the Enter key rather than the button
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
          Commons.clearPane(NetflixGUI.mainPanel);

          // Stores the current size
          width = NetflixGUI.frame.getWidth();
          height = NetflixGUI.frame.getHeight();

          // Adds the registration panel
          NetflixGUI.mainPanel.add(RegistrationView.registerPanel(NetflixGUI.frame));
        });
  }

  public static void switchPaneOnClick(JButton button, String pane) {
    // Use lambda to handle button pressing to switch views
    button.addActionListener(
        (ActionEvent e) -> {
          Commons.clearPane(NetflixGUI.lpane);

          // Switch case
          switch (pane) {
            case "Series":
              NetflixGUI.lpane.add(SerieMediaView.pane());
              break;
            case "Films":
              NetflixGUI.lpane.add(FilmMediaView.pane());
              break;
            case "Account":
              NetflixGUI.lpane.add(AccountView.pane());
              break;
            case "Manager":
              NetflixGUI.lpane.add(AdminView.panel());
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
          Commons.clearPane(NetflixGUI.mainPanel);
          Commons.clearPane(AccountView.pane());
          Commons.clearPane(FilmMediaView.pane());
          Commons.clearPane(SerieMediaView.pane());
          Commons.clearPane(NetflixGUI.lpane);
          NetflixGUI.loggedIn = false;
          LoginView.usernameBox.setText("");
          LoginView.passwordBox.setText("");
          usernameBoxValue = "";
          passwordBoxValue = "";

          // Reload everything
          Netflix.gui.setFrame(Netflix.width, Netflix.height);
        });
  }
}
