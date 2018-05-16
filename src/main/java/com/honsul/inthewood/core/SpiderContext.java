package com.honsul.inthewood.core;

import java.io.Serializable;

import com.honsul.inthewood.core.model.Hotel;

public class SpiderContext {

  static class ContextInfo implements Serializable {
    String hotelId = null;
    String hotelNm = null;
  }

  private final static ThreadLocal<ContextInfo> contextHolder = new ThreadLocal<ContextInfo>() {
    @Override
    protected ContextInfo initialValue() {
      return new ContextInfo();
    }
  };

  private SpiderContext() {
  }

  public static void setHotelId(String hotelId) {
    ContextInfo context = contextHolder.get();
    context.hotelId = hotelId;
  }

  public static String getHotelId() {
    ContextInfo context = contextHolder.get();
    return context.hotelId;
  }

  public static void setHotelNm(String hotelNm) {
    ContextInfo context = contextHolder.get();
    context.hotelNm = hotelNm;
  }

  public static String getHotelNm() {
    ContextInfo context = contextHolder.get();
    return context.hotelNm;
  }
  
  public static void clean() {
    contextHolder.remove();
  }

  public static void setHotel(Hotel hotel) {
    setHotelId(hotel.getHotelId());
    setHotelNm(hotel.getHotelNm());
  }
}
