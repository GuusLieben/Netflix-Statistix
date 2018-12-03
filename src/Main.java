package com.netflix;

import com.netflix.commons.*;
import com.netflix.gui.*;

public class Main {

  public static void main(String... args) {
    final int width = Integer.parseInt(PropertyIndex.get("window.width"));
    final int height = Integer.parseInt(PropertyIndex.get("window.height"));
    new NetflixGUI(width, height);
    System.out.println("Dikke doei");
  }
}
