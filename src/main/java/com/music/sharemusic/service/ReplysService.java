package com.music.sharemusic.service;

import com.music.sharemusic.dto.LoggedDto;
import com.music.sharemusic.dto.ReplysDto;
import java.util.List;

public interface ReplysService {
  public List<ReplysDto> getReplyAll(int postNo);

  public void putReply(LoggedDto replyAuth, ReplysDto replysDto);

  public void updateReply(ReplysDto replysDto);

  public void deleteReply(ReplysDto replysDto);

  //기본 CRUD 이벤트들

  public ReplysDto returnHidden(ReplysDto replysDto);
  //비밀글 숨겨주는 이벤트
}
