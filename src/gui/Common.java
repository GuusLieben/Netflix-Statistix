package com.netflix.gui;

import com.netflix.gui.listeners.ActionListeners;
import com.netflix.gui.panes.GradientPanel;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import static java.awt.BorderLayout.*;

public class Common {

  public static JPanel logo() {
    // Create and add logo above menu
    GradientPanel gradientPanel = new GradientPanel();

    JPanel logo = gradientPanel.getGradientPanel();

    logo.setLayout(new BorderLayout());

    // Get the Netflix logo and scale it properly
    Image image = new ImageIcon("resources/netflix.png").getImage();
    ImageIcon icon = new ImageIcon(image.getScaledInstance(68, 30, Image.SCALE_SMOOTH));

    // Use a label to attach the image as icon
    JLabel thumb = new JLabel();
    thumb.setIcon(icon);

    // Fix some styling and add the label with icon
    logo.setBackground(new Color(151, 2, 4));
    logo.setBorder(new EmptyBorder(10, 10, 7, 0));
    logo.add(thumb, WEST);

    return logo;
  }

  static JPanel menu() {
    // Create and set up the content pane.
    JPanel menu = new GradientPanel().getGradientPanel();
    JPanel wrapper = new GradientPanel().getGradientPanel();
    wrapper.setLayout(new BorderLayout());

    // Basic styling
    menu.setBorder(new EmptyBorder(0, 5, 0, 15));
    menu.setBackground(new Color(34, 34, 34));
    wrapper.setBackground(new Color(34, 34, 34));
    menu.setLayout(new FlowLayout(FlowLayout.LEFT));

    // Fill the menu with buttons, then add the menu to the wrapper
    fillMenu(menu);
    wrapper.add(menu);

    return wrapper;
  }

  private static void fillMenu(Container pane) {
    // Make all content (buttons) align vertically
    pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
    JPanel panel = new JPanel(new BorderLayout());
    panel.setOpaque(false);

    // Add buttons with their appropriate location and label
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

    // Background styling
    wrapper.setOpaque(false);

    // Grab appropriate image depending on the label, image will be null if it's another label
    Image image = null;
    switch (text) {
      case "Series":
        ActionListeners.switchPaneOnClick(button, "Series");
        image = new ImageIcon("resources/serie.png").getImage();
        break;
      case "Films":
          ActionListeners.switchPaneOnClick(button, "Films");
        image = new ImageIcon("resources/film.png").getImage();
        break;
      case "Account":
          ActionListeners.switchPaneOnClick(button, "Account");
        image = new ImageIcon("resources/account.png").getImage();
        break;
      default:
        break;
    }

    // Scale icon to fit labels, then add it to the label
    ImageIcon icon = new ImageIcon(image.getScaledInstance(12, 12, Image.SCALE_SMOOTH));
    button.setIcon(icon);

    // Add hover effect (underline and cursor)
    ActionListeners.mouseEventUnderline(button);

    // Style buttons
    button.setOpaque(false);

    Border margin = new EmptyBorder(0, 0, 0, 15);
    Border compound = new CompoundBorder(margin, null);
    button.setBorder(compound);
    button.setFont(new Font(button.getFont().getName(), button.getFont().getStyle(), 12));

    // Create panel for single button, easy for margins
    JPanel panel = new JPanel();
    panel.setBorder(new EmptyBorder(5, 0, 0, 0));
    panel.setOpaque(false);

    // Add all the things
    panel.add(button);
    wrapper.add(panel);
    container.add(wrapper, location);
  }

  public static JPanel bottomPane() {
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

    // Project label
    JLabel project = new JLabel("Netflix Statistix");
    project.setForeground(new Color(219, 0, 0));

    // Add all the things
    bottomPanel.add(project, WEST);
    bottomPanel.add(creators, EAST);

    return bottomPanel;
  }
}
