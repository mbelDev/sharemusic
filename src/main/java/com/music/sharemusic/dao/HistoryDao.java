package com.music.sharemusic.dao;

import com.music.sharemusic.dto.BoardDto;
import com.music.sharemusic.dto.HistoryDto;
import com.music.sharemusic.dto.LoggedDto;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HistoryDao {
  public List<HistoryDto> getHistoryRecent(String userID);

  public List<HistoryDto> getHistoryLike(String userID);

  public List<BoardDto> getHistoryBoard(HistoryDto historyDto);

  public int getHistoryOne(LoggedDto loggdeUser);

  public void putHistory(LoggedDto loggdeUser);

  public void updateHistoryDate(LoggedDto loggdeUser);

  // view로 들어갈 때, History에 있으면 postHits++ 없으면 History의 readDate 최신화 History자체가 없으면 생성.
  // HistodyDao Select 후 없으면 input후 BoardDao에서 UpdateHits 호출 있으면 UpdateHistory호출

  public void updateHistoryLike(LoggedDto loggdeUser);

  //todo-list때 했던 Done 처럼 하기.

  public void deleteHistoryAll(String userID);

  public void deleteHistoryOne(LoggedDto loggdeUser);

  public int test();
}
