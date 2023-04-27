package com.music.sharemusic.service;

import com.music.sharemusic.dao.MemberDao;
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

  @Autowired
  MemberDao memberDao;

  //덧글 받아오기
  public List<ReplysDto> getReplyAll(int postNo) {
    List<ReplysDto> result = replysDao.getReplyAll(postNo);
    for (ReplysDto item : result) {
      int replyNo = item.getReplyGroup();
      String userID = item.getReplyAuthID();
      String userIcon = memberDao.getMemberOne(userID).getUserIconReal();
      item.setReplyAuthIcon(userIcon);
      if (replyNo == 0) {
        continue;
      }
      ReplysDto target = replysDao.getReply(replyNo);
      String targetNM = target.getReplyAuthNM();
      item.setReplyGroupTarget(targetNM);

      if (target.getReplyHidden() == 1) {
        item.setReplyGroupTarget("비밀 덧글");
      }
      if (target.getReplyHidden() == 2) {
        item.setReplyGroupTarget("삭제된 덧글");
      }
    }
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
    //replysDto에는 replyCont와 replyGroup, replyHidden이 담겨온다
    replysDto.setPostNo(replyAuth.getPostNo());
    replysDto.setReplyAuthID(replyAuth.getUserID());
    replysDto.setReplyAuthNM(replyAuth.getUserNM());
    int step;
    int group = replysDto.getReplyGroup();
    int level = replysDao.getReplyLevel(replysDto);
    replysDto.setReplyLevel(level + 1);
    log.info("old Dto = {}", replysDto);
    replysDto = getReplyNoMaxStep(replysDto);
    log.info("new Dto = {}", replysDto);
    step = replysDao.getReplyNextStep(replysDto);
    if (step == 0) {
      step = replysDao.getReplyStep(replysDto);
    }
    replysDto.setReplyStep(step);
    replysDto.setReplyLevel(level + 1);
    replysDto.setReplyGroup(group);
    replysDao.setReplyStepOnePlus(replysDto);
    replysDto.setReplyStep(step + 1);
    replysDao.putReply(replysDto);
  }

  public void updateReply(ReplysDto replysDto) {
    log.info("reply==={}", replysDto);
    replysDao.updateReply(replysDto);
  }

  public void deleteReply(ReplysDto replysDto) {
    log.info("replyDto==={}", replysDto);
    replysDao.deleteReply(replysDto);
  }

  public ReplysDto getReplyNoMaxStep(ReplysDto replysDto) {
    ReplysDto result = replysDto;
    int replyNo = replysDao.getReplyNoNextStep(replysDto);
    int replyLevel = replysDto.getReplyLevel() + 1;
    if (replyNo == 0) {
      return replysDto;
    } else {
      replysDto.setReplyGroup(replyNo);
      replysDto.setReplyLevel(replyLevel);
      result = getReplyNoMaxStep(replysDto);
    }
    return result;
  }

  //기본 CRUD 이벤트들

  public ReplysDto returnHidden(ReplysDto replysDto) {
    ReplysDto result = null;
    return result;
  }
}
