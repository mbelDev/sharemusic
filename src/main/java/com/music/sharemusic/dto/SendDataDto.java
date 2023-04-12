package com.music.sharemusic.dto;

import lombok.Data;

@Data
public class SendDataDto {

  private String userID;
  private int postNo;
  private int liked;
  private int bookmark;
}
