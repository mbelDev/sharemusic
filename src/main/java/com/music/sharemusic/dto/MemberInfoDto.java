package com.music.sharemusic.dto;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
  private int userFollower;
  private Map<String, Object> userHistoryList;
  private int userPrivate;
}
