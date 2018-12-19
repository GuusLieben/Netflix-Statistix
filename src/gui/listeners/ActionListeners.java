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

  private static String usernameBoxValue;
  private static String passwordBoxValue;

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

  public static void loginClickEvent(JButton login, String passwordMD5) {
    login.addActionListener(
        (ActionEvent e) -> {
          for (Account account : Commons.accounts) {

            String key = account.getEmail();
            String value = Commons.users.get(account.getEmail());

            System.out.printf("Trying : %s :: %s%n", key, value);

            if ((usernameBoxValue.equals(key))
                && (Commons.hashMD5(passwordBoxValue).equals(value))) {

                System.out.printf(
                        "Actual : %s :: %s [ Accepted ]%n",
                        usernameBoxValue, Commons.hashMD5(passwordBoxValue));
              Commons.logger.info(
                  String.format(
                      "Authorized login %s :: %s",
                      usernameBoxValue, Commons.hashMD5(passwordBoxValue)));
              // Clear the mainPanel (removing login panel), set loggedIn status to true and
              // load the media panels
              MediaView.clearPane(NetflixGUI.mainPanel);
              NetflixGUI.loggedIn = true;
              Commons.currentAccount = account;
              NetflixGUI.mainPanel.add(LoginView.ProfileLogin.profileSelection());
              break;
            }
          }
        });
  }

  public static void profileSelectionEvent(JButton button, Profile profile) {
    button.addActionListener(
        (ActionEvent e) -> {
          MediaView.clearPane(NetflixGUI.mainPanel);
          Commons.currentUser = profile;
          NetflixGUI.loadPanels();
        });
  }

  @SuppressWarnings("deprecation")
  public static void simulateClickOnEnter(JPasswordField textField, JButton button) {
    textField.addKeyListener(
        new KeyListener() {
          @Override
          public void keyTyped(KeyEvent e) {
            if (e.getKeyChar() == KeyEvent.VK_ENTER) button.doClick();
          }

          public void keyPressed(KeyEvent e) {
            // Ignored
          }

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
          public void keyReleased(KeyEvent e) {
            usernameBoxValue = textField.getText();
          }
        });
  }

  public static void switchRegisterPane(JButton register) {
    register.addActionListener(
        (ActionEvent e) -> {
          MediaView.clearPane(NetflixGUI.mainPanel);
          NetflixGUI.mainPanel.add(RegistrationView.registerPanel(NetflixGUI.frame));
        });
  }

  public static void switchPaneOnClick(JButton button, String pane) {
    // Use lambda to handle button pressing to switch views
    button.addActionListener(
        (ActionEvent e) -> {
          MediaView.clearPane(NetflixGUI.lpane);

          switch (pane) {
            case "Series":
              NetflixGUI.lpane.add(SerieView.pane());
              break;
            case "Films":
              NetflixGUI.lpane.add(FilmView.pane());
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
          MediaView.clearPane(NetflixGUI.mainPanel);
          MediaView.clearPane(AccountView.pane());
          MediaView.clearPane(FilmView.pane());
          MediaView.clearPane(SerieView.pane());
          MediaView.clearPane(NetflixGUI.lpane);
          NetflixGUI.loggedIn = false;
          LoginView.usernameBox.setText("");
          LoginView.passwordBox.setText("");
          usernameBoxValue = "";
          passwordBoxValue = "";

          Netflix.gui.setFrame(Netflix.width, Netflix.height);
        });
  }
}
