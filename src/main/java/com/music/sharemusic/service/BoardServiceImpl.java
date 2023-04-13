package com.music.sharemusic.service;

import com.music.sharemusic.dao.BoardDao;
import com.music.sharemusic.dao.HistoryDao;
import com.music.sharemusic.dto.BoardDto;
import com.music.sharemusic.dto.LoggedDto;
import java.io.Console;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BoardServiceImpl implements BoardService {

  @Autowired
  BoardDao boardDao;

  @Autowired
  HistoryDao historyDao;

  @Override
  public void putPost(BoardDto boardDto) {
    boardDao.putPost(boardDto);
  }

  @Override
  public BoardDto getPostOne(int postNo) {
    BoardDto result = boardDao.getPostOne(postNo);
    return result;
  }

  @Override
  public List<BoardDto> getPostAll(String genre, String searchTxt, String sort) {
    HashMap<String, String> hashMap = new HashMap<>();
    hashMap.put("genre", genre);
    hashMap.put("searchTxt", searchTxt);
    hashMap.put("sort", sort);
    log.info("==========" + hashMap);

    List<BoardDto> result = boardDao.getPostAll(hashMap);
    return result;
  }

  @Override
  public List<BoardDto> getRankPost() {
    List<BoardDto> result = boardDao.getRankPost();
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
  public int deletePost(BoardDto boardDto) {
    int result = boardDao.deletePost(boardDto);
    return result;
  }
}
