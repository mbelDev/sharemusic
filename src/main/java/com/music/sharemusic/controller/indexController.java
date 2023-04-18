package com.music.sharemusic.controller;

import com.music.sharemusic.dto.BoardDto;
import com.music.sharemusic.dto.LoggedDto;
import com.music.sharemusic.service.BoardService;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("loggedUser")
@Slf4j
public class indexController {

  @Autowired
  BoardService boardService;

  public LoggedDto loggedUser(HttpSession session) {
    LoggedDto loggedUser = null;
    if (session == null || session.getAttribute("loggedUser") == null) {
      return null;
    } else {
      loggedUser = (LoggedDto) session.getAttribute("loggedUser");
    }
    return loggedUser;
  }

  @GetMapping("/")
  public String intro() {
    return "/intro";
  }

  @GetMapping(value = { "/mainPage", "mainPage/{genre}", "mainPage/{genre}/{emote}" })
  //Value Path 입니다. genre를 받아서 해당 장르만 뿌려주세요.
  public String indexGenre(
    HttpSession session,
    Model model,
    @PathVariable(name = "genre", required = false) String genre,
    @PathVariable(name = "emote", required = false) String emote,
    @RequestParam(defaultValue = "") String searchTxt,
    @RequestParam(defaultValue = "postNo") String sort
  ) {
    if (loggedUser(session) != null) {
      LoggedDto loggedUser = loggedUser(session);
      model.addAttribute("loggedUser", loggedUser);
    }

    // 상위 랭킹
    List<BoardDto> rankList = boardService.getRankPost();
    model.addAttribute("rankList", rankList);

    // 게시판 글
    List<BoardDto> postList = boardService.getPostAll(genre, emote, searchTxt, sort);
    model.addAttribute("postList", postList);

    // 검색 기능 searchTxt
    model.addAttribute("searchTxt", searchTxt);
    return "/mainPage/mainPage";
  }

  //테스트용
  // @GetMapping(value = { "/reload", "/reload/{genre}" })
  // public String writtingReply(
  //   Model model,
  //   @PathVariable(name = "genre", required = false) String genre,
  //   @RequestParam(defaultValue = "") String searchTxt,
  //   @RequestParam(defaultValue = "postNo") String sort
  // ) {
  //   log.info(genre);
  //   log.info(searchTxt);
  //   log.info(sort);

  //   // 게시판 글
  //   List<BoardDto> postList = boardService.getPostAll(genre, searchTxt, sort);
  //   model.addAttribute("postList", postList);

  //   // 검색 기능 searchTxt
  //   model.addAttribute("searchTxt", searchTxt);
  //   String target = "/mainPage/mainPage :: #test";
  //   return target;
  // }

  // //테스트용
  // @PostMapping("/reload")
  // public String writtingReply(
  //   @RequestParam Map<String, String> map,
  //   Model model
  // ) {
  //   String genre = map.get("genre");
  //   String searchTxt = map.get("searchTxt");
  //   String sort = map.get("sort");
  //   log.info("genre==={}", map.get("genre"));
  //   log.info("searchTxt==={}", map.get("searchTxt"));
  //   log.info("sort by==={}", map.get("sort"));

  //   // 게시판 글
  //   List<BoardDto> postList = boardService.getPostAll(genre, searchTxt, sort);
  //   model.addAttribute("postList", postList);

  //   // 검색 기능 searchTxt
  //   model.addAttribute("searchTxt", searchTxt);
  //   String target = "/mainPage/mainPage :: #test";
  //   return target;
  // }

  // include 추가 2023.04.06/13:55
  @GetMapping("/layout")
  public String layout() {
    return "/include/layout";
  }
}
