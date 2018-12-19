/*
 * Copyright © 2018. Guus Lieben.
 * All rights reserved.
 */

package com.netflix.gui.views;

import com.netflix.commons.Commons;
import com.netflix.entities.Profile;
import com.netflix.gui.commons.Common;
import com.netflix.gui.commons.GradientPanel;
import com.netflix.gui.listeners.ActionListeners;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Random;

import static com.netflix.gui.commons.Common.logo;
import static java.awt.BorderLayout.SOUTH;

public class LoginView {

  public static JTextField usernameBox = new JTextField(20);
  public static JPasswordField passwordBox = new JPasswordField(20);

  public static class AccountLogin {
    @SuppressWarnings("deprecation")
    public static JPanel login() {
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

      ActionListeners.switchRegisterPane(register);

      // If someone presses enter on the passwordBox, simulate a button click
      ActionListeners.simulateClickOnEnter(passwordBox, login);
      ActionListeners.mouseEventUnderline(login);
      ActionListeners.mouseEventUnderline(register);
      ActionListeners.updateString(usernameBox);

      // Lazy spacing method, as the emptyborder added later will be colored
      JPanel spacer = new JPanel();

      // Add items in order
      constraints.gridy = 1;
      loginbox.add(loginTitle, constraints);

      constraints.gridy++; // 2
      JPanel usernamePanel = new JPanel(new BorderLayout());
      JLabel userLabel = new JLabel("Username");
      userLabel.setForeground(Color.LIGHT_GRAY);
      usernamePanel.add(userLabel, BorderLayout.NORTH);
      userLabel.setBorder(new EmptyBorder(3, 6, 3, 3));
      usernamePanel.add(usernameBox, BorderLayout.CENTER);
      usernamePanel.setOpaque(false);
      loginbox.add(usernamePanel, constraints);

      constraints.gridy++; // 3
      loginbox.add(spacer, constraints);

      constraints.gridy++; // 4
      JPanel passwordPanel = new JPanel(new BorderLayout());
      JLabel passLabel = new JLabel("Password");
      passLabel.setForeground(Color.LIGHT_GRAY);
      passwordPanel.add(passLabel, BorderLayout.NORTH);
      passLabel.setBorder(new EmptyBorder(3, 6, 3, 3));
      passwordBox.setText("");
      passwordPanel.add(passwordBox, BorderLayout.CENTER);
      passwordPanel.setOpaque(false);
      loginbox.add(passwordPanel, constraints);

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

      // If someone presses the button..
      ActionListeners.loginClickEvent(login, passwordBox.getText());

      return main;
    }
  }

  public static class ProfileLogin {
    public static JPanel profileSelection() {
      JPanel main = new JPanel(new BorderLayout());

      JPanel profileWrapper = new GradientPanel().getGradientPanel();
      profileWrapper.setLayout(new GridBagLayout());
      profileWrapper.setBackground(new Color(34, 34, 34));

      GridBagConstraints constraints = new GridBagConstraints();
      constraints.gridx = 0;

      Random random = new Random();
      Image image = null;

      for (Profile profile : Commons.currentAccount.getProfiles()) {
        int randomNum = random.nextInt((7 - 1) + 1) + 1;

        image = new ImageIcon("resources/profiles/profile" + randomNum + ".png").getImage();
        // Scale icon to fit labels, then add it to the label
        ImageIcon icon = new ImageIcon(image.getScaledInstance(100, 100, Image.SCALE_SMOOTH));

        String labelText = profile.getName();
        JButton label = new JButton();

        if (profile.getAccount().isAdmin()) {
          labelText += " [Admin]";
          label.setFont(new Font(label.getFont().getName(), Font.BOLD, label.getFont().getSize()));
        }

        label.setText(labelText);

        label.setIcon(icon);
        label.setBorder(new EmptyBorder(3, 10, 3, 10));

        label.setIconTextGap(5);
        label.setHorizontalTextPosition(JLabel.CENTER);
        label.setVerticalTextPosition(JLabel.BOTTOM);

        label.setForeground(Color.LIGHT_GRAY);
        ActionListeners.mouseEventUnderline(label);
        ActionListeners.profileSelectionEvent(label, profile);

        profileWrapper.add(label);
      }

      main.add(Common.logo(), BorderLayout.NORTH);
      main.add(profileWrapper, BorderLayout.CENTER);
      main.add(Common.bottomPane(), BorderLayout.SOUTH);

      return main;
    }
  }
}
