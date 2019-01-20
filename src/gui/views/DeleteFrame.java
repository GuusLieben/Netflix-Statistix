package com.netflix.gui.views;

import com.netflix.*;
import com.netflix.commons.*;
import com.netflix.entities.*;
import com.netflix.gui.*;

import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.table.*;
import java.awt.*;
import java.sql.*;
import java.util.*;

public class DeleteFrame extends JFrame {

  public static DeleteFrame frame() {
    DeleteFrame frame = new DeleteFrame();
    frame.getContentPane().setLayout(new BorderLayout());
    frame.add(Commons.logo(), BorderLayout.NORTH);
    frame.add(overviewTable(), BorderLayout.CENTER);
    frame.add(Commons.credits(), BorderLayout.SOUTH);
    frame.setSize(600, 500);
    frame.setMinimumSize(new Dimension(600, 500));
    return frame;
  }

  private static JPanel overviewTable() {
    JPanel panel = new JPanel(new BorderLayout());
    JTable table =
        new JTable() {
          @Override
          public boolean isCellEditable(int row, int column) {
            return false;
          }
        };

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
      if (ent.getSecondaryIdentifier() == null
          || ent.getSecondaryIdentifier().equals(-1)
          || ent.getSecondaryIdentifier().equals("Geen"))
        secondaryId = "<html><i style='background: yellow'>NULL</i></html>";
      else secondaryId = ent.getSecondaryIdentifier().toString();
      Object databaseId;
      if (type.equals("Language")) databaseId = primaryId;
      else if (type.equals("AgeRating")) databaseId = secondaryId;
      else databaseId = "" + ent.databaseId;

      if (!(ent.getPrimaryIdentifier() == null
          || ent.getPrimaryIdentifier().equals(-1)
          || ent.getPrimaryIdentifier().equals("Geen")))
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
          buttonPressed(table, row);
          dtm.removeRow(modelRow);
        });

    panel.add(remove, BorderLayout.SOUTH);

    return panel;
  }

  private static void buttonPressed(JTable table, int row) {
    String label = table.getValueAt(row, 0).toString();
    String primaryId = table.getValueAt(row, 1).toString();
    String databaseId = table.getValueAt(row, 3).toString();

    System.out.println(label + " " + primaryId + " / " + databaseId);

    // Runtime removals are collected by the Garbage Collector, remove it from the Sets/Lists to
    // make it ready for this

    switch (label) {
      case "Account":
        if (Netflix.database.executeSqlNoResult(
                "DELETE FROM Account WHERE AccountId=?", new Object[] {databaseId})
            == DataHandle.SQLResults.PASS) {
          Account.accounts.remove(Account.getByDbId(Integer.parseInt(databaseId)));
          Commons.clearPane(NetflixFrame.lpane);
          Commons.clearPane(AdminView.accountListTable.tablePanel);
          Commons.clearPane(AdminView.panel());
          NetflixFrame.lpane.add(AdminView.panel());
        }
        break;
      case "AgeRating":
        if (Netflix.database.executeSqlNoResult(
                "DELETE FROM Rating WHERE MPAA=?", new Object[] {primaryId})
            == DataHandle.SQLResults.PASS) {

          MediaCommons.AgeRating.ratings.remove(MediaCommons.AgeRating.getByCode(primaryId));

          Film.films.stream()
              .filter(film -> film.getRating() == MediaCommons.AgeRating.getByCode(primaryId))
              .forEach(film -> film.setRating(MediaCommons.AgeRating.getByCode("Geen")));

          Serie.series.stream()
              .filter(serie -> serie.getRating() == MediaCommons.AgeRating.getByCode(primaryId))
              .forEach(serie -> serie.setRating(MediaCommons.AgeRating.getByCode("Geen")));
        }
        break;
      case "Episode":
        if (Netflix.database.executeSqlNoResult(
                "DELETE FROM Episode WHERE EpisodeId=?", new Object[] {databaseId})
            == DataHandle.SQLResults.PASS) {
          removeEpisodeLocal(Serie.Episode.getByDbId(Integer.parseInt(databaseId)));
        }
        break;
      case "Film":
        if (Netflix.database.executeSqlNoResult(
                "DELETE FROM Film WHERE FilmId=?", new Object[] {databaseId})
            == DataHandle.SQLResults.PASS) {

          Film.films.remove(Film.getByDbId(Integer.parseInt(databaseId)));

          Film.filmTitles.remove(Film.getByDbId(Integer.parseInt(databaseId)).getTitle());
          for (Map.Entry<Account.Profile, Time> entry :
              Film.getByDbId(Integer.parseInt(databaseId)).getWatchedBy().entrySet()) {
            entry.getKey().unviewFilm(Film.getByDbId(Integer.parseInt(databaseId)));
          }
        }
        break;
      case "Genre":
        if (Netflix.database.executeSqlNoResult(
                "DELETE FROM Genre WHERE GenreId=?", new Object[] {databaseId})
            == DataHandle.SQLResults.PASS) {

          MediaCommons.Genre.genres.remove(MediaCommons.Genre.getByName(primaryId));
          Film.films.stream()
              .filter(film -> film.getGenre() == MediaCommons.Genre.getByName(primaryId))
              .forEach(film -> film.setGenre(MediaCommons.Genre.getByName("Geen")));

          Serie.series.stream()
              .filter(serie -> serie.getGenre() == MediaCommons.Genre.getByName(primaryId))
              .forEach(serie -> serie.setGenre(MediaCommons.Genre.getByName("Geen")));
        }
        break;
      case "Language":
        if (Netflix.database.executeSqlNoResult(
                "DELETE FROM Language WHERE LanguageCode=?", new Object[] {primaryId})
            == DataHandle.SQLResults.PASS) {
          MediaCommons.Language.langs.remove(MediaCommons.Language.getByCode(primaryId));
          Film.films.stream()
              .filter(film -> film.getLang() == MediaCommons.Language.getByCode(primaryId))
              .forEach(film -> film.setLang(MediaCommons.Language.getByCode("nu_LL")));

          Serie.series.stream()
              .filter(serie -> serie.getLang() == MediaCommons.Language.getByCode(primaryId))
              .forEach(serie -> serie.setLang(MediaCommons.Language.getByCode("nu_LL")));
        }
        break;
      case "Profile":
        if (Netflix.database.executeSqlNoResult(
                "DELETE FROM Users WHERE UserId=?", new Object[] {databaseId})
            == DataHandle.SQLResults.PASS) {
          for (MediaObject object :
              Account.Profile.getByDbId(Integer.parseInt(databaseId)).getMediaWatched()) {
            object.removeWatchedBy(Account.Profile.getByDbId(Integer.parseInt(databaseId)));
          }
          for (Serie.Episode epi : Serie.Episode.episodes) {
            if (epi.watchedByProfile(Account.Profile.getByDbId(Integer.parseInt(databaseId))))
              epi.removeWatchedBy(Account.Profile.getByDbId(Integer.parseInt(databaseId)));
          }

          Account.Profile.getByDbId(Integer.parseInt(databaseId))
              .getAccount()
              .getProfiles()
              .remove(Account.Profile.getByDbId(Integer.parseInt(databaseId)));
        }
        break;
      case "Season":
        if (Netflix.database.executeSqlNoResult(
                "DELETE FROM Season WHERE SeasonId=?", new Object[] {databaseId})
            == DataHandle.SQLResults.PASS) {
          removeSeasonLocal(Serie.Season.getByDbId(Integer.parseInt(databaseId)));
        }

        break;
      case "Serie":
        if (Netflix.database.executeSqlNoResult(
                "DELETE FROM Serie WHERE SerieId=?", new Object[] {databaseId})
            == DataHandle.SQLResults.PASS) {

          Serie.series.remove(Serie.getByDbId(Integer.parseInt(databaseId)));
          Serie.serieTitles.remove(Serie.getByDbId(Integer.parseInt(databaseId)).getTitle());

          for (Serie.Season season : Serie.getByDbId(Integer.parseInt(databaseId)).getSeasons()) {
            removeSeasonLocal(season);
          }
        }
        break;
    }
  }

  private static void removeEpisodeLocal(Serie.Episode episode) {
    Serie.Episode.episodes.remove(episode);
    episode.getSeason().removeEpisode(episode);

    for (Map.Entry<Account.Profile, Time> entry : episode.getWatchedBy().entrySet()) {
      entry.getKey().unviewEpisode(episode);
    }
  }

  private static void removeSeasonLocal(Serie.Season season) {
    for (Serie.Episode epi : season.getEpisodes()) {
      removeEpisodeLocal(epi);
    }

    season.getSerie().removeSeason(season);
  }
}
