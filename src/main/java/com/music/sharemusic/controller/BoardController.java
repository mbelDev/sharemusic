package com.music.sharemusic.controller;

import com.music.sharemusic.dto.BoardDto;
import com.music.sharemusic.dto.HistoryDto;
import com.music.sharemusic.dto.LoggedDto;
import com.music.sharemusic.service.BoardService;
import com.music.sharemusic.service.MemberService;
import com.music.sharemusic.service.MemberServiceImpl;
import java.util.HashMap;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("loggedUser")
@RequestMapping("/board")
@Slf4j
public class BoardController {

  @Autowired
  BoardService boardService;

  @ModelAttribute("loggedUser")
  public LoggedDto loggedUser(HttpSession session) {
    LoggedDto loggedUser = null;
    if (session == null || session.getAttribute("loggedUser") == null) {
      return null;
    } else {
      loggedUser = (LoggedDto) session.getAttribute("loggedUser");
    }
    return loggedUser;
  }

  //세션을 만들고 세션과 모델에 LoggedDto loggedUser 를 담아주는 물건

  @GetMapping("/write")
  public String write(HttpSession session) {
    //로그인 정보를 받아온다
    //loggedUser 내부 정보 userID, userNM, userIcon
    if (loggedUser(session) == null) {
      //로그인 정보가 없다면
      return "redirect:/member/login";
      //로그인 페이지로 보낸다
    }
    return "/board/write";
    //그렇지 않다면 작성페이지로
  }

  @GetMapping("/view")
  public String view(HttpSession session, int postNo, Model model) {
    if (loggedUser(session) != null) {
      LoggedDto loggedUser = loggedUser(session);
      loggedUser.setPostNo(postNo);
      // HistoryDto historyDto = memberService.getHistoryLiked(loggedUser);
      boardService.updateHits(loggedUser);
      //로그인 정보가 있을 때만 조회수 증가
    }
    BoardDto boardDto = boardService.getPostOne(postNo);
    // List<BoardDto> recentDto = boardService.getRecentAuth(boardDto.getUserID());
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
    return "redirect:/board/view?postNo=" + boardDto.getPostNo();
  }

  @PostMapping("/write")
  public String writeprogress(HttpSession session, BoardDto boardDto) {
    LoggedDto loggedUser = loggedUser(session);
    boardDto.setPostAuth(loggedUser.getUserNM());
    boardService.putPost(boardDto);
    return "redirect:/mainPage/";
  }

  @PostMapping("/delete")
  @ResponseBody
  public Map<String, Object> delete(BoardDto boardDto) {
    int result = boardService.deletePost(boardDto);
    Map<String, Object> sendJson = new HashMap<>();

    if (result > 0) {
      sendJson.put("msg", "ok");
    } else {
      sendJson.put("msg", "fail");
    }

    return sendJson;
  }

  @PostMapping("/updateLike")
  public ResponseEntity<Object> updateLike(BoardDto boardDto) {
    int updateLike = 1; /////////////// history

    int result = boardService.updateLike(updateLike, boardDto.getPostNo());

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
