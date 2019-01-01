package com.netflix.gui.views;

import com.netflix.commons.Commons;
import com.netflix.entities.Episode;
import com.netflix.entities.Film;
import com.netflix.entities.Season;
import com.netflix.entities.Serie;
import com.netflix.entities.abstracts.MediaObject;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;

import static java.awt.BorderLayout.*;

public class ObjectView {

  public static Serie serie;
  // Default panels
  private static JPanel main = new JPanel(new BorderLayout());
  private static JPanel inner = new JPanel(new BorderLayout());
  private static JPanel aboutMediaInner = new JPanel(new BorderLayout());
  private static JPanel overviewPanel = new JPanel(new BorderLayout());
  // Common stuff
  private JLabel title;
  private String description;

  public ObjectView() {}

  private ObjectView(MediaObject object) {
    new ObjectView();
    title = new JLabel(object.getTitle());

    // If it's a serie
    if (object.getType() == 2) {
      Serie serie = Serie.getSerieByName(object.getTitle());
      description =
          String.format(
              "<html>Taal : %s<br>Genre : %s<br>Seizoenen : %d<br>Afleveringen : %d<br>Leeftijdsclassificatie %s<br>Bekeken door %s%% van het totaal aantal gebruikers</html>",
              object.getLang().getLanguageName(),
              object.getGenre(),
              serie.getSeasonCount(),
              serie.getEpisodeCount(),
              object.getRating(),
              Commons.percentage(object.getWatchedPercentage()));
    }

    // If it's a film
    if (object.getType() == 1) {
      Film film = Film.getFilmByName(object.getTitle());
      description =
          String.format(
              "<html>Genre : %s<br>Taal : %s<br>Leeftijdsclassificatie : %s<br>Regisseur : %s<br>Tijdsduur : %s<br>Bekeken door %s%% van het totaal aantal gebruikers</html>",
              object.getGenre(),
              object.getLang().getLanguageName(),
              object.getRating(),
              film.getDirector(),
              film.getDuration(),
              Commons.percentage(object.getWatchedPercentage()));
    }
  }

  // Clear the overview on demand
  void clearOverview() {
    Commons.clearPane(overviewPanel);
  }

  JPanel getOverview(MediaObject media) {

    // Add sub-panels
    ObjectView objectView = null;

    switch (media.getType()) {
      case 2: // Serie
        ObjectView.serie = (Serie) media;
        break;
      case 1: // Film, make sure it doesn't check for episodes
        ObjectView.serie = null;
        break;
      default:
        Commons.exception(new Exception("Could not collect series/films"));
        break;
    }

    objectView = new ObjectView(media);

    overviewPanel.add(objectView.getPanel());
    overviewPanel.setBackground(Color.WHITE);

    return overviewPanel;
  }

  private JPanel getPanel() {
    Commons.clearPane(main);
    Commons.clearPane(inner);
    Commons.clearPane(aboutMediaInner);

    title.setFont(new Font(title.getFont().getFontName(), Font.BOLD, 18));

    JLabel descriptionLabel = new JLabel();
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
    aboutMediaInner.add(descriptionLabel, NORTH);
    quickView.add(title, NORTH);
    quickView.add(descriptionLabel, SOUTH);
    inner.add(quickView, NORTH);

    if (ObjectView.serie != null) { // If it's a film this will be null
      // Generate a table
      JTable table =
          new JTable() {
            @Override
            public boolean isCellEditable(int row, int column) {
              return false;
            }
          };
      DefaultTableModel tableModel = new DefaultTableModel(0, 0);

      // Table headers
      String[] columnNames = {"Aflevering", "Titel", "Seizoen", "Duratie"};

      tableModel.setColumnIdentifiers(columnNames);
      table.setModel(tableModel);

      // Add all episodes in the serie
      for (Season season : ObjectView.serie.getSeasons()) {
        for (Episode episode : season.getEpisodes()) {
          tableModel.addRow(
              new Object[] {
                episode.getEpisodeNumber(),
                episode.getTitle(),
                episode.getSeason(),
                episode.getDuration()
              });
        }
      }

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

    public void componentResized(ComponentEvent e) {
      pane.setPreferredSize(new Dimension(inner.getWidth(), main.getHeight() / 2));
    }
  }
}