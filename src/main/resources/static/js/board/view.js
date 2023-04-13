
window.onload = function(){
  let swiper = new Swiper(".boardView-swiper", {
    slidesPerView: 3,
    spaceBetween: 30,
    loop: true,
    autoplay: {
      delay: 4000, //add
      disableOnInteraction: false,
    },
  });

  // let good = document.getElementsByClassName("good");
  // let like = document.getElementsByClassName("like");
  // werjwkrj();

  // function werjwkrj(){
  //   like.addEventListener("click", function(){
  //     good.classList.toggle('liked')
  //   });
  // }
  
}

function test(){
  document.querySelector(".good-img").classList.toggle('liked');
}



