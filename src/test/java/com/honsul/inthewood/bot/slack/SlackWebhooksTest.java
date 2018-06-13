package com.honsul.inthewood.bot.slack;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.honsul.inthewood.HonsulInTheWoodApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= {HonsulInTheWoodApplication.class})
public class SlackWebhooksTest {

  @Autowired
  SlackWebhooks webhooks;
  
  @Test
  public void testInvokeSlackWebhook() {
    webhooks.invokeSlackWebhook();
  }

}
