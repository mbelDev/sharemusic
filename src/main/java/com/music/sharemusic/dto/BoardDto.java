package com.music.sharemusic.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

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

  @NotBlank(message = "곡은 필수 입력 사항입니다.")
  private String postTitle; // 곡명 varchar2 50

  @NotBlank(message = "가수는 필수 입력 사항입니다.")
  private String postSinger; // 가수 varchar2 50

  @NotBlank(message = "내용은 필수 입력 사항입니다.")
  private String postCont; // 내용 varchar2 999

  @NotBlank(message = "링크는 필수 입력 사항입니다.")
  private String postLink; // 유튜브 링크 varchar2 300

  @NotBlank(message = "장르는 필수 입력 사항입니다.")
  private String postGenre; // 장르 

  @NotBlank(message = "감성은 필수 입력 사항입니다.")
  private String postEmote; // 감성 

  private int postLike; // 추천수
  private int postHits; // 조회수
  private String postDate; // 작성일자
}
