package com.honsul.inthewood.core.jdbc;

import java.util.HashMap;

import org.springframework.jdbc.support.JdbcUtils;

@SuppressWarnings("unchecked")
public class CamelCaseMap<K, V> extends HashMap<K, V> {

  private static final long serialVersionUID = 2531342599931056097L;

  @Override 
  public V put(K key, V value) {
    return super.put((K) JdbcUtils.convertUnderscoreNameToPropertyName((String)key), value); 
  }

}
