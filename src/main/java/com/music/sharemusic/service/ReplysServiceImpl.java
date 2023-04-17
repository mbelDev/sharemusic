package com.music.sharemusic.service;

import com.music.sharemusic.dao.ReplysDao;
import com.music.sharemusic.dto.LoggedDto;
import com.music.sharemusic.dto.ReplysDto;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class ReplysServiceImpl implements ReplysService {

  @Autowired
  ReplysDao replysDao;

  //덧글 받아오기
  public List<ReplysDto> getReplyAll(int postNo) {
    List<ReplysDto> result = replysDao.getReplyAll(postNo);
    return result;
  }

  //새 덧글달기
  public void putReply(LoggedDto replyAuth, ReplysDto replysDto) {
    String replyAuthID = replyAuth.getUserID();
    String replyAuthNM = replyAuth.getUserNM();
    int postNo = replyAuth.getPostNo();
    int step = replysDao.getReplyStepMax(postNo);
    replysDto.setPostNo(postNo);
    replysDto.setReplyAuthID(replyAuthID);
    replysDto.setReplyAuthNM(replyAuthNM);
    replysDto.setReplyStep(step);
    replysDao.putReply(replysDto);
  }

  //덧글의 덧글 복잡하다.
  public void putReplyReply(LoggedDto replyAuth, ReplysDto replysDto) {
    int postNo = replyAuth.getPostNo();
    int replyNo = replysDto.getReplyNo();
    int step = replysDao.getReplyStep(replyNo) + 1;
    int level = replysDao.getReplyLevel(replyNo) + 1;
    replysDto.setReplyAuthID(replyAuth.getUserID());
    replysDto.setReplyAuthNM(replyAuth.getUserNM());
    replysDto.setPostNo(postNo);
    replysDto.setReplyGroup(replyNo);
    replysDto.setReplyStep(step);
    replysDto.setReplyLevel(level);
    log.info("this==={}", replysDto);
    replysDao.putReply(replysDto);
    replysDao.setReplyStepOnePlus(replysDto);
  }

  public void updateReply(ReplysDto replysDto) {
    replysDao.updateReply(replysDto);
  }

  public void deleteReply(ReplysDto replysDto) {
    log.info("replyDto==={}", replysDto);
    replysDao.deleteReply(replysDto);
  }

  //기본 CRUD 이벤트들

  public ReplysDto returnHidden(ReplysDto replysDto) {
    ReplysDto result = null;
    return result;
  }
}
