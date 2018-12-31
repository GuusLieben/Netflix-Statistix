/*
 * Copyright © 2018. Guus Lieben.
 * All rights reserved.
 */

package com.netflix.gui.views.management;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class AdminView {

  public static JPanel wrapper = new JPanel(new BorderLayout());

  public static JPanel panel() {
    wrapper.setBackground(Color.WHITE);

    wrapper.add(buttonGroupMenu.buttonGroup(), BorderLayout.NORTH);
    wrapper.add(accountListTable.accountListTable(), BorderLayout.CENTER);


    return wrapper;
  }
}
