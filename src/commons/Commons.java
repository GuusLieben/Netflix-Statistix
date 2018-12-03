package com.netflix.commons;

import java.util.*;

import static java.lang.System.*;

public class Commons {

  public static void exception(Exception ex) {
    out.println(ex.getMessage());
    out.println(ex.getCause());
    out.println(Arrays.toString(ex.getStackTrace()));
  }
}
