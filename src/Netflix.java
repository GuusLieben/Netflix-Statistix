package com.netflix;

import com.netflix.commons.*;
import com.netflix.gui.NetflixFrame;

import static com.netflix.commons.DataHandle.get;

public class Netflix {

  public static final int width = Integer.parseInt(get("window.width"));
  public static final int height = Integer.parseInt(get("window.height"));
  public static DataHandle database = new DataHandle();
  public static NetflixFrame gui;

  public static void main(String... args) {
    database.collectData();
    gui = new NetflixFrame(width, height);
  }
}
