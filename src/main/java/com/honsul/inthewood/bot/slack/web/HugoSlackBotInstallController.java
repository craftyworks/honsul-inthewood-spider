package com.honsul.inthewood.bot.slack.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/bot/slack/hugo")
public class HugoSlackBotInstallController {
  
  @GetMapping("/install")
  public String install(Model model) {
    System.out.println(">>>>>>>>>>>>>");
    model.addAttribute("name", "SpringBlog from Millky");
    return "bot/slack/install";
  }
}
