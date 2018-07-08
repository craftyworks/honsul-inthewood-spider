package com.honsul.inthewood.core.jdbc;

import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.support.JdbcUtils;

@SuppressWarnings("unchecked")
public class CamelCaseMap<K, V> extends HashMap<K, V> {

  private static final long serialVersionUID = 2531342599931056097L;

  @Override 
  public V put(K key, V value) {
    if(String.class == key.getClass() && (StringUtils.isAllUpperCase((String)key) || StringUtils.contains((String)key, "_"))) {
      return super.put((K) JdbcUtils.convertUnderscoreNameToPropertyName((String)key), value);
    }
    return super.put(key, value);
  }

}
