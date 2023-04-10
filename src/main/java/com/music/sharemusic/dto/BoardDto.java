package com.music.sharemusic.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BoardDto {

  private int postNo; // 글 번호
  private String postAuth; // 글 작성자
  private String postTitle; // 곡명 varchar2 50
  private String postSinger; // 가수 varchar2 50
  private String postCont; // 내용 varchar2 150
  private String postLink; // 유튜브 링크 varchar2 300
  private String postGenre; // 장르 
  private String postEmote; // 분위기 
  private int postLike; // 추천수
  private int postHits; // 조회수
  private String postDate; // 작성일자
}
