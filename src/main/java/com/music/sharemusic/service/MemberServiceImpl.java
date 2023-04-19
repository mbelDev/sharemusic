package com.music.sharemusic.service;

import com.music.sharemusic.dao.BoardDao;
import com.music.sharemusic.dao.HistoryDao;
import com.music.sharemusic.dao.MemberDao;
import com.music.sharemusic.dto.BoardDto;
import com.music.sharemusic.dto.HistoryDto;
import com.music.sharemusic.dto.LoggedDto;
import com.music.sharemusic.dto.MemberDto;
import com.music.sharemusic.dto.MemberInfoDto;
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

  //회원가입
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
      //파일 저장
      fileUpload(uploadFile, imgFilePath);
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

  //파일 업로드
  public void fileUpload(MultipartFile uploadFile, Path FilePath) {
    try {
      Files.write(FilePath, uploadFile.getBytes());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  //오류 검증 Validate
  @Override
  public Map<String, String> validateHandler(Errors errors) {
    Map<String, String> validateResult = new HashMap<>();

    for (FieldError error : errors.getFieldErrors()) {
      String validKeyName = "valid_" + error.getField();
      validateResult.put(validKeyName, error.getDefaultMessage());
    }

    return validateResult;
  }

  //뭐 할때 쓰던 메소드더라???
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

  //중복 아이디 확인 메소드
  public int checkIDtest(String userID) {
    int result = memberDao.checkID(userID);
    return result;
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

  //로그인 메소드
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

  //로그인 한 유저 정보
  public MemberDto getMemberLogged(LoggedDto loggedDto) {
    String userID = loggedDto.getUserID();
    MemberDto result = getMemberOne(userID);
    loggedDto.setUserDate(result.getUserDate());
    loggedDto.setUserNM(result.getUserNM());
    return result;
  }

  //활동 및 강삼기록 저장
  public Map<String, Object> getHistoryList(MemberDto memberDto) {
    Map<String, Object> result = new HashMap<>();
    List<BoardDto> listWritten = getHistoryWritten(memberDto);
    List<HistoryDto> listRecent = getHistoryRecent(memberDto);
    List<HistoryDto> listLiked = getHistoryLiked(memberDto);
    List<HistoryDto> listBookmark = getHistoryBookmark(memberDto);
    result.put("listWritten", listWritten);
    result.put("listRecent", listRecent);
    result.put("listLiked", listLiked);
    result.put("listBookmark", listBookmark);
    return result;
  }

  public List<BoardDto> getHistoryWritten(MemberDto memberDto) {
    String userNM = memberDto.getUserNM();
    List<BoardDto> result = historyDao.getHistoryWritten(userNM);
    return result;
  }

  public List<HistoryDto> getHistoryRecent(MemberDto memberDto) {
    String userID = memberDto.getUserID();
    List<HistoryDto> result = historyDao.getHistoryRecent(userID);
    dependency(result);
    return result;
  }

  public List<HistoryDto> getHistoryLiked(MemberDto memberDto) {
    String userID = memberDto.getUserID();
    List<HistoryDto> result = historyDao.getHistoryLiked(userID);
    dependency(result);
    return result;
  }

  public List<HistoryDto> getHistoryBookmark(MemberDto memberDto) {
    String userID = memberDto.getUserID();
    List<HistoryDto> result = historyDao.getHistoryBookmark(userID);
    dependency(result);
    return result;
  }

  //좋아요 업데이트 << History, Board >>
  public int updateLike(SendDataDto data) {
    int result = historyDao.getLiked(data);
    //현재 liked 상태를 받아옴

    data.setLiked(result);
    int postNo = data.getPostNo();
    int liked = data.getLiked();
    historyDao.updateHistoryLike(data);
    //liked를 업데이트 함

    HashMap<String, Integer> hashMap = new HashMap<>();
    hashMap.put("postNo", postNo);
    hashMap.put("updateLike", liked == 1 ? -1 : 1);
    boardDao.updateLike(hashMap);
    //board에다 like를 업데이트 함

    result = historyDao.getLiked(data);
    //업데이트 이후 liked 상태를 받아서 리턴함.

    return result;
  }

  public int updateBookmark(SendDataDto data) {
    //userID postNo bookmark 필요
    data.setBookmark(historyDao.getBookmark(data));
    historyDao.updateHistoryBookmark(data);
    int result = historyDao.getBookmark(data);
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

  //userInfo 보는 메소드
  public MemberInfoDto getMemberInfo(String userID) {
    MemberInfoDto result = new MemberInfoDto(); //return할 member의 Dto
    MemberDto userInfo = getMemberOne(userID); //얻어올 member의 정보
    String userNM = userInfo.getUserNM(); //나중에 작성 글 목록 받아올 때 필요함. 왜냐면 지금은 작성자 이름을 userID가 아니라 userNM으로 쓰고있음.
    Map<String, Object> historyList = new HashMap<>(); //작성글 목록 / 좋아요 목록 / 북마크 목록 뿌리기
    // LoggedDto userData = new LoggedDto();
    // userData.setUserID(userInfo.getUserID());
    // userData.setUserNM(userInfo.getUserNM());
    historyList = getHistoryList(userInfo);
    result.setUserID(userInfo.getUserID());
    result.setUserNM(userInfo.getUserNM());
    result.setUserDate(userInfo.getUserDate());
    result.setUserIconReal(userInfo.getUserIconReal());
    result.setUserPosts(historyDao.getCountPosts(userNM));
    //나중에 userID로 바꾸고 BOARD 테이블에도 AuthID 추가 할 것이다 반드시.
    result.setUserReplys(historyDao.getCountReplys(userID));
    result.setUserLiked(historyDao.getCountLiked(userID));
    result.setUserFollower(historyDao.getCountFollower(userID));
    result.setUserHistoryList(historyList);
    return result;
  }

  //이거 어디다 쓰는거더라???
  public MemberDto getMemberOne(String userID) {
    MemberDto result = memberDao.getMemberOne(userID);
    return result;
  }

  //관리자가 쓰는 메소드
  public List<MemberDto> getMemberAll() {
    List<MemberDto> result = memberDao.getMemberAll();
    return result;
  }

  //회원정보 수정
  public void updateMember(MemberDto memberDto) {
    MultipartFile uploadFile = memberDto.getUserIconFile();
    if (uploadFile.getOriginalFilename() != "") {
      UUID uuid = UUID.randomUUID();
      String userIconPath = uploadFile.getOriginalFilename();
      String userIconReal = uuid + "_" + userIconPath;
      Path imgFilePath = Paths.get(uploadFolder + userIconReal); // C:\tempStorage
      memberDto.setUserIcon(userIconPath);
      memberDto.setUserIconReal(userIconReal);
      //파일 저장
      fileUpload(uploadFile, imgFilePath);
    } else {
      String userIcon = "sampleprofile.jpg";
      String userIconReal = "sampleprofile.jpg";
      memberDto.setUserIcon(userIcon);
      memberDto.setUserIconReal(userIconReal);
    }

    memberDao.updateMember(memberDto);
  }

  public void deleteMember(MemberDto memberDto) {
    memberDao.deleteMember(memberDto);
  }
}
