package com.music.sharemusic.controller;

import com.music.sharemusic.dto.BoardDto;
import com.music.sharemusic.service.BoardService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/board")
@Slf4j
public class BoardController {

  @Autowired
  BoardService boardService;

  @GetMapping("/write")
  public String write() {
    return "/board/write";
  }

  @GetMapping("/view")
  public String view(int postNo, Model model) {
    boardService.updateHits(postNo);
    BoardDto boardDto = boardService.getPostOne(postNo);
    model.addAttribute("boardDto", boardDto);
    return "/board/view";
  }

  @GetMapping("/modify")
  public String modify(int postNo, Model model) {
    BoardDto boardDto = boardService.getPostOne(postNo);
    model.addAttribute("boardDto", boardDto);
    return "/board/modify";
  }

  @PostMapping("/modify")
  public String modifyprogress(BoardDto boardDto) {
    boardService.updatePost(boardDto);
    return "redirect:/index/";
  }

  @PostMapping("/write")
  public String writeprogress(BoardDto boardDto) {
    boardService.putPost(boardDto);
    return "redirect:/index/";
  }

  @PostMapping("/delete")
  public String delete(BoardDto boardDto) {
    boardService.deletePost(boardDto);
    return "redirect:/index/";
  }
}
