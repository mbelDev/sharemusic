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

  private int postNo;
  private String postAuth;
  private String postTitle;
  private String postCont;
  private String postGenre;
  private String postEmote;
  private String postTags;
  private int postLike;
  private int postHits;
  private String postDate;
}
