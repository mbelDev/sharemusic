package com.music.sharemusic.controller;

import com.music.sharemusic.dto.MemberDto;
import com.music.sharemusic.service.MemberServiceImpl;
import java.util.Map;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

  @PostMapping("/checkID")
  public Map<String, Object> checkID(String userID) {
    log.info("get ID === {}", userID);
    Map<String, Object> result = memberService.checkID(userID);
    log.info("now, I got ID === {}", result);
    return result;
  }
}
