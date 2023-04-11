package com.music.sharemusic.service;

import com.music.sharemusic.dao.BoardDao;
import com.music.sharemusic.dao.HistoryDao;
import com.music.sharemusic.dto.BoardDto;
import com.music.sharemusic.dto.LoggedDto;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class BoardServiceImpl implements BoardService {

  @Autowired
  BoardDao boardDao;

  @Autowired
  HistoryDao historyDao;

  @Override
  public void putPost(BoardDto boardDto) {
    // 로그인 한 유저의 이름 받아오기
    boardDto.setPostAuth("임시 작성자");

    boardDao.putPost(boardDto);
  }

  @Override
  public BoardDto getPostOne(int postNo) {
    BoardDto result = boardDao.getPostOne(postNo);
    return result;
  }

  @Override
  public List<BoardDto> getPostAll() {
    List<BoardDto> result = boardDao.getPostAll();
    return result;
  }

  @Override
  public void updatePost(BoardDto boardDto) {
    boardDao.updatePost(boardDto);
  }

  @Override
  public int updateLike(int postNo) {
    return boardDao.updateLike(postNo);
  }

  @Override
  public void updateHits(LoggedDto loggedUser) {
    int postNo = loggedUser.getPostNo();
    // log.info("loggedUser === {}", loggedUser);
    int test = historyDao.getHistory(loggedUser);
    // log.info("you read it? === {}", test);
    if (test == 0) {
      boardDao.updateHits(postNo);
      historyDao.putHistory(loggedUser);
    } else {
      log.info("yes, you were read it!! {}", postNo);
      historyDao.updateHistoryDate(loggedUser);
    }
  }

  @Override
  public void deletePost(BoardDto boardDto) {
    boardDao.deletePost(boardDto);
  }
}
