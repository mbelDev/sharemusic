package com.music.sharemusic.controller;

import com.music.sharemusic.dao.HistoryDao;
import com.music.sharemusic.dto.BoardDto;
import com.music.sharemusic.dto.LoggedDto;
import com.music.sharemusic.dto.ReplysDto;
import com.music.sharemusic.dto.SendDataDto;
import com.music.sharemusic.service.BoardService;
import com.music.sharemusic.service.MemberServiceImpl;
import com.music.sharemusic.service.ReplysServiceImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@SessionAttributes("loggedUser")
@RequestMapping("/board")
@Slf4j
public class BoardController {

  @Autowired
  BoardService boardService;

  @Autowired
  ReplysServiceImpl replysService;

  @Autowired
  MemberServiceImpl memberService;

  @Autowired
  HistoryDao historyDao;

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
  public String write(HttpSession session, Model model) {
    //로그인 정보를 받아온다
    //loggedUser 내부 정보 userID, userNM, userIcon
    if (loggedUser(session) == null) {
      //로그인 정보가 없다면
      return "redirect:/member/login";
      //로그인 페이지로 보낸다
    }
    model.addAttribute("boardDto", new BoardDto());
    return "/board/write";
    //그렇지 않다면 작성페이지로
  }

  @GetMapping("/view")
  public String view(HttpSession session, int postNo, Model model) {
    BoardDto boardDto = boardService.getPostOne(postNo);
    // List<BoardDto> recentDto = boardService.getRecentAuth(boardDto.getUserID());
    if (loggedUser(session) != null) {
      LoggedDto loggedUser = loggedUser(session);
      loggedUser.setPostNo(postNo);
      // HistoryDto historyDto = memberService.getHistoryLiked(loggedUser);
      boardService.updateHits(loggedUser);
      SendDataDto data = new SendDataDto();
      data.setPostNo(postNo);
      data.setFollowID(boardDto.getPostAuthID());
      data.setUserID(loggedUser.getUserID());
      int liked = historyDao.getLiked(data);
      loggedUser.setPostLiked(liked);
      int bookmark = historyDao.getBookmark(data);
      loggedUser.setPostBookmarked(bookmark);
      int follow = historyDao.getFollow(data);
      loggedUser.setPostFollowed(follow);
      //로그인 정보가 있을 때만 조회수 증가
    }
    String userIcon = getIcon(boardDto);
    boardDto.setPostAuthIcon(userIcon);

    model.addAttribute("boardDto", boardDto);
    List<ReplysDto> replysList = replysService.getReplyAll(postNo);
    model.addAttribute("replysList", replysList);
    log.info("", replysList);
    return "/board/view";
  }

  @GetMapping("/modify")
  public String modify(int postNo, Model model) {
    BoardDto boardDto = boardService.getPostOne(postNo);
    boardDto.setPostLink("https://youtu.be/" + boardDto.getPostLink());
    model.addAttribute("boardDto", boardDto);
    return "/board/modify";
  }

  @PostMapping("/modify")
  public String modifyprogress(
    @Valid @ModelAttribute("boardDto") BoardDto boardDto,
    BindingResult bindingResult,
    RedirectAttributes redirectAttributes,
    @ModelAttribute("linkCheck") String linkCheck
  ) {
    if (bindingResult.hasErrors() || linkCheck.equals("error")) {
      redirectAttributes.addFlashAttribute("linkCheck", linkCheck);
      redirectAttributes.addFlashAttribute("boardDto", boardDto);
      return "/board/modify";
    }

    boardDto.setPostLink(linkCheck);
    boardService.updatePost(boardDto);
    return "redirect:/board/view?postNo=" + boardDto.getPostNo();
  }

