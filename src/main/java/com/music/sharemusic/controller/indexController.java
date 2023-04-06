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

  // include 추가 2023.04.06/13:55
  @GetMapping("/layout")
  public String layout() {
    return "/include/layout";
  }
}
