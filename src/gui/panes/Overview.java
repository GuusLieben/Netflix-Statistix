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
    main.setBorder(BorderFactory.createLineBorder(Color.black));
    inner.setBorder(new EmptyBorder(10, 10, 10, 10));
  }

  Overview(Serie serie) {
    new Overview();
    title = new JLabel(serie.getTitle());
    description =
        String.format(
            "<html>Genre : %s<br>Language : %s<br>Rating : %s<br>Seasons : %d<br>Episodes : %d</html>",
            serie.getGenre(),
            serie.getLang().getLanguage(),
            serie.getRating(),
            serie.getSeasons(),
            serie.getEpisodes());
  }

  Overview(Film film) {
    new Overview();
    title = new JLabel(film.getTitle()); // Director, duration, rating
    description =
        String.format(
            "<html>Genre : %s<br>Language : %s<br>Rating : %s<br>Director : %s<br>Duration : %s</html>",
            film.getGenre(),
            film.getLang().getLanguage(),
            film.getRating(),
            film.getDirector(),
            film.getDuration());
  }

  public static JPanel newOverview(Film film, Serie serie) {

    JPanel overview = new JPanel(new BorderLayout());

    // Create, center, and add padding to label
    JLabel averageViews = null;
    if (film == null)
      averageViews = new JLabel("Gemiddeld 48.2% bekeken per aflevering"); // Sample label
    if (serie == null)
      averageViews = new JLabel("Gemiddeld 48.2% bekeken per film"); // Sample label
    averageViews.setBorder(new EmptyBorder(5, 0, 10, 0));
    averageViews.setHorizontalAlignment(JLabel.CENTER);

    // Add sub-panels
    Overview serieOverview = null;
    if (film == null) serieOverview = new Overview(serie);
    if (serie == null) serieOverview = new Overview(film);
    overview.add(averageViews, NORTH);
    overview.add(serieOverview.Panel());

    overview.setBackground(Color.WHITE);

    return overview;
  }

  public static void clearPane(JPanel pane) {
    pane.removeAll();
    pane.repaint();
    pane.revalidate();
  }

  JPanel Panel() {
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

    JScrollPane SerieDisplay = new JScrollPane(aboutMediaInner);
    SerieDisplay.setBorder(null);
    SerieDisplay.setPreferredSize(new Dimension(SerieDisplay.getWidth(), SerieDisplay.getHeight()));
    SerieDisplay.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

    inner.add(SerieDisplay, CENTER);
    main.add(inner);

    return main;
  }
}
