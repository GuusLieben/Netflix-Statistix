
package com.netflix.gui.views.management;

import javax.swing.JPanel;
import java.awt.BorderLayout;

public class AdminView {

  private static JPanel wrapper = new JPanel(new BorderLayout());

  public static JPanel panel() {
    wrapper.add(buttonGroupMenu.buttonGroup(), BorderLayout.NORTH);
    wrapper.add(accountListTable.accountListTable(), BorderLayout.CENTER);
    return wrapper;
  }
}
