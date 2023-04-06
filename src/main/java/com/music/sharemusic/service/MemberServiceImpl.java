package com.music.sharemusic.service;

import com.music.sharemusic.dao.MemberDao;
import com.music.sharemusic.dto.MemberDto;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Log4j2
public class MemberServiceImpl implements MemberService {

  @Autowired
  MemberDao memberDao;

  @Value("${file.path}")
  private String uploadFolder;

  public void putMember(MemberDto memberDto) {
    log.info("=========upload========");
    UUID uuid = UUID.randomUUID();
    MultipartFile uploadFile = memberDto.getUserIconFile();
    String userIconPath = uploadFile.getOriginalFilename();
    String userIconReal = uuid + "_" + userIconPath;
    Path imgFilePath = Paths.get(uploadFolder + userIconReal); // C:\tempStorage
    memberDto.setUserIcon(userIconPath);
    memberDto.setUserIconReal(imgFilePath.toString());

    //저장되는 경로
    try {
      Files.write(imgFilePath, uploadFile.getBytes());
    } catch (IOException e) {
      e.printStackTrace();
    }
    if (memberDao.putMember(memberDto) > 0) {
      log.info("회원가입 성공  === {} ", memberDto);
    } else {
      log.info("회원가입 실패");
    }
  }

  public Map<String, Object> checkID(String userID) {
    log.info("check ID === {}", userID);
    int checkID = memberDao.checkID(userID);
    Map<String, Object> result = new HashMap<>();

    if (checkID > 0) {
      result.put("result", "이미 사용중인 아이디 입니다.");
    } else {
      result.put("result", "사용하실 수 있는 아이디 입니다.");
    }
    log.info("send ID === {}", result);
    return result;
  }

  public MemberDto getMemberOne(String userID) {
    MemberDto result = memberDao.getMemberOne(userID);
    return result;
  }

  public List<MemberDto> getMemberAll() {
    List<MemberDto> result = memberDao.getMemberAll();
    return result;
  }

  public void updateMember(MemberDto memberDto) {
    memberDao.updateMember(memberDto);
  }

  public void deleteMember(MemberDto memberDto) {
    memberDao.deleteMember(memberDto);
  }
}
