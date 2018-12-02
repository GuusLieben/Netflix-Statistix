package com.netflix;

import java.util.*;

import static java.lang.System.*;

class Commons {

  static void exception(Exception ex) {
    out.println(ex.getMessage());
    out.println(ex.getCause());
    out.println(Arrays.toString(ex.getStackTrace()));
  }
}
