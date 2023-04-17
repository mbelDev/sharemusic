package com.music.sharemusic.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ReplysDto {

  private int postNo;
  private int replyNo;
  private String replyAuthID;
  private String replyAuthNM;
  private String replyCont;
  private int replyHidden;
  private String replyDate;
  private int replayGroup;
  private int replyLevel;
  private int replyStep;
}
