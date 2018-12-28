package com.netflix;

import com.netflix.gui.NetflixFrame;
import com.netflix.handles.DatabaseHandle;
import com.netflix.handles.PropertiesHandle;

public class Netflix {

  public static final int width = Integer.parseInt(PropertiesHandle.get("window.width"));
  public static final int height = Integer.parseInt(PropertiesHandle.get("window.height"));
  public static DatabaseHandle database = new DatabaseHandle();
  public static NetflixFrame gui;

  @SuppressWarnings("deprecation")
  public static void main(String... args) {
    //        database.connectDatabase();
    DatabaseHandle.loadSampleData();
    gui = new NetflixFrame(width, height);
  }
}
