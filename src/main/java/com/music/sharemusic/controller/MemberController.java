package com.music.sharemusic.controller;

import com.music.sharemusic.dto.BoardDto;
import com.music.sharemusic.dto.HistoryDto;
import com.music.sharemusic.dto.LoggedDto;
import com.music.sharemusic.dto.MemberDto;
import com.music.sharemusic.dto.SendDataDto;
import com.music.sharemusic.service.MemberServiceImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@SessionAttributes("loggedUser")
@RequestMapping("/member")
@Log4j2
public class MemberController {

  @Autowired
  MemberServiceImpl memberService;

  public LoggedDto loggedUser(HttpSession session) {
    LoggedDto loggedUser = null;
    if (session == null || session.getAttribute("loggedUser") == null) {
      return null;
    } else {
      loggedUser = (LoggedDto) session.getAttribute("loggedUser");
    }
    return loggedUser;
  }

  @GetMapping("/mypage")
  public String mypage(HttpSession session, Model model) {
    if (loggedUser(session) == null) {
      return "redirect:/member/login";
    }
    LoggedDto loggedUser = loggedUser(session);
    MemberDto memberDto = memberService.getMemberLogged(loggedUser);
    model.addAttribute("memberDto", memberDto);
    Map<String, Object> historyList = new HashMap<>();
    historyList = memberService.getHistoryList(loggedUser);
    model.addAttribute("history", historyList);
    return "/member/mypage";
  }

  @GetMapping("/user/{userID}")
  public String mypage(
    HttpSession session,
    Model model,
    @PathVariable String userID
  ) {
    if (loggedUser(session) == null) {
      return "redirect:/member/login";
    }
    LoggedDto pageUser = new LoggedDto();
    pageUser.setUserID(userID);
    MemberDto memberDto = memberService.getMemberLogged(pageUser);
    model.addAttribute("memberDto", memberDto);
    Map<String, Object> historyList = new HashMap<>();
    historyList = memberService.getHistoryList(pageUser);
    model.addAttribute("history", historyList);
    return "/member/mypage";
  }

  @GetMapping("/mypage/list")
  public String mypageCategories(
    HttpSession session,
    Model model,
    String category
  ) {
    LoggedDto loggedUser = loggedUser(session);
    if (loggedUser == null) {
      return "redirect:/member/login";
    }
    MemberDto memberDto = memberService.getMemberLogged(loggedUser);
    model.addAttribute("memberDto", memberDto);

    List<HistoryDto> historyList = null;
    switch (category) {
      case "recent":
        historyList = memberService.getHistoryRecent(loggedUser);
        break;
      case "bookmark":
        historyList = memberService.getHistoryBookmark(loggedUser);
        break;
      case "liked":
        historyList = memberService.getHistoryLiked(loggedUser);
        break;
    }
    log.info("historyList");
    model.addAttribute("historyList", historyList);

    return "/member/recent";
  }

  // @GetMapping("/mypage/recent")
  // public String mypageCategories(HttpServletRequest request, Model model) {
  //   HttpSession session = request.getSession(false);
  //   if (session != null && session.getAttribute("loggedUser") != null) {
  //     LoggedDto loggedInfo = (LoggedDto) session.getAttribute("loggedUser");
  //     MemberDto memberDto = memberService.getMemberLogged(loggedInfo);
  //     model.addAttribute("memberDto", memberDto);
  //     List<HistoryDto> historyRecent = memberService.getHistoryRecent(
  //       loggedInfo
  //     );
  //     model.addAttribute("historyRecent", historyRecent);

  //     return "/member/recent";
  //   }

  //   return "redirect:/member/login";
  // }

  @GetMapping("/mypage/written")
  public String myPost(HttpSession session, Model model) {
    LoggedDto loggedUser = loggedUser(session);
    if (loggedUser == null) {
      return "redirect:/member/login";
    }
    MemberDto memberDto = memberService.getMemberLogged(loggedUser);
    List<BoardDto> historyList = memberService.getHistoryWritten(loggedUser);
    model.addAttribute("memberDto", memberDto);
    model.addAttribute("historyList", historyList);
    return "/member/written";
  }

