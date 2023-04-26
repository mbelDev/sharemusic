// 취소 버튼
function cancel() {
  window.history.back();
}

// 유튜브 링크가 맞는지 확인&링크에서 영상의 id만 뽑아오기
$("#btnWriteConfirm").on("click", function () {
  const url = $(".postLink").val();
  const regExp =
    "^.*(?:(?:youtu\\.be\\/|v\\/|vi\\/|u\\/\\w\\/|embed\\/)|(?:(?:watch)?\\?v(?:i)?=|\\&v(?:i)?=))([^#\\&\\?]*).*";
  const match = url.match(regExp);

  if (match) {
    const getId = match[1];
    $(".linkCheck").val(match[1]);
  } else {
    $(".linkCheck").val("error");
  }

  return true;
});

// modal 생성, 종료
function getModalWriteGenre() {
  getModal02($("#modalWriteGenre"), $("#closeModalWriteGenre"));
}

function getModalWriteEmote() {
  getModal02($("#modalWriteEmote"), $("#closeModalWriteEmote"));
}

// modal 장르 선택
$("#writeKPop").on("click", function () {
  $("#writePostGenre").val("요즘 K-Pop");
});

$("#writeGIdol").on("click", function () {
  $("#writePostGenre").val("여자 아이돌");
});

$("#writeBIdol").on("click", function () {
  $("#writePostGenre").val("남자 아이돌");
});

$("#writeBallad").on("click", function () {
  $("#writePostGenre").val("슬픈 발라드");
});

$("#writeRB").on("click", function () {
  $("#writePostGenre").val("편안한 알앤비");
});

$("#writeHipHop").on("click", function () {
  $("#writePostGenre").val("요즘 국힙");
});

$("#writeRock").on("click", function () {
  $("#writePostGenre").val("요즘 락");
});

$("#writeEdm").on("click", function () {
  $("#writePostGenre").val("EDM");
});

$("#writeDrama").on("click", function () {
  $("#writePostGenre").val("드라마 OST");
});

$("#writeMovie").on("click", function () {
  $("#writePostGenre").val("영화 OST");
});

// modal 감성 선택
$("#writeFav").on("click", function () {
  $("#writePostEmote").val("지금인기");
});

$("#writeHit").on("click", function () {
  $("#writePostEmote").val("힙터질때");
});

$("#writeExcite").on("click", function () {
  $("#writePostEmote").val("신났을때");
});

$("#writeGloomy").on("click", function () {
  $("#writePostEmote").val("우울할때");
});

$("#writeLove").on("click", function () {
  $("#writePostEmote").val("사랑할때");
});

$("#writeOut").on("click", function () {
  $("#writePostEmote").val("멍때릴때");
});

$("#writeSports").on("click", function () {
  $("#writePostEmote").val("운동할때");
});

$("#writeRelax").on("click", function () {
  $("#writePostEmote").val("휴식할때");
});

$("#writeParty").on("click", function () {
  $("#writePostEmote").val("파티할때");
});

$("#writeLonely").on("click", function () {
  $("#writePostEmote").val("외로울때");
});
