package com.netflix.gui.views;

import com.netflix.commons.*;
import com.netflix.entities.*;
import com.netflix.gui.*;
import com.netflix.gui.views.subpanels.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

import static com.netflix.Netflix.database;

public class AdminView {

  private static JPanel wrapper = new JPanel(new BorderLayout());

  public static JPanel panel() {
    wrapper.add(buttonGroupMenu.buttonGroup(), BorderLayout.NORTH);
    wrapper.add(accountListTable.accountListTable(), BorderLayout.CENTER);
    return wrapper;
  }

  static class buttonGroupMenu {

    private static GraphView mediaGraph =
        new GraphView(
            "Media Object Id", "Percentage", "Gemiddelde kijkcijfers over totaal aantal profielen");

    private static void showGraph(JButton button, GraphView graph) {
      button.addActionListener(
          e -> {
            if (graph.frame.isVisible()) graph.showGraph(false);
            else graph.showGraph(true);
          });
    }

    private static void showFrame(JButton button, JFrame frame) {
      button.addActionListener(
          e -> {
            if (frame.isVisible()) frame.setVisible(false);
            else frame.setVisible(true);
          });
    }

    static JPanel buttonGroup() {
      for (MediaObject object : MediaObject.objectIds) {
        mediaGraph.addNumber(object.objectId, object.getWatchedPercentage());
      }

      JPanel wrapper = new JPanel(new GridLayout(1, 3, 0, 25));

      JButton mediaGraph = new JButton("Media grafiek");
      JButton watchLogs = new JButton("Gebruiker statistieken");
      JButton viewEnts = new JButton("Entiteiten");

      showFrame(viewEnts, DeleteFrame.frame());
      showGraph(mediaGraph, buttonGroupMenu.mediaGraph);
      showFrame(watchLogs, watchedMediaList.watchedMediaFrame());

      wrapper.add(mediaGraph);
      wrapper.add(watchLogs);
      wrapper.add(viewEnts);
      wrapper.setOpaque(false);

      return wrapper;
    }
  }

  static class watchedMediaList {

    private static JFrame watchedMediaFrame() {
      JFrame frame = new JFrame();
      frame.add(watchedMediaPanel());

      // Keep in mind that the bottom pane has to be wide enough to prevent overdraws
      frame.setMinimumSize(new Dimension(500, 350));
      frame.setSize(500, 350);

      frame.pack();

      return frame;
    }

    private static JPanel watchedMediaPanel() {
      JPanel wrapper = new JPanel(new BorderLayout());

      JScrollPane scrollPane =
          new JScrollPane(
              watchedMediaList(),
              ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
              ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

      wrapper.add(Common.logo(), BorderLayout.NORTH);
      wrapper.add(scrollPane, BorderLayout.CENTER);
      wrapper.add(Common.bottomPane(), BorderLayout.SOUTH);

      return wrapper;
    }

    private static JLabel watchedMediaList() {
      JLabel allwatched = new JLabel("<html>");

      for (Account acc : Account.accounts) {
        allwatched.setText(
            String.format("%s<br><b>Account : %s</b>", allwatched.getText(), acc.getEmail()));

        for (Account.Profile prof : acc.getProfiles()) {
          allwatched.setText(
              allwatched.getText()
                  + "<br><i>Profiel : "
                  + prof.getName()
                  + "</i><br>Bekeken media: <br>");
          for (MediaObject obj : prof.getMediaWatched()) {
            allwatched.setText(
                String.format(
                    "%s&nbsp;&nbsp;<b>%s</b> (Bekeken door %s%% van het totaal aantal gebruikers)<br>",
                    allwatched.getText(),
                    obj.getTitle(),
                    Commons.percentage(obj.getWatchedPercentage())));
          }

          if (prof.getMediaWatched().size() == 0)
            allwatched.setText(String.format("%s&nbsp;<i>Geen</i><br>", allwatched.getText()));

          allwatched.setText(allwatched.getText() + "<br>");
        }
      }

      allwatched.setText(allwatched.getText() + "</html>");
      allwatched.setOpaque(false);
      return allwatched;
    }
  }

