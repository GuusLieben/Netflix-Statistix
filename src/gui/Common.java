package com.netflix.gui;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.*;
import java.util.*;

import static java.awt.BorderLayout.*;

class Common {

  static JPanel logo() {
    // Create and add logo above menu
    JPanel logo =
        new JPanel(new BorderLayout()) {
          @Override
          protected void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);
            Graphics2D g2d = (Graphics2D) graphics;
            g2d.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            GradientPaint gp =
                new GradientPaint(
                    0, 0, getBackground().brighter(), 0, getHeight(), getBackground().darker());
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, getWidth(), getHeight());
          }
        };
    Image image = new ImageIcon("netflix.png").getImage();
    ImageIcon icon = new ImageIcon(image.getScaledInstance(68, 30, Image.SCALE_SMOOTH));

    JLabel thumb = new JLabel();
    thumb.setIcon(icon);

    logo.setBackground(new Color(151, 2, 4));
    logo.setBorder(new EmptyBorder(10, 10, 7, 0));
    logo.add(thumb, WEST);

    return logo;
  }

  static JPanel menu() {
    // Create and set up the content pane.
    JPanel wrapper = new JPanel(new BorderLayout());
    JPanel menu = new JPanel();
    menu.setBorder(new EmptyBorder(0, 5, 0, 15));
    menu.setBackground(new Color(34, 34, 34));
    wrapper.setBackground(new Color(34, 34, 34));
    menu.setLayout(new FlowLayout(FlowLayout.LEFT));

    fillMenu(menu);

    wrapper.add(menu);

    return wrapper;
  }

  private static void fillMenu(Container pane) {
    // Make all content (buttons) align vertically
    pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
    JPanel panel = new JPanel(new BorderLayout());
    addButton("Series", panel, NORTH);
    addButton("Films", panel, CENTER);
    addButton("Account", panel, SOUTH);

    pane.add(panel);
  }

  private static void addButton(String text, Container container, String location) {
    // Add button with text, align left
    JButton button = new JButton(text);
    button.setForeground(Color.WHITE);

    JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.LEFT));

    wrapper.setBackground(new Color(34, 34, 34));
    Image image = null;
    if (text.equals("Series")) {
      NetflixGUI.switchPane(button, "Series");
      image = new ImageIcon("serie.png").getImage();
    }
    if (text.equals("Films")) {
      NetflixGUI.switchPane(button, "Films");
      image = new ImageIcon("film.png").getImage();
    }
    if (text.equals("Account")) {
      NetflixGUI.switchPane(button, "Account");
      image = new ImageIcon("account.png").getImage();
    }

    ImageIcon icon = new ImageIcon(image.getScaledInstance(12, 12, Image.SCALE_SMOOTH));
    button.setIcon(icon);

    // MouseOver effects for the menu (underline and cursor effect)
    HashMap<TextAttribute, Object> textAttrMap = new HashMap<>();
    button.addMouseListener(
        new MouseAdapter() {
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
    button.setBackground(new Color(34, 34, 34));

    Border margin = new EmptyBorder(0, 0, 0, 15);
    Border compound = new CompoundBorder(margin, null);
    button.setBorder(compound);
    button.setFont(new Font(button.getFont().getName(), button.getFont().getStyle(), 12));

    // Create panel for single button, easy for margins
    JPanel panel = new JPanel();
    panel.setBorder(new EmptyBorder(5, 0, 0, 0));
    panel.setBackground(new Color(34, 34, 34));
    panel.add(button);

    wrapper.add(panel);
    container.add(wrapper, location);
  }

  static JPanel bottomPane() {
    // Create bottom panel (static information)
    JPanel bottomPanel = new JPanel(new BorderLayout());

    bottomPanel.setBackground(new Color(34, 31, 31).darker().darker());
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
