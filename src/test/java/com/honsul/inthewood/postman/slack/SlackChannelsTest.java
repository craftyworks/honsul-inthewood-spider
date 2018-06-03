package com.honsul.inthewood.postman.slack;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.honsul.inthewood.spider.SpiderApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= {SpiderApplication.class})
public class SlackChannelsTest {

  @Autowired
  SlackChannels slackChannels;
   
  @Test
  public void testGetWebhookURL() {
    System.out.println(slackChannels.getWebhookURL("honsul-holiday"));
    
  }
  @Test
  public void testGetWebhookToken() {
    System.out.println(slackChannels.getWebhookToken("honsul-holiday"));
  }

}
