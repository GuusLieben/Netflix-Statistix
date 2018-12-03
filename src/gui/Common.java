package com.netflix.gui;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

import static java.awt.BorderLayout.*;

class Common {

  static JPanel menu() {
    // Create and set up the content pane.
    JPanel menu = new JPanel();
    menu.setBorder(new EmptyBorder(10, 15, 0, 15));
    menu.setBackground(new Color(51, 51, 51));
    fillMenu(menu);

    return menu;
  }

  private static void fillMenu(Container pane) {
    // Make all content (buttons) align vertically
    pane.setLayout(new BoxLayout(pane, BoxLayout.LINE_AXIS));

    // Sample data
    addButton("Algemene informatie", pane);
    addButton("Statistieken", pane);
    addButton("Account informatie", pane);
    addButton("Profiel informatie", pane);
  }

  private static void addButton(String text, Container container) {
    // Add button with text, align left
    JButton button = new JButton(text);
    button.setAlignmentX(Component.LEFT_ALIGNMENT);
    button.setForeground(Color.WHITE);
    Border margin = new EmptyBorder(5, 15, 5, 15);
    Border compound = new CompoundBorder(margin, null);
    button.setBorder(compound);
    JPanel panel = new JPanel();
    panel.setBorder(new EmptyBorder(5, 0, 5, 0));
    panel.setBackground(new Color(51, 51, 51));
    panel.add(button);
    container.add(panel);
  }

  static JPanel bottomPane() {
    // Create bottom panel (static information)
    JPanel bottomPanel = new JPanel(new BorderLayout());

    bottomPanel.setBackground(new Color(34, 31, 31));
    bottomPanel.setBorder(new EmptyBorder(4, 7, 4, 7));

    // Add labels to panel
    JLabel creators =
        new JLabel("Informatica 1.2 - 23IVT1D - Guus Lieben, Tim van Wouwe, Coen Rijsdijk");
    creators.setFont(
        new Font(creators.getFont().getFontName(), Font.ITALIC, creators.getFont().getSize() - 2));
    creators.setForeground(new Color(129, 130, 133));

    JLabel project = new JLabel("Netflix Statistix");
    project.setForeground(new Color(219, 0, 0));

    bottomPanel.add(project, WEST);
    bottomPanel.add(creators, EAST);

    return bottomPanel;
  }
}
