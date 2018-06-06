package com.honsul.inthewood.bot.telegram;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import com.honsul.inthewood.HonsulInTheWoodApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= {HonsulInTheWoodApplication.class})
public class HuegoBotTest {
  @BeforeClass
  public static void setup() {
    ApiContextInitializer.init();
  }
  
  @Autowired
  HuegoBot bot;
  
  @Test
  public void test() {

    TelegramBotsApi botsApi = new TelegramBotsApi();

    try {
        botsApi.registerBot(new HuegoBot());
    } catch (TelegramApiException e) {
        e.printStackTrace();
    }
  }

}
