package com.netflix;

import com.netflix.commons.DatabaseHandle;
import com.netflix.commons.PropertyIndex;
import com.netflix.gui.NetflixGUI;

public class Netflix {

  public static void main(String... args) {
    System.out.println(DatabaseHandle.generateConnectionString());

    final int width = Integer.parseInt(PropertyIndex.get("window.width"));
    final int height = Integer.parseInt(PropertyIndex.get("window.height"));

    new NetflixGUI(width, height);
  }
}
