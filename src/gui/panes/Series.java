package com.netflix.gui.panes;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.NORTH;
import static java.awt.BorderLayout.SOUTH;

public class Series {
    private static String title = "House of Cards";
    private static int episodes = 73;
    private static String[] choices = {
            "House of Cards",
            "Daredevil",
            "Stranger Things",
            "Orange Is the New Black",
            "Narcos",
            "The Crown"
    };

    public static JPanel pane() {
        // Create panel with 10px padding
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        mainPanel.setBackground(Color.WHITE);

        // Add sub-panels
        mainPanel.add(selectSeries(), NORTH);
        mainPanel.add(seriesOverview(), CENTER);

        return mainPanel;
    }

    static JPanel seriesOverview() {
        JPanel seriesOverview = new JPanel(new BorderLayout());

        // Create, center, and add padding to label
        JLabel averageViews = new JLabel("Gemiddeld 48.2% bekeken per aflevering");
        averageViews.setBorder(new EmptyBorder(5, 0, 10, 0));
        averageViews.setHorizontalAlignment(JLabel.CENTER);

        // Add sub-panels
        seriesOverview.add(averageViews, NORTH);
        seriesOverview.add(serieOverview());

        seriesOverview.setBackground(Color.WHITE);

        return seriesOverview;
    }

    private static JPanel serieOverview() {
        // Create panel with 1px black border
        JPanel main = new JPanel(new BorderLayout());
        main.setBorder(BorderFactory.createLineBorder(Color.black));

        // Create inner panel with 10px padding
        JPanel inner = new JPanel(new BorderLayout());
        inner.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Sample title + formatting (using private values to ease future editing)
        JLabel serieTitle = new JLabel(title);
        serieTitle.setFont(new Font(serieTitle.getFont().getFontName(), Font.BOLD, 18));
        // Sample count
        JLabel episodeCount = new JLabel("No. of episodes : " + episodes);

        // Create sub-panel to make locations easier
        JPanel aboutSerieInner = new JPanel(new BorderLayout());

        // New label, use <html> to allow easy formatting and wrapping of text
        JLabel aboutSerie = new JLabel();
        aboutSerie.setText(
                "<html><i>A Congressman works with his equally conniving wife to exact revenge on the people who betrayed him.</i>"
                        + "<br><br><b>Creator</b>: Beau Willimon"
                        + "<br><br><b>Stars</b>: Kevin Spacey, Michel Gill, Robin Wright</html>");
        aboutSerie.setFont(
                new Font(aboutSerie.getFont().getFontName(), Font.PLAIN, aboutSerie.getFont().getSize()));

        JPanel quickView = new JPanel(new BorderLayout());

        // Set background colors
        inner.setBackground(Color.WHITE);
        aboutSerie.setBackground(Color.WHITE);
        aboutSerieInner.setBackground(Color.WHITE);
        serieTitle.setBackground(Color.WHITE);
        quickView.setBackground(Color.WHITE);
        main.setBackground(Color.WHITE);

        // Add all the things!
        aboutSerieInner.add(aboutSerie, NORTH);
        quickView.add(serieTitle, NORTH);
        quickView.add(episodeCount, SOUTH);
        inner.add(quickView, NORTH);
        inner.add(aboutSerieInner, CENTER);
        main.add(inner);

        return main;
    }

    static JPanel selectSeries() {
        // Create dropdown with sample values
        JPanel selectSeries = new JPanel();
        JLabel selectSerie = new JLabel("Selecteer een serie : ");
        JComboBox<String> comboBox = new JComboBox<>(choices);
        comboBox.setVisible(true);

        // Add label + dropdown
        selectSeries.add(selectSerie);
        selectSeries.add(comboBox);

        selectSeries.setBackground(Color.WHITE);

        return selectSeries;
    }
}
