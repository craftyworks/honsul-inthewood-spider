package com.honsul.inthewood.postman.slack;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.honsul.inthewood.spider.SpiderApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= {SpiderApplication.class})
public class SlackBotTest {

  @Autowired
  SlackBot slackBot;
  
  @Test
  public void testSendMessage() {
    slackBot.sendMessage("honsul-holiday", "안녕하세요!<br> #뿌잉");
  }

}
