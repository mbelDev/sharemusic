// modal
function getModalWriteGenre() {
  getModal($("#modalWriteGenre"), $("#closeModalWriteGenre"));
}

function getModalWriteEmote() {
  getModal($("#modalWriteEmote"), $("#closeModalWriteEmote"));
}

// 유튜브 링크가 맞는지 확인&링크에서 영상의 id만 뽑아오기
$(".btnWriteConfirm").on("click", function () {
  const url = $(".postLink").val();
  const regExp =
    "^.*(?:(?:youtu\\.be\\/|v\\/|vi\\/|u\\/\\w\\/|embed\\/)|(?:(?:watch)?\\?v(?:i)?=|\\&v(?:i)?=))([^#\\&\\?]*).*";
  const match = url.match(regExp);

  if (match) {
    const getId = match[1];
    $(".postLink").val(match[1]);
    return true;
  } else {
    // 유튜브 링크를 다시 확인하라고 경고 창
    alert("다시 확인해주세요.");
  }
  return false;
});

// 장르 선택
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

// 감성 선택
$("#writeExcite").on("click", function () {
  $("#writeEmoteGenre").val("신남");
});

$("#writeGloomy").on("click", function () {
  $("#writeEmoteGenre").val("우울");
});

$("#writeEcstasy").on("click", function () {
  $("#writeEmoteGenre").val("희열");
});

$("#writeSpooky").on("click", function () {
  $("#writeEmoteGenre").val("으스스");
});

$("#writeMagni").on("click", function () {
  $("#writeEmoteGenre").val("웅장한");
});

$("#writeCalm").on("click", function () {
  $("#writeEmoteGenre").val("잔잔");
});

$("#writeFlutter").on("click", function () {
  $("#writeEmoteGenre").val("설렘");
});

$("#writeSorrow").on("click", function () {
  $("#writeEmoteGenre").val("애절한");
});

$("#writeDreamy").on("click", function () {
  $("#writeEmoteGenre").val("몽환적");
});

$("#writeWarm").on("click", function () {
  $("#writeEmoteGenre").val("따뜻한");
});
