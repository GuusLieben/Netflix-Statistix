package com.netflix.gui.views.subpanels;

import com.netflix.commons.Commons;
import com.netflix.entities.*;
import com.netflix.entities.MediaObject;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.sql.*;
import java.time.*;
import java.util.*;

import static com.netflix.Netflix.database;
import static java.awt.BorderLayout.*;

public class ReadObject {

  public static Serie serie;
  // Default panels
  private static JPanel main = new JPanel(new BorderLayout());
  private static JPanel inner = new JPanel(new BorderLayout());
  private static JPanel aboutMediaInner = new JPanel(new BorderLayout());
  private static JPanel overviewPanel = new JPanel(new BorderLayout());
  private static JTable table;
  // Common stuff
  private JLabel title;
  private String description;
  private JLabel descriptionLabel;
  private MediaObject obj;

  ReadObject() {}

  private ReadObject(MediaObject object) {
    obj = object;
    title = new JLabel(object.getTitle());

    // If it's a serie
    if (object.getType() == 2) {
      Serie serie = Serie.getSerieByName(object.getTitle());
      Serie similar = serie.getSimilarObject();

      String similarTitle;
      String similarPercentage;
      if (similar == null) {
        similarTitle = "Geen";
        similarPercentage = "0.00%";
      } else {
        similarTitle = similar.getTitle();
        similarPercentage = Commons.percentage(similar.getWatchedPercentage());
      }

      description =
          String.format(
              "<html>Taal : %s<br>Genre : %s<br>Seizoenen : %d<br>Afleveringen : %d<br>Leeftijdsclassificatie %s<br>Bekeken door %d personen (%s%% van het totaal aantal gebruikers)<br><br>Vergelijkbaar : %s (%s%%)</html>",
              object.getLang().getLanguageName(),
              object.getGenre(),
              serie.getSeasonCount(),
              serie.getEpisodeCount(),
              object.getRating(),
              object.getWatchedByAmount(),
              Commons.percentage(object.getWatchedPercentage()),
              similarTitle,
              similarPercentage);
    }

    // If it's a film
    if (object.getType() == 1) {
      Film film = Film.getFilmByName(object.getTitle());

      Film similar = film.getSimilarObject();

      String similarTitle;
      String similarPercentage;
      if (similar == null) {
        similarTitle = "Geen";
        similarPercentage = "0.00%";
      } else {
        similarTitle = similar.getTitle();
        similarPercentage = Commons.percentage(similar.getWatchedPercentage());
      }

      description =
          String.format(
              "<html>Genre : %s<br>Taal : %s<br>Leeftijdsclassificatie : %s<br>Regisseur : %s<br>Tijdsduur : %s, gemiddeld %s%% bekeken<br>Bekeken door %d personen (%s%% van het totaal aantal gebruikers)<br><br>Vergelijkbaar : %s (%s%%)</html>",
              object.getGenre(),
              object.getLang().getLanguageName(),
              object.getRating(),
              film.getDirector(),
              film.getDuration(),
              Commons.percentage(film.getAverageWatchedTime()),
              object.getWatchedByAmount(),
              Commons.percentage(object.getWatchedPercentage()),
              similarTitle,
              similarPercentage);
    }
  }

  // Clear the overview on demand
  void clearOverview() {
    Commons.clearPane(overviewPanel);
  }

  JPanel getOverview(MediaObject media) {

    // Add sub-panels
    ReadObject readObject;

    switch (media.getType()) {
      case 2: // Serie
        ReadObject.serie = (Serie) media;
        break;
      case 1: // Film, make sure it doesn't check for episodes
        ReadObject.serie = null;
        break;
      default:
        Commons.exception(new Exception("Could not collect series/films"));
        break;
    }

    readObject = new ReadObject(media);

    overviewPanel.add(readObject.getPanel());
    overviewPanel.setBackground(Color.WHITE);

    return overviewPanel;
  }

