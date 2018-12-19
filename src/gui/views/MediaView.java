package com.netflix.gui.views;

import com.netflix.commons.Commons;
import com.netflix.entities.Episode;
import com.netflix.entities.Film;
import com.netflix.entities.Season;
import com.netflix.entities.Serie;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

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

  public MediaView(String title) {}

  private MediaView(Serie serie) {
    new MediaView(serie.getTitle());
    title = new JLabel(serie.getTitle());
    description =
        String.format(
            "<html>Taal : %s<br>Genre : %s<br>Seizoenen : %d<br>Afleveringen : %d<br>Leeftijdsclassificatie %s</html>",
            serie.getLang().getLanguageName(),
            serie.getGenre(),
            serie.getSeasonCount(),
            serie.getEpisodeCount(),
            serie.getRating());
  }

  private MediaView(Film film) {
    new MediaView(serie.getTitle());
    title = new JLabel(film.getTitle()); // Director, duration, rating
    description =
        String.format(
            "<html>Genre : %s<br>Taal : %s<br>Leeftijdsclassificatie : %s<br>Regisseur : %s<br>Tijdsduur : %s</html>",
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

    if (MediaView.serie != null) {
      JTable table =
          new JTable() {
            @Override
            public boolean isCellEditable(int row, int column) {
              return false;
            }
          };
      DefaultTableModel tableModel = new DefaultTableModel(0, 0);

      String[] columnNames = {"Title", "Season", "Duration"};

      tableModel.setColumnIdentifiers(columnNames);
      table.setModel(tableModel);

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
