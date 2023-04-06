package com.music.sharemusic.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.music.sharemusic.dto.BoardDto;
import com.music.sharemusic.service.BoardService;

import lombok.extern.slf4j.Slf4j;

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
    List<BoardDto> postList = boardService.getPostAll();
    model.addAttribute("postList", postList);
    return "/index/index";
  }
}
