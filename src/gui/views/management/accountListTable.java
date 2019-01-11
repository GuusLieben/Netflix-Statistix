package com.netflix.gui.views.management;

import com.netflix.entities.Account;
import com.netflix.gui.commons.GradientPanel;
import com.netflix.gui.commons.NButton;
import com.netflix.gui.listeners.ActionListeners;
import com.netflix.gui.views.management.creation.AccountFrame2;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;

import static com.netflix.Netflix.database;

public class accountListTable {

  public static JPanel tablePanel = new JPanel(new BorderLayout());

  static JPanel accountListTable() {
    JTable table = new JTable();
    DefaultTableModel tableModel = new DefaultTableModel(0, 0);
    TableColumn tc;
    String[] columnNames = {
      "E-mail", "Straat", "Huisnummer", "Toevoeging", "Woonplaats", "Admin", "DatabaseId"
    };
    JTableHeader header;
    JPanel tableAccounts;
    JScrollPane tableScroll =
        new JScrollPane(
            table,
            ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    JButton createAccount;

    tableModel.setColumnIdentifiers(columnNames);
    table.setModel(tableModel);

    CheckBoxModelListener cml = new CheckBoxModelListener();
    cml.table = table;
    tableModel.addTableModelListener(cml);

    // Add all tableAccounts in the serie
    for (Account account : Account.accounts) {

      tableModel.addRow(
          new Object[] {
            account.getEmail(),
            account.getStreet(),
            account.getHouseNumber(),
            account.getAddition(),
            account.getCity(),
            account.isAdmin(),
            account.databaseId
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

    TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel());
    table.setRowSorter(sorter);

    ArrayList<RowSorter.SortKey> sortKeys = new ArrayList<>(25);
    sortKeys.add(new RowSorter.SortKey(6, SortOrder.ASCENDING)); // First sort it by season
    sorter.setSortKeys(sortKeys);

    JPanel buttonSplit = new GradientPanel().getGradientPanel();
    buttonSplit.setLayout(new BorderLayout());

    createAccount = new NButton("Nieuw account");
    ActionListeners.showFrame(createAccount, new AccountFrame2());

    buttonSplit.add(createAccount, BorderLayout.NORTH);
    buttonSplit.add(new NButton("Nieuwe aflevering"), BorderLayout.WEST);
    buttonSplit.add(new NButton("Nieuwe serie"), BorderLayout.CENTER);
    buttonSplit.add(new NButton("Nieuw seizoen"), BorderLayout.EAST);
    buttonSplit.add(new NButton("Nieuwe film"), BorderLayout.SOUTH);
    buttonSplit.setBorder(new EmptyBorder(10, 10, 10, 10));

    tableAccounts = new JPanel(new BorderLayout());
    tableAccounts.add(tablePanel, BorderLayout.CENTER);
    tableAccounts.add(buttonSplit, BorderLayout.SOUTH);
    tableAccounts.addComponentListener(new ResizeListener(tableScroll));

    tableScroll.setBorder(new EmptyBorder(0, 0, 10, 0));

    tc = table.getColumnModel().getColumn(5);
    tc.setCellEditor(table.getDefaultEditor(Boolean.class));
    tc.setCellRenderer(table.getDefaultRenderer(Boolean.class));

    return tableAccounts;
  }

  // Resize the scrollpane with the inner panel to make it easily adjustable
  static class ResizeListener extends ComponentAdapter {
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
}

class CheckBoxModelListener implements TableModelListener {

  JTable table;

  @Override
  public void tableChanged(TableModelEvent e) {
    int row = e.getFirstRow();
    int column = e.getColumn();
    tableUpdate(row, column); // Series
  }

  private void tableUpdate(int row, int column) {
    if (0 <= column && column <= 6) {
      String qr =
          "UPDATE Account SET isAdmin = ?, Email = ?, Straatnaam = ?, Huisnummer = ?, Toevoeging = ?, Woonplaats = ? WHERE AccountId = ?";
      // Columns in order of the query, not in order of the table
      boolean isAdmin = Boolean.parseBoolean(table.getValueAt(row, 5).toString());
      String email = table.getValueAt(row, 0).toString();
      String street = table.getValueAt(row, 1).toString();
      int houseNum = Integer.parseInt(table.getValueAt(row, 2).toString());
      String addition;
      try {
        addition = table.getValueAt(row, 3).toString();
      } catch (Exception ex) {
        addition = "";
      }
      String city = table.getValueAt(row, 4).toString();
      int id = Integer.parseInt(table.getValueAt(row, 6).toString());

      Object[] arr = {isAdmin, email, street, houseNum, addition, city, id};

      database.executeSqlNoResult(qr, arr);

      Account acc = Account.getByDbId(id);
      acc.setAdmin(isAdmin);
      acc.setEmail(email);
      acc.setStreet(street);
      acc.setHouseNumber(houseNum);
      acc.setAddition(addition);
      acc.setCity(city);
    }
  }
}
