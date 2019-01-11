package com.netflix.gui.commons;

import javax.swing.JPanel;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class GradientPanel {

    private JPanel panel = new JPanel() {
        @Override
        protected void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);
            Graphics2D g2d = (Graphics2D) graphics;

            // Make sure it's rendered smoothly
            g2d.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Add gradientPaint using the background
            GradientPaint gp =
                    new GradientPaint(
                            0, 0, getBackground(), 0, getHeight(), getBackground().darker());

            // Paint the entire rectangle
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }
    };

    public JPanel getGradientPanel() {
        return panel;
    }
}
