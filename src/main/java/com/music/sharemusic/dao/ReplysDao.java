package com.music.sharemusic.dao;

import com.music.sharemusic.dto.MemberDto;
import com.music.sharemusic.dto.ReplysDto;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReplysDao {
  public List<ReplysDto> getReplyAll(int postNo);

  // postNo에 해당하는 덧글 모두 불러오기

  public void putReply(ReplysDto replyDto);

  //덧글 삽입

  public void updateReply(ReplysDto replysDto);

  // 덧글 수정하기

  public void deleteReply(ReplysDto replysDto);

  // 덧글 지우기. MemberDto는 작성자 본인인지 확인하기위해 Session에서 받아올 것.

  //이하 덧글의 덧글 기능에 관련된 기능들
  public int getReplyStepMax(int postNo);

  public int getReplyStep(ReplysDto replysDto);

  public int getReplyNextStep(ReplysDto replysDto);

  public int getReplyLevel(ReplysDto replysDto);

  public void setReplyStepOnePlus(ReplysDto replysDto);
}
