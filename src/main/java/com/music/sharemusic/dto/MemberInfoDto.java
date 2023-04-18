package com.music.sharemusic.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.pl.NIP;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberInfoDto {

  private String userID;
  private String userNM;
  private String userIconReal;
  private String userDate;
  private int userPosts;
  private int userReplys;
  private int userLiked;
  private int userPrivate;
}