  public static class accountListTable {

    public static final JPanel tablePanel = new JPanel(new BorderLayout());

    static JPanel accountListTable() {
      JTable table =
          new JTable() {
            @Override
            public boolean isCellEditable(int row, int column) {
              return column != 5;
            }
          };
      DefaultTableModel tableModel = new DefaultTableModel(0, 0);
      TableColumn tc;
      String[] columnNames = {
        "E-mail",
        "Straat",
        "Huisnummer",
        "Toevoeging",
        "Woonplaats",
        "Profielen",
        "Admin",
        "DatabaseId"
      };
      JTableHeader header;
      JPanel tableAccounts;
      JScrollPane tableScroll =
          new JScrollPane(
              table,
              ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
              ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

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
              account.getProfiles().size(),
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

      JPanel createSwitch = new JPanel(new BorderLayout());
      createSwitch.setBorder(new EmptyBorder(10, 10, 10, 10));

      JButton createButton = new JButton("Niewe entiteit met type : ");
      createSwitch.add(createButton, BorderLayout.WEST);
      createButton.setHorizontalAlignment(JLabel.RIGHT);

      String[] arr = {
        "Account", "Aflevering", "Seizoen", "Serie", "Film", "Genre", "Taal", "Classificatie"
      };
      JComboBox cb = new JComboBox(arr);
      createSwitch.add(cb, BorderLayout.CENTER);

      Map<String, CreationFrame> entities =
          new HashMap<String, CreationFrame>() {
            {
              put("Account", new ChildFrames.AccountCreation());
              put("Aflevering", new ChildFrames.EpisodeCreation());
              put("Seizoen", new ChildFrames.SeasonCreation());
              put("Serie", new ChildFrames.SerieCreation());
              put("Film", new ChildFrames.FilmCreation());
              put("Genre", new ChildFrames.GenreCreation());
              put("Taal", new ChildFrames.LanguageCreation());
              put("Classificatie", new ChildFrames.RatingCreation());
            }
          };

      createButton.addActionListener(
          e -> {
            String entity = cb.getSelectedItem().toString();
            entities.get(entity).setVisible(true);
          });

      tableAccounts = new JPanel(new BorderLayout());
      tableAccounts.add(tablePanel, BorderLayout.CENTER);
      tableAccounts.add(createSwitch, BorderLayout.SOUTH);
      tableAccounts.addComponentListener(new ResizeListener(tableScroll));

      tableScroll.setBorder(new EmptyBorder(0, 0, 10, 0));

      tc = table.getColumnModel().getColumn(6);
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
                accountListTable.tablePanel.getWidth(),
                accountListTable.tablePanel.getHeight() / 2));
      }
    }
  }

  static class CheckBoxModelListener implements TableModelListener {

    JTable table;

    @Override
    public void tableChanged(TableModelEvent e) {
      int row = table.getSelectedRow();
      int column = e.getColumn();
      tableUpdate(row, column); // Series
    }

    private void tableUpdate(int row, int column) {
      if (0 <= column && column <= 6) {
        String qr =
            "UPDATE Account SET isAdmin = ?, Email = ?, Straatnaam = ?, Huisnummer = ?, Toevoeging = ?, Woonplaats = ? WHERE AccountId = ?";
        // Columns in order of the query, not in order of the table
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
        int id = Integer.parseInt(table.getValueAt(row, 7).toString());
        boolean isAdmin = false;

        if (!(addition.equals("") || addition.matches("^[a-zA-Z]$"))) {
          addition = "";
          table.setValueAt("", row, 3);
        }

        if (!Account.getByDbId(id).isAdmin()) isAdmin = true;

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
}
