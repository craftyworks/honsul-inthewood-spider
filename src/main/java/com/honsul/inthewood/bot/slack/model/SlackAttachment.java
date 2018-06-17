package com.honsul.inthewood.bot.slack.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(Include.NON_NULL)
public class SlackAttachment {
  private String id;
  private String fallback;
  private String color;
  private String pretext;
  @JsonProperty("callback_id")
  private String callbackId;
  @JsonProperty("attachment_type")
  private String attachmentType;
  @JsonProperty("author_name")
  private String authorName;
  @JsonProperty("author_link")
  private String authorLink;
  @JsonProperty("author_icon")
  private String authorIcon;
  private String title;
  @JsonProperty("title_link")
  private String titleLink;
  private String text;
  private SlackField[] fields;
  @JsonProperty("image_url")
  private String imageUrl;
  @JsonProperty("thumb_url")
  private String thumbUrl;
  private String footer;
  @JsonProperty("footer_icon")
  private String footerIcon;
  private String ts;
  @JsonProperty("mrkdwn_in")
  private List<String> markdownIn;
  private SlackAction[] actions;
}
