package com.netflix;

import com.netflix.gui.NetflixFrame;
import com.netflix.handles.DatabaseHandle;

import static com.netflix.handles.PropertiesHandle.get;
import static java.lang.Integer.parseInt;

public class Netflix {

  public static final int width = parseInt(get("window.width"));
  public static final int height = parseInt(get("window.height"));
  public static DatabaseHandle database = new DatabaseHandle();
  public static NetflixFrame gui;

  public static void main(String... args) {
    database.collectData();
    gui = new NetflixFrame(width, height);
  }
}
