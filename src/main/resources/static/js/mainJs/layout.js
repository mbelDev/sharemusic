window.onload = function(){
  let headerLayoutArea = document.querySelector("#headerLayout-Area");

  window.addEventListener("scroll", function(){

    headerLayoutArea.classList.add("active");
  });
}