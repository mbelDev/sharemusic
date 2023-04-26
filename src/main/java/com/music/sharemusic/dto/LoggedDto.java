package com.music.sharemusic.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoggedDto {

  private String userID;
  private String userNM;
  private String userIconReal;
  private int userPrincipal;
  private String userDate;
  private int postNo;
  private int postLiked;
  private int postBookmarked;
  private int postFollowed;
}
