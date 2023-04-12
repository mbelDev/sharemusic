package com.music.sharemusic.service;

import com.music.sharemusic.dao.BoardDao;
import com.music.sharemusic.dao.HistoryDao;
import com.music.sharemusic.dto.BoardDto;

import lombok.extern.slf4j.Slf4j;

import java.io.Console;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
  public void updateHits(int postNo) {
    boardDao.updateHits(postNo);
    // if (historyDao.getHistoryOne(postNo) == 0) {
    //   historyDao.putHistory(postNo);
    // } else {
    //   historyDao.updateHistoryDate(postNo);
    // }
  }

  @Override
  public void deletePost(BoardDto boardDto) {
    boardDao.deletePost(boardDto);
  }
}
