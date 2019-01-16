package com.netflix.gui.views.subpanels;

import com.netflix.*;
import com.netflix.commons.*;
import com.netflix.entities.*;
import com.netflix.gui.*;
import com.netflix.gui.views.*;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class DeleteFrame extends JFrame {

  public static DeleteFrame frame() {
    DeleteFrame frame = new DeleteFrame();
    frame.getContentPane().setLayout(new BorderLayout());
    frame.add(Common.logo(), BorderLayout.NORTH);
    frame.add(overviewTable(), BorderLayout.CENTER);
    frame.add(Common.bottomPane(), BorderLayout.SOUTH);
    frame.setSize(600, 500);
    frame.setMinimumSize(new Dimension(600, 500));
    return frame;
  }

  private static JPanel overviewTable() {
    JPanel panel = new JPanel(new BorderLayout());
    JTable table = new JTable();

    DefaultTableModel dtm = new DefaultTableModel();

    String[] columnNames = {"Type", "Primary Identifier", "Secondary Identifier", "Database Id"};
    JTableHeader header;
    JScrollPane tableScroll =
        new JScrollPane(
            table,
            ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

    dtm.setColumnIdentifiers(columnNames);
    table.setModel(dtm);

    for (Entity ent : Entity.entities) {

      String type = ent.getChildName();
      String primaryId = ent.getPrimaryIdentifier().toString();
      String secondaryId;
      if (ent.getSecondaryIdentifier() == null) secondaryId = "NULL";
      else secondaryId = ent.getSecondaryIdentifier().toString();
      Object databaseId;
      if (type.equals("Language")) databaseId = primaryId;
      else if (type.equals("AgeRating")) databaseId = secondaryId;
      else databaseId = "" + ent.databaseId;

      dtm.addRow(new Object[] {type, primaryId, secondaryId, databaseId});
    }

    header = table.getTableHeader();
    header.setForeground(new Color(151, 2, 4));
    header.setFont(new Font(header.getFont().getName(), Font.BOLD, 12));
    header.setOpaque(false);

    TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel());
    table.setRowSorter(sorter);

    ArrayList<RowSorter.SortKey> sortKeys = new ArrayList<>(25);
    sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
    sorter.setSortKeys(sortKeys);

    table.setShowGrid(true);
    table.setGridColor(Color.LIGHT_GRAY);

    panel.add(tableScroll, BorderLayout.CENTER);

    JButton remove = new JButton("Verwijder geselecteerde entiteit");
    remove.addActionListener(
        e -> {
          int row = table.getSelectedRow();
          int modelRow = table.convertRowIndexToModel(row);
          buttonPressed(remove, table, modelRow);
          dtm.removeRow(modelRow);
        });

    panel.add(remove, BorderLayout.SOUTH);

    return panel;
  }

  private static void buttonPressed(JButton button, JTable table, int row) {
    String label = table.getValueAt(row, 0).toString();
    String primaryId = table.getValueAt(row, 1).toString();
    String secondaryId = table.getValueAt(row, 2).toString();
    String databaseId = table.getValueAt(row, 3).toString();

    switch (label) {
      case "Account":
        Account acc = Account.getByDbId(Integer.parseInt(primaryId));
        // Delete all data asoociated with the profiles
        for (Account.Profile prof : acc.getProfiles()) {
          Netflix.database.executeSqlNoResult(
              "DELETE FROM WatchedEpisodes WHERE UserId=?", new Object[] {prof.databaseId});
          Netflix.database.executeSqlNoResult(
              "DELETE FROM WatchedFilms WHERE UserId=?", new Object[] {prof.databaseId});
        }

        // Delete all profiles
        Netflix.database.executeSqlNoResult(
            "DELETE FROM Users WHERE AccountId=?", new Object[] {primaryId});

        // Delete the actual account
        Netflix.database.executeSqlNoResult(
            "DELETE FROM Account WHERE AccountId=?", new Object[] {primaryId});

        Commons.clearPane(NetflixFrame.lpane);
        Commons.clearPane(AdminView.accountListTable.tablePanel);
        Commons.clearPane(AdminView.panel());
        NetflixFrame.lpane.add(AdminView.panel());
        break;

      case "AgeRating":
          for (Map<String, Object> map :
                  Netflix.database.executeSql(
                          "SELECT FilmId FROM Film WHERE MPAA=?",
                          new Object[] {secondaryId})) removeFilm(map.get("FilmId"));

          for (Map<String, Object> map :
                  Netflix.database.executeSql(
                          "SELECT SerieId FROM Serie WHERE MPAA=?",
                          new Object[] {secondaryId})) removeFilm(map.get("SerieId"));

        Netflix.database.executeSqlNoResult(
            "DELETE FROM Rating WHERE Rating=?", new Object[] {secondaryId});
        break;

      case "Episode":
        // First delete all watch data
        Netflix.database.executeSqlNoResult(
            "DELETE FROM WatchedEpisodes WHERE EpisodeId=?", new Object[] {databaseId});

        // Delete the episode
        Netflix.database.executeSqlNoResult(
            "DELETE FROM Episode WHERE EpisodeId=?", new Object[] {databaseId});
        break;

      case "Film":
        removeFilm(databaseId);
        break;

      case "Genre":
        // First delete all relations, these also remove the relations inside the Genre<>Media
        // tables
        for (Map<String, Object> map :
            Netflix.database.executeSql(
                "SELECT FilmId FROM Koppeltabel_GenreId_Film WHERE GenreId=?",
                new Object[] {databaseId})) removeFilm(map.get("FilmId"));

        for (Map<String, Object> map :
            Netflix.database.executeSql(
                "SELECT SerieId FROM Koppeltabel_Serie_Genre WHERE GenreId=?",
                new Object[] {databaseId})) removeSerie(map.get("SerieId"));

        // Delete the genre
        Netflix.database.executeSqlNoResult(
            "DELETE FROM Genre WHERE GenreId=?", new Object[] {databaseId});
        break;

      case "Language":
        for (Map<String, Object> map :
            Netflix.database.executeSql(
                "SELECT SerieId FROM Serie WHERE LanguageCode=?", new Object[] {primaryId}))
          removeSerie(map.get("SerieId"));

        for (Map<String, Object> map :
            Netflix.database.executeSql(
                "SELECT FilmId FROM Film WHERE LanguageCode=?", new Object[] {primaryId}))
          removeFilm(map.get("FilmId"));

        // Delete the language
        Netflix.database.executeSqlNoResult(
            "DELETE FROM Language WHERE LanguageCode=?", new Object[] {primaryId});
        break;

      case "Profile":
        Netflix.database.executeSqlNoResult(
            "DELETE FROM WatchedEpisodes WHERE UserId=?", new Object[] {databaseId});
        Netflix.database.executeSqlNoResult(
            "DELETE FROM WatchedFilms WHERE UserId=?", new Object[] {databaseId});

        Netflix.database.executeSqlNoResult(
            "DELETE FROM Users WHERE UserId=?", new Object[] {databaseId});
        break;

      case "Season":
        removeSeason(databaseId, Serie.getSerieByName(secondaryId));
        break;

      case "Serie":
        removeSerie(databaseId);
        break;
    }
  }

  private static void removeSeason(Object seasonId, Serie serie) {
    // Delete related episode viewdata
    for (Serie.Episode epi : Serie.Season.getByDbId((int) seasonId).getEpisodes()) {
      Netflix.database.executeSqlNoResult(
          "DELETE FROM WatchedEpisodes WHERE EpisodeId=? AND SeasonId=?",
          new Object[] {epi.databaseId, seasonId});

      Serie.Episode.episodes.remove(epi);
    }
    // Delete related episodes
    Netflix.database.executeSqlNoResult(
        "DELETE FROM Episode WHERE SeasonId=?", new Object[] {seasonId});

    // Delete season
    Netflix.database.executeSqlNoResult(
        "DELETE FROM Season WHERE SeasonId=?", new Object[] {seasonId});

    serie.removeSeason(Serie.Season.getByDbId((int) seasonId));
  }

  private static void removeFilm(Object filmId) {
    // First delete all watch data
    Netflix.database.executeSqlNoResult(
        "DELETE FROM WatchedFilms WHERE FilmId=?", new Object[] {filmId});

    Netflix.database.executeSqlNoResult(
        "DELETE FROM Koppeltabel_GenreId_Film WHERE FilmId=?", new Object[] {filmId});

    // Delete the film
    Netflix.database.executeSqlNoResult("DELETE FROM Film WHERE FilmId=?", new Object[] {filmId});
  }

  private static void removeSerie(Object serieId) {
    for (Serie.Season season : Serie.getByDbId((int) serieId).getSeasons())
      removeSeason(season.databaseId, Serie.getByDbId((int) serieId));

    Netflix.database.executeSqlNoResult(
        "DELETE FROM Koppeltabel_Serie_Genre WHERE SerieId=?", new Object[] {serieId});
    // Delete serie
    Netflix.database.executeSqlNoResult(
        "DELETE FROM Serie WHERE SerieId=?", new Object[] {serieId});

    Serie.series.remove(Serie.getByDbId((int) serieId));
  }
}
