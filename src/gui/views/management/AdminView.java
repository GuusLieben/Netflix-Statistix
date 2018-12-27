/*
 * Copyright © 2018. Guus Lieben.
 * All rights reserved.
 */

package com.netflix.gui.views;

import com.netflix.entities.Account;
import com.netflix.entities.Profile;
import com.netflix.entities.abstracts.MediaObject;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class AdminView {

  public static JPanel wrapper = new JPanel(new BorderLayout());
  public static JPanel tablePanel = new JPanel(new BorderLayout());

  private static JPanel accountListTable() {
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

    accounts = new JLabel("Accounts");
    accounts.setFont(new Font(accounts.getFont().getName(), accounts.getFont().getStyle(), 14));
    accounts.setBorder(new EmptyBorder(0, 0, 8, 0));

    createAccount = new JButton("Nieuw account");

    panelHead = new JPanel(new BorderLayout());
    panelHead.add(accounts, BorderLayout.NORTH);
    panelHead.add(createAccount, BorderLayout.SOUTH);
    panelHead.setOpaque(false);

    tableAccounts = new JPanel(new BorderLayout());
    tableAccounts.setOpaque(false);
    tableAccounts.add(panelHead, BorderLayout.NORTH);
    tableAccounts.add(tablePanel, BorderLayout.CENTER);
    tableAccounts.addComponentListener(new ResizeListener(tableScroll));

    tableScroll.setBorder(BorderFactory.createEmptyBorder());

    tc = table.getColumnModel().getColumn(5);
    tc.setCellEditor(table.getDefaultEditor(Boolean.class));
    tc.setCellRenderer(table.getDefaultRenderer(Boolean.class));

    return tableAccounts;
  }

  public static JPanel panel() {
    wrapper.add(accountListTable(), BorderLayout.CENTER);
    wrapper.setBackground(Color.WHITE);
    wrapper.setBorder(new EmptyBorder(5, 5, 5, 5));

    JLabel allwatched = new JLabel("<html>");

    int profileCount = Account.accounts.stream().mapToInt(acc -> acc.getProfiles().size()).sum();

    for (Account acc : Account.accounts)
      for (Profile prof : acc.getProfiles())
        for (MediaObject obj : prof.getMediaWatched()) {
          double percentageWatchedBy = (double) obj.getWatchedByAmount() / profileCount * 100;
          allwatched.setText(
              String.format(
                  "%s<br>[ %s ] :: %s watched media : <b>%s</b> (Bekeken door %s%% van het totaal aantal gebruikers)",
                  allwatched.getText(),
                  acc.getEmail(),
                  prof.getName(),
                  obj.getTitle(),
                  percentageWatchedBy));
        }

    allwatched.setText(allwatched.getText() + "</html>");

    wrapper.add(allwatched, BorderLayout.SOUTH);

    return wrapper;
  }
}
// Resize the scrollpane with the inner panel to make it easily adjustable
class ResizeListener extends ComponentAdapter {
  private JScrollPane pane;

  ResizeListener(JScrollPane pane) {
    this.pane = pane;
  }

  public void componentResized(ComponentEvent e) {
    pane.setPreferredSize(
        new Dimension(AdminView.tablePanel.getWidth(), AdminView.tablePanel.getHeight() / 2));
  }
}
