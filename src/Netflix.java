package com.netflix;

import com.netflix.gui.NetflixFrame;
import com.netflix.commons.DataHandle;

import static com.netflix.commons.DataHandle.get;
import static java.lang.Integer.parseInt;

public class Netflix {

  public static final int width = parseInt(get("window.width"));
  public static final int height = parseInt(get("window.height"));
  public static DataHandle database = new DataHandle();
  public static NetflixFrame gui;

  public static void main(String... args) {
    database.collectData();
    gui = new NetflixFrame(width, height);
  }
}
