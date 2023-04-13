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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@SessionAttributes("loggedUser")
@RequestMapping("/member")
@Log4j2
public class MemberController {

  @Autowired
  MemberServiceImpl memberService;

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

  @GetMapping("/mypage")
  public String mypage(HttpServletRequest request, Model model) {
    LoggedDto loggedUser = loggedUser(request);
    if (loggedUser == null) {
      return "redirect:/member/login";
    }
    MemberDto memberDto = memberService.getMemberLogged(loggedUser);
    model.addAttribute("memberDto", memberDto);
    Map<String, Object> historyList = new HashMap<>();
    historyList = memberService.getHistoryList(loggedUser);
    model.addAttribute("history", historyList);
    return "/member/mypage";
  }

  @GetMapping("/user/{userID}")
  public String mypage(
    HttpServletRequest request,
    Model model,
    @PathVariable String userID
  ) {
    LoggedDto loggedUser = loggedUser(request);
    if (loggedUser == null) {
      return "redirect:/member/login";
    }
    MemberDto memberDto = memberService.getMemberLogged(loggedUser);
    model.addAttribute("memberDto", memberDto);
    return "/member/mypage";
  }

  @GetMapping("/mypage/list")
  public String mypageCategories(
    HttpServletRequest request,
    Model model,
    String category
  ) {
    LoggedDto loggedUser = loggedUser(request);
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
  public String myPost(HttpServletRequest request, Model model) {
    LoggedDto loggedUser = loggedUser(request);
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
  public String memberInfo(HttpServletRequest request, Model model) {
    HttpSession session = request.getSession(false);
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
    HttpServletRequest request,
    @ModelAttribute MemberDto memberDto
  ) {
    HttpSession session = request.getSession();
    if (session != null && session.getAttribute("loggedUser") != null) {
      return "redirect:/member/view";
    }
    return "/member/join";
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
  public String loginPage(HttpServletRequest request) {
    HttpSession session = request.getSession(false);
    if (session != null && session.getAttribute("loggedUser") != null) {
      log.info("login success");
      return "redirect:/member/mypage";
    }
    return "/member/login";
  }

  @PostMapping("/login")
  public String login(
    HttpServletRequest request,
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
    HttpSession session = request.getSession();
    session.setAttribute("loggedUser", loggedUser);
    session.setMaxInactiveInterval(30 * 60);
    return "redirect:/";
  }

  @PostMapping("/like")
  public String setLike(SendDataDto data) {
    log.info("Controller result==={}", data);
    int result = 0;
    result = memberService.updateLike(data);
    log.info("Controller result==={}", result);
    return "redirect:/member/mypage";
  }

  @PostMapping("/bookmark")
  public int setBookmark(SendDataDto data) {
    int result = 0;
    result = memberService.updateBookmark(data);
    return result;
  }

  @GetMapping("/logout")
  public String logout(HttpServletRequest request) {
    HttpSession session = request.getSession(false);
    if (session != null) {
      session.invalidate(); // 세션 날림
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
