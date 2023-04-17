package com.music.sharemusic.dao;

import com.music.sharemusic.dto.BoardDto;
import com.music.sharemusic.dto.HistoryDto;
import com.music.sharemusic.dto.LoggedDto;
import com.music.sharemusic.dto.SendDataDto;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HistoryDao {
  public List<HistoryDto> getHistoryRecent(String userID);

  //최근 시청글 목록 요약정보

  public List<BoardDto> getHistoryWritten(String userID);

  //최근 작성글 목록 정보

  public List<HistoryDto> getHistoryLiked(String userID);

  //좋아요 표시글 목록

  public List<HistoryDto> getHistoryBookmark(String userID);

  //북마크 목록

  public List<BoardDto> getHistoryBoard(HistoryDto historyDto);

  //안씀

  public int getHistory(LoggedDto loggdeUser);

  //읽은적 있는지 postNo로 단 하나 확인

  public void putHistory(LoggedDto loggdeUser);

  //열람 정보 기록

  public int getLiked(SendDataDto data);

  //좋아요 누른적 있는지 단 하나 확인

  public void updateHistoryDate(LoggedDto loggdeUser);

  // view로 들어갈 때, History에 있으면 postHits++ 없으면 History의 readDate 최신화 History자체가 없으면 생성.
  // HistodyDao Select 후 없으면 input후 BoardDao에서 UpdateHits 호출 있으면 UpdateHistory호출

  public int updateHistoryLike(SendDataDto data);

  //좋아요 최신화

  public int updateHistoryBookmark(SendDataDto data);

  //북마크 최신화

  public int getFollow(SendDataDto data);

  //팔로우 확인

  public int putFollow(SendDataDto data);

  //팔로우

  public int unFollow(SendDataDto data);

  //언팔로우

  public void deleteHistoryAll(String userID);

  //userID의 모든 기록 삭제

  public void deleteHistoryOne(LoggedDto loggdeUser);

  //userID의 postNo에 대한 기록 삭제

  public int test();
}
