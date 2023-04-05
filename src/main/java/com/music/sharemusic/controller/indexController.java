package com.music.sharemusic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class indexController {

  @GetMapping("/")
  public String intro() {
    return "/intro";
  }

  @GetMapping("/index")
  public String index() {
    return "/index/index";
  }
}
