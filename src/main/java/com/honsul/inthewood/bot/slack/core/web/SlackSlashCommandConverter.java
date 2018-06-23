package com.honsul.inthewood.bot.slack.core.web;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.honsul.inthewood.bot.slack.model.SlackSlashCommand;

@Component
public class SlackSlashCommandConverter extends AbstractHttpMessageConverter<SlackSlashCommand> {
  private final Logger logger = LoggerFactory.getLogger(getClass());
  private static final FormHttpMessageConverter formHttpMessageConverter = new FormHttpMessageConverter();
  private static final ObjectMapper mapper = new ObjectMapper();
  
  public SlackSlashCommandConverter() {
    super(new MediaType("application","x-www-form-urlencoded", Charset.forName("UTF-8")));
  }
  
  @Override
  protected boolean supports(Class<?> clazz) {
    return (SlackSlashCommand.class == clazz);
  }

  @Override
  protected SlackSlashCommand readInternal(Class<? extends SlackSlashCommand> clazz, HttpInputMessage inputMessage)
      throws IOException, HttpMessageNotReadableException {
    Map<String, String> vals = formHttpMessageConverter.read(null, inputMessage).toSingleValueMap();
    return mapper.convertValue(vals, SlackSlashCommand.class);
  }

  @Override
  protected void writeInternal(SlackSlashCommand slackSlashCommand, HttpOutputMessage outputMessage)
      throws IOException, HttpMessageNotWritableException {

  }
}
