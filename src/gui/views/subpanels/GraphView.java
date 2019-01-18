package com.netflix.gui.views.subpanels;

import com.netflix.commons.*;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.SamplingXYLineRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

public class GraphView {

  public static GraphView instance;
  public JFrame frame;
  private XYSeries series;
  private JFreeChart chart;
  private Plot plot;
  private XYDataset data;
  private JPanel panel;
  private JPanel wrapper;
  private JPanel labelPanel;
  private JLabel graphLabel;
  private JPanel bottomPanel;

  public GraphView(String domainAxis, String rangeAxis, String graph) {
    series = new XYSeries("");

    series.add(0, 0);
    data = new XYSeriesCollection(series);
    plot =
        new XYPlot(
            data,
            new NumberAxis(domainAxis),
            new NumberAxis(rangeAxis),
            new SamplingXYLineRenderer());
    chart = new JFreeChart(plot);

    panel = new ChartPanel(chart);
    wrapper = new JPanel();
    labelPanel = new JPanel();
    graphLabel = new JLabel(graph);

    frame = new JFrame();

    // Center frame
    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    frame.setLocation(
        dim.width / 2 - this.frame.getSize().width / 2,
        dim.height / 2 - this.frame.getSize().height / 2);

    XYItemRenderer renderer = chart.getXYPlot().getRenderer();
    renderer.setSeriesPaint(0, Color.LIGHT_GRAY);

    NumberAxis xAxis = (NumberAxis) chart.getXYPlot().getDomainAxis();
    xAxis.setTickUnit(new NumberTickUnit(1));
    xAxis.setTickLabelPaint(Color.LIGHT_GRAY);
    xAxis.setLabelPaint(Color.LIGHT_GRAY);

    NumberAxis yAxis = (NumberAxis) chart.getXYPlot().getRangeAxis();
    yAxis.setTickLabelPaint(Color.LIGHT_GRAY);
    yAxis.setRange(0, 100);
    yAxis.setLabelPaint(Color.LIGHT_GRAY);

    wrapper.add(panel);
    chart.removeLegend();

    labelPanel.setBackground(new Color(40, 40, 40));

    graphLabel.setFont(new Font(graphLabel.getFont().getName(), Font.ITALIC, 13));

    graphLabel.setHorizontalAlignment(JLabel.CENTER);
    graphLabel.setForeground(Color.LIGHT_GRAY);
    labelPanel.add(graphLabel);

    bottomPanel = new JPanel(new BorderLayout());

    bottomPanel.add(labelPanel, BorderLayout.NORTH);
    bottomPanel.add(Commons.credits(), BorderLayout.SOUTH);

    frame.add(Commons.logo(), BorderLayout.NORTH);
    frame.add(wrapper, BorderLayout.CENTER);
    frame.add(bottomPanel, BorderLayout.SOUTH);

    ((ChartPanel) panel).setDomainZoomable(false);
    ((ChartPanel) panel).setRangeZoomable(false);

    // Borders
    chart.setBorderPaint(new Color(40, 40, 40));
    graphLabel.setBorder(new EmptyBorder(0, 0, 8, 0));
    wrapper.setBorder(new EmptyBorder(10, 10, 10, 10));

    // Backgrounds
    chart.getXYPlot().setBackgroundPaint(new Color(40, 40, 40));
    chart.setBackgroundPaint(new Color(40, 40, 40));
    frame.setBackground(new Color(40, 40, 40));
    panel.setBackground(new Color(40, 40, 40));
    wrapper.setBackground(new Color(40, 40, 40));

    frame.setResizable(false);
    frame.pack();
  }

  public void addNumber(int x, double y) {
    if (x == 0) x = (int) this.series.getMaxX() + 1;
    this.series.add(x, y);
  }

  public void showGraph(boolean visible) {
    frame.setVisible(visible);
  }
}
