
package com.netflix.gui.views.management.creation;

import com.netflix.commons.Commons;
import com.netflix.gui.NetflixFrame;
import com.netflix.gui.commons.Common;
import com.netflix.gui.commons.GradientPanel;
import com.netflix.gui.commons.NButton;
import com.netflix.gui.views.management.AdminView;
import com.netflix.gui.views.management.accountListTable;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;

import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.NORTH;
import static java.awt.Color.LIGHT_GRAY;
import static java.awt.event.WindowEvent.WINDOW_CLOSING;
import static javax.swing.JOptionPane.WARNING_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;

public class AccountFrame extends JFrame {

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

  public AccountFrame() {
    setSize(300, 500);
    setMinimumSize(new Dimension(300, 500));
    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    setLocation(
        dim.width / 2 - getSize().width / 2, dim.height / 2 - getSize().height / 2);

    main = new GradientPanel().getGradientPanel();
    main.setLayout(new GridBagLayout());
    main.setBackground(new Color(43, 43, 43));
    constraints = new GridBagConstraints();
    constraints.gridy = 0;
    main.setBorder(new EmptyBorder(10, 10, 10, 10));

    email.setCaretColor(LIGHT_GRAY);
    confirmEmail.setCaretColor(LIGHT_GRAY);
    password.setCaretColor(LIGHT_GRAY);
    confirmEmail.setCaretColor(LIGHT_GRAY);
    street.setCaretColor(LIGHT_GRAY);
    houseNumber.setCaretColor(LIGHT_GRAY);
    addition.setCaretColor(LIGHT_GRAY);
    city.setCaretColor(LIGHT_GRAY);

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
    addAcount(addAccount);
    addComponent(addAccount);

    add(Common.logo(), NORTH);
    add(main, CENTER);

    // Make the visible
    pack();
  }

  private JPanel spacer() {
    JPanel spacer = new JPanel();
    spacer.setMinimumSize(new Dimension(20, 5));
    spacer.setOpaque(false);
    return spacer;
  }

  private void addComponent(JComponent component) {
    component.setForeground(LIGHT_GRAY);
    component.setBorder(new EmptyBorder(5, 5, 5, 5));
    component.setBackground(new Color(20, 20, 20));
    main.add(component, constraints);
    constraints.gridy++;
  }

  private void addAcount(JButton button) {
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
            new EntityCreation().createAccount(
                    false, emailStr, streetStr, houseNumberInt, additionStr, cityStr, passwordStr);

            // Close the  saves us from having to clear all fields and hiding it
            dispatchEvent(new WindowEvent( this, WINDOW_CLOSING));

            // Refreshes the management to load newest data
            Commons.clearPane(NetflixFrame.lpane);
            Commons.clearPane(accountListTable.tablePanel);
            Commons.clearPane(AdminView.panel());
            NetflixFrame.lpane.add(AdminView.panel());

          } else {
            showMessageDialog(this,
                "Entered information was invalid",
                "Invalid input",
                WARNING_MESSAGE);
          }
        });
  }
}
