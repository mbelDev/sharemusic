
// 스와이퍼
  // let swiper = new Swiper(".swiper", {
  //   slidesPerView: "auto",
  //   spaceBetween: 50,
  //   loop: true,
  //   autoplay: {   
  //     delay: 2500, 
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
      loopAdditionalSlides: 4,
      slidesPerView: "auto",
    });
  });


