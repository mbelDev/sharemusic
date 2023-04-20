package com.music.sharemusic.dto;

import lombok.Data;

@Data
public class SendDataDto {

  private String userID;
  private String followID;
  private int postNo;
  private int replyNo;
  private int replyHidden;
  private int liked;
  private int bookmark;
}
