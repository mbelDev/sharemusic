function getModal(getBtn,getModal,getClose){
    let ModalBtn = getBtn//document.getElementById("Modal***Btn");
    let Modal = getModal//document.getElementById("Modal***");
    let closeBtn = getClose//document.getElementById("closeModal***");

    $(closeBtn).click(()=>{
        console.log(Modal);
        $(Modal).removeClass("ModalThis-ok");
    });
    
    $(ModalBtn).click(()=>{
        alert("test");
    $(Modal).addClass("ModalThis-ok");
    });
  }
  
