package com.netflix;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

import static java.awt.BorderLayout.*;
import static javax.swing.JFrame.*;

public class NetflixGUI {

  private JFrame frame;

  public NetflixGUI(int width, int height) {
    frame = new JFrame();
    setFrame(width, height);
  }

  private static void addComponentsToPane(Container pane) {
    pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));

    addAButton("Overzicht #1", pane);
    addAButton("Overzicht #2", pane);
    addAButton("Overzicht #3", pane);
    addAButton("Overzicht #4", pane);
  }

  private static void addAButton(String text, Container container) {
    JButton button = new JButton(text);
    button.setAlignmentX(Component.LEFT_ALIGNMENT);
    container.add(button);
  }

  private void setFrame(int width, int height) {
    // Set defaults for frame
    frame.setLayout(new BorderLayout());
    frame.setDefaultCloseOperation(EXIT_ON_CLOSE);

    // Make sure the given sizes don't exceed the minimum frame size
    if (width < 600) width = 600;
    if (height < 400) height = 400;

    // Set sizes for frame
    frame.setMinimumSize(new Dimension(600, 400));
    frame.setSize(width, height);

    frame.add(bottomPane(), SOUTH);
    frame.add(menu(), WEST);
    frame.add(mainPane(), CENTER);

    // Make the frame visible
    frame.setVisible(true);
  }

  private JPanel mainPane() {
    JPanel mainPanel = new JPanel(new BorderLayout());

    mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

    mainPanel.add(selectSeries(), NORTH);
    mainPanel.add(seriesOverview(), CENTER);

    return mainPanel;
  }

  private JPanel seriesOverview() {
    JPanel seriesOverview = new JPanel(new BorderLayout());

    JLabel averageViews = new JLabel("Gemiddeld 48.2% bekeken per aflevering");
    averageViews.setBorder(new EmptyBorder(5, 0, 10, 0));
    averageViews.setHorizontalAlignment(JLabel.CENTER);
    seriesOverview.add(averageViews, NORTH);

    JPanel main = new JPanel();
    main.setBackground(Color.PINK);
    main.setBorder(BorderFactory.createLineBorder(Color.RED));
    seriesOverview.add(main);

    return seriesOverview;
  }

  private JPanel selectSeries() {
    JPanel selectSeries = new JPanel();
    String[] choices = {
      "House of Cards",
      "Daredevil",
      "Stranger Things",
      "Orange Is the New Black",
      "Narcos",
      "The Crown"
    };

    JLabel selectSerie = new JLabel("Selecteer een serie : ");
    JComboBox<String> cb = new JComboBox<>(choices);
    cb.setVisible(true);

    selectSeries.add(selectSerie);
    selectSeries.add(cb);

    return selectSeries;
  }

  private JPanel menu() {
    // Create and set up the content pane.
    JPanel menu = new JPanel();
    menu.setBorder(new EmptyBorder(10, 0, 0, 0));
    menu.setBackground(Color.GRAY);
    addComponentsToPane(menu);

    return menu;
  }

  private JPanel bottomPane() {
    // Create bottom panel (static information)
    JPanel bottomPanel = new JPanel(new BorderLayout());

    bottomPanel.setBackground(Color.LIGHT_GRAY);
    bottomPanel.setBorder(new EmptyBorder(2, 5, 2, 5));

    // Add labels to panel
      JLabel creators = new JLabel("Informatica 1.2 - 23IVT1D - Guus Lieben, Tim van Wouwe, Coen Rijsdijk");
      creators.setFont(new Font(creators.getFont().getFontName(), Font.ITALIC, creators.getFont().getSize() - 2));
    bottomPanel.add(new JLabel("Netflix Statistix"), WEST);
    bottomPanel.add(creators, EAST);

    return bottomPanel;
  }
}
