package com.music.sharemusic.dao;

import com.music.sharemusic.dto.HistoryDto;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HistoryDao {
  public List<HistoryDto> getHistoryRecent();

  public List<HistoryDto> getHistoryLike();

  public int getHistoryOne(int postNo);

  public void putHistory(int postNo);

  public void updateHistoryDate(int postNo);

  // view로 들어갈 때, History에 있으면 postHits++ 없으면 History의 readDate 최신화 History자체가 없으면 생성.
  // HistodyDao Select 후 없으면 input후 BoardDao에서 UpdateHits 호출 있으면 UpdateHistory호출

  public void updateHistoryLike(int postNo);

  //todo-list때 했던 Done 처럼 하기.

  public void deleteHistoryAll();

  public void deleteHistoryOne(int postNo);
}
