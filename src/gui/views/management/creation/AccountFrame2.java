package com.netflix.gui.views.management.creation;

import com.netflix.commons.Commons;
import com.netflix.gui.NetflixFrame;
import com.netflix.gui.views.management.AdminView;
import com.netflix.gui.views.management.accountListTable;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.util.Map;
import java.util.stream.Stream;

import static javax.swing.JOptionPane.showMessageDialog;

public class AccountFrame2 extends CreationFrame {

    public AccountFrame2() {
    super("Nieuw account");

    String[] fields = {
      "E-mail",
      "Bevestig e-mail",
      "Wachtwoord",
      "Bevestig wachtwoord",
      "Straat",
      "Nummer",
      "Toevoeging",
      "Woonplaats"
    };

    for (String str : fields) addTextField(str);

    JButton addAccount = new JButton("Toevoegen");
    actionListenerAccountPress(addAccount);

    constraints.gridy++;
    wrapper.add(addAccount);
  }

  private void actionListenerAccountPress(JButton button) {
      button.addActionListener(
        (ActionEvent e) -> {
          boolean matchingEmail = false;
          boolean matchingPassword = false;
          boolean validLocation = false;

          Map<String, JTextField> map = values;
          String email = map.get("email").getText();
          String confirmEmail = map.get("Bevestig e-mail").getText();
          String password = map.get("Wachtwoord").getText();
          String confirmPassword = map.get("Bevestig wachtwoord").getText();
          String street = map.get("Straat").getText();
          int houseNumber = Integer.parseInt(map.get("Nummer").getText());
          String addition = map.get("Toevoeging").getText();
          String city = map.get("Woonplaats").getText();

          if (!email.equals("") && email.equals(confirmEmail)) matchingEmail = true;

          if (!password.equals("") && email.equals(confirmPassword)) matchingPassword = true;

          if (Stream.of(street, houseNumber, city).noneMatch(s -> s.equals("")))
            validLocation = true;

          if (matchingEmail && matchingPassword && validLocation) {
            new EntityCreation()
                .createAccount(false, email, street, houseNumber, addition, city, password);

            dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));

            values.clear();
            Commons.clearPane(NetflixFrame.lpane);
            Commons.clearPane(accountListTable.tablePanel);
            Commons.clearPane(AdminView.panel());
            NetflixFrame.lpane.add(AdminView.panel());

          } else {
            showMessageDialog(
                this,
                "Gegevens zijn incorrect. Controleer alle velden op typfouten",
                "Incorrecte gegevens",
                JOptionPane.WARNING_MESSAGE);
          }
        });
  }
}
