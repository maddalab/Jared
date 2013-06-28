package com.jamaav.jared;

public interface ResultRow {

  public abstract ReObject getObj();

  public abstract ReObject[] getObjArray();

  public abstract String[] getStringArray();

  public abstract double[] getDoubleArray();

  public abstract long[] getLongArray();

  public abstract int[] getIntArray();

  public abstract boolean[] getBoolArray();

  public abstract String getString();

  public abstract double getDouble();

  public abstract long getLong();

  public abstract int getInt();

  public abstract boolean getBool();

}
