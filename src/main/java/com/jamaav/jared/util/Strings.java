package com.jamaav.jared.util;

public class Strings {
  private Strings() {

  }

  public static boolean isEmpty(String s) {
    return s == null || s.trim().isEmpty();
  }

  public static boolean isNotEmpty(String s) {
    return !isEmpty(s);
  }
}
