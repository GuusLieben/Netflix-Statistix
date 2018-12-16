package com.netflix.gui.views;

import com.netflix.commons.Commons;
import com.netflix.entities.Episode;
import com.netflix.entities.Film;
import com.netflix.entities.Season;
import com.netflix.entities.Serie;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.ArrayList;

import static java.awt.BorderLayout.*;

public class MediaView {

  public static Serie serie;
  // Default panels
  private static JPanel main = new JPanel(new BorderLayout());
  private static JPanel inner = new JPanel(new BorderLayout());
  private static JPanel aboutMediaInner = new JPanel(new BorderLayout());
  private static JPanel overviewPanel = new JPanel(new BorderLayout());
  // Common stuff
  private JLabel title;
  private String description;

  public MediaView() {
    inner.setBorder(new EmptyBorder(10, 10, 10, 10));
  }

  private MediaView(Serie serie) {
    new MediaView();
    title = new JLabel(serie.getTitle());
    description =
        String.format(
            "<html>%s is a %s %s tv-show. <br>It has %d seasons and %d episodes. <br>It's rated for %s audiences</html>",
            serie.getTitle(),
            serie.getLang().getLanguageName(),
            serie.getGenre(),
            serie.getSeasonCount(),
            serie.getEpisodeCount(),
            serie.getRating());
  }

  private MediaView(Film film) {
    new MediaView();
    title = new JLabel(film.getTitle()); // Director, duration, rating
    description =
        String.format(
            "<html>Genre : %s<br>Language : %s<br>Rating : %s<br>Director : %s<br>Duration : %s</html>",
            film.getGenre(),
            film.getLang().getLanguageName(),
            film.getRating(),
            film.getDirector(),
            film.getDuration());
  }

  public static void clearPane(Container con) {
    con.removeAll();
    con.repaint();
    con.revalidate();
  }

  void clearOverview() {
    MediaView.clearPane(overviewPanel);
  }

  JPanel getOverview(Film film, Serie serie) {

    // Add sub-panels
    MediaView mediaView = null;
    if ((serie != null) && (film == null)) {
      MediaView.serie = serie;
      mediaView = new MediaView(serie);
    } else if ((film != null) && (serie == null)) {
      MediaView.serie = null;
      mediaView = new MediaView(film);
    } else Commons.exception(new Exception("Could not collect series/films"));

    overviewPanel.add(mediaView.getPanel());
    overviewPanel.setBackground(Color.WHITE);

    return overviewPanel;
  }

  private JPanel getPanel() {
    clearPane(main);
    clearPane(inner);
    clearPane(aboutMediaInner);

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

    JScrollPane mediaDisplay = new JScrollPane(aboutMediaInner);
    mediaDisplay.setBorder(null);
    mediaDisplay.setPreferredSize(new Dimension(mediaDisplay.getWidth(), mediaDisplay.getHeight()));
    mediaDisplay.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

    if (MediaView.serie != null) {
      JPanel episodes = new JPanel(new BorderLayout());
      episodes.setOpaque(false);
      episodes.setBorder(new EmptyBorder(10, 10, 10, 10));

      JLabel episodeAnnouncer = new JLabel("<html><h3>Episodes</h3></html>");
      episodes.add(episodeAnnouncer, BorderLayout.NORTH);

      JTable table = new JTable();
      DefaultTableModel tableModel = new DefaultTableModel(0, 0);

      String[] columnNames = {"Title", "Season", "Duration"};

      tableModel.setColumnIdentifiers(columnNames);
      table.setModel(tableModel);

      ArrayList<Object[]> episodeTable = new ArrayList<>();

      for (Season season : MediaView.serie.getSeasons()) {
        for (Episode episode : season.getEpisodes()) {
          tableModel.addRow(
              new Object[] {episode.getTitle(), episode.getSeason(), episode.getDuration()});
        }
      }

      JTableHeader header = table.getTableHeader();
      header.setForeground(new Color(151, 2, 4));
      header.setFont(new Font(header.getFont().getName(), Font.BOLD, 12));
      header.setOpaque(false);

      table.setShowGrid(true);
      table.setGridColor(Color.LIGHT_GRAY);

      episodes.add(header, BorderLayout.CENTER);
      episodes.add(table, BorderLayout.SOUTH);

      inner.add(episodes, SOUTH);
    }

    inner.add(mediaDisplay, CENTER);
    main.add(inner);

    return main;
  }
}
