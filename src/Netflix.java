package com.netflix;

import com.netflix.commons.DatabaseHandle;
import com.netflix.commons.PropertyIndex;
import com.netflix.gui.NetflixGUI;

public class Netflix {

  private static final int width = Integer.parseInt(PropertyIndex.get("window.width"));
  private static final int height = Integer.parseInt(PropertyIndex.get("window.height"));
  //  public static DatabaseHandle database = new DatabaseHandle();

  @SuppressWarnings("deprecation")
  public static void main(String... args) {
    //    database.connectDatabase();
    DatabaseHandle.loadFilms();
    DatabaseHandle.loadSeries();
    DatabaseHandle.loadSampleData();

    new NetflixGUI(width, height);
  }
}
