package com.music.sharemusic.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {

  private String userID;
  private String userPW;
  private String userNM;
  private String userIcon;
  private MultipartFile userIconFile;
  private String userIconReal;
  private int userBann;
  private String userDate;
}
