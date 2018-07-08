package com.honsul.inthewood.bot.slack.message;

import com.honsul.inthewood.bot.slack.model.SlackDialog;
import com.honsul.inthewood.bot.slack.model.SlackDialogSelectElement;
import com.honsul.inthewood.bot.slack.model.SlackDialogSelectElement.Option;

public class UserSettingDialog {
  public static SlackDialog build(String userId) {
    SlackDialog dialog = SlackDialog.builder()
        .callbackId("callback_id")
        .title("알림 설정")
        .submitLabel("저장")
        .notifyOnCancel(true)
        .build();
    SlackDialogSelectElement sector = SlackDialogSelectElement.builder()
        .label("지역")
        .name("sector")
        .placeholder("지역을 선택하세요.")
        .build();
    sector.addOption(Option.of("전국", "*")).addOption(Option.of("서울/경기", "01")).addOption(Option.of("강원", "02"));
    
    dialog.addElement(sector);
    return dialog;
  }
}
