package com.honsul.inthewood.bot.slack.core;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class SlackAPITest {

  @Test
  public void testGetCommand() throws Exception {
    assertThat(SlackAPI.auth_test.name(), is("auth_test"));
    
    assertThat(SlackAPI.auth_test.getCommand(), is("auth.test"));
  }

}
