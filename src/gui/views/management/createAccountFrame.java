
package com.netflix.gui.views.management;

import com.netflix.commons.*;
import com.netflix.entities.*;
import com.netflix.gui.*;
import com.netflix.gui.commons.*;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class createAccountFrame {

  private static JPanel main;
  private static GridBagConstraints constraints;
  private static JTextField email = new JTextField(20);
  private static JTextField confirmEmail = new JTextField(20);
  private static JTextField password = new JTextField(20);
  private static JTextField confirmPassword = new JTextField(20);
  private static JTextField street = new JTextField(20);
  private static JTextField houseNumber = new JTextField(20);
  private static JTextField addition = new JTextField(20);
  private static JTextField city = new JTextField(20);
  private static JFrame frame;

  public static JFrame createAccountFrame() {
    frame = new JFrame();

    frame.setSize(300, 500);
    frame.setMinimumSize(new Dimension(300, 500));
    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    frame.setLocation(
        dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);

    main = new GradientPanel().getGradientPanel();
    main.setLayout(new GridBagLayout());
    main.setBackground(new Color(43, 43, 43));
    constraints = new GridBagConstraints();
    constraints.gridy = 0;
    main.setBorder(new EmptyBorder(10, 10, 10, 10));

    email.setCaretColor(Color.LIGHT_GRAY);
    confirmEmail.setCaretColor(Color.LIGHT_GRAY);
    password.setCaretColor(Color.LIGHT_GRAY);
    confirmEmail.setCaretColor(Color.LIGHT_GRAY);
    street.setCaretColor(Color.LIGHT_GRAY);
    houseNumber.setCaretColor(Color.LIGHT_GRAY);
    addition.setCaretColor(Color.LIGHT_GRAY);
    city.setCaretColor(Color.LIGHT_GRAY);

    addComponent(new JLabel("<htmL><h3>Nieuw account</h3></html>"));
    addComponent(spacer());
    addComponent(new JLabel("E-mail"));
    addComponent(email);
    addComponent(spacer());
    addComponent(new JLabel("Bevestig e-mail"));
    addComponent(confirmEmail);
    addComponent(spacer());
    addComponent(new JLabel("Wachtwoord"));
    addComponent(password);
    addComponent(spacer());
    addComponent(new JLabel("Bevestig wachtwoord"));
    addComponent(confirmPassword);
    addComponent(spacer());
    addComponent(new JLabel("Straat"));
    addComponent(street);
    addComponent(spacer());
    addComponent(new JLabel("Nummer"));
    addComponent(houseNumber);
    addComponent(spacer());
    addComponent(new JLabel("Toevoeging"));
    addComponent(addition);
    addComponent(spacer());
    addComponent(new JLabel("Woonplaats"));
    addComponent(city);
    addComponent(spacer());

    JButton addAccount = new NButton("Toevoegen");
//    ActionListeners.mouseEventUnderline(addAccount);
    addAcount(addAccount);
    addComponent(addAccount);

    frame.add(Common.logo(), BorderLayout.NORTH);
    frame.add(main, BorderLayout.CENTER);

    // Make the frame visible
    frame.pack();

    return frame;
  }

  private static JPanel spacer() {
    JPanel spacer = new JPanel();
    spacer.setMinimumSize(new Dimension(20, 5));
    spacer.setOpaque(false);
    return spacer;
  }

  private static void addComponent(JComponent component) {
    component.setForeground(Color.LIGHT_GRAY);
    component.setBorder(new EmptyBorder(5, 5, 5, 5));
    component.setBackground(new Color(20, 20, 20));
    main.add(component, constraints);
    constraints.gridy++;
  }

  private static void addAcount(JButton button) {
    button.addActionListener(
        (ActionEvent e) -> {
          boolean validEmail = false;
          boolean validPassword = false;
          boolean validLocation = false;

          String emailStr = email.getText();
          String confirmEmailStr = confirmEmail.getText();
          String passwordStr = password.getText();
          String confirmPasswordStr = confirmPassword.getText();
          String streetStr = street.getText();
          String houseNumberStr = houseNumber.getText();
          int houseNumberInt = Integer.parseInt(houseNumberStr);
          String additionStr = addition.getText();
          String cityStr = city.getText();

          if (!emailStr.equals("") && emailStr.equals(confirmEmailStr)) validEmail = true;

          if (!passwordStr.equals("") && passwordStr.equals(confirmPasswordStr))
            validPassword = true;

          if (!streetStr.equals("") && !houseNumberStr.equals("") && !cityStr.equals(""))
            validLocation = true;

          if (validEmail && validPassword && validLocation) {
            Account newAccount =
                new Account(1,
                    false, emailStr, streetStr, houseNumberInt, additionStr, cityStr, passwordStr);

            // Close the frame, saves us from having to clear all fields and hiding it
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));

            // Refreshes the management frame to load newest data
            Commons.clearPane(NetflixFrame.lpane);
            Commons.clearPane(accountListTable.tablePanel);
            Commons.clearPane(AdminView.panel());
            NetflixFrame.lpane.add(AdminView.panel());

          } else {
            JOptionPane.showMessageDialog(
                frame,
                "Entered information was invalid",
                "Invalid input",
                JOptionPane.WARNING_MESSAGE);
          }
        });
  }
}
