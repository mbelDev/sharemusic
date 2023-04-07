package com.music.sharemusic.controller;

import com.music.sharemusic.dto.LoggedDto;
import com.music.sharemusic.dto.MemberDto;
import com.music.sharemusic.service.MemberServiceImpl;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@SessionAttributes("loggedUser")
@Controller
@RequestMapping("/member")
@Log4j2
public class MemberController {

  @Autowired
  MemberServiceImpl memberService;

  @GetMapping("/join")
  public String joinPage(@ModelAttribute MemberDto memberDto) {
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
  public String loginPage() {
    return "/member/login";
  }

  @PostMapping("/login")
  public String login(
    @ModelAttribute @Validated MemberDto memberDto,
    BindingResult bindingResult,
    @RequestParam(defaultValue = "/") String redirectURL,
    Model model
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
      return "/member/login";
    }
    model.addAttribute("loggedUser", loggedUser);
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
  @ResponseBody
  public Map<String, String> checkID(String userID) {
    log.info("get ID === {}", userID);
    Map<String, String> result = memberService.checkID(userID);
    log.info("now, I got ID === {}", result);
    return result;
  }
}
