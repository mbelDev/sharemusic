package com.music.sharemusic.service;

import com.music.sharemusic.dto.ReplysDto;

public interface ReplysService {
  public ReplysDto getReplyAll(int postNo);

  public void putReply(ReplysDto replysDto);

  public void updateReply(ReplysDto replysDto);

  public void deleteReply(ReplysDto replysDto);

  //기본 CRUD 이벤트들

  public ReplysDto returnHidden(ReplysDto replysDto);
  //비밀글 숨겨주는 이벤트
}
