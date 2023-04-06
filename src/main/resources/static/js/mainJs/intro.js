window.onload = function(){
  let introBtn = document.querySelector(".intro-Area--btn .intro-btn");
  let introModel = document.querySelector(".intro-model");
  
  introBtn.addEventListener("click", function(){
    introModel.classList.toggle("aaa");
  });
}