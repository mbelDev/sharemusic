package com.music.sharemusic.service;

import com.music.sharemusic.dao.BoardDao;
import com.music.sharemusic.dto.BoardDto;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardServiceImpl implements BoardService {

  @Autowired
  BoardDao boardDao;

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
  public void updateHits(int postNo) {
    boardDao.updateHits(postNo);
  }

  @Override
  public void deletePost(BoardDto boardDto) {
    boardDao.deletePost(boardDto);
  }  
}
