package com.honsul.inthewood.core;

import java.io.Serializable;

import com.honsul.inthewood.core.model.Resort;

public class SpiderContext {

  static class ContextInfo implements Serializable {
    String resortId = null;
    String resortNm = null;
  }

  private final static ThreadLocal<ContextInfo> contextHolder = new ThreadLocal<ContextInfo>() {
    @Override
    protected ContextInfo initialValue() {
      return new ContextInfo();
    }
  };

  private SpiderContext() {
  }

  public static void setResortId(String resortId) {
    ContextInfo context = contextHolder.get();
    context.resortId = resortId;
  }

  public static String getResortId() {
    ContextInfo context = contextHolder.get();
    return context.resortId;
  }

  public static void setResortNm(String resortNm) {
    ContextInfo context = contextHolder.get();
    context.resortNm = resortNm;
  }

  public static String getResortNm() {
    ContextInfo context = contextHolder.get();
    return context.resortNm;
  }
  
  public static void clean() {
    contextHolder.remove();
  }

  public static void setResort(Resort resort) {
    setResortId(resort.getResortId());
    setResortNm(resort.getResortNm());
  }
}
