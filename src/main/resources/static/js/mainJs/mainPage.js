window.onload = function(){
  let swiper = new Swiper(".mySwiper", {
    effect: "cards",
    grabCursor: true,
    loop:false,
    autoplay: {     
      delay: 2500, // 시간 설정
      disableOnInteraction: false, // false-스와이프 후 자동 재생
    },
  });
}