  @PostMapping("/write")
  public String writeprogress(
    HttpSession session,
    @Valid @ModelAttribute("boardDto") BoardDto boardDto,
    BindingResult bindingResult,
    RedirectAttributes redirectAttributes,
    @ModelAttribute("linkCheck") String linkCheck
  ) {
    if (bindingResult.hasErrors() || linkCheck.equals("error")) {
      redirectAttributes.addFlashAttribute("linkCheck", linkCheck);
      redirectAttributes.addFlashAttribute("boardDto", boardDto);
      return "/board/write";
    }

    LoggedDto loggedUser = loggedUser(session);
    boardDto.setPostAuth(loggedUser.getUserNM());
    boardDto.setPostAuthID(loggedUser.getUserID());
    boardDto.setPostLink(linkCheck);
    log.info("test===={}", boardDto);
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
  public ResponseEntity<Object> updateLike(SendDataDto data) {
    int updateLike = data.getLiked();
    int postNo = data.getPostNo();
    int result = boardService.updateLike(updateLike, postNo);

    Map<String, Integer> resultMap = new HashMap<>();
    resultMap.put("result", result);

    if (result > 0) {
      int postLike = boardService.getPostOne(postNo).getPostLike();
      resultMap.put("postLike", postLike);
      return ResponseEntity.status(HttpStatus.OK).body(resultMap);
    } else {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body(resultMap);
    }
  }

  //삭제 확인용 게시글 정보 열람 #권인호
  @PostMapping("/getPost")
  @ResponseBody
  public BoardDto getPostConfirm(BoardDto boardDto) {
    log.info("postData = {}", boardDto);
    int postNo = boardDto.getPostNo();
    BoardDto result = boardService.getPostOne(postNo);
    return result;
  }

  //좋아요 리로드 #권인호
  @PostMapping("/getLike")
  @ResponseBody
  public int getLike(int postNo) {
    int result = boardService.getPostOne(postNo).getPostLike();
    return result;
  }

  //덧글작성 #권인호
  @PostMapping("/reply")
  public String writeReply(
    ReplysDto replysDto,
    Model model,
    HttpSession session
  ) {
    log.info("reply Cont = {}", replysDto);
    if (session.getAttribute("loggedUser") == null) {
      return "<script>alert('로그인 해 주세용 여기에 당신 이름 석 자만 적어주세용.');location.href='/member/login';</script>";
    } else {
      LoggedDto replyAuth = (LoggedDto) session.getAttribute("loggedUser");
      int postNo = replyAuth.getPostNo();
      replysService.putReply(replyAuth, replysDto);
      List<ReplysDto> replysList = replysService.getReplyAll(postNo);
      model.addAttribute("replysList", replysList);
      BoardDto boardDto = boardService.getPostOne(postNo);
      model.addAttribute("boardDto", boardDto);
      return "/board/view :: #reply-container";
    }
  }

  //덧글의 덧글작성 #권인호
  @PostMapping("/reply/reply")
  public String writeReplyReply(
    ReplysDto replysDto,
    Model model,
    HttpSession session
  ) {
    if (session.getAttribute("loggedUser") == null) {
      return "로그인 정보가 없습니다";
    } else {
      LoggedDto replysAuth = (LoggedDto) session.getAttribute("loggedUser");
      log.info("reply==={}", replysDto);
      replysService.putReplyReply(replysAuth, replysDto);

      int postNo = replysAuth.getPostNo();
      List<ReplysDto> replysList = replysService.getReplyAll(postNo);
      model.addAttribute("replysList", replysList);
      BoardDto boardDto = boardService.getPostOne(postNo);
      model.addAttribute("boardDto", boardDto);
      return "/board/view :: #reply-container";
    }
  }

  //덧글수정 #권인호
  @PostMapping("/reply/modify")
  public String modiftReply(
    ReplysDto replysDto,
    Model model,
    HttpSession session
  ) {
    if (session.getAttribute("loggedUser") == null) {
      return "로그인 정보가 없습니다";
    }
    LoggedDto replysAuth = (LoggedDto) session.getAttribute("loggedUser");
    int postNo = replysAuth.getPostNo();
    replysDto.setReplyAuthID(replysAuth.getUserID());
    replysDto.setPostNo(postNo);
    replysService.updateReply(replysDto);
    List<ReplysDto> replysList = replysService.getReplyAll(postNo);
    model.addAttribute("replysList", replysList);
    BoardDto boardDto = boardService.getPostOne(postNo);
    model.addAttribute("boardDto", boardDto);
    return "/board/view :: #reply-container";
  }

  //덧글삭제 권인호
  @PostMapping("/reply/delete")
  public String deleteReply(
    ReplysDto replysDto,
    Model model,
    HttpSession session
  ) {
    if (session.getAttribute("loggedUser") == null) {
      return "로그인 정보가 없습니다";
    }
    LoggedDto replysAuth = (LoggedDto) session.getAttribute("loggedUser");
    int postNo = replysAuth.getPostNo();
    replysDto.setPostNo(postNo);
    replysDto.setReplyAuthID(replysAuth.getUserID());
    replysDto.setReplyHidden(2);
    replysService.updateReply(replysDto);
    List<ReplysDto> replysList = replysService.getReplyAll(postNo);
    model.addAttribute("replysList", replysList);
    BoardDto boardDto = boardService.getPostOne(postNo);
    model.addAttribute("boardDto", boardDto);
    return "/board/view :: #reply-container";
  }

  //물리적 덧글 완전 삭제 권인호
  @PostMapping("/reply/delete/complete")
  public String deleteReplyComplete(
    ReplysDto replysDto,
    Model model,
    HttpSession session
  ) {
    if (session.getAttribute("loggedUser") == null) {
      return "로그인 정보가 없습니다";
    }
    LoggedDto replysAuth = (LoggedDto) session.getAttribute("loggedUser");
    int postNo = replysAuth.getPostNo();
    replysDto.setPostNo(postNo);
    replysDto.setReplyAuthID(replysAuth.getUserID());
    replysService.deleteReply(replysDto);
    List<ReplysDto> replysList = replysService.getReplyAll(postNo);
    model.addAttribute("replysList", replysList);
    BoardDto boardDto = boardService.getPostOne(postNo);
    model.addAttribute("boardDto", boardDto);
    return "/board/view :: #reply-container";
  }

  //프로필 사진 받아오기
  public String getIcon(BoardDto boardDto) {
    String userID = boardDto.getPostAuthID();
    String postAuthIcon = memberService.getMemberOne(userID).getUserIconReal();
    return postAuthIcon;
  }
}
