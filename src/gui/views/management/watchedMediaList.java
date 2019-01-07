package com.netflix.gui.views.management;

import com.netflix.commons.*;
import com.netflix.entities.*;
import com.netflix.entities.abstracts.*;
import com.netflix.gui.commons.*;

import javax.swing.*;
import java.awt.*;

public class watchedMediaList {

  public static JFrame watchedMediaFrame() {
    JFrame frame = new JFrame();
    frame.add(watchedMediaPanel());

    // Keep in mind that the bottom pane has to be wide enough to prevent overdraws
    frame.setMinimumSize(new Dimension(500, 350));
    frame.setSize(500, 350);

    frame.pack();

    return frame;
  }

  public static JPanel watchedMediaPanel() {
    JPanel wrapper = new JPanel(new BorderLayout());

    JScrollPane scrollPane =
        new JScrollPane(
            watchedMediaList(),
            ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    wrapper.add(Common.logo(), BorderLayout.NORTH);
    wrapper.add(scrollPane, BorderLayout.CENTER);
    wrapper.add(Common.bottomPane(), BorderLayout.SOUTH);

    return wrapper;
  }

  public static JLabel watchedMediaList() {
    JLabel allwatched = new JLabel("<html>");

    for (Account acc : Account.accounts) {
      allwatched.setText(
          String.format("%s<br><b>Account : %s</b>", allwatched.getText(), acc.getEmail()));

      for (Profile prof : acc.getProfiles()) {
        allwatched.setText(
            allwatched.getText()
                + "<br><i>Profiel : "
                + prof.getName()
                + "</i><br>Bekeek media: <br>");
        for (MediaObject obj : prof.getMediaWatched()) {
          allwatched.setText(
              String.format(
                  "%s&nbsp;&nbsp;<b>%s</b> (Bekeken door %s%% van het totaal aantal gebruikers)<br>",
                  allwatched.getText(),
                  obj.getTitle(),
                  Commons.percentage(obj.getWatchedPercentage())));
        }
        allwatched.setText(allwatched.getText() + "<br>");
      }
    }

    allwatched.setText(allwatched.getText() + "</html>");
    allwatched.setOpaque(false);
    return allwatched;
  }
}
