package com.honsul.inthewood.bot.slack.message;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.honsul.inthewood.bot.slack.model.SlackDialog;

public class SlackAddSubscriptionDialogTest {

private static final Logger logger = LoggerFactory.getLogger(SlackAddSubscriptionDialogTest.class);

  @Test
  public void testBuild() throws Exception {
    SlackDialog dialog = SlackAddSubscriptionDialog.build("userid");
    
    assertThat(dialog).isNotNull();

    dialog.getElements().forEach(e -> assertThat(e.getLabel()).isNotEmpty());
    
    dialog.getElements().forEach(e -> assertThat(e.getName()).isNotEmpty());
    
    ObjectMapper mapper = new ObjectMapper();
    logger.debug(mapper.writeValueAsString(dialog));
  }


}
