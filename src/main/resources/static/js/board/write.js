window.onload = function(){
  let ModalBox = document.getElementById("ModalBox");
  let ModalBox02 = document.getElementById("ModalBox02");
  let ModalThis = document.getElementById("ModalThis");
  let closeModel = document.querySelector(".closeModel");

  Models();

  function Models(){
    closeModel.addEventListener("click", function(){
      ModalThis.classList.remove("ModalThis-ok");
    });
    ModalBox.addEventListener("click", function(){
      ModalThis.classList.add("ModalThis-ok");
    });
    ModalBox02.addEventListener("click", function(){
      ModalThis.classList.add("ModalThis-ok");
    });

  }
}
