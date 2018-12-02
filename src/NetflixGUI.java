package com.netflix;

import com.raphaellevy.fullscreen.*;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

import static com.netflix.Commons.*;
import static java.awt.BorderLayout.*;
import static javax.swing.JFrame.*;

class NetflixGUI {

  private JFrame frame;
  private String title = "House of Cards";
  private int episodeCount = 73;
  private String[] choices = {
    "House of Cards",
    "Daredevil",
    "Stranger Things",
    "Orange Is the New Black",
    "Narcos",
    "The Crown"
  };

  NetflixGUI(int width, int height) {
    frame = new JFrame();
    setFrame(width, height);
  }

  private static void fillMenu(Container pane) {
    // Make all content (buttons) align vertically
    pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));

    // Sample data
    addButton("Overzicht #1", pane);
    addButton("Overzicht #2", pane);
    addButton("Overzicht #3", pane);
    addButton("Overzicht #4", pane);
  }

  private static void addButton(String text, Container container) {
    // Add button with text, align left
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

    // Add all panes
    frame.add(bottomPane(), SOUTH);
    frame.add(menu(), WEST);
    frame.add(mainPane(), CENTER);

    // Make sure the application can be used full-screen on MacOS devices
    try {
      if (System.getProperty("os.name").startsWith("Mac"))
        FullScreenMacOS.setFullScreenEnabled(frame, true);
    } catch (FullScreenException ex) {
      exception(ex);
    }

    // Make the frame visible
    frame.setVisible(true);
  }

  private JPanel mainPane() {
    // Create panel with 10px padding
    JPanel mainPanel = new JPanel(new BorderLayout());
    mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

    // Add sub-panels
    mainPanel.add(selectSeries(), NORTH);
    mainPanel.add(seriesOverview(), CENTER);

    return mainPanel;
  }

  private JPanel seriesOverview() {
    JPanel seriesOverview = new JPanel(new BorderLayout());

    // Create, center, and add padding to label
    JLabel averageViews = new JLabel("Gemiddeld 48.2% bekeken per aflevering");
    averageViews.setBorder(new EmptyBorder(5, 0, 10, 0));
    averageViews.setHorizontalAlignment(JLabel.CENTER);

    // Add sub-panels
    seriesOverview.add(averageViews, NORTH);
    seriesOverview.add(serieOverview());

    return seriesOverview;
  }

  private JPanel serieOverview() {
    // Create panel with 1px black border
    JPanel main = new JPanel(new BorderLayout());
    main.setBorder(BorderFactory.createLineBorder(Color.black));

    // Create inner panel with 10px padding
    JPanel inner = new JPanel(new BorderLayout());
    inner.setBorder(new EmptyBorder(10, 10, 10, 10));

    // Sample title + formatting (using private values to ease future editing)
    JLabel title = new JLabel(this.title);
    title.setFont(new Font(title.getFont().getFontName(), Font.BOLD, 18));
    // Sample count
    JLabel episodeCount = new JLabel("No. of episodes : " + this.episodeCount);

    // Create sub-panel to make locations easier
    JPanel aboutSerieInner = new JPanel(new BorderLayout());

    // New label, use <html> to allow easy formatting and wrapping of text
    JLabel aboutSerie = new JLabel();
    aboutSerie.setText(
        "<html><i>A Congressman works with his equally conniving wife to exact revenge on the people who betrayed him.</i>"
            + "<br><br><b>Creator</b>: Beau Willimon"
            + "<br><br><b>Stars</b>: Kevin Spacey, Michel Gill, Robin WrightNewline</html>");
    aboutSerie.setFont(
        new Font(aboutSerie.getFont().getFontName(), Font.PLAIN, aboutSerie.getFont().getSize()));

    // Add all the things!
    aboutSerieInner.add(aboutSerie, NORTH);
    JPanel quickView = new JPanel(new BorderLayout());
    quickView.add(title, NORTH);
    quickView.add(episodeCount, SOUTH);
    inner.add(quickView, NORTH);
    inner.add(aboutSerieInner, CENTER);
    main.add(inner);

    return main;
  }

  private JPanel selectSeries() {
    // Create dropdown with sample values
    JPanel selectSeries = new JPanel();
    JLabel selectSerie = new JLabel("Selecteer een serie : ");
    JComboBox<String> comboBox = new JComboBox<>(this.choices);
    comboBox.setVisible(true);

    // Add label + dropdown
    selectSeries.add(selectSerie);
    selectSeries.add(comboBox);

    return selectSeries;
  }

  private JPanel menu() {
    // Create and set up the content pane.
    JPanel menu = new JPanel();
    menu.setBorder(new EmptyBorder(10, 0, 0, 0));
    menu.setBackground(Color.GRAY);
    fillMenu(menu);

    return menu;
  }

  private JPanel bottomPane() {
    // Create bottom panel (static information)
    JPanel bottomPanel = new JPanel(new BorderLayout());

    bottomPanel.setBackground(Color.LIGHT_GRAY);
    bottomPanel.setBorder(new EmptyBorder(2, 5, 2, 5));

    // Add labels to panel
    JLabel creators =
        new JLabel("Informatica 1.2 - 23IVT1D - Guus Lieben, Tim van Wouwe, Coen Rijsdijk");
    creators.setFont(
        new Font(creators.getFont().getFontName(), Font.ITALIC, creators.getFont().getSize() - 2));
    bottomPanel.add(new JLabel("Netflix Statistix"), WEST);
    bottomPanel.add(creators, EAST);

    return bottomPanel;
  }
}
