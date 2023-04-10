package com.music.sharemusic.service;

import com.music.sharemusic.dao.BoardDao;
import com.music.sharemusic.dao.HistoryDao;
import com.music.sharemusic.dao.MemberDao;
import com.music.sharemusic.dto.BoardDto;
import com.music.sharemusic.dto.HistoryDto;
import com.music.sharemusic.dto.LoggedDto;
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
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.multipart.MultipartFile;

@Service
@Log4j2
public class MemberServiceImpl implements MemberService {

  @Autowired
  MemberDao memberDao;

  @Autowired
  BoardDao boardDao;

  @Autowired
  HistoryDao historyDao;

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

  @Override
  public Map<String, String> validateHandler(Errors errors) {
    Map<String, String> validateResult = new HashMap<>();

    for (FieldError error : errors.getFieldErrors()) {
      String validKeyName = "valid_" + error.getField();
      validateResult.put(validKeyName, error.getDefaultMessage());
    }

    return validateResult;
  }

  public Map<String, String> checkID(String userID) {
    log.info("check ID === {}", userID);
    int checkID = memberDao.checkID(userID);
    Map<String, String> result = new HashMap<>();

    if (checkID > 0) {
      result.put("result", "이미 사용중인 아이디 입니다.");
    } else {
      result.put("result", "사용하실 수 있는 아이디 입니다.");
    }
    log.info("send ID === {}", result);
    return result;
  }

  public LoggedDto login(MemberDto memberDto) {
    LoggedDto result = null;
    String userID = memberDto.getUserID();
    int checkMember = memberDao.login(memberDto);
    if (checkMember > 0) {
      memberDto = memberDao.getMemberOne(userID);
      result = new LoggedDto();
      result.setUserID(memberDto.getUserID());
      result.setUserNM(memberDto.getUserNM());
      result.setUserIconReal(memberDto.getUserIconReal());
      result.setUserPrincipal("member");
    }
    log.info("who?==={}", memberDto);
    log.info("login?==={}", result);
    return result;
  }

  public List<HistoryDto> getHistoryRecent(LoggedDto loggedUser) {
    String userID = loggedUser.getUserID();
    List<HistoryDto> result = historyDao.getHistoryRecent(userID);
    return result;
  }

  public MemberDto getMemberLogged(LoggedDto loggedDto) {
    String userID = loggedDto.getUserID();
    MemberDto result = getMemberOne(userID);
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
