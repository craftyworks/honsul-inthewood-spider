package com.honsul.inthewood.bot.kakao.model;

import lombok.Data;

@Data
public class Photo {
  private String url;
  private int width;
  private int height;
  
  public static Photo of(String url, int width, int height) {
    Photo photo = new Photo();
    photo.setUrl(url);
    photo.setWidth(width);
    photo.setHeight(height);
    return photo;
  }
}
