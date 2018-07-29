package com.honsul.inthewood.bot.slack;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.HashMap;

import org.junit.BeforeClass;
import org.junit.Test;

import com.google.common.eventbus.EventBus;
import com.honsul.inthewood.bot.slack.model.SlackActionCommand;
import com.honsul.inthewood.bot.slack.model.SlackActionCommand.Type;
import com.honsul.inthewood.bot.slack.model.SlackActionCommandResponsable;
import com.honsul.inthewood.bot.slack.model.SlackDialogSubmissionResponse;

public class SlackBotControllerTest {
  private static SlackBotService service;
  
  private static SlackWebClient slackClient;

  private static EventBus eventBus;
  
  private static SlackBotController controller;
  
  @BeforeClass
  public static void before() {
    service = mock(SlackBotService.class);
    slackClient = mock(SlackWebClient.class);
    eventBus = mock(EventBus.class);
    
    when(service.saveSubscription(any(SlackActionCommand.class))).thenReturn(SlackDialogSubmissionResponse.ok());
    
    controller = new SlackBotController(service, slackClient, eventBus);
  }

  @Test
  public void testActionCommand() throws Exception {
    SlackActionCommand command = new SlackActionCommand();
    command.setType(Type.dialog_submission);
    command.setCallbackId("edit_subscription$123");
    command.setSubmission(new HashMap<>());
    
    SlackActionCommandResponsable response = controller.actionCommand(command);
    
    assertThat(response).isNotNull();
    assertThat(command.getSubmission()).containsKey("subscription_id").containsValue("123");
    assertThat(command.getCallbackId()).isEqualTo("edit_subscription");
    
    verify(service).saveSubscription(command);
    verify(eventBus).post(command);
  }

}
