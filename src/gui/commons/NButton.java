package com.netflix.gui.commons;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.util.HashMap;

public class NButton extends javax.swing.JButton {

  public NButton() {
    super();
    stylePush();
  }

  public NButton(String text) {
    super(text);
    stylePush();
  }

  private void stylePush() { // New instance of NButton? These changes will be added
    setOpaque(false);
    setContentAreaFilled(false);
    setBorderPainted(false);

    HashMap<TextAttribute, Object> textAttrMap = new HashMap<>();

    // Listens for mouse events
    addMouseListener(
        new MouseAdapter() {
          // If you hover it ...
          @Override
          public void mouseEntered(MouseEvent evt) {
            // Underline the text...
            textAttrMap.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_LOW_GRAY);
            setFont(getFont().deriveFont(textAttrMap));
            // And change the cursor
            Cursor hoverCursor = new Cursor(Cursor.HAND_CURSOR);
            setCursor(hoverCursor);
          }

          // If you are no longer hovering it ...
          @Override
          public void mouseExited(MouseEvent evt) {
            // Remove the underline effect ...
            textAttrMap.put(TextAttribute.UNDERLINE, null);
            setFont(getFont().deriveFont(textAttrMap));
            // And reset the cursor ...
            Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
            setCursor(normalCursor);
          }
        });
  }
}
