package com.music.sharemusic.service;

import com.music.sharemusic.dto.LoggedDto;
import com.music.sharemusic.dto.MemberDto;
import java.util.List;
import java.util.Map;
import org.springframework.validation.Errors;

public interface MemberService {
  public void putMember(MemberDto memberDto);

  public Map<String, String> checkID(String userID);

  public Map<String, String> validateHandler(Errors errors);

  public MemberDto getMemberOne(String userID);

  public MemberDto getMemberLogged(LoggedDto loggedDto);

  public LoggedDto login(MemberDto memberDto);

  public List<MemberDto> getMemberAll();

  public void updateMember(MemberDto memberDto);

  public void withdraw(MemberDto memberDto);
}
