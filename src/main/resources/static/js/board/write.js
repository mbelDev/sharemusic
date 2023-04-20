// 유튜브 링크가 맞는지 확인&링크에서 영상의 id만 뽑아오기
$(".btnWriteConfirm").on("click", function () {
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
$("#writeDance").on("click", function () {
  $("#writePostGenre").val("댄스/팝");
});

$("#writeFolk").on("click", function () {
  $("#writePostGenre").val("포크/어쿠스틱");
});

$("#writeIdol").on("click", function () {
  $("#writePostGenre").val("아이돌");
});

$("#writeRap").on("click", function () {
  $("#writePostGenre").val("랩/힙합");
});

$("#writeRB").on("click", function () {
  $("#writePostGenre").val("알앤비/소울");
});

$("#writeElect").on("click", function () {
  $("#writePostGenre").val("일렉트로닉");
});

$("#writeRock").on("click", function () {
  $("#writePostGenre").val("락/메탈");
});

$("#writeJazz").on("click", function () {
  $("#writePostGenre").val("재즈");
});

$("#writeIndie").on("click", function () {
  $("#writePostGenre").val("인디");
});

$("#writeAdult").on("click", function () {
  $("#writePostGenre").val("성인가요");
});

// modal 감성 선택
$("#writeExcite").on("click", function () {
  $("#writePostEmote").val("신남");
});

$("#writeGloomy").on("click", function () {
  $("#writePostEmote").val("우울");
});

$("#writeEcstasy").on("click", function () {
  $("#writePostEmote").val("희열");
});

$("#writeSpooky").on("click", function () {
  $("#writePostEmote").val("으스스");
});

$("#writeMagni").on("click", function () {
  $("#writePostEmote").val("웅장한");
});

$("#writeCalm").on("click", function () {
  $("#writePostEmote").val("잔잔");
});

$("#writeFlutter").on("click", function () {
  $("#writePostEmote").val("설렘");
});

$("#writeSorrow").on("click", function () {
  $("#writePostEmote").val("애절한");
});

$("#writeDreamy").on("click", function () {
  $("#writePostEmote").val("몽환적");
});

$("#writeWarm").on("click", function () {
  $("#writePostEmote").val("따뜻한");
});
