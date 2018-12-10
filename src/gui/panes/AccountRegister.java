/*
 * Copyright © 2018. Guus Lieben.
 * All rights reserved.
 */

package com.netflix.gui.panes;

import com.netflix.objects.Account;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

import static com.netflix.gui.Common.*;
import static java.awt.BorderLayout.*;

public class AccountRegister {

  public static void createAccount(Account account) {}

  public static JPanel registerPanel(JFrame frame) {
    frame.setResizable(false);
    // Background gradient
    GradientPanel gradientPanel = new GradientPanel();

    // Set panels
    JPanel main = new JPanel(new BorderLayout());
    JPanel box = gradientPanel.getGradientPanel();
    box.setLayout(new BorderLayout());
    JPanel leftBox = new JPanel(new GridBagLayout());
    JPanel rightBox = new JPanel(new GridBagLayout());
    leftBox.setOpaque(false);
    rightBox.setOpaque(false);

    // GridBagLayout constraints to throw each new item on the appropriate y level
    GridBagConstraints constraints = new GridBagConstraints();

    box.setBorder(new EmptyBorder(20, 20, 10, 25));

    // Title styling
    JLabel registerTitle = new JLabel("Registreer");
    registerTitle.setFont(
        new Font(registerTitle.getFont().getName(), registerTitle.getFont().getStyle(), 18));

    // Basic text boxes
    JTextField emailBox = new JTextField(20);
    JTextField emailBox2 = new JTextField(20);
    JPasswordField passwordBox = new JPasswordField(20);
    JPasswordField passwordBox2 = new JPasswordField(20);
    JTextField streetBox = new JTextField(10);
    JTextField numberBox = new JTextField(10);
    JTextField additionBox = new JTextField(10);
    JTextField cityBox = new JTextField(10);

    // Button
    JButton register = new JButton("Registreren");

    // Set minimum sizes for the input boxes, to prevent them from being too small
    emailBox.setMinimumSize(
        new Dimension(emailBox.getPreferredSize().width + 20, emailBox.getPreferredSize().height));
    emailBox2.setMinimumSize(
        new Dimension(emailBox.getPreferredSize().width + 20, emailBox.getPreferredSize().height));

    // Add borders to create extra spacing
    registerTitle.setBorder(new EmptyBorder(0, 10, 20, 10));
    register.setBorder(new EmptyBorder(15, 0, 0, 0));

    // If someone presses the button..
    register.addActionListener(
        (ActionEvent e) -> {
          //        DatabaseHandle.addUser();
        });

    addHoverEffect(register);

    // Add items in order
    constraints.gridy = 1;
    box.add(registerTitle, BorderLayout.NORTH);

    addBox(leftBox, constraints, emailBox, "E-mail");
    addBox(leftBox, constraints, emailBox2, "Bevestig e-mail");

    addBox(leftBox, constraints, passwordBox, "Wachtwoord");
    addBox(leftBox, constraints, passwordBox2, "Bevestig wachtwoord");

    // Reset and reuse
    constraints.gridy = 1;

    addBox(rightBox, constraints, streetBox, "Straat");
    addBox(rightBox, constraints, numberBox, "Nummer");
    addBox(rightBox, constraints, additionBox, "Toevoeging");
    addBox(rightBox, constraints, cityBox, "Woonplaats");

    constraints.gridy++;
    box.add(register, BorderLayout.SOUTH);

    box.add(leftBox, BorderLayout.CENTER);
    box.add(rightBox, BorderLayout.EAST);

    register.setHorizontalAlignment(SwingConstants.RIGHT);

    // Styling
    box.setBackground(new Color(43, 43, 43));
    register.setForeground(Color.LIGHT_GRAY);
    registerTitle.setForeground(Color.LIGHT_GRAY);
    register.setBorder(new EmptyBorder(20,20,10,0));
    leftBox.setBorder(new EmptyBorder(0, -50, 0, 0));

    // Add all the things
    main.add(logo(), BorderLayout.NORTH);
    main.add(box, BorderLayout.CENTER);
    main.add(bottomPane(), BorderLayout.SOUTH);

    return main;
  }

  private static void addBox(
      JPanel box, GridBagConstraints constraints, JTextField component, String description) {
    constraints.gridy++; // 4
    JPanel spacer = new JPanel();
    spacer.setOpaque(false);
    box.add(spacer, constraints);

    JPanel fieldLabel = new JPanel(new BorderLayout());
    JLabel descriptionLabel = new JLabel(description);
    descriptionLabel.setBorder(new EmptyBorder(5,0,5,0));
    fieldLabel.add(descriptionLabel, BorderLayout.NORTH);
    fieldLabel.add(component, CENTER);

    constraints.gridy++;
    box.add(fieldLabel, constraints);

    component.setForeground(Color.LIGHT_GRAY);
    component.setCaretColor(Color.LIGHT_GRAY);

    descriptionLabel.setForeground(Color.LIGHT_GRAY);

    fieldLabel.setOpaque(false);

    component.setBorder(new EmptyBorder(8, 8, 8, 8));
    component.setBackground(new Color(20, 20, 20));
  }
}
