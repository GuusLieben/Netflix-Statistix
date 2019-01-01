/*
 * Copyright © 2018. Guus Lieben.
 * All rights reserved.
 */

package com.netflix.gui.views;

import com.netflix.commons.*;
import com.netflix.entities.*;
import com.netflix.gui.*;
import com.netflix.gui.commons.*;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class createProfileView {

  private static JTextField nameField;
  private static JSpinner ageField;
  private static GridBagConstraints constraints = new GridBagConstraints();

  public static JPanel panel() {
    JPanel main = new JPanel(new BorderLayout());

    JPanel profileWrapper = new GradientPanel().getGradientPanel();
    profileWrapper.setLayout(new GridBagLayout());
    profileWrapper.setBackground(new Color(34, 34, 34));

    constraints.gridy = 0;

    JLabel nameLabel = new JLabel("Naam");
    nameField = new JTextField(20);

    JLabel ageLabel = new JLabel("Leeftijd");
    //    JTextField ageField = new JTextField(20);
    ageField = new JSpinner();

    nameField.setBackground(new Color(20, 20, 20));
    ageField.setBackground(new Color(20, 20, 20));

    nameField.setCaretColor(Color.LIGHT_GRAY);

    JPanel spacer = new JPanel();
    spacer.setOpaque(false);

    JLabel header = new JLabel("Nieuw profiel");
    header.setFont(new Font(header.getFont().getName(), header.getFont().getStyle(), 18));
    header.setBorder(new EmptyBorder(0, 0, 10, 0));

    JButton addProfile = new NButton("Toevoegen");
//    ActionListeners.mouseEventUnderline(addProfile);

    addComponent(header, profileWrapper);
    addComponent(nameLabel, profileWrapper);
    addComponent(nameField, profileWrapper);
    addComponent(spacer, profileWrapper);
    addComponent(ageLabel, profileWrapper);
    addComponent(ageField, profileWrapper);
    addComponent(spacer, profileWrapper);
    addComponent(addProfile, profileWrapper);

    addLoginEvent(addProfile);

    // Add all the things
    main.add(Common.logo(), BorderLayout.NORTH);
    main.add(profileWrapper, BorderLayout.CENTER);
    main.add(Common.bottomPane(), BorderLayout.SOUTH);

    return main;
  }

  private static void addComponent(JComponent com, JPanel panel) {
    constraints.gridy++;
    com.setForeground(Color.LIGHT_GRAY);
    com.setBorder(new EmptyBorder(8, 8, 8, 8));
    panel.add(com, constraints);
  }

  private static void addLoginEvent(JButton button) {
    button.addActionListener(
        (ActionEvent e) -> {
          String name = nameField.getText();
          int age = (Integer) ageField.getValue();

          if ((age > 0 && age < 150) && !(name.equals(""))) {
            Account.currentAccount.addProfile(new Profile(Account.currentAccount, name, age, Account.currentAccount.getProfiles().size()+1)); // TODO : add database push
            Commons.clearPane(NetflixFrame.mainPanel);
            NetflixFrame.mainPanel.add(LoginView.ProfileLogin.profileSelection());
          } else {
            System.out.println("Something is not right");
            System.out.println(name);
            System.out.println(age);
          }
        });
  }
}
