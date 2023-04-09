package com.music.sharemusic.controller;

import com.music.sharemusic.dto.BoardDto;
import com.music.sharemusic.service.BoardService;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

  @PostMapping("/updateLike")
  public ResponseEntity<Object> updateLike(BoardDto boardDto) {
    int result = boardService.updateLike(boardDto.getPostNo());
    Map<String, Integer> resultMap = new HashMap<>();
    resultMap.put("result", result);

    if (result > 0) {
      int postLike = boardService.getPostOne(boardDto.getPostNo()).getPostLike();
      resultMap.put("postLike", postLike);
      return ResponseEntity.status(HttpStatus.OK).body(resultMap);
    } else {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body(resultMap);
    }
  }
  
}
