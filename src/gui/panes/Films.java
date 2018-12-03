package com.netflix.gui.panes;

import javax.swing.*;
import java.awt.*;

public class Films {

    public static JPanel pane() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("Films!"));
        panel.setBackground(Color.WHITE);
        return panel;
    }
}
