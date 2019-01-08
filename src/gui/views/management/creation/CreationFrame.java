package com.netflix.gui.views.management.creation;

import com.netflix.gui.commons.*;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.*;

import static java.awt.Color.LIGHT_GRAY;

public class CreationFrame extends JFrame {

  JPanel wrapper;
  GridBagConstraints constraints;
  HashMap<String, JTextField> values;

  public CreationFrame(String title) {
    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    setLocation(dim.width / 2 - getSize().width, dim.height / 2 - getSize().height / 2);

    wrapper = new GradientPanel().getGradientPanel();
    wrapper.setLayout(new GridBagLayout());
    wrapper.setBackground(new Color(43, 43, 43));
    wrapper.setBorder(new EmptyBorder(10, 10, 10, 10));

    constraints = new GridBagConstraints();

    wrapper.add(new JLabel(String.format("<html><h3>%s</h3></html>", title)));

    values = new HashMap<>();
  }

  public void addTextField(String str) {
    JTextField field = new JTextField();
    defaultStyling(field);
    field.setCaretColor(LIGHT_GRAY);

    values.put(str, field);

    wrapper.add(label(str), constraints);
    constraints.gridy++;

    wrapper.add(field, constraints);
    constraints.gridy++;

    wrapper.add(spacer());
    constraints.gridy++;
  }

  private JLabel label(String str) {
    JLabel label = new JLabel(str);
    defaultStyling(label);
    return label;
  }

  private void defaultStyling(JComponent component) {
    component.setForeground(LIGHT_GRAY);
    component.setBorder(new EmptyBorder(5, 5, 5, 5));
    component.setBackground(new Color(20, 20, 20));
  }

  public Set returnValues() {
    return values.entrySet();
  }

  private JPanel spacer() {
    JPanel spacer = new JPanel();
    spacer.setMinimumSize(new Dimension(20, 5));
    spacer.setOpaque(false);
    return spacer;
  }
}
