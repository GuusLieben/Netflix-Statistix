package com.netflix.gui.views;

import com.netflix.commons.*;

import javax.swing.*;
import java.awt.*;

public class AlterView extends JFrame {

  public AlterView() {
    getContentPane().setLayout(new BorderLayout());
    add(Commons.logo(), BorderLayout.NORTH);
    add(Commons.credits(), BorderLayout.SOUTH);
    
  }
}
