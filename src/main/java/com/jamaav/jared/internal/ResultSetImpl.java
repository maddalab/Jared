package com.jamaav.jared.internal;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.jamaav.jared.ConversionException;
import com.jamaav.jared.Ql2.Datum;
import com.jamaav.jared.Ql2.Term;
import com.jamaav.jared.ReObject;
import com.jamaav.jared.ResultRow;
import com.jamaav.jared.ResultSet;

class ResultRowImpl implements ResultRow {
  private final Datum data;

  ResultRowImpl(Datum data) {
    this.data = data;
  }

  @Override
  public boolean getBool() {
    return Converters.asBoolean(data);
  }

  @Override
  public int getInt() {
    return Converters.asInteger(data);
  }

  @Override
  public long getLong() {
    return Converters.asLong(data);
  }

  @Override
  public double getDouble() {
    return Converters.asDouble(data);
  }

  @Override
  public String getString() {
    return Converters.asString(data);
  }

  @Override
  public boolean[] getBoolArray() {
    return Converters.asBooleanArray(data);
  }

  @Override
  public int[] getIntArray() {
    return Converters.asIntegerArray(data);
  }

  @Override
  public long[] getLongArray() {
    return Converters.asLongArray(data);
  }

  @Override
  public double[] getDoubleArray() {
    return Converters.asDoubleArray(data);
  }

  @Override
  public String[] getStringArray() {
    return Converters.asStringArray(data);
  }

  @Override
  public ReObject[] getObjArray() {
    return Converters.asObjArray(data);
  }

  @Override
  public Object[] getArray() throws ConversionException {
    return Converters.asArray(data);
  }

  @Override
  public ReObject getObj() {
    return Converters.asObj(data);
  }

  @Override
  public ReObject get() {
    return null;
  }
}

abstract class AbstractResultSetImpl implements ResultSet {
  private final long token;
  private final List<Datum> data;

  AbstractResultSetImpl(List<Datum> data, long token) {
    this.data = data;
    this.token = token;
  }

  long getToken() {
    return token;
  }

  @Override
  public Iterator<ResultRow> iterator() {
    return new Iterator<ResultRow>() {
      Iterator<Datum> it = data.iterator();

      @Override
      public boolean hasNext() {
        return it.hasNext();
      }

      @Override
      public ResultRow next() {
        return new ResultRowImpl(it.next());
      }

      @Override
      public void remove() {
        throw new UnsupportedOperationException();
      }

    };
  }
}

class ResultSetImpl extends AbstractResultSetImpl {
  private final Term query;
  private final boolean complete;

  public ResultSetImpl(Term query, long token, List<Datum> data,
      boolean complete) {
    super(data, token);
    this.query = query;
    this.complete = complete;
  }

  @Override
  public void close() {
    // TODO: implement a close see the ql2 spec
  }
}

class SingleResultResultSet extends ResultSetImpl {
  public SingleResultResultSet(Term query, long token, Datum data) {
    super(query, token, Arrays.asList(data), true);
  }

  public void close() {
    // no-op
  }
}

class EmptyResultResultSet extends SingleResultResultSet {
  public EmptyResultResultSet(Term query, long token) {
    super(query, token, null);
  }

  public boolean isEmpty() {
    return true;
  }

  public boolean next() {
    return false;
  }

  public boolean first() {
    return false;
  }

  public void close() {
    // no-op
  }
}
