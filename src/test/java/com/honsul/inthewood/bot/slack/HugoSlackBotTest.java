package com.honsul.inthewood.bot.slack;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.honsul.inthewood.HonsulInTheWoodApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= {HonsulInTheWoodApplication.class})
public class HugoSlackBotTest {

  @Autowired
  HugoSlackBot bot;
  
  @Value("${slackBotToken}")
  private String slackToken;
  
  @Test
  public void testBot() {
    assertThat(bot.getSlackBot(), is(bot));
    assertThat(bot.getSlackToken(), is(slackToken));
  }

}
