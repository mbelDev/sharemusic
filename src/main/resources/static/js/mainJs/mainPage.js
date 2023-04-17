
// 스와이퍼
// var swiper = new Swiper(".profileMySwiper", {
//   speed: 3000,
//   loop: true,
//   slidesPerView: 5,
//   spaceBetween: 10,
//   autoplay: {
//     delay: 3000,
//     disableOnInteraction: false,
//   },
// });

  $('.swiper').each(function(index) {
    t = $(this);
    t.addClass('swiepr-' + index);
  
    let swiper = new Swiper( ".swiper", {
      autoplay: {
        delay: 3000, //add
        disableOnInteraction: false,
      },
      mousewheel: {
        invert: false,
      },
      speed: 3000,
      loop: true,
      slidesPerView: 5,
      spaceBetween: 10,
    });
  });


