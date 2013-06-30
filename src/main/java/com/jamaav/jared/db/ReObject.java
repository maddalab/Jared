package com.jamaav.jared.db;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jamaav.jared.ConversionException;
import com.jamaav.jared.Ql2.Datum;
import com.jamaav.jared.util.Converters;

public class ReObject {
  private Map<String, Datum> m_data = new HashMap<String, Datum>();

  public ReObject() {
  }

  public ReObject(List<Datum.AssocPair> pairs) {
    for (Datum.AssocPair kv : pairs) {
      m_data.put(kv.getKey(), kv.getVal());
    }
  }

  public int getInt(String key) {
    return Converters.asInteger(m_data.get(key));
  }

  public int getInt(String key, int defaultValue) {
    Datum val = m_data.get(key);

    return val == null ? defaultValue : Converters.asInteger(val);
  }

  public long getLong(String key) {
    return Converters.asLong(m_data.get(key));
  }

  public long getLong(String key, long defaultValue) {
    Datum val = m_data.get(key);

    return val == null ? defaultValue : Converters.asLong(val);
  }

  public double getDouble(String key) {
    return Converters.asDouble(m_data.get(key));
  }

  public double getDouble(String key, double defaultValue) {
    Datum val = m_data.get(key);

    return val == null ? defaultValue : Converters.asDouble(val);
  }

  public boolean getBool(String key) {
    return Converters.asBoolean(m_data.get(key));
  }

  public boolean getBool(String key, boolean defaultValue) {
    Datum val = m_data.get(key);

    return val == null ? defaultValue : Converters.asBoolean(val);
  }

  public String getString(String key) {
    return Converters.asString(m_data.get(key));
  }

  public String getString(String key, String defaultValue) {
    Datum val = m_data.get(key);

    return val == null ? defaultValue : Converters.asString(val);
  }

  public boolean isNull(String key) {
    return Converters.isNull(m_data.get(key));
  }

  public boolean hasField(String key) {
    return m_data.containsKey(key);
  }

  // array
  public int[] getIntArray(String key) {
    return Converters.asIntegerArray(m_data.get(key));
  }

  public long[] getLongArray(String key) {
    return Converters.asLongArray(m_data.get(key));
  }

  public double[] getDoubleArray(String key) {
    return Converters.asDoubleArray(m_data.get(key));

  }

  public boolean[] getBoolArray(String key) {
    return Converters.asBooleanArray(m_data.get(key));

  }

  public String[] getStringArray(String key) {
    return Converters.asStringArray(m_data.get(key));

  }

  public ReObject[] getObjArray(String key) {
    return Converters.asObjArray(m_data.get(key));
  }

  public Object[] getArray(String key) throws ConversionException {
    return Converters.asArray(m_data.get(key));
  }

  // objects
  public ReObject getObj(String key) {
    return new ReObject(m_data.get(key).getRObjectList());
  }

  public Object get(String key) throws ConversionException {
    return Converters.get(m_data.get(key));
  }

  public Object get(String key, Object defaultValue) throws ConversionException {
    Datum val = m_data.get(key);

    return val == null ? defaultValue : Converters.get(val);
  }

  public Map<String, Object> toMap() throws ConversionException {
    Map<String, Object> result = new HashMap<String, Object>();
    for (Map.Entry<String, Datum> e : m_data.entrySet()) {
      result.put(e.getKey(), Converters.deepConvert(e.getValue()));
    }

    return result;
  }
}