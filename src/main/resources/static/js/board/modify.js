// 유튜브 링크가 맞는지 확인&링크에서 영상의 id만 뽑아오기
$(".btnModifyConfirm").on("click", function () {
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
$("#modifyDance").on("click", function () {
  $("#modifyPostGenre").val("신남");
});

$("#modifyFolk").on("click", function () {
  $("#modifyPostGenre").val("포크/어쿠스틱");
});

$("#modifyIdol").on("click", function () {
  $("#modifyPostGenre").val("아이돌");
});

$("#modifyRap").on("click", function () {
  $("#modifyPostGenre").val("랩/힙합");
});

$("#modifyRB").on("click", function () {
  $("#modifyPostGenre").val("알앤비/소울");
});

$("#modifyElect").on("click", function () {
  $("#modifyPostGenre").val("일렉트로닉");
});

$("#modifyRock").on("click", function () {
  $("#modifyPostGenre").val("락/메탈");
});

$("#modifyJazz").on("click", function () {
  $("#modifyPostGenre").val("재즈");
});

$("#modifyIndie").on("click", function () {
  $("#modifyPostGenre").val("인디");
});

$("#modifyAdult").on("click", function () {
  $("#modifyPostGenre").val("성인가요");
});

// modal 감성 선택
$("#modifyExcite").on("click", function () {
  $("#modifyPostEmote").val("신남");
});

$("#modifyGloomy").on("click", function () {
  $("#modifyPostEmote").val("우울");
});

$("#modifyEcstasy").on("click", function () {
  $("#modifyPostEmote").val("희열");
});

$("#modifySpooky").on("click", function () {
  $("#modifyPostEmote").val("으스스");
});

$("#modifyMagni").on("click", function () {
  $("#modifyPostEmote").val("웅장한");
});

$("#modifyCalm").on("click", function () {
  $("#modifyPostEmote").val("잔잔");
});

$("#modifyFlutter").on("click", function () {
  $("#modifyPostEmote").val("설렘");
});

$("#modifySorrow").on("click", function () {
  $("#modifyPostEmote").val("애절한");
});

$("#modifyDreamy").on("click", function () {
  $("#modifyPostEmote").val("몽환적");
});

$("#modifyWarm").on("click", function () {
  $("#modifyPostEmote").val("따뜻한");
});