  private JPanel getPanel() {
    Commons.clearPane(main);
    Commons.clearPane(inner);
    Commons.clearPane(aboutMediaInner);

    title.setFont(new Font(title.getFont().getFontName(), Font.BOLD, 18));

    descriptionLabel = new JLabel();
    descriptionLabel.setText(description);
    descriptionLabel.setFont(
        new Font(
            descriptionLabel.getFont().getFontName(),
            Font.PLAIN,
            descriptionLabel.getFont().getSize()));

    JPanel quickView = new JPanel(new BorderLayout());

    // Set background colors
    inner.setBackground(Color.WHITE);
    descriptionLabel.setBackground(Color.WHITE);
    aboutMediaInner.setBackground(Color.WHITE);
    title.setBackground(Color.WHITE);
    quickView.setBackground(Color.WHITE);
    main.setBackground(Color.WHITE);

    // Add all the things!
    aboutMediaInner.add(descriptionLabel, CENTER);

    if (MediaObject.type == 1) {
      JCheckBox cb = new JCheckBox("Bekeken");

      if (Account.Profile.currentUser.getFilmsWatched().contains(obj)) cb.setSelected(true);

      cb.addItemListener(
          e -> {
            if (cb.isSelected()) {

              Random random = new Random();

              Film film = Film.getByDbId(obj.databaseId);
              Time duration = film.getDuration();
              int hours = duration.getHours();
              int minutes = duration.getMinutes();
              int seconds = duration.getMinutes();

              int watchedHours = random.nextInt(hours);
              int watchedMinutes = random.nextInt(minutes);
              int watchedSeconds = random.nextInt(seconds);

              Account.Profile.currentUser.viewFilmNoDB(
                  Film.getByDbId(obj.databaseId),
                  Time.valueOf(LocalTime.of(watchedHours, watchedMinutes, watchedSeconds)));
              String qr =
                  "INSERT INTO WatchedFilms (FilmId, UserId, FilmsWatched, TimeWatched) VALUES (?, ?, ?, ?)";
              Object[] arr = {
                obj.databaseId,
                Account.Profile.currentUser.databaseId,
                Account.Profile.currentUser.getFilmsWatched().size(),
                Time.valueOf(LocalTime.of(watchedHours, watchedMinutes, watchedSeconds))
              };

              database.executeSqlNoResult(qr, arr);

            } else {
              Account.Profile.currentUser.unviewFilm(Film.getByDbId(obj.databaseId));
              String qr = "DELETE FROM WatchedFilms WHERE UserId=? AND FilmId=?";
              Object[] arr = {Account.Profile.currentUser.databaseId, obj.databaseId};

              database.executeSqlNoResult(qr, arr);
            }

            // If it's a film
            Film film = Film.getFilmByName(obj.getTitle());
            descriptionLabel.setText(
                String.format(
                    "<html>Genre : %s<br>Taal : %s<br>Leeftijdsclassificatie : %s<br>Regisseur : %s<br>Tijdsduur : %s, gemiddeld %s minuten bekeken<br>Bekeken door %d personen (%s%% van het totaal aantal gebruikers)<br><br>Vergelijkbaar : %s (%s%%)</html>",
                    obj.getGenre(),
                    obj.getLang().getLanguageName(),
                    obj.getRating(),
                    film.getDirector(),
                    film.getDuration(),
                    Commons.percentage(film.getAverageWatchedTime()),
                    obj.getWatchedByAmount(),
                    Commons.percentage(obj.getWatchedPercentage()),
                    film.getSimilarObject().getTitle(),
                    film.getSimilarObject().getWatchedPercentage()));
          });
      aboutMediaInner.add(cb, BorderLayout.SOUTH);
    }

    quickView.add(title, NORTH);
    quickView.add(descriptionLabel, SOUTH);
    inner.add(quickView, NORTH);

    if (ReadObject.serie != null) { // If it's a film this will be null
      // Generate a table
      table =
          new JTable() {
            @Override
            public boolean isCellEditable(int row, int column) {
              return column == 6;
            }
          };

      DefaultTableModel tableModel = new DefaultTableModel(0, 0);
      CheckBoxModelListener cml = new CheckBoxModelListener();
      cml.lable = descriptionLabel;
      cml.obj = obj;
      tableModel.addTableModelListener(cml);

      // Table headers
      String[] columnNames = {
        "Aflevering",
        "Titel",
        "Seizoen",
        "Duratie",
        "Gemiddeld bekeken",
        "Door jou bekeken",
        "Bekeken",
        "DatabaseId"
      };

      tableModel.setColumnIdentifiers(columnNames);
      table.setModel(tableModel);

      TableColumnModel tcm = table.getColumnModel();
      tcm.removeColumn(
          tcm.getColumn(7)); // Hide DatabaseId from user, but store the data in the table

      // Add all episodes in the serie
      for (Serie.Season season : ReadObject.serie.getSeasons()) {
        for (Serie.Episode episode : season.getEpisodes()) {
          tableModel.addRow(
              new Object[] {
                episode.getEpisodeNumber(),
                episode.getTitle(),
                episode.getSeason(),
                episode.getDuration(),
                Commons.percentage(episode.getAverageWatchedTime()) + "%",
                episode.getWatchedAmountBy(Account.Profile.currentUser),
                episode.watchedByCurrentProfile(),
                episode.databaseId
              });
        }
      }

      TableColumn tc = table.getColumnModel().getColumn(6);
      tc.setCellEditor(table.getDefaultEditor(Boolean.class));
      tc.setCellRenderer(table.getDefaultRenderer(Boolean.class));

      JTableHeader header = table.getTableHeader();
      header.setForeground(new Color(151, 2, 4));
      header.setFont(new Font(header.getFont().getName(), Font.BOLD, 12));
      header.setOpaque(false);

      table.setShowGrid(true);
      table.setGridColor(Color.LIGHT_GRAY);

      TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel());
      table.setRowSorter(sorter);

      ArrayList<RowSorter.SortKey> sortKeys = new ArrayList<>(25);
      sortKeys.add(new RowSorter.SortKey(2, SortOrder.ASCENDING)); // First sort it by season
      sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING)); // Then sort it by episode
      sorter.setSortKeys(sortKeys);

      // Make it scrollable
      JScrollPane tableScroll =
          new JScrollPane(
              table,
              ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
              ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

      tableScroll.setPreferredSize(new Dimension(inner.getWidth(), main.getHeight() / 2));

      JPanel episodes = new JPanel(new BorderLayout());

      episodes.addComponentListener(new ResizeListener(tableScroll));

      episodes.setOpaque(false);
      episodes.add(header, BorderLayout.NORTH);
      episodes.add(tableScroll, BorderLayout.CENTER);

      inner.add(episodes, SOUTH);
    }

    inner.add(aboutMediaInner, CENTER);
    main.add(inner);

    return main;
  }

  // Resize the scrollpane with the inner panel to make it easily adjustable
  class ResizeListener extends ComponentAdapter {
    private JScrollPane pane;

    ResizeListener(JScrollPane pane) {
      this.pane = pane;
    }

    @Override
    public void componentResized(ComponentEvent e) {
      pane.setPreferredSize(new Dimension(inner.getWidth(), main.getHeight() / 2));
    }
  }

  public class CheckBoxModelListener implements TableModelListener {
    JLabel lable;
    MediaObject obj;

    @Override
    public void tableChanged(TableModelEvent e) {
      int row = e.getFirstRow();
      int column = e.getColumn();

      episodeTableUpdate(row, column, e); // Series
    }

    private void episodeTableUpdate(int row, int column, TableModelEvent e) {
      if (column == 6) {
        TableModel model = (TableModel) e.getSource();
        Boolean checked = (Boolean) model.getValueAt(row, column);
        int dbID = (int) ReadObject.table.getModel().getValueAt(row, column + 1);
        if (checked) {

          Random random = new Random();

          Serie.Episode epi = Serie.Episode.getByDbId(dbID);
          Time duration = epi.getDuration();
          int hours = duration.getHours();
          int minutes = duration.getMinutes();
          int seconds = duration.getMinutes();

          // Check if the value is above 0, because nextInt bounds can not be zero. Also make the
          // default value 0, can't watch more than 0 hours of a 48 minute episode, unless you play
          // in Interstellar or fall into a black hole.
          int watchedHours = 0;
          if (hours > 0) watchedHours = random.nextInt(hours);

          int watchedMinutes = 0;
          if (minutes > 0) watchedMinutes = random.nextInt(minutes);

          int watchedSeconds = 0;
          if (seconds > 0) watchedSeconds = random.nextInt(seconds);

          Account.Profile.currentUser.viewEpisodeNoDB(
              Serie.Episode.getByDbId(dbID),
              Time.valueOf(LocalTime.of(watchedHours, watchedMinutes, watchedSeconds)));

          String qr =
              "INSERT INTO WatchedEpisodes (EpisodesWatched, UserId, EpisodeId, TimeWatched) VALUES (?, ?, ?, ?)";
          Object[] arr = {
            Account.Profile.currentUser.getEpisodesWatched().size(),
            Account.Profile.currentUser.databaseId,
            dbID,
            Time.valueOf(LocalTime.of(watchedHours, watchedMinutes, watchedSeconds))
          };

          database.executeSqlNoResult(qr, arr);

          table.setValueAt(
              Commons.percentage(Serie.Episode.getByDbId(dbID).getAverageWatchedTime()) + "%",
              table.getSelectedRow(),
              4);

          table.setValueAt(
              LocalTime.of(watchedHours, watchedMinutes, watchedSeconds),
              table.getSelectedRow(),
              5);

        } else {
          Account.Profile.currentUser.unviewEpisode(Serie.Episode.getByDbId(dbID));

          String qr = "DELETE FROM WatchedEpisodes WHERE UserId=? AND EpisodeId=?";
          Object[] arr = {Account.Profile.currentUser.databaseId, dbID};

          database.executeSqlNoResult(qr, arr);

          table.setValueAt("0.00%", table.getSelectedRow(), 4);

          table.setValueAt("00:00:00", table.getSelectedRow(), 5);
        }

        Serie serie = Serie.getSerieByName(obj.getTitle());
        lable.setText(
            String.format(
                "<html>Taal : %s<br>Genre : %s<br>Seizoenen : %d<br>Afleveringen : %d<br>Leeftijdsclassificatie %s<br>Bekeken door %d personen (%s%% van het totaal aantal gebruikers)<br><br>Vergelijkbaar : %s (%s%%)</html>",
                obj.getLang().getLanguageName(),
                obj.getGenre(),
                serie.getSeasonCount(),
                serie.getEpisodeCount(),
                obj.getRating(),
                obj.getWatchedByAmount(),
                Commons.percentage(obj.getWatchedPercentage()),
                serie.getSimilarObject().getTitle(),
                serie.getSimilarObject().getWatchedPercentage()));
      }
    }
  }
}
