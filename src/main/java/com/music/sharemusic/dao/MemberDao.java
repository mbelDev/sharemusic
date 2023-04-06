package com.music.sharemusic.dao;

import com.music.sharemusic.dto.MemberDto;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberDao {
  public int putMember(MemberDto memberDto);

  public int checkID(String userID);

  public int login(MemberDto memberDto);

  public MemberDto getMemberOne(String userID);

  public List<MemberDto> getMemberAll();

  public void updateMember(MemberDto memberDto);

  public void deleteMember(MemberDto memberDto);
}
