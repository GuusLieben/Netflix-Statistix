package com.netflix.gui.panes;

import javax.swing.*;

public class Films {

    public static JPanel pane() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("Films!"));
        return panel;
    }
}
