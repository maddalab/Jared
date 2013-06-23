package com.jamaav.jared;

import com.jamaav.jared.Ql2.Datum;

public class ConversionException extends JaredException {
  private static final long serialVersionUID = 3354789486263993513L;

  private final Datum data;

  public ConversionException(String msg, Datum data) {
    super(msg);
    this.data = data;
  }

  public Datum getData() {
    return data;
  }
}
