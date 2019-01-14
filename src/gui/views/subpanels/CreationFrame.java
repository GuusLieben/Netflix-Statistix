package com.netflix.gui.views.subpanels;

import com.netflix.gui.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.*;
import java.util.List;

import static java.awt.Color.LIGHT_GRAY;

public abstract class CreationFrame extends JFrame {

  private JPanel wrapper;
  private GridBagConstraints constraints;
  protected HashMap<String, JTextField> values = new HashMap<>();
  protected HashMap<String, JComboBox> comboValues = new HashMap<>();

  protected CreationFrame(String title) {
    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    setLocation(dim.width / 2 - getSize().width, dim.height / 2 - getHeight() / 2);

    wrapper = new Common.GradientPanel().getGradientPanel();
    wrapper.setLayout(new GridBagLayout());
    wrapper.setBackground(new Color(43, 43, 43));
    wrapper.setBorder(new EmptyBorder(10, 10, 10, 10));

    getContentPane().setLayout(new BorderLayout());

    constraints = new GridBagConstraints();
    constraints.gridy = 0;

    setSize(500, 201);
    setResizable(false);

    wrapper.add(spacer());
    constraints.gridy++;
    wrapper.add(
        new JLabel(String.format("<html><h3 color=white>%s</h3></html>", title)), constraints);
    constraints.gridy++;

    add(Common.logo(), BorderLayout.NORTH);
    add(wrapper, BorderLayout.CENTER);
    add(Common.bottomPane(), BorderLayout.SOUTH);
  }

  protected void addTextField(String str) {
    JTextField field = new JTextField(20);
    defaultStyling(field);
    field.setCaretColor(LIGHT_GRAY);

    values.put(str, field);

    addElement(str, field);
    setSize(500, getHeight() + 62);
  }

  protected JComboBox<String> generateDropDown(String str, List<String> list) {
    String[] arr = new String[list.size()];
    for (int i = 0; i < list.size(); i++) arr[i] = list.get(i);
    return addDropDown(str, arr);
  }

  public JComboBox<String> addDropDown(String str, String[] arr) {
    JComboBox<String> cb = new JComboBox<>(arr);
    comboValues.put(str, cb);
    //    cb.setPreferredSize(new Dimension(400, 50));
    cb.setPrototypeDisplayValue("XXXXXXXXXXXXXXXXXXXX");

    addElement(str, cb);
    setSize(500, getHeight() + 62);

    return cb;
  }

  private void addElement(String str, JComponent element) {
    constraints.gridy++;

    wrapper.add(label(str), constraints);
    constraints.gridy++;

    wrapper.add(element, constraints);
    constraints.gridy++;

    wrapper.add(spacer(), constraints);
    constraints.gridy++;
  }

  protected void addButton(Common.NButton btn) {
    btn.setForeground(Color.LIGHT_GRAY);

    constraints.gridy++;
    wrapper.add(btn, constraints);
    setSize(500, getHeight() + 50);
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
    spacer.setMinimumSize(new Dimension(20, 15));
    spacer.setOpaque(false);
    return spacer;
  }
}
