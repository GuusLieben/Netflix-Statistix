
package com.netflix.gui.views.management;

import com.netflix.entities.abstracts.MediaObject;
import com.netflix.gui.commons.NButton;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;

class buttonGroupMenu {

  private static watchedMediaGraph mediaGraph =
      new watchedMediaGraph(
          "Media Object Id", "Percentage", "Gemiddelde kijkcijfers over totaal aantal profielen");

  private static void showGraph(JButton button, watchedMediaGraph graph) {
    button.addActionListener(
        (ActionEvent e) -> {
          if (graph.frame.isVisible()) {
            graph.showGraph(false);
            button.setText(button.getText().replace("Verberg", "Toon"));
          } else {
            graph.showGraph(true);
            button.setText(button.getText().replace("Toon", "Verberg"));
          }
        });
  }

  private static void showFrame(JButton button, JFrame frame) {
    button.addActionListener(
        (ActionEvent e) -> {
          if (frame.isVisible()) {
            frame.setVisible(false);
            button.setText(button.getText().replace("Verberg", "Toon"));
          } else {
            frame.setVisible(true);
            button.setText(button.getText().replace("Toon", "Verberg"));
          }
        });
  }

  static JPanel buttonGroup() {
    for (MediaObject object : MediaObject.objectIds) {
      mediaGraph.addNumber(object.objectId, object.getWatchedPercentage());
    }

    JPanel wrapper = new JPanel();

    JButton mediaGraph = new NButton("Toon media grafiek");
    JButton watchLogs = new NButton("Toon gebruiker-media statistieken");

    showGraph(mediaGraph, buttonGroupMenu.mediaGraph);
    showFrame(watchLogs, watchedMediaList.watchedMediaFrame());

    wrapper.add(mediaGraph);
    wrapper.add(watchLogs);
    wrapper.setOpaque(false);

    return wrapper;
  }
}
