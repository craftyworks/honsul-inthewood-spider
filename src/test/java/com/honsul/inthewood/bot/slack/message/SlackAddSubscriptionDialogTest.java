package com.honsul.inthewood.bot.slack.message;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.honsul.inthewood.bot.slack.model.SlackDialog;
import com.honsul.inthewood.bot.slack.model.SlackDialogSelectElement.Option;

public class SlackAddSubscriptionDialogTest {

private static final Logger logger = LoggerFactory.getLogger(SlackAddSubscriptionDialogTest.class);

  @Test
  public void testBuild() throws Exception {
    
    List<Option> resortOptions = new ArrayList<>();
    resortOptions.add(Option.of("전국 " + 100 + "개 휴양림" , "*"));

    
    List<Option> bookingDtOptions = new ArrayList<>();
    bookingDtOptions.add(Option.of("주말과 연휴", "holiday"));
    
    SlackDialog dialog = SlackAddSubscriptionDialog.build(resortOptions, bookingDtOptions);
    
    assertThat(dialog).isNotNull();

    dialog.getElements().forEach(e -> assertThat(e.getLabel()).isNotEmpty());
    
    dialog.getElements().forEach(e -> assertThat(e.getName()).isNotEmpty());
    
    ObjectMapper mapper = new ObjectMapper();
    logger.debug(mapper.writeValueAsString(dialog));
  }


}
