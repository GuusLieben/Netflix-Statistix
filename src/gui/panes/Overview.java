package com.netflix.gui.panes;

import com.netflix.objects.Film;
import com.netflix.objects.Serie;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import static java.awt.BorderLayout.*;

public class Overview {

  // Default panels
  private static JPanel main = new JPanel(new BorderLayout());
  private static JPanel inner = new JPanel(new BorderLayout());
  private static JPanel aboutMediaInner = new JPanel(new BorderLayout());

  // Common stuff
  private JLabel title;
  private String description;

  private Overview() {
    inner.setBorder(new EmptyBorder(10, 10, 10, 10));
  }

  private Overview(Serie serie) {
    new Overview();
    title = new JLabel(serie.getTitle());
    description =
        String.format(
            "<html>Genre : %s<br>Language : %s<br>Rating : %s<br>Seasons : %d<br>Episodes : %d</html>",
            serie.getGenre(),
            serie.getLang().getLanguageName(),
            serie.getRating(),
            serie.getSeasons(),
            serie.getEpisodes());
  }

  private Overview(Film film) {
    new Overview();
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

  static JPanel newOverview(Film film, Serie serie) {

    JPanel overviewPanel = new JPanel(new BorderLayout());

    // Add sub-panels
    Overview overview = null;
    if (film == null) overview = new Overview(serie);
    if (serie == null) overview = new Overview(film);

    overviewPanel.add(overview.getPanel());

    overviewPanel.setBackground(Color.WHITE);

    return overviewPanel;
  }

  public static void clearPane(Container con) {
    con.removeAll();
    con.repaint();
    con.revalidate();
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

    JScrollPane serieDisplay = new JScrollPane(aboutMediaInner);
    serieDisplay.setBorder(null);
    serieDisplay.setPreferredSize(new Dimension(serieDisplay.getWidth(), serieDisplay.getHeight()));
    serieDisplay.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

    inner.add(serieDisplay, CENTER);
    main.add(inner);

    return main;
  }
}
