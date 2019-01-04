
package com.netflix.gui.views;

import com.netflix.commons.Commons;
import com.netflix.entities.Account;
import com.netflix.entities.Profile;
import com.netflix.gui.NetflixFrame;
import com.netflix.gui.commons.*;
import com.netflix.gui.listeners.ActionListeners;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
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

      // Buttons
      JButton login = new NButton("Inloggen");
      JButton register = new NButton("Registreren");
      JPanel buttonFrame = new JPanel(new BorderLayout());

      buttonFrame.add(login, BorderLayout.WEST);
      buttonFrame.add(register, BorderLayout.EAST);

      buttonFrame.setOpaque(false);

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
//      ActionListeners.mouseEventUnderline(login);
//      ActionListeners.mouseEventUnderline(register);
      ActionListeners.updateString(usernameBox);

      // Lazy spacing method, as the emptyborder added later will be colored
      JPanel spacer = new JPanel();

      // Add items in order
      // Object 1, title
      constraints.gridy = 1;
      loginbox.add(loginTitle, constraints);

      // Object 2, Email box + label
      constraints.gridy++; // 2
      JPanel usernamePanel = new JPanel(new BorderLayout());
      JLabel userLabel = new JLabel("E-mail");
      userLabel.setForeground(Color.LIGHT_GRAY);
      usernamePanel.add(userLabel, BorderLayout.NORTH);
      userLabel.setBorder(new EmptyBorder(3, 6, 3, 3));
      usernamePanel.add(usernameBox, BorderLayout.CENTER);
      usernamePanel.setOpaque(false);
      loginbox.add(usernamePanel, constraints);

      // Object 3, spacer
      constraints.gridy++; // 3
      loginbox.add(spacer, constraints);

      // Object 4, Password box + label
      constraints.gridy++; // 4
      JPanel passwordPanel = new JPanel(new BorderLayout());
      JLabel passLabel = new JLabel("Wachtwoord");
      passLabel.setForeground(Color.LIGHT_GRAY);
      passwordPanel.add(passLabel, BorderLayout.NORTH);
      passLabel.setBorder(new EmptyBorder(3, 6, 3, 3));
      passwordBox.setText("");
      passwordPanel.add(passwordBox, BorderLayout.CENTER);
      passwordPanel.setOpaque(false);
      loginbox.add(passwordPanel, constraints);

      // Object 5, buttons
      constraints.gridy++; // 5
      loginbox.add(buttonFrame, constraints);

      // Background, will be a gradient because of the GradientPanel type
      loginbox.setBackground(new Color(43, 43, 43));

      // Lots of foregrounds
      loginTitle.setForeground(Color.LIGHT_GRAY);
      login.setForeground(Color.LIGHT_GRAY);
      register.setForeground(Color.LIGHT_GRAY);
      usernameBox.setForeground(Color.LIGHT_GRAY);
      passwordBox.setForeground(Color.LIGHT_GRAY);
      usernameBox.setCaretColor(Color.LIGHT_GRAY);
      passwordBox.setCaretColor(Color.LIGHT_GRAY);

      // Borders
      usernameBox.setBorder(new EmptyBorder(8, 8, 8, 8));
      passwordBox.setBorder(new EmptyBorder(8, 8, 8, 8));

      // Backgrounds of the boxes, and invisible spacer
      usernameBox.setBackground(new Color(20, 20, 20));
      spacer.setOpaque(false);
      passwordBox.setBackground(new Color(20, 20, 20));

      // Add all the things
      main.add(logo(), BorderLayout.NORTH);
      main.add(loginbox, BorderLayout.CENTER);
      main.add(Common.bottomPane(), SOUTH);

      // If someone presses the button..
      ActionListeners.loginClickEvent(login);

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

      // Use randoms for the profile icons
      Random random = new Random();
      Image image = null;

      for (Profile profile :
          Account.currentAccount
              .getProfiles()) { // For all profiles, shouldn't be more than 5 unless someone hacked
        // an extra profile into the account

        int randomNum =
            random.nextInt((7 - 1) + 1)
                + 1; // Random profile picture, can show duplicate icons, intended behavior
        image = new ImageIcon(String.format("resources/profiles/profile%d.png", randomNum)).getImage();
        // Scale icon to fit labels, then add it to the label
        ImageIcon icon = new ImageIcon(image.getScaledInstance(100, 100, Image.SCALE_SMOOTH));

        String labelText = profile.getName();
        JButton label = new NButton();

        // Close the html tags
        label.setText(String.format("<html><center>%s</center></html>", labelText));

        // Set icon and basic styling
        label.setIcon(icon);
        label.setBorder(new EmptyBorder(3, 10, 3, 10));
        label.setIconTextGap(5);
        label.setHorizontalTextPosition(JLabel.CENTER);
        label.setVerticalTextPosition(JLabel.BOTTOM);
        label.setForeground(Color.LIGHT_GRAY);

        // Action listener
        ActionListeners.profileSelectionEvent(label, profile);

        profileWrapper.add(label);
      }

      if (Account.currentAccount.getProfiles().size() <= 4) {
        JButton addProfileLabel = new NButton("Nieuw profiel");
        image = new ImageIcon("resources/profiles/addprofile.png").getImage();
        ImageIcon icon = new ImageIcon(image.getScaledInstance(100, 100, Image.SCALE_SMOOTH));

        addProfileLabel.setIcon(icon);
        addProfileLabel.setBorder(new EmptyBorder(3, 10, 3, 10));
        addProfileLabel.setIconTextGap(5);
        addProfileLabel.setHorizontalTextPosition(JLabel.CENTER);
        addProfileLabel.setVerticalTextPosition(JLabel.BOTTOM);
        addProfileLabel.setForeground(Color.LIGHT_GRAY);

        addProfileLabel.addActionListener(
            (ActionEvent e) -> {
              Commons.clearPane(NetflixFrame.mainPanel);
              NetflixFrame.mainPanel.add(createProfileView.panel());
            });

        profileWrapper.add(addProfileLabel);
      }

      JPanel centerWrapper = new JPanel(new BorderLayout());
      JLabel watching = new JLabel("Wie kijkt Netflix Statistix?");
      watching.setFont(new Font(watching.getFont().getName(), watching.getFont().getStyle(), 18));

      centerWrapper.add(watching, BorderLayout.NORTH);
      centerWrapper.add(profileWrapper, BorderLayout.CENTER);
      centerWrapper.setBackground(new Color(34, 34, 34));
      watching.setHorizontalAlignment(JLabel.CENTER);
      watching.setBorder(new EmptyBorder(35, 0, 0, 0));
      watching.setForeground(Color.LIGHT_GRAY);

      // Add all the things
      main.add(Common.logo(), BorderLayout.NORTH);
      main.add(centerWrapper, BorderLayout.CENTER);
      main.add(Common.bottomPane(), BorderLayout.SOUTH);

      return main;
    }
  }
}
