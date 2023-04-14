package com.music.sharemusic.service;

import com.music.sharemusic.dao.BoardDao;
import com.music.sharemusic.dao.HistoryDao;
import com.music.sharemusic.dao.MemberDao;
import com.music.sharemusic.dto.BoardDto;
import com.music.sharemusic.dto.HistoryDto;
import com.music.sharemusic.dto.LoggedDto;
import com.music.sharemusic.dto.MemberDto;
import com.music.sharemusic.dto.SendDataDto;
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
    MultipartFile uploadFile = memberDto.getUserIconFile();
    if (uploadFile.getOriginalFilename() != "") {
      UUID uuid = UUID.randomUUID();
      String userIconPath = uploadFile.getOriginalFilename();
      String userIconReal = uuid + "_" + userIconPath;
      Path imgFilePath = Paths.get(uploadFolder + userIconReal); // C:\tempStorage
      memberDto.setUserIcon(userIconPath);
      memberDto.setUserIconReal(userIconReal);
      //저장되는 경로
      try {
        Files.write(imgFilePath, uploadFile.getBytes());
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else {
      String userIcon = "sampleprofile.jpg";
      String userIconReal = "sampleprofile.jpg";
      memberDto.setUserIcon(userIcon);
      memberDto.setUserIconReal(userIconReal);
      memberDto.setUserPrincipal(0);
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

  public void dependency(List<HistoryDto> list) {
    for (HistoryDto item : list) {
      int postNo = item.getPostNo();
      BoardDto tempDto = boardDao.getPostOne(postNo);
      if (tempDto != null) {
        String link = tempDto.getPostLink();
        String singer = tempDto.getPostSinger();
        item.setPostLink(link);
        item.setPostSinger(singer);
      }
    }
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

  public Map<String, Object> getHistoryList(LoggedDto loggedUser) {
    Map<String, Object> result = new HashMap<>();
    List<BoardDto> listWritten = getHistoryWritten(loggedUser);
    List<HistoryDto> listRecent = getHistoryRecent(loggedUser);
    List<HistoryDto> listLiked = getHistoryLiked(loggedUser);
    List<HistoryDto> listBookmark = getHistoryBookmark(loggedUser);
    result.put("listWritten", listWritten);
    result.put("listRecent", listRecent);
    result.put("listLiked", listLiked);
    result.put("listBookmark", listBookmark);
    return result;
  }

  public List<BoardDto> getHistoryWritten(LoggedDto loggedUser) {
    String userNM = loggedUser.getUserNM();
    List<BoardDto> result = historyDao.getHistoryWritten(userNM);
    return result;
  }

  public List<HistoryDto> getHistoryRecent(LoggedDto loggedUser) {
    String userID = loggedUser.getUserID();
    List<HistoryDto> result = historyDao.getHistoryRecent(userID);
    dependency(result);
    return result;
  }

  public List<HistoryDto> getHistoryLiked(LoggedDto loggedUser) {
    String userID = loggedUser.getUserID();
    List<HistoryDto> result = historyDao.getHistoryLiked(userID);
    dependency(result);
    return result;
  }

  public List<HistoryDto> getHistoryBookmark(LoggedDto loggedUser) {
    String userID = loggedUser.getUserID();
    List<HistoryDto> result = historyDao.getHistoryBookmark(userID);
    dependency(result);
    return result;
  }

  public int updateLike(SendDataDto data) {
    int result = historyDao.getLiked(data);
    //현재 liked 상태를 받아옴
    data.setLiked(result);
    historyDao.updateHistoryLike(data);
    //liked를 업데이트 함
    result = historyDao.getLiked(data);
    //업데이트 이후 liked 상태를 받아서 리턴함.
    return result;
  }

  public int updateBookmark(SendDataDto data) {
    //userID postNo bookmark 필요
    int result = historyDao.updateHistoryBookmark(data);
    return result;
  }

  public int updateFollow(SendDataDto data) {
    //userID followID 필요
    int result = historyDao.getFollow(data);
    if (result > 0) {
      historyDao.unFollow(data);
    } else {
      historyDao.putFollow(data);
    }
    result = historyDao.getFollow(data);
    return result;
  }

  public MemberDto getMemberLogged(LoggedDto loggedDto) {
    String userID = loggedDto.getUserID();
    MemberDto result = getMemberOne(userID);
    loggedDto.setUserDate(result.getUserDate());
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
