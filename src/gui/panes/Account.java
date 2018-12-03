package com.netflix.gui.panes;

import javax.swing.*;
import java.awt.*;

public class Account {

    public static JPanel pane() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("Account!"));
        panel.setBackground(Color.WHITE);
        return panel;
    }
}
