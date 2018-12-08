package com.netflix.gui.panes;

import javax.swing.*;
import java.awt.*;

public class GradientPanel {

    private JPanel gradientPanel;

    public GradientPanel() {
        gradientPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics graphics) {
                super.paintComponent(graphics);
                Graphics2D g2d = (Graphics2D) graphics;
                g2d.setRenderingHint(
                        RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp =
                        new GradientPaint(
                                0, 0, getBackground(), 0, getHeight(), getBackground().darker());
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
    }

    public JPanel getGradientPanel() {
        return gradientPanel;
    }
}
