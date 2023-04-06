package com.music.sharemusic.dao;

import com.music.sharemusic.dto.MemberDto;
import com.music.sharemusic.dto.ReplysDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReplysDao {
  public ReplysDto getReplyAll(int postNo);

  // postNo에 해당하는 덧글 모두 불러오기

  public void updateReply(ReplysDto replysDto);

  // 덧글 수정하기

  public void deleteReply(int replyNo, MemberDto memberDto);
  // 덧글 지우기. MemberDto는 작성자 본인인지 확인하기위해 Session에서 받아올 것.

}
