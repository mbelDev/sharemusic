package com.music.sharemusic.service;

import com.music.sharemusic.dto.BoardDto;
import java.util.List;

public interface BoardService {
  public void putPost(BoardDto boardDto);

  //게시글 등록

  public BoardDto getPostOne(int postNo);

  //view 에서 요청. 한 글 불러오기

  public List<BoardDto> getPostAll();

  //index 에서 요청. 모든 글 불러오기
  //paging작업은 아직^^

  public void updatePost(BoardDto boardDto);

  //modify 에서 요청. 글 내용 수정하기

  public void deletePost(BoardDto boardDto);
  //view 에서 요청. 글 삭제하기
}
