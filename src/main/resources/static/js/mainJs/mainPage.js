window.onload = function(){
  let swiper = new Swiper(".mySwiper", {
    effect: "cards",
    grabCursor: true,
    loop:false,
    autoplay: {     
      delay: 2500,
      disableOnInteraction: false,
    },
    mousewheel: {
      invert: false,
    },
  });
}