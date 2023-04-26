package com.music.sharemusic.controller;

import com.music.sharemusic.dto.BoardDto;
import com.music.sharemusic.dto.HistoryDto;
import com.music.sharemusic.dto.LoggedDto;
import com.music.sharemusic.dto.MemberDto;
import com.music.sharemusic.dto.MemberInfoDto;
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
    MemberInfoDto userInfo = memberService.getMemberInfo(
      loggedUser.getUserID()
    );
    model.addAttribute("userInfo", userInfo);
    return "/member/mypage";
  }

  @GetMapping("/user/{userID}")
  public String mypage(
    HttpSession session,
    Model model,
    @PathVariable String userID
  ) {
    LoggedDto loggedUser = loggedUser(session);
    if (loggedUser == null) {
      return "redirect:/member/login";
    }
    if (loggedUser.getUserID().equals(userID)) {
      return "redirect:/member/mypage";
    }
    if (memberService.getMemberOne(userID) == null) {
      return "redirect:/member/alert";
    }
    if (memberService.getMemberOne(userID).getUserPrincipal() == -1) {
      return "redirect:/member/alert";
    }

    MemberInfoDto userInfo = memberService.getMemberInfo(userID);
    model.addAttribute("userInfo", userInfo);
    log.info(loggedUser.getUserID());
    log.info(userInfo.getUserID());

    return "/member/mypage";
  }

  @GetMapping("/alert")
  @ResponseBody
  public String alert() {
    return "<script>alert('탈퇴하였거나 존재하지 않는 회원입니다.'); history.back();</script>";
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
        historyList = memberService.getHistoryRecent(memberDto);
        break;
      case "bookmark":
        historyList = memberService.getHistoryBookmark(memberDto);
        break;
      case "liked":
        historyList = memberService.getHistoryLiked(memberDto);
        break;
    }
    log.info("historyList");
    model.addAttribute("historyList", historyList);

    return "/member/recent";
  }

  @GetMapping("/mypage/written")
  public String myPost(HttpSession session, Model model) {
    LoggedDto loggedUser = loggedUser(session);
    if (loggedUser == null) {
      return "redirect:/member/login";
    }
    MemberDto memberDto = memberService.getMemberLogged(loggedUser);
    List<BoardDto> historyList = memberService.getHistoryWritten(memberDto);
    model.addAttribute("memberDto", memberDto);
    model.addAttribute("historyList", historyList);
    return "/member/written";
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
    @Valid MemberDto memberDto,
    BindingResult bindingResult,
    RedirectAttributes redirectAttributes
  ) {
    log.info("we're going to check all validation!!");
    //중복검사
    if (memberService.checkIDtest(memberDto.getUserID()) > 0) {
      bindingResult.rejectValue(
        "userID",
        "duplicated ID",
        new Object[] { memberDto.getUserID() },
        "이미 사용중인 ID 입니다."
      );
      log.info("what errors? === {}", bindingResult);
      return "/member/join";
    }
    if (bindingResult.hasErrors()) {
      log.info("we have some error!!! === {}", bindingResult);
      redirectAttributes.addFlashAttribute("memberDto", memberDto);
      return "/member/join";
    }

    memberService.putMember(memberDto);
    log.info("join successful === {}", memberDto);
    return "redirect:/member/login";
  }

  @GetMapping("/login")
  public String loginPage(
    HttpServletRequest request,
    HttpSession session,
    @ModelAttribute MemberDto memberDto
  ) {
    if (loggedUser(session) == null) {
      if (request.getHeader("Referer") != null) {
        session.setAttribute(
          "pagePrev",
          request.getHeader("Referer").split(request.getHeader("host"))[1]
        );
      }
      return "/member/login";
    }
    return "redirect:/member/mypage";
  }

  @PostMapping("/login")
  public String login(
    HttpSession session,
    @Validated MemberDto memberDto,
    BindingResult bindingResult,
    RedirectAttributes redirectAttributes,
    @RequestParam(defaultValue = "/") String redirectURL
  ) {
    if (bindingResult.hasErrors()) {
      log.info("what errors? === {}", bindingResult);
      return "/member/login";
    }
    LoggedDto loggedUser = memberService.login(memberDto);
    if (loggedUser == null) {
      bindingResult.reject(
        "loginFailed",
        "아이디 또는 비밀번호가 맞지 않습니다."
      );

      return "/member/login";
    }
    MemberDto loggedData = memberService.getMemberOne(memberDto.getUserID());
    if (loggedData.getUserPrincipal() == -1) {
      bindingResult.reject("loginFailed", "존재하지 않거나 탈퇴한 회원입니다.");
      return "/member/login";
    }
    session.setAttribute("loggedUser", loggedUser);
    session.setMaxInactiveInterval(30 * 60);
    if (session.getAttribute("pagePrev") != null) {
      log.info(session.getAttribute("pagePrev"));
      redirectAttributes.addFlashAttribute("memberDto", memberDto);
      return "redirect:" + (String) session.getAttribute("pagePrev");
    }
    return "redirect:/mainPage";
  }

  @PostMapping("/update")
  @ResponseBody
  public int updateMember(HttpSession session, MemberDto memberDto) {
    LoggedDto loggedUser = loggedUser(session);
    if (loggedUser == null) {
      return -1;
    }
    String userID = loggedUser.getUserID();
    memberDto.setUserID(userID);
    memberService.updateMember(memberDto);
    memberDto = memberService.getMemberOne(userID);
    loggedUser.setUserIconReal(memberDto.getUserIconReal());
    loggedUser.setUserNM(memberDto.getUserNM());
    // session.setAttribute("loggedUser", loggedUser);
    return 1;
  }

  @PostMapping("/like")
  @ResponseBody
  public int setLike(HttpSession session, SendDataDto data) {
    LoggedDto loggedUser = loggedUser(session);
    if (loggedUser == null) {
      return -1;
    }
    data.setUserID(loggedUser.getUserID());
    int update = memberService.updateLike(data);
    return update;
  }

  @PostMapping("/getlike")
  @ResponseBody
  public int getLike(HttpSession session, SendDataDto data) {
    LoggedDto loggedUser = loggedUser(session);
    if (loggedUser == null) {
      return 0;
    }
    data.setUserID(loggedUser.getUserID());
    int like = memberService.getLike(data);
    return like;
  }

  @PostMapping("/bookmark")
  @ResponseBody
  public int setBookmark(HttpSession session, SendDataDto data) {
    LoggedDto loggedUser = loggedUser(session);
    if (loggedUser == null) {
      return -1;
    }
    data.setUserID(loggedUser.getUserID());
    int result = memberService.updateBookmark(data);
    return result;
  }

  @PostMapping("/follow")
  @ResponseBody
  public int setfollow(HttpSession session, SendDataDto data) {
    LoggedDto loggedUser = loggedUser(session);
    if (loggedUser == null) {
      return -1;
    }
    data.setUserID(loggedUser.getUserID());
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

  //회원 탈퇴
  @PostMapping("/withdraw")
  @ResponseBody
  public int withdraw(HttpSession session, MemberDto memberDto) {
    LoggedDto loggedUser = loggedUser(session);
    String userID = loggedUser.getUserID();
    if (loggedUser == null) {
      return -1;
    }
    MemberDto checkDto = memberService.getMemberOne(userID);
    if (
      !checkDto.getUserID().equals(userID) ||
      !checkDto.getUserPW().equals(memberDto.getUserPW())
    ) {
      log.info("here==>>{}", memberDto);
      log.info("gere==>>{}", checkDto);
      return -1;
    }
    //로그인 정보가 없거나 현재 로그인 한 정보가 일치하지 않으면 되돌려보냄

    memberService.withdraw(memberDto);
    return 1;
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
