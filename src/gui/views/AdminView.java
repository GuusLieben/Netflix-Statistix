/*
 * Copyright © 2018. Guus Lieben.
 * All rights reserved.
 */

package com.netflix.gui.views;

import com.netflix.commons.Commons;
import com.netflix.entities.Account;
import com.netflix.entities.Profile;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class AdminView {

  public static JPanel panel() {
    JPanel wrapper = new JPanel(new BorderLayout());
    JPanel main = new JPanel(new GridBagLayout());

    Commons.logger.warning("Management view loaded!");

    JScrollPane pane = new JScrollPane();
    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

    for (Account account : Account.accounts)
      for (Profile profile : account.getProfiles()) panel.add(addPanel(profile, main));
    pane.getViewport().add(panel);

    main.add(pane);
    main.setBorder(new EmptyBorder(0, 0, 0, 0));

    wrapper.add(main, BorderLayout.WEST);

    return wrapper;
  }

  public static JPanel addPanel(Profile profile, JPanel parent) {
    JPanel panel = new JPanel(new BorderLayout());
    panel.add(
        new JLabel(
            String.format(
                "Account: %s  ::  Profiel: %s", profile.getAccount().getEmail(), profile.getName())),
        BorderLayout.NORTH);
    panel.add(new JButton("Change password"), BorderLayout.WEST);
    panel.add(new JButton("Change e-mail"), BorderLayout.CENTER);
    panel.add(new JButton("Change account type"), BorderLayout.EAST);
    return panel;
  }
}
