package com.music.sharemusic.service;

import com.music.sharemusic.dto.BoardDto;
import com.music.sharemusic.dto.LoggedDto;
import java.util.List;
import java.util.Map;

public interface BoardService {
  public void putPost(BoardDto boardDto);

  //게시글 등록

  public BoardDto getPostOne(int postNo);

  //view 에서 요청. 한 글 불러오기

  public List<BoardDto> getPostAll(
    String category,
    String searchTxt,
    String sort
  );

  //index 에서 요청. 모든 글 불러오기
  //paging작업은 아직^^

  public List<BoardDto> getRankPost(String category);

  // 랭킹 글들 불러오기

  public List<BoardDto> getMonthRankPost(int moveMonth);

  // 월간 랭킹 글들 불러오기

  public Map<String, String> getMonthRankDate(int moveMonth);

  // 월간 랭킹 날짜

  public List<BoardDto> getWeeklyRankPost(int moveWeekly);

  //  주간 랭킹 글들 불러오기

  public Map<String, String> getWeeklyRankDate(int moveWeekly);

  // 주간 랭킹 날짜

  public void updatePost(BoardDto boardDto);

  //modify 에서 요청. 글 내용 수정하기

  public int updateLike(int updateLike, int postNo);

  // 추천 누르면 추천수 업데이트

  public void updateHits(LoggedDto loggedUser);

  // 글 누르면 조회수 업데이트

  public int deletePost(BoardDto boardDto);

  //view 에서 요청. 글 삭제하기

  public List<BoardDto> getFollowList(Map<String, String> data);

  //mainPage 에서 요청, 구독 한 사람들의 글들 불러오기

  public List<BoardDto> getMyList(Map<String, String> data);
  //mainPage 에서 요청, 좋아요,북마크 한 글들 불러오기
}
