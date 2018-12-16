/*
 * Copyright © 2018. Guus Lieben.
 * All rights reserved.
 */

package com.netflix;

import java.util.Arrays;

public class TestClass {

  public static void main(String[] args) {
    int[] input = {2, 3, 0, 6};
    System.out.println(min(input, 4));
  }

  public static int min(int[] arr, int n) {
      Arrays.sort(arr);

      if (n <= arr.length) return arr[n-1];
      else return -1;
  }
}
