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

  public static JPanel panel() {
    JTable table = new JTable();
    DefaultTableModel tableModel = new DefaultTableModel(0, 0);

    // Table headers
    String[] columnNames = {"E-mail", "Straat", "Huisnummer", "Toevoeging", "Woonplaats", "Admin"};

    tableModel.setColumnIdentifiers(columnNames);
    table.setModel(tableModel);

    // Add all episodes in the serie
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

    JTableHeader header = table.getTableHeader();
    header.setForeground(new Color(151, 2, 4));
    header.setFont(new Font(header.getFont().getName(), Font.BOLD, 12));
    header.setOpaque(false);

    table.setShowGrid(true);
    table.setGridColor(Color.LIGHT_GRAY);

    // Make it scrollable
    JScrollPane tableScroll =
        new JScrollPane(
            table,
            ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

    JPanel episodes = new JPanel(new BorderLayout());

    episodes.setOpaque(false);

    tablePanel.add(header, BorderLayout.NORTH);
    tablePanel.add(tableScroll, BorderLayout.CENTER);

    JLabel accounts = new JLabel("Accounts");
    accounts.setFont(new Font(accounts.getFont().getName(), accounts.getFont().getStyle(), 14));
    accounts.setBorder(new EmptyBorder(0, 0, 8, 0));

    episodes.add(accounts, BorderLayout.NORTH);
    episodes.add(tablePanel, BorderLayout.CENTER);

    wrapper.add(episodes, BorderLayout.CENTER);
    wrapper.setBackground(Color.WHITE);
    wrapper.setBorder(new EmptyBorder(5, 5, 5, 5));

    tableScroll.setBorder(BorderFactory.createEmptyBorder());

    episodes.addComponentListener(new ResizeListener(tableScroll));

    TableColumn tc = table.getColumnModel().getColumn(5);
    tc.setCellEditor(table.getDefaultEditor(Boolean.class));
    tc.setCellRenderer(table.getDefaultRenderer(Boolean.class));

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
