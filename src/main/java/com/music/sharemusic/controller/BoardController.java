package com.music.sharemusic.controller;

import com.music.sharemusic.dto.BoardDto;
import com.music.sharemusic.dto.LoggedDto;
import com.music.sharemusic.service.BoardService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/board")
@Slf4j
public class BoardController {

  @Autowired
  BoardService boardService;

  @ModelAttribute("loggedUser")
  public LoggedDto loggedUser(HttpServletRequest request) {
    HttpSession session = request.getSession(false);
    LoggedDto loggedUser = (LoggedDto) session.getAttribute("loggedUser");
    if (session == null || loggedUser == null) {
      return null;
    }
    return loggedUser;
  }

  //세션을 만들고 세션과 모델에 LoggedDto loggedUser 를 담아주는 물건

  @GetMapping("/write")
  public String write(HttpServletRequest request) {
    LoggedDto loggedUser = loggedUser(request);
    //로그인 정보를 받아온다
    //loggedUser 내부 정보 userID, userNM, userIcon
    if (loggedUser == null) {
      //로그인 정보가 없다면
      return "redirect:/member/login";
      //로그인 페이지로 보낸다
    }
    return "/board/write";
    //그렇지 않다면 작성페이지로
  }

  @GetMapping("/view")
  public String view(HttpServletRequest request, int postNo, Model model) {
    LoggedDto loggedUser = loggedUser(request);
    if (loggedUser == null) {
      return "redirect:/member/login";
    }
    loggedUser.setPostNo(postNo);
    boardService.updateHits(loggedUser);
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
  public String modifyprogress(
    BoardDto boardDto,
    @RequestParam(required = false) String genreEtc,
    @RequestParam(required = false) String emoteEtc
  ) {
    if (genreEtc != "" && (boardDto.getPostGenre()).equals("etc")) {
      boardDto.setPostGenre("etc-" + genreEtc);
    }

    if (emoteEtc != "" && (boardDto.getPostEmote()).equals("etc")) {
      boardDto.setPostEmote("etc-" + emoteEtc);
    }

    boardService.updatePost(boardDto);
    return "redirect:/mainPage/mainPage";
  }

  @PostMapping("/write")
  public String writeprogress(
    BoardDto boardDto,
    @RequestParam(required = false) String genreEtc,
    @RequestParam(required = false) String emoteEtc
  ) {
    if (genreEtc != "" && (boardDto.getPostGenre()).equals("etc")) {
      boardDto.setPostGenre("etc-" + genreEtc);
    }

    if (emoteEtc != "" && (boardDto.getPostEmote()).equals("etc")) {
      boardDto.setPostEmote("etc-" + emoteEtc);
    }

    boardService.putPost(boardDto);
    return "redirect:/mainPage/mainPage";
  }

  @PostMapping("/delete")
  public String delete(BoardDto boardDto) {
    boardService.deletePost(boardDto);
    return "redirect:/mainPage/mainPage";
  }

  @PostMapping("/updateLike")
  public ResponseEntity<Object> updateLike(BoardDto boardDto) {
    int result = boardService.updateLike(boardDto.getPostNo());
    Map<String, Integer> resultMap = new HashMap<>();
    resultMap.put("result", result);

    if (result > 0) {
      int postLike = boardService
        .getPostOne(boardDto.getPostNo())
        .getPostLike();
      resultMap.put("postLike", postLike);
      return ResponseEntity.status(HttpStatus.OK).body(resultMap);
    } else {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body(resultMap);
    }
  }
}
