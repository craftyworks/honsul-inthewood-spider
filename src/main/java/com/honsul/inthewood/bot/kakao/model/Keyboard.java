package com.honsul.inthewood.bot.kakao.model;

import lombok.Data;

@Data
public class Keyboard {
  private String type;
  private String[] buttons;
  
  public static Keyboard of(String type) {
    Keyboard keyboard = new Keyboard();
    keyboard.setType(type);
    keyboard.setButtons(new String[] {});
    return keyboard;
  }
}
