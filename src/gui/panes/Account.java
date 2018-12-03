package com.netflix.gui.panes;

import javax.swing.*;

public class Account {

    public static JPanel pane() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("Account!"));
        return panel;
    }
}
