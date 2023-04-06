package com.music.sharemusic.controller;

import com.music.sharemusic.dto.MemberDto;
import com.music.sharemusic.service.MemberServiceImpl;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/member")
@Log4j2
public class MemberController {

  @Autowired
  MemberServiceImpl memberService;

  @GetMapping("/join")
  public String join() {
    return "/member/join";
  }

  @PostMapping("/join")
  public String joinprogress(MemberDto memberDto) {
    memberService.putMember(memberDto);
    return "redirect:/index";
  }

  @GetMapping("/login")
  public String loginPage() {
    return "/member/login";
  }

  @PostMapping("/login")
  public String login(
    @ModelAttribute @Validated MemberDto memberDto,
    BindingResult bindingResult,
    @RequestParam(defaultValue = "/") String redirectURL,
    HttpServletRequest request
  ) {
    if (bindingResult.hasErrors()) {
      return "/member/login";
    }
    MemberDto logInfo = memberService.login(memberDto);
    if (logInfo == null) {
      bindingResult.reject(
        "loginFail",
        "아이디 또는 비밀번호가 맞지 않습니다."
      );
      return "/member/login";
    }

    HttpSession session = request.getSession();
    session.setAttribute("loggedUser", memberDto);

    return "redirect:" + redirectURL;
  }

  @PostMapping("/logout")
  public String logout(HttpServletRequest request) {
    HttpSession session = request.getSession(false);
    if (session != null) {
      session.invalidate(); // 세션 날림
    }

    return "redirect:/";
  }

  @PostMapping("/checkID")
  public Map<String, Object> checkID(String userID) {
    log.info("get ID === {}", userID);
    Map<String, Object> result = memberService.checkID(userID);
    log.info("now, I got ID === {}", result);
    return result;
  }
}
