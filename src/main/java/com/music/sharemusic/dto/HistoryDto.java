package com.music.sharemusic.dto;

import groovy.transform.ToString;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class HistoryDto {

  private String userID;
  private int postNO;
  private String PostAuth;
  private String PostTitle;
  private String readDate;
  private int liked;
  private int bookmark;
}
