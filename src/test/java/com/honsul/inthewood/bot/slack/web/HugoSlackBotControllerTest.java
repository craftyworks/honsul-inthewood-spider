package com.honsul.inthewood.bot.slack.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.honsul.inthewood.HonsulInTheWoodApplication;
import com.honsul.inthewood.bot.slack.model.SlackMessage;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= {HonsulInTheWoodApplication.class})
public class HugoSlackBotControllerTest {

  @Autowired
  HugoSlackBotController controller;
  
  @Test
  public void testSendMessage() {
    SlackMessage message = SlackMessage.builder().text("Hello").build();
    controller.sendMessage("https://hooks.slack.com/services/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", message);
  }

}