  @GetMapping("/view")
  public String memberInfo(HttpSession session, Model model) {
    if (session != null && session.getAttribute("loggedUser") != null) {
      LoggedDto loggedInfo = (LoggedDto) session.getAttribute("loggedUser");
      MemberDto memberDto = memberService.getMemberLogged(loggedInfo);
      model.addAttribute("memberDto", memberDto);
      return "/member/view";
    }

    return "redirect:/member/login";
  }

  @GetMapping("/join")
  public String joinPage(
    HttpSession session,
    @ModelAttribute MemberDto memberDto
  ) {
    if (loggedUser(session) == null) {
      return "/member/join";
    }
    return "redirect:/member/mypage";
  }

  @PostMapping("/join")
  public String join(
    @Valid @ModelAttribute("memberDto") MemberDto memberDto,
    BindingResult bindingResult,
    RedirectAttributes redirectAttributes
  ) {
    log.info("we're going to check all validation!!");
    if (bindingResult.hasErrors()) {
      log.info("we have some error!!! === {}", bindingResult);
      redirectAttributes.addFlashAttribute("memberDto", memberDto);
      return "redirect:/member/join";
    }
    memberService.putMember(memberDto);
    log.info("join successful === {}", memberDto);
    return "redirect:/member/login";
  }

  @GetMapping("/login")
  public String loginPage(HttpServletRequest request, HttpSession session) {
    if (loggedUser(session) == null) {
      session.setAttribute(
        "pagePrev",
        request.getHeader("Referer").split(request.getHeader("host"))[1]
      );
      log.info(request.getRequestURI());
      return "/member/login";
    }
    log.info("login success");
    return "redirect:/member/mypage";
  }

  @PostMapping("/login")
  public String login(
    HttpSession session,
    @ModelAttribute @Validated MemberDto memberDto,
    BindingResult bindingResult,
    @RequestParam(defaultValue = "/") String redirectURL
  ) {
    // if (bindingResult.hasErrors()) {
    //   log.info("what errors? === {}", bindingResult);
    //   return "/member/login";
    // }
    LoggedDto loggedUser = memberService.login(memberDto);
    if (loggedUser == null) {
      bindingResult.reject(
        "loginFailed",
        "아이디 또는 비밀번호가 맞지 않습니다."
      );
      log.info("what errors? === {}", bindingResult);
      return "redirect:/member/login";
    }
    session.setAttribute("loggedUser", loggedUser);
    session.setMaxInactiveInterval(30 * 60);
    if (session.getAttribute("pagePrev") != null) {
      log.info(session.getAttribute("pagePrev"));
      return "redirect:" + (String) session.getAttribute("pagePrev");
    }
    return "redirect:/mainPage";
  }

  @PostMapping("/like")
  @ResponseBody
  public int setLike(SendDataDto data) {
    int update = memberService.updateLike(data);
    return update;
  }

  @PostMapping("/bookmark")
  @ResponseBody
  public int setBookmark(SendDataDto data) {
    int result = memberService.updateBookmark(data);
    return result;
  }

  @PostMapping("/follow")
  @ResponseBody
  public int setfollow(SendDataDto data) {
    int result = memberService.updateFollow(data);
    return result;
  }

  @GetMapping("/logout")
  public String logout(HttpSession session, SessionStatus status) {
    if (session != null) {
      status.setComplete(); // 세션 날림
      log.info("logout");
    }

    return "redirect:/mainPage";
  }

  @PostMapping("/checkID")
  @ResponseBody
  public Map<String, String> checkID(String userID) {
    log.info("get ID === {}", userID);
    Map<String, String> result = memberService.checkID(userID);
    log.info("now, I got ID === {}", result);
    return result;
  }
}
