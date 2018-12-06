package com.netflix.gui;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.*;
import java.util.*;

import static java.awt.BorderLayout.*;

class Common {

  static JPanel menu() {
    // Create and set up the content pane.
    JPanel wrapper = new JPanel(new BorderLayout());
    JPanel menu = new JPanel();
    menu.setBorder(new EmptyBorder(0, 15, 0, 15));
    menu.setBackground(new Color(31, 31, 31));

    // Setup breadcrumbs, can be modified by switching panels
    JLabel breadcrumb = new JLabel("Overzicht : Series");
    breadcrumb.setBorder(new EmptyBorder(3, 0, 3, 0));
    fillMenu(menu, breadcrumb);

    wrapper.add(menu, CENTER);

    // Create and add logo above menu
    JPanel logo = new JPanel();
    Image image = new ImageIcon("netflix.png").getImage(); // Get image from local file
    ImageIcon icon =
        new ImageIcon(
            image.getScaledInstance(153, 54, java.awt.Image.SCALE_SMOOTH)); // Create icon from smoothed down image

    JLabel thumb = new JLabel();
    thumb.setHorizontalAlignment(JLabel.CENTER);
    thumb.setIcon(icon);

    logo.setBackground(new Color(131, 16, 16));
    logo.setBorder(new EmptyBorder(5, 0, 5, 0));
    logo.add(thumb);

    wrapper.add(logo, NORTH);

    // Center breadcrumb, add it
    breadcrumb.setHorizontalAlignment(JLabel.CENTER);
    wrapper.add(breadcrumb, SOUTH);

    return wrapper;
  }

  private static void fillMenu(Container pane, JLabel label) {
    // Make all content (buttons) align vertically
    pane.setLayout(new BoxLayout(pane, BoxLayout.LINE_AXIS));

    // Sample data
    addButton("Series", pane, label);
    addButton("Films", pane, label);
    addButton("Account", pane, label);
  }

  private static void addButton(String text, Container container, JLabel label) {
    // Add button with text, align left
    JButton button = new JButton(text);
    button.setForeground(Color.WHITE);

    if (text.equals("Series")) NetflixGUI.switchPane(button, "Series", label);
    if (text.equals("Films")) NetflixGUI.switchPane(button, "Films", label);
    if (text.equals("Account")) NetflixGUI.switchPane(button, "Account", label);

    // MouseOver effects for the menu (underline and cursor effect)
    HashMap<TextAttribute, Object> textAttrMap = new HashMap<>();
    button.addMouseListener(
        new java.awt.event.MouseAdapter() {
          public void mouseEntered(MouseEvent evt) {
            textAttrMap.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_LOW_GRAY);
            button.setFont(button.getFont().deriveFont(textAttrMap));
            Cursor hoverCursor = new Cursor(Cursor.HAND_CURSOR);
            button.setCursor(hoverCursor);
          }

          public void mouseExited(MouseEvent evt) {
            textAttrMap.put(TextAttribute.UNDERLINE, null);
            button.setFont(button.getFont().deriveFont(textAttrMap));
            Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
            button.setCursor(normalCursor);
          }
        });

    // Style buttons
    button.setAlignmentX(Component.LEFT_ALIGNMENT);
    button.setBackground(new Color(41, 41, 41));

    Border margin = new EmptyBorder(5, 15, 5, 15);
    Border compound = new CompoundBorder(margin, null);
    button.setBorder(compound);

    // Create panel for single button, easy for margins
    JPanel panel = new JPanel();
    panel.setBorder(new EmptyBorder(5, 0, 5, 0));
    panel.setBackground(new Color(31, 31, 31));
    panel.add(button);

    container.add(panel);
  }

  static JPanel bottomPane() {
    // Create bottom panel (static information)
    JPanel bottomPanel = new JPanel(new BorderLayout());

    bottomPanel.setBackground(new Color(34, 31, 31));
    bottomPanel.setBorder(new EmptyBorder(4, 7, 4, 7));

    // Add static labels to panel
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
