package com.netflix.gui.views;

import com.netflix.commons.*;
import com.netflix.gui.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.*;
import java.util.*;
import java.util.stream.*;

import static com.netflix.commons.ActionListeners.switchLoginPane;
import static com.netflix.commons.Commons.credits;
import static com.netflix.commons.Commons.logo;
import static java.awt.BorderLayout.CENTER;

public class RegistrationPanel {

  private static Map<String, JTextField> values = new HashMap<>();

  public static JPanel registerPanel(JFrame frame) {
    frame.setResizable(false);
    frame.setSize(650, 525);
    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    frame.setLocation(
        dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);

    // Background gradient
    Commons.GradientPanel gradientPanel = new Commons.GradientPanel();

    // Set panels
    JPanel mainRegister = new JPanel(new BorderLayout());
    JPanel boxRegister = gradientPanel.getGradientPanel();
    boxRegister.setLayout(new BorderLayout());
    JPanel leftBox = new JPanel(new GridBagLayout());
    JPanel rightBox = new JPanel(new GridBagLayout());
    leftBox.setOpaque(false);
    rightBox.setOpaque(false);

    // GridBagLayout constraints to throw each new item on the appropriate y level
    GridBagConstraints constraints = new GridBagConstraints();

    boxRegister.setBorder(new EmptyBorder(20, 20, 10, 25));

    // Title styling
    JLabel registerTitle = new JLabel("Nieuw account");
    JButton backToPrevious = new Commons.NButton("< Terug");
    backToPrevious.setHorizontalAlignment(JLabel.LEFT);
    backToPrevious.setForeground(Color.LIGHT_GRAY);
    backToPrevious.setBorder(new EmptyBorder(0, 10, 0, 0));
    ActionListeners.backtoLogin(backToPrevious);

    JPanel titleAndCheck = new JPanel(new BorderLayout());
    titleAndCheck.add(registerTitle, BorderLayout.CENTER);
    titleAndCheck.add(backToPrevious, BorderLayout.SOUTH);

    registerTitle.setFont(
        new Font(registerTitle.getFont().getName(), registerTitle.getFont().getStyle(), 18));

    // Basic text boxes
    JTextField emailBox = new JTextField(20);
    JTextField emailBox2 = new JTextField(20);
    JPasswordField passwordBox = new JPasswordField(20);
    JPasswordField passwordBox2 = new JPasswordField(20);
    JTextField streetBox = new JTextField(15);
    JTextField numberBox = new JTextField(15);
    JTextField additionBox = new JTextField(15);
    JTextField cityBox = new JTextField(15);

    // Button
    JButton register = new Commons.NButton("Registreren");

    // Set minimum sizes for the input boxes, to prevent them from being too small
    emailBox.setMinimumSize(
        new Dimension(emailBox.getPreferredSize().width + 20, emailBox.getPreferredSize().height));
    emailBox2.setMinimumSize(
        new Dimension(emailBox.getPreferredSize().width + 20, emailBox.getPreferredSize().height));

    // Add borders to create extra spacing
    registerTitle.setBorder(new EmptyBorder(0, 10, 20, 10));

    // Add items in order
    constraints.gridy = 1;
    titleAndCheck.setOpaque(false);
    boxRegister.add(titleAndCheck, BorderLayout.NORTH);

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
    boxRegister.add(register, BorderLayout.SOUTH);

    boxRegister.add(leftBox, BorderLayout.CENTER);
    boxRegister.add(rightBox, BorderLayout.EAST);

    register.setHorizontalAlignment(SwingConstants.RIGHT);

    // Styling
    boxRegister.setBackground(new Color(43, 43, 43));
    register.setForeground(Color.LIGHT_GRAY);
    registerTitle.setForeground(Color.LIGHT_GRAY);
    register.setBorder(new EmptyBorder(20, 20, 10, 0));
    leftBox.setBorder(new EmptyBorder(0, -50, 0, 0));

    // Add all the things
    mainRegister.add(logo(), BorderLayout.NORTH);
    mainRegister.add(boxRegister, BorderLayout.CENTER);
    mainRegister.add(credits(), BorderLayout.SOUTH);

    register.addActionListener(
        e -> {
          boolean matchingEmail = false;
          boolean matchingPassword = false;
          boolean validLocation = false;

          Map<String, JTextField> map = values;

          try {
            String email = map.get("E-mail").getText();
            String confirmEmail = map.get("Bevestig e-mail").getText();
            String password = map.get("Wachtwoord").getText();
            String confirmPassword = map.get("Bevestig wachtwoord").getText();
            String street = map.get("Straat").getText();
            int houseNumber = Integer.parseInt(map.get("Nummer").getText());

            String addition;
            try {
              addition = map.get("Toevoeging").getText();
            } catch (NullPointerException ex) {
              addition = ""; // Addition can be null, "" is replaced with SQL type NULL
            }

            String city = map.get("Woonplaats").getText();

            if (!email.equals("") && email.equals(confirmEmail)) {
              matchingEmail = true;
            }

            if (!password.equals("") && password.equals(confirmPassword)) {
              matchingPassword = true;
            }

            if (Stream.of(street, houseNumber, city).noneMatch(s -> s.equals(""))
                && (addition.equals("") || addition.matches("^[a-zA-Z]$"))) {
              validLocation = true;
            }

            if (matchingEmail && matchingPassword && validLocation) {
              new CreateOnDatabase()
                  .createAccount(false, email, street, houseNumber, addition, city, password);
            }

          } catch (NullPointerException | NumberFormatException ex) {
            Commons.exception(ex);
            Commons.showError("Incorrecte gegevens");
          }
        });

    switchLoginPane(register);

    return mainRegister;
  }

  private static void addBox(
      JPanel box, GridBagConstraints constraints, JTextField component, String description) {
    constraints.gridy++; // 4
    JPanel spacer = new JPanel();
    spacer.setOpaque(false);
    box.add(spacer, constraints);
    values.put(description, component);

    // Labels
    JPanel fieldLabel = new JPanel(new BorderLayout());
    JLabel descriptionLabel = new JLabel(description);
    descriptionLabel.setBorder(new EmptyBorder(5, 0, 5, 0));
    fieldLabel.add(descriptionLabel, BorderLayout.NORTH);
    fieldLabel.add(component, CENTER);

    constraints.gridy++;
    box.add(fieldLabel, constraints);

    // Styling
    component.setForeground(Color.LIGHT_GRAY);
    component.setCaretColor(Color.LIGHT_GRAY);

    descriptionLabel.setForeground(Color.LIGHT_GRAY);

    fieldLabel.setOpaque(false);

    component.setBorder(new EmptyBorder(8, 8, 8, 8));
    component.setBackground(new Color(20, 20, 20));
  }
}
