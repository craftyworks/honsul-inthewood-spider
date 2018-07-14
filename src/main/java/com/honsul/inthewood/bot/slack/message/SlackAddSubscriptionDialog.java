package com.honsul.inthewood.bot.slack.message;

import com.honsul.inthewood.bot.slack.model.SlackDialog;
import com.honsul.inthewood.bot.slack.model.SlackDialogSelectElement;
import com.honsul.inthewood.bot.slack.model.SlackDialogSelectElement.DataSourceType;
import com.honsul.inthewood.bot.slack.model.SlackDialogSelectElement.Option;

public class SlackAddSubscriptionDialog {
  public static SlackDialog build(String userId) {
    SlackDialog dialog = SlackDialog.builder()
        .callbackId("add_subscription")
        .title("휴양림 정찰 추가")
        .submitLabel("저장")
        .notifyOnCancel(true)
        .build();
    
    SlackDialogSelectElement resort = SlackDialogSelectElement.builder()
        .label("휴양림")
        .name("resort_nm")
        .placeholder("휴양림 이름을 입력하거나 선택하세요.")
        .dataSource(DataSourceType.external)
        .minQueryLength(3)
        .build();
    /*
    resort.addOptionGroup(OptionGroup.of("전국").addOption(Option.of("78개 전체 휴양림", "*")))
        .addOptionGroup(OptionGroup.of("강원")
            .addOption(Option.of("가리왕산자연휴양림", "001"))
            .addOption(Option.of("검봉산자연휴양림", "001"))
            .addOption(Option.of("대관령자연휴양림", "003")))
        .addOptionGroup(OptionGroup.of("경기")
            .addOption(Option.of("산음자연휴양림", "001"))
            .addOption(Option.of("아세안자연휴양림", "001"))
            .addOption(Option.of("운악산자연휴양림", "003")));
    */
    
    SlackDialogSelectElement bookingDt = SlackDialogSelectElement.builder()
        .label("일정")
        .name("booking_dt")
        .placeholder("날짜를 입력하거나 선택하세요.")
        .build();
    bookingDt.addOption(Option.of("주말을 포함한 연휴", "holiday"))
        .addOption(Option.of("2018년 7월 14일 토요일", "2018-07-14"));
    
    dialog.addElement(resort);
    dialog.addElement(bookingDt);
    return dialog;
  }
}
