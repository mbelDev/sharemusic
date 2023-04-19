function getModal(getBtn, getModal, getClose) {
  let ModalBtn = getBtn; //document.getElementById("Modal***Btn");
  let Modal = getModal; //document.getElementById("Modal***");
  let closeBtn = getClose; //document.getElementById("closeModal***");

  $(closeBtn).click(() => {
    $(Modal).removeClass("modal-open");
  });

  $(ModalBtn).click(() => {
    $(Modal).addClass("modal-open");
    console.log("modalBtnClicked");
  });
}

function getModal02(getModal, getClose) {
  let Modal = getModal; //document.getElementById("Modal***");
  let closeBtn = getClose; //document.getElementById("closeModal***");

  $(Modal).addClass("modal-open");
  $(closeBtn).click(() => {
    $(Modal).removeClass("modal-open");
  });
}
