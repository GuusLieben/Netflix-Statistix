package com.netflix;

import com.netflix.commons.PropertyIndex;
import com.netflix.gui.NetflixGUI;

public class Netflix {

  public static void main(String... args) {
    final int width = Integer.parseInt(PropertyIndex.get("window.width"));
    final int height = Integer.parseInt(PropertyIndex.get("window.height"));
    new NetflixGUI(width, height);
      System.out.println("dikke doei!!!!!!!!!!!!!!!!!1");
  }
}
