package com.music.sharemusic.service;

import com.music.sharemusic.dto.MemberDto;
import java.util.List;
import java.util.Map;

public interface MemberService {
  public void putMember(MemberDto memberDto);

  public Map<String, Object> checkID(String userID);

  // public void login(MemberDto memberDto);

  public MemberDto getMemberOne(String userID);

  public MemberDto login(MemberDto memberDto);

  public List<MemberDto> getMemberAll();

  public void updateMember(MemberDto memberDto);

  public void deleteMember(MemberDto memberDto);
}
