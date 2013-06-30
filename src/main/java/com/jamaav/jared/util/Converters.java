package com.jamaav.jared.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jamaav.jared.ConversionException;
import com.jamaav.jared.Ql2.Datum;
import com.jamaav.jared.Ql2.Datum.DatumType;
import com.jamaav.jared.Ql2.Term;
import com.jamaav.jared.Ql2.Term.TermType;
import com.jamaav.jared.db.ReObject;

public final class Converters {
  private Converters() {
  }

  public static int asInteger(Datum datum) {
    return (int) datum.getRNum();
  }

  public static long asLong(Datum datum) {
    return (long) datum.getRNum();
  }

  public static double asDouble(Datum datum) {
    return datum.getRNum();
  }

  public static boolean asBoolean(Datum datum) {
    return datum.getRBool();
  }

  public static String asString(Datum datum) {
    return datum.getRStr();
  }

  public static boolean isNull(Datum datum) {
    return datum.getType() == Datum.DatumType.R_NULL;
  }

  // array
  public static int[] asIntegerArray(Datum datum) {
    List<Datum> values = datum.getRArrayList();
    int[] result = new int[values.size()];
    int idx = 0;
    for (Datum d : values) {
      result[idx++] = (int) d.getRNum();
    }

    return result;
  }

  public static long[] asLongArray(Datum datum) {
    List<Datum> values = datum.getRArrayList();
    long[] result = new long[values.size()];
    int idx = 0;
    for (Datum d : values) {
      result[idx++] = (long) d.getRNum();
    }

    return result;
  }

  public static double[] asDoubleArray(Datum datum) {
    List<Datum> values = datum.getRArrayList();
    double[] result = new double[values.size()];
    int idx = 0;
    for (Datum d : values) {
      result[idx++] = d.getRNum();
    }

    return result;
  }

  public static boolean[] asBooleanArray(Datum datum) {
    List<Datum> values = datum.getRArrayList();
    boolean[] result = new boolean[values.size()];
    int idx = 0;
    for (Datum d : values) {
      result[idx++] = d.getRBool();
    }

    return result;
  }

  public static String[] asStringArray(Datum datum) {
    String[] result = new String[datum.getRArrayCount()];
    int idx = 0;
    for (Datum d : datum.getRArrayList()) {
      result[idx++] = d.getRStr();
    }

    return result;
  }

  public static ReObject[] asObjArray(Datum datum) {
    ReObject[] result = new ReObject[datum.getRArrayCount()];
    int idx = 0;
    for (Datum d : datum.getRArrayList()) {
      result[idx++] = asObj(d);
    }

    return result;
  }

  public static Object[] asArray(Datum datum) throws ConversionException {
    Object[] result = new Object[datum.getRArrayCount()];
    int idx = 0;
    for (Datum d : datum.getRArrayList()) {
      result[idx++] = get(d);
    }

    return result;
  }

  // objects
  public static ReObject asObj(Datum datum) {
    return new ReObject(datum.getRObjectList());
  }

  public static Object get(Datum data) throws ConversionException {

    switch (data.getType()) {
    case R_NULL:
      return null;
    case R_BOOL:
      return Boolean.valueOf(data.getRBool());
    case R_STR:
      return data.getRStr();
    case R_NUM:
      double dv = data.getRNum();
      Double dobj = dv;
      if (dv == dobj.intValue()) {
        return Integer.valueOf(dobj.intValue());
      } else if (dv == dobj.longValue()) {
        return Long.valueOf(dobj.longValue());
      }
      return dobj;
    case R_ARRAY:
      Object[] arr = new Object[data.getRArrayCount()];
      int idx = 0;
      for (Datum d : data.getRArrayList()) {
        arr[idx++] = get(d);
      }
      return arr;
    case R_OBJECT:
      return new ReObject(data.getRObjectList());
    }

    throw new ConversionException("Unknown data type", data);
  }

  public static Object deepConvert(Datum data) throws ConversionException {
    switch (data.getType()) {
    case R_ARRAY:
      Object[] arr = new Object[data.getRArrayCount()];
      int idx = 0;
      for (Datum d : data.getRArrayList()) {
        if (Datum.DatumType.R_OBJECT == d.getType()) {
          arr[idx++] = deepConvert(d);
        } else if (Datum.DatumType.R_ARRAY == d.getType()) {
          arr[idx++] = deepConvert(d);
        } else {
          arr[idx++] = get(d);
        }
      }
      return arr;
    case R_OBJECT:
      Map<String, Object> res = new HashMap<String, Object>(
          data.getRObjectCount());
      for (Datum.AssocPair d : data.getRObjectList()) {
        Datum val = d.getVal();
        if (Datum.DatumType.R_OBJECT == val.getType()) {
          res.put(d.getKey(), deepConvert(val));
        } else if (Datum.DatumType.R_ARRAY == d.getVal().getType()) {
          res.put(d.getKey(), deepConvert(val));
        } else {
          res.put(d.getKey(), get(val));
        }
      }
      return res;
    default:
      return get(data);
    }
  }

  public static Term asTermWithDatum(String value) {
    return Term.newBuilder().setType(TermType.DATUM).setDatum(asDatum(value))
        .build();
  }

  public static Datum asDatum(String value) {
    return Datum.newBuilder().setType(DatumType.R_STR).setRStr(value).build();
  }

  public static Datum asDatum(double value) {
    return Datum.newBuilder().setType(DatumType.R_NUM).setRNum(value).build();
  }

  public static Term asTermWithDatum(double value) {
    return Term.newBuilder().setType(TermType.DATUM).setDatum(asDatum(value))
        .build();
  }
}