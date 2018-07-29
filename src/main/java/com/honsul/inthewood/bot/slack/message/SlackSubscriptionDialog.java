package com.honsul.inthewood.bot.slack.message;

import com.honsul.inthewood.bot.slack.model.SlackDialog;
import com.honsul.inthewood.bot.slack.model.SlackDialogSelectElement;
import com.honsul.inthewood.bot.slack.model.SlackDialogSelectElement.DataSourceType;
import com.honsul.inthewood.bot.slack.model.SlackDialogSelectElement.Option;

public class SlackSubscriptionDialog {
  public static SlackDialog build() {
    return build("add_subscription", null, null);
  }
  
  public static SlackDialog build(String callbackId) {
    return build(callbackId, null, null);
  }
      
  public static SlackDialog build(String callbackId, Option selectedResortOption, Option selectedBookingDtOption) {
    SlackDialog dialog = SlackDialog.builder()
        .callbackId(callbackId)
        .title("휴양림 정찰")
        .submitLabel("저장")
        .notifyOnCancel(false)
        .build();
    
    SlackDialogSelectElement resort = SlackDialogSelectElement.builder()
        .label("휴양림")
        .name("resort_nm")
        .placeholder("휴양림 이름을 입력하거나 선택하세요.")
        .dataSource(DataSourceType.external)
        .minQueryLength(0)
        .build();
    if(selectedResortOption != null) {
      resort.addSelectedOption(selectedResortOption);
    }
    
    SlackDialogSelectElement bookingDt = SlackDialogSelectElement.builder()
        .label("일정")
        .name("booking_dt")
        .placeholder("날짜를 입력하거나 선택하세요.")
        .dataSource(DataSourceType.external)
        .minQueryLength(0)
        .build();
    if(selectedBookingDtOption != null) {
      bookingDt.addSelectedOption(selectedBookingDtOption);
    }
    dialog.addElement(resort);
    dialog.addElement(bookingDt);
    return dialog;
  }
}
