package com.honsul.inthewood.bot.slack.event;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import com.honsul.inthewood.bot.slack.SlackBotService;
import com.honsul.inthewood.bot.slack.SlackWebClient;
import com.honsul.inthewood.bot.slack.model.SlackMessage;
import com.honsul.inthewood.bot.slack.model.SlackSlashCommand;

public class SlackSlashCommandListenerTest {

  private SlackWebClient slackClient;
  
  private SlackBotService service;
  
  private SlackSlashCommandListener listener;
  
  @Before
  public void setup() {
    slackClient = mock(SlackWebClient.class);

    service = mock(SlackBotService.class);
    when(service.selectSlackSubscription(any(String.class), any(String.class))).thenReturn(new ArrayList<>());
    
    listener = new SlackSlashCommandListener();
    
    ReflectionTestUtils.setField(listener, "slackClient", slackClient);
    ReflectionTestUtils.setField(listener, "service", service);
  }
  
  @Test
  public void testReceive() throws Exception {
    SlackSlashCommand slashCommand = SlackSlashCommand.builder()
        .responseUrl("url")
        .text("list")
        .build();

    listener.receive(slashCommand);
    
    verify(slackClient).sendMessage(anyString(), any(SlackMessage.class));
  }

}
