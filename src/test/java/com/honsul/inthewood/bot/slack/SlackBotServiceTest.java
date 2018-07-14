package com.honsul.inthewood.bot.slack;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.honsul.inthewood.HonsulInTheWoodApplication;
import com.honsul.inthewood.bot.slack.model.SlackSlashCommand;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= {HonsulInTheWoodApplication.class})
public class SlackBotServiceTest {

  @Autowired
  SlackBotService service;
  
  @Test
  public void testGetSlackBotAccessToken() throws Exception {
    String token = service.getSlackBotAccessToken("U0502LPJC");
    assertThat(token).contains("xoxb");
  }

  @Test
  public void testGetSlackUserAccessToken() throws Exception {
    String token = service.getSlackUserAccessToken("U0502LPJC");
    assertThat(token).contains("xoxp");
  }

  @Test
  public void testSelectSlackSubscription() throws Exception {
    SlackSlashCommand slashCommand = mock(SlackSlashCommand.class);
    when(slashCommand.getUserId()).thenReturn("U0502LPJC");
    when(slashCommand.getChannelId()).thenReturn("GB1NY12UX");
    
    assertThat(service.selectSlackSubscription(slashCommand)).isNotEmpty();
  }
  
  
}
