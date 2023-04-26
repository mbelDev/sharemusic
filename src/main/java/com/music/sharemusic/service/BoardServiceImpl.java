package com.music.sharemusic.service;

import com.music.sharemusic.dao.BoardDao;
import com.music.sharemusic.dao.DateDao;
import com.music.sharemusic.dao.HistoryDao;
import com.music.sharemusic.dto.BoardDto;
import com.music.sharemusic.dto.LoggedDto;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@Log4j2
public class BoardServiceImpl implements BoardService {

  @Autowired
  BoardDao boardDao;

  @Autowired
  HistoryDao historyDao;

  @Autowired
  DateDao dateDao;

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
  public List<BoardDto> getPostAll(
    String category,
    String searchTxt,
    String sort
  ) {
    HashMap<String, String> hashMap = new HashMap<>();
    hashMap.put("category", category);
    hashMap.put("searchTxt", searchTxt);
    hashMap.put("sort", sort);

    List<BoardDto> result = boardDao.getPostAll(hashMap);
    return result;
  }

  @Override
  public List<BoardDto> getRankPost() {
    List<BoardDto> result = boardDao.getRankPost();
    return result;
  }

  @Override
  public List<BoardDto> getMonthRankPost(int moveMonth) {
    List<BoardDto> result = boardDao.getMonthRankPost(moveMonth);
    return result;
  }

  @Override
  public Map<String, String> getMonthRankDate(int moveMonth) {
    Map<String, String> result = dateDao.getMonthRankDate(moveMonth);
    return result;
  }

  @Override
  public List<BoardDto> getWeeklyRankPost(int moveWeekly) {
    List<BoardDto> result = boardDao.getWeeklyRankPost(moveWeekly);
    return result;
  }

  @Override
  public Map<String, String> getWeeklyRankDate(int moveWeekly) {
    Map<String, String> result = dateDao.getWeeklyRankDate(moveWeekly);
    return result;
  }

  @Override
  public void updatePost(BoardDto boardDto) {
    boardDao.updatePost(boardDto);
  }

  @Override
  public int updateLike(int updateLike, int postNo) {
    HashMap<String, Integer> hashMap = new HashMap<>();
    hashMap.put("postNo", postNo);
    hashMap.put("updateLike", updateLike);
    log.info("Hash Map is THIS!!==={}", hashMap);
    return boardDao.updateLike(hashMap);
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

  //권인호 2023.04.25
  @Override
  public List<BoardDto> getFollowList(Map<String, String> data) {
    List<BoardDto> result = boardDao.getFollowList(data);
    return result;
  }

  @Override
  public List<BoardDto> getMyList(Map<String, String> data) {
    List<BoardDto> result = boardDao.getMyList(data);
    return result;
  }
}
