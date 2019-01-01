package com.netflix;

import com.netflix.gui.*;
import com.netflix.handles.*;

public class Netflix {

  public static final int width = Integer.parseInt(PropertiesHandle.get("window.width"));
  public static final int height = Integer.parseInt(PropertiesHandle.get("window.height"));
  public static DatabaseHandle database = new DatabaseHandle();
  public static NetflixFrame gui;

  @SuppressWarnings("deprecation")
  public static void main(String... args) {
    database.collectData();
//    DatabaseHandle.loadSampleData();
    gui = new NetflixFrame(width, height);
  }
}
