package com.netflix;

import com.netflix.commons.Commons;
import com.netflix.gui.NetflixGUI;
import com.netflix.handles.DatabaseHandle;
import com.netflix.handles.PropertiesHandle;

import java.io.IOException;
import java.util.logging.FileHandler;

public class Netflix {

  public static final int width = Integer.parseInt(PropertiesHandle.get("window.width"));
  public static final int height = Integer.parseInt(PropertiesHandle.get("window.height"));
//    public static DatabaseHandle database = new DatabaseHandle();
  public static NetflixGUI gui;

  @SuppressWarnings("deprecation")
  public static void main(String... args) {
//        database.connectDatabase();
    DatabaseHandle.loadSampleData();
    gui = new NetflixGUI(width, height);
  }
}
