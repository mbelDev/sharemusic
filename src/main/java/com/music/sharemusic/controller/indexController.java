package com.music.sharemusic.controller;

import com.music.sharemusic.dto.BoardDto;
import com.music.sharemusic.dto.LoggedDto;
import com.music.sharemusic.service.BoardService;
import java.util.HashMap;
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

  @GetMapping(value = { "/mainPage", "mainPage/{category}" })
  //Value Path 입니다. genre를 받아서 해당 장르만 뿌려주세요.
  public String indexGenre(
    HttpSession session,
    Model model,
    @PathVariable(name = "category", required = false) String category,
    @RequestParam(defaultValue = "") String searchTxt,
    @RequestParam(defaultValue = "postDate") String sort
  ) {
    if (loggedUser(session) != null) {
      LoggedDto loggedUser = loggedUser(session);
      model.addAttribute("loggedUser", loggedUser);
    }

    // 상위 랭킹
    List<BoardDto> rankList = boardService.getRankPost(category);
    model.addAttribute("rankList", rankList);

    // 게시판 글
    List<BoardDto> postList = boardService.getPostAll(
      category,
      searchTxt,
      sort
    );
    model.addAttribute("postList", postList);

    // 검색 기능 searchTxt
    model.addAttribute("searchTxt", searchTxt);
    return "/mainPage/mainPage";
  }

  @GetMapping(value = { "/mainPage/mylist/{category}" })
  public String myList(
    HttpSession session,
    Model model,
    @PathVariable(name = "category", required = false) String category,
    @RequestParam(defaultValue = "postDate") String sort
  ) {
    String userID = null;
    if (loggedUser(session) != null) {
      LoggedDto loggedUser = loggedUser(session);
      userID = loggedUser.getUserID();
      model.addAttribute("loggedUser", loggedUser);
    } else {
      return "redirect:/member/login";
    }
    Map<String, String> data = new HashMap<>();
    List<BoardDto> postList = null;
    data.put("userID", userID);
    data.put("sort", sort);
    log.info("test==={}", data);
    switch (category) {
      case "follow":
        postList = boardService.getFollowList(data);
        break;
      case "liked":
      case "bookmark":
        data.put("category", category);
        postList = boardService.getMyList(data);
        break;
      default:
        return "redirect:/mainPage";
    }
    log.info("test==={}", data);
    log.info("list === {}", postList);
    model.addAttribute("postList", postList);
    return "/mainPage/mylist";
  }

  // 월 랭킹
  @GetMapping("/monthRanking")
  public String monthRanking(Model model) {
    List<BoardDto> monthRankList = boardService.getMonthRankPost(0);
    model.addAttribute("monthRankList", monthRankList);

    Map<String, String> monthDate = boardService.getMonthRankDate(0);
    model.addAttribute("monthDate", monthDate);
    return "/mainPage/monthRanking";
  }

  // 월 랭킹 월 이동
  @PostMapping("/reload/monthRanking")
  public String reloadMonthRanking(Model model, int moveMonth) {
    List<BoardDto> monthRankList = boardService.getMonthRankPost(moveMonth);
    model.addAttribute("monthRankList", monthRankList);
    
    String target = "/mainPage/monthRanking :: #monthRankList";
    return target;
  }

  // 주간 랭킹
  @GetMapping("/weeklyRanking")
  public String weeklyRanking(Model model) {
    List<BoardDto> weeklyRankList = boardService.getWeeklyRankPost(0);
    model.addAttribute("weeklyRankList", weeklyRankList);

    Map<String, String> weeklyDate = boardService.getWeeklyRankDate(0);
    model.addAttribute("weeklyDate", weeklyDate);
    return "/mainPage/weeklyRanking";
  }

  @PostMapping("/reload/weeklyRanking")
  public String reloadWeeklyRanking(Model model, int moveWeekly) {
    List<BoardDto> weeklyRankList = boardService.getWeeklyRankPost(moveWeekly);
    model.addAttribute("weeklyRankList", weeklyRankList);

    String target = "/mainPage/weeklyRanking :: #weeklyRankList";
    return target;
  }

  // 월간, 주간 날짜
  @PostMapping("/reload/moveDate")
  public String moveDate(Model model, String dateType, int moveDate) {
    String target;

    if (dateType.equals("month")) {
      Map<String, String> monthDate = boardService.getMonthRankDate(moveDate);
      model.addAttribute("monthDate", monthDate);
      
      target = "/mainPage/monthRanking :: #monthDate";
    } 
    else {
      Map<String, String> weeklyDate = boardService.getWeeklyRankDate(moveDate);
      model.addAttribute("weeklyDate", weeklyDate);

      target = "/mainPage/weeklyRanking :: #weeklyDate";
    }

    return target;
  }

  // 테스트용
  @GetMapping(value = { "/reload", "/reload/{category}" })
  public String writtingReply(
    Model model,
    @PathVariable(name = "category", required = false) String category,
    @RequestParam(defaultValue = "") String searchTxt,
    @RequestParam(defaultValue = "postDate") String sort
  ) {
    log.info(category);
    log.info(searchTxt);
    log.info(sort);

    // 게시판 글
    List<BoardDto> postList = boardService.getPostAll(
      category,
      searchTxt,
      sort
    );
    model.addAttribute("postList", postList);

    // 검색 기능 searchTxt
    model.addAttribute("searchTxt", searchTxt);
    String target = "/mainPage/mainPage :: #test";
    return target;
  }

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
