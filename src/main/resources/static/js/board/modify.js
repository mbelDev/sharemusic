getModal(
  $("#modalModifyGenreBtn"),
  $("#modalModifyGenre"),
  $("#closeModalModifyGenre")
);
getModal(
  $("#modalEmoteModifyBtn"),
  $("#modalModifyEmote"),
  $("#closeModalModifyEmote")
);

// 유튜브 링크가 맞는지 확인&링크에서 영상의 id만 뽑아오기
$(".btnModifyConfirm").on("click", function () {
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

// 장르선택
$("#modifyDance").on("click", function () {
  $("#modifyPostGenre").val("댄스/팝");
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
