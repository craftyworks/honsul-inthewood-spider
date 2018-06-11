package com.honsul.inthewood.bot.telegram;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.honsul.inthewood.HonsulInTheWoodApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= {HonsulInTheWoodApplication.class})
public class TelegramBotTokensTest {

  @Autowired
  TelegramBotTokens botTokens;
   
  @Test
  public void testGetBotToken() {
    assertThat(botTokens.getBotToken("HuegoBot"), is("520929795:AAHwDezSwwVXMF7NkdfNU2rcW8mQ1-0DC70"));
    
  }


}
