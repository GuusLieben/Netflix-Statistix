package com.netflix;

import com.netflix.commons.Commons;
import com.netflix.commons.DatabaseHandle;
import com.netflix.commons.PropertyIndex;
import com.netflix.gui.NetflixGUI;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Netflix {

  private static final Logger logger = Logger.getLogger(Commons.class.getName());

  public static void main(String... args) {
    logger.log(Level.CONFIG, "Connection string : {0}", DatabaseHandle.generateConnectionString());

    final int width = Integer.parseInt(PropertyIndex.get("window.width"));
    final int height = Integer.parseInt(PropertyIndex.get("window.height"));

    new NetflixGUI(width, height);

    System.out.println(Commons.hashMD5("pass"));
  }
}
