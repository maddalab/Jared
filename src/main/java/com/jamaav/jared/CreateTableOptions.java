package com.jamaav.jared;

public final class CreateTableOptions {
  public static CreateTableOptions NO_OPTIONS = new CreateTableOptions();

  private String primaryKey;
  private Durability durability = Durability.HARD;
  private int cacheSize = -1;
  private String datacenter;

  public CreateTableOptions() {
  }

  public String getPrimaryKey() {
    return primaryKey;
  }

  public void setPrimaryKey(String primaryKey) {
    this.primaryKey = primaryKey;
  }

  public Durability getDurability() {
    return durability;
  }

  public void setDurability(Durability durability) {
    this.durability = durability;
  }

  public int getCacheSize() {
    return cacheSize;
  }

  public void setCacheSize(int cacheSize) {
    this.cacheSize = cacheSize;
  }

  public String getDatacenter() {
    return datacenter;
  }

  public void setDatacenter(String datacenter) {
    this.datacenter = datacenter;
  }

  public static class Builder {
    private CreateTableOptions options = new CreateTableOptions();

    public Builder withPrimaryKey(String key) {
      options.primaryKey = key;
      return this;
    }

    public Builder withDurability(Durability durability) {
      options.durability = durability;
      return this;
    }

    public Builder withCacheSize(int size) {
      options.cacheSize = size;
      return this;
    }

    public Builder withDataCenter(String datacenter) {
      options.datacenter = datacenter;
      return this;
    }
  }

  public static enum Durability {
    SOFT() {

      @Override
      public String getValue() {
        return "soft";
      }

    },
    HARD {
      @Override
      public String getValue() {
        return "hard";
      }
    };

    public abstract String getValue();
  }
}
