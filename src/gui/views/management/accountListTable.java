/*
 * Copyright © 2018. Guus Lieben.
 * All rights reserved.
 */

package com.netflix.gui.views.management;

import com.netflix.entities.*;
import com.netflix.gui.commons.*;
import com.netflix.gui.listeners.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

public class accountListTable {

  public static JPanel tablePanel = new JPanel(new BorderLayout());

  public static JPanel accountListTable() {
    JTable table = new JTable();
    DefaultTableModel tableModel = new DefaultTableModel(0, 0);
    TableColumn tc;
    String[] columnNames = {"E-mail", "Straat", "Huisnummer", "Toevoeging", "Woonplaats", "Admin"};
    JTableHeader header;
    JPanel tableAccounts;
    JLabel accounts;
    JScrollPane tableScroll =
        new JScrollPane(
            table,
            ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    JButton createAccount;
    JPanel panelHead;

    tableModel.setColumnIdentifiers(columnNames);
    table.setModel(tableModel);

    // Add all tableAccounts in the serie
    for (Account account : Account.accounts) {

      tableModel.addRow(
          new Object[] {
            account.getEmail(),
            account.getStreet(),
            account.getHouseNumber(),
            account.getAddition(),
            account.getCity(),
            account.isAdmin()
          });
    }

    header = table.getTableHeader();
    header.setForeground(new Color(151, 2, 4));
    header.setFont(new Font(header.getFont().getName(), Font.BOLD, 12));
    header.setOpaque(false);

    table.setShowGrid(true);
    table.setGridColor(Color.LIGHT_GRAY);

    tablePanel.add(header, BorderLayout.NORTH);
    tablePanel.add(tableScroll, BorderLayout.CENTER);

    tablePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
    tablePanel.setOpaque(false);

    JPanel buttonSplit = new GradientPanel().getGradientPanel();
    buttonSplit.setLayout(new BorderLayout());

    createAccount = new NButton("Nieuw account");
    ActionListeners.showFrame(createAccount, createAccountFrame.createAccountFrame());

    buttonSplit.add(createAccount, BorderLayout.NORTH);
    buttonSplit.add(new NButton("Nieuwe aflevering"), BorderLayout.WEST);
    buttonSplit.add(new NButton("Nieuwe serie"), BorderLayout.CENTER);
    buttonSplit.add(new NButton("Nieuw seizoen"), BorderLayout.EAST);
    buttonSplit.add(new NButton("Nieuwe film"), BorderLayout.SOUTH);
    buttonSplit.setBackground(new Color(151, 2, 4));
    buttonSplit.setBorder(new EmptyBorder(10, 10, 10, 10));

    tableAccounts = new JPanel(new BorderLayout());
    tableAccounts.setOpaque(false);
    tableAccounts.add(tablePanel, BorderLayout.CENTER);
    tableAccounts.add(buttonSplit, BorderLayout.SOUTH);
    tableAccounts.addComponentListener(new ResizeListener(tableScroll));

    tableScroll.setBorder(new EmptyBorder(0, 0, 10, 0));

    tc = table.getColumnModel().getColumn(5);
    tc.setCellEditor(table.getDefaultEditor(Boolean.class));
    tc.setCellRenderer(table.getDefaultRenderer(Boolean.class));

    return tableAccounts;
  }
}

// Resize the scrollpane with the inner panel to make it easily adjustable
class ResizeListener extends ComponentAdapter {
  private JScrollPane pane;

  ResizeListener(JScrollPane pane) {
    this.pane = pane;
  }

  @Override
  public void componentResized(ComponentEvent e) {
    pane.setPreferredSize(
        new Dimension(
            accountListTable.tablePanel.getWidth(), accountListTable.tablePanel.getHeight() / 2));
  }
}
