/*
 * Copyright © 2018. Guus Lieben.
 * All rights reserved.
 */

package com.netflix.gui.views;

import com.netflix.commons.Commons;
import com.netflix.entities.Account;
import com.netflix.gui.commons.GradientPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;

public class AdminView {

  public static JPanel panel() {
    JPanel main = new JPanel(new GridBagLayout());

    JPanel accounts = new JPanel(new BorderLayout());

    GridBagConstraints constraints = new GridBagConstraints();
    constraints.gridy = 0;

    main.add(new JLabel("You're an admin, whoo!"), constraints);

    JTable table = new JTable();
    DefaultTableModel tableModel = new DefaultTableModel(0, 0);

    String[] columnNames = {"Email", "Location", "Type"};

    tableModel.setColumnIdentifiers(columnNames);
    table.setModel(tableModel);

    for (Account account : Commons.accounts) {
      String type = "User";
      if (account.isAdmin()) type = "Administrator";
      tableModel.addRow(new Object[] {account.getEmail(), account.getLocation(), type});
    }

    JTableHeader header = table.getTableHeader();
    header.setForeground(new Color(151, 2, 4));
    header.setFont(new Font(header.getFont().getName(), Font.BOLD, 12));
    header.setOpaque(false);
    table.setShowGrid(true);
    table.setGridColor(Color.LIGHT_GRAY);

    JScrollPane tableScroll =
        new JScrollPane(
            table,
            ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

    accounts.add(header, BorderLayout.NORTH);
    accounts.add(table, BorderLayout.CENTER);

    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    tableScroll.setMaximumSize(new Dimension(table.getWidth(), dim.height / 2));

    TableColumnModel columnModel = table.getColumnModel();

    for (int column = 0; column < table.getColumnCount(); column++) {
      int width = 15; // Min width
      for (int row = 0; row < table.getRowCount(); row++) {
        TableCellRenderer renderer = table.getCellRenderer(row, column);
        Component comp = table.prepareRenderer(renderer, row, column);
        width = Math.max(comp.getPreferredSize().width + 1, width);
      }
      columnModel.getColumn(column).setPreferredWidth(width + 10);
    }

    constraints.gridy++;
    main.add(accounts, constraints);

    accounts.setOpaque(false);
    main.setOpaque(false);

    return main;
  }
}
