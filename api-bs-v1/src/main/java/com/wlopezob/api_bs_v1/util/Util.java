package com.wlopezob.api_bs_v1.util;

public class Util {
  public static final String getKeyRedis(String key) {
    return Constants.KEY_REDIS.concat("_").concat(key);
  }
}
