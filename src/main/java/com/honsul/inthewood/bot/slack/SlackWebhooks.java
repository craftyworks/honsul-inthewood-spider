package com.honsul.inthewood.bot.slack;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.palantir.roboslack.api.markdown.SlackMarkdown;

import me.ramswaroop.jbot.core.slack.models.Attachment;
import me.ramswaroop.jbot.core.slack.models.Field;
import me.ramswaroop.jbot.core.slack.models.RichMessage;

/**
 * This is a Slack Webhook sample. Webhooks are nothing but POST calls to
 * Slack with data relevant to your users. You can send the data
 * in the POST call in either ways:
 * 1) Send as a JSON string as the payload parameter in a POST request
 * 2) Send as a JSON string as the body of a POST request
 *
 * @author ramswaroop
 * @version 1.0.0, 21/06/2016
 */
@Component
public class SlackWebhooks {

    private static final Logger logger = LoggerFactory.getLogger(SlackWebhooks.class);

    /**
     * The Url you get while configuring a new incoming webhook
     * on Slack. You can setup a new incoming webhook
     * <a href="https://my.slack.com/services/new/incoming-webhook/">here</a>.
     */
    @Value("${slackIncomingWebhookUrl}")
    private String slackIncomingWebhookUrl;

    /**
     * Make a POST call to the incoming webhook url.
     */
    //@PostConstruct
    public void invokeSlackWebhook() {
      Map<String, String> booking = new HashMap<>();
      
      booking.put("resortId", "R001");
      booking.put("resortNm", "테스트자연휴양림");
      booking.put("address",  "충청북도 충주시 노은면 우성1길 191");
      booking.put("homepage", "http://honsul.kr");
      booking.put("bookingDt", LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + " 토요일" );
      booking.put("roomNm", "갈음달");
      booking.put("roomTypeNm",  "숲속의집");
      booking.put("price", "100,000");
      booking.put("peakPrice", "120,000");
      booking.put("occupancy",  "4");
      
        RestTemplate restTemplate = new RestTemplate();
        RichMessage richMessage = buildRichMessage(booking);

        // For debugging purpose only
        try {
            logger.debug("Reply (RichMessage): {}", new ObjectMapper().writeValueAsString(richMessage));
        } catch (JsonProcessingException e) {
            logger.debug("Error parsing RichMessage: ", e);
        }

        // Always remember to send the encoded message to Slack
        try {
            restTemplate.postForEntity(slackIncomingWebhookUrl, richMessage.encodedMessage(), String.class);
        } catch (RestClientException e) {
            logger.error("Error posting to Slack Incoming Webhook: ", e);
        }
    }
    
    private RichMessage buildRichMessage(Map<String, String> booking) {
      String fallback = booking.get("resortNm") + " " + booking.get("bookingDt") + " " + booking.get("roomNm") + " (" + booking.get("roomTypeNm") + ")";
      String title = booking.get("resortNm");
      String titleLink = booking.get("homepage");
      String text = SlackMarkdown.BOLD.decorate(booking.get("roomNm")) + " (" + booking.get("roomTypeNm") + ")"; 

      RichMessage richMessage = new RichMessage("Room Available!");
      Attachment[] attachments = new Attachment[1];
      richMessage.setAttachments(attachments);
      
      attachments[0] = new Attachment();
      attachments[0].setFallback(fallback);
      attachments[0].setTitle(title);
      attachments[0].setText(text);
      attachments[0].setTitleLink(titleLink);
      attachments[0].setColor("good");
      
      Field[] fields = new Field[3];
      attachments[0].setFields(fields);

      fields[0] = new Field();
      fields[0].setShort(false);
      fields[0].setTitle("");
      fields[0].setValue(booking.get("bookingDt"));
      
      fields[1] = new Field();
      fields[1].setShort(true);
      fields[1].setTitle("Room");
      fields[1].setValue(booking.get("occupancy") + "명");
      
      fields[2] = new Field();
      fields[2].setShort(true);
      fields[2].setTitle("Price");
      fields[2].setValue(booking.get("price") + " / " + booking.get("peakPrice") + "원");
      
      attachments[0].setFooter(booking.get("address"));
      attachments[0].setFooterIcon("https://platform.slack-edge.com/img/default_application_icon.png");
      
      return richMessage;
    }    
}
