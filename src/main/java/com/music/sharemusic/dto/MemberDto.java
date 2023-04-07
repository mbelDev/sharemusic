package com.music.sharemusic.dto;

import javax.validation.constraints.NotBlank;
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

  @NotBlank(message = "아이디는 필수 입력 사항입니다.")
  private String userID;

  @NotBlank(message = "비밀번호는 필수 입력 사항입니다.")
  private String userPW;

  @NotBlank(message = "별명은 필수 입력 사항입니다.")
  private String userNM;

  private String userIcon;
  private MultipartFile userIconFile;
  private String userIconReal;
  private int userBann;
  private String userDate;
}
