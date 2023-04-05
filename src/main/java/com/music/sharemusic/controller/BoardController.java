package com.music.sharemusic.controller;

import com.music.sharemusic.service.BoardServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/board")
public class BoardController {

  @Autowired
  BoardServiceImpl boardServiceImpl;

  @GetMapping("/write")
  public String write() {
    return "/board/write";
  }

  @GetMapping("view")
  public String view() {
    return "/board/view";
  }

  @GetMapping("modify")
  public String modify() {
    return "/board/modify";
  }

  @PostMapping("/write")
  public void writeprogress() {}
}
