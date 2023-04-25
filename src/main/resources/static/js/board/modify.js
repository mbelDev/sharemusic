// 유튜브 링크가 맞는지 확인&링크에서 영상의 id만 뽑아오기
$("#btnModifyConfirm").on("click", function () {
  const url = $(".postLink").val();
  const regExp =
    "^.*(?:(?:youtu\\.be\\/|v\\/|vi\\/|u\\/\\w\\/|embed\\/)|(?:(?:watch)?\\?v(?:i)?=|\\&v(?:i)?=))([^#\\&\\?]*).*";
  const match = url.match(regExp);

  if (match) {
    const getId = match[1];
    $(".linkCheck").val(match[1]);
    return true;
  } else {
    $(".linkCheck").val("error");
  }
  return true;
});

// modal 생성, 종료
function getModalModifyGenre() {
  getModal02($("#modalModifyGenre"), $("#closeModalModifyGenre"));
}

function getModalModifyEmote() {
  getModal02($("#modalModifyEmote"), $("#closeModalModifyEmote"));
}

// modal 장르 선택
$("#modifyKPop").on("click", function () {
  $("#modifyPostGenre").val("요즘 K-Pop");
});

$("#modifyGIdol").on("click", function () {
  $("#modifyPostGenre").val("여자 아이돌");
});

$("#modifyBIdol").on("click", function () {
  $("#modifyPostGenre").val("남자 아이돌");
});

$("#modifyBallad").on("click", function () {
  $("#modifyPostGenre").val("슬픈 발라드");
});

$("#modifyRB").on("click", function () {
  $("#modifyPostGenre").val("편안한 알앤비");
});

$("#modifyHipHop").on("click", function () {
  $("#modifyPostGenre").val("요즘 국힙");
});

$("#modifyRock").on("click", function () {
  $("#modifyPostGenre").val("요즘 락");
});

$("#modifyEdm").on("click", function () {
  $("#modifyPostGenre").val("EDM");
});

$("#modifyDrama").on("click", function () {
  $("#modifyPostGenre").val("드라마 OST");
});

$("#modifyMovie").on("click", function () {
  $("#modifyPostGenre").val("영화 OST");
});

// modal 감성 선택
$("#modifyFav").on("click", function () {
  $("#modifyPostEmote").val("지금인기");
});

$("#modifyHit").on("click", function () {
  $("#modifyPostEmote").val("힙터질때");
});

$("#modifyExcite").on("click", function () {
  $("#modifyPostEmote").val("신났을때");
});

$("#modifyGloomy").on("click", function () {
  $("#modifyPostEmote").val("우울할때");
});

$("#modifyLove").on("click", function () {
  $("#modifyPostEmote").val("사랑할때");
});

$("#modifyOut").on("click", function () {
  $("#modifyPostEmote").val("멍때릴때");
});

$("#modifySports").on("click", function () {
  $("#modifyPostEmote").val("운동할때");
});

$("#modifyRelax").on("click", function () {
  $("#modifyPostEmote").val("휴식할때");
});

$("#modifyParty").on("click", function () {
  $("#modifyPostEmote").val("파티할때");
});

$("#modifyLonely").on("click", function () {
  $("#modifyPostEmote").val("외로울때");
});
