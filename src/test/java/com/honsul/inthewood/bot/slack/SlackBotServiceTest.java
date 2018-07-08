package com.honsul.inthewood.bot.slack;

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
public class SlackBotServiceTest {

  @Autowired
  SlackBotService service;
  
  @Test
  public void testGetSlackBotAccessToken() throws Exception {
    String token = service.getSlackBotAccessToken("U0502LPJC");
    assertThat(token.contains("xoxb"), is(true));
  }

  @Test
  public void testGetSlackUserAccessToken() throws Exception {
    String token = service.getSlackUserAccessToken("U0502LPJC");
    assertThat(token.contains("xoxp"), is(true));
  }
}
