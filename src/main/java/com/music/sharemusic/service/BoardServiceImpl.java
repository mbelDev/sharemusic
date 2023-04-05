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
  public void putPost(BoardDto boardDto) {}

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
  public void updatePost(BoardDto boardDto) {}

  @Override
  public void deletePost(BoardDto boardDto) {}
}
