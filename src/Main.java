package com.netflix;

public class Main {

  public static void main(String... args) {
    final int width = Integer.parseInt(PropertyIndex.get("window.width"));
    final int height = Integer.parseInt(PropertyIndex.get("window.height"));
    new NetflixGUI(width, height);
  }
}
