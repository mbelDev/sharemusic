package com.music.sharemusic.controller;

import com.music.sharemusic.service.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@SessionAttributes("member")
@Controller
@Slf4j
public class indexController {

  @Autowired
  BoardService boardService;

  @GetMapping("/")
  public String intro() {
    return "/intro";
  }

  @GetMapping("/index")
  public String index(Model model) {
    // List<BoardDto> postList = boardService.getPostAll();
    // model.addAttribute("postList", postList);
    return "/index/index";
  }

  // include 추가 2023.04.06/13:55
  @GetMapping("/layout")
  public String layout() {
    return "/include/layout";
  }
}
