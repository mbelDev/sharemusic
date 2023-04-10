
// 스와이퍼
  // let swiper = new Swiper(".mySwiper", {
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
        delay: 500, //add
        disableOnInteraction: false,
      },
      mousewheel: {
        invert: false,
      },
      speed: 2000,
      loop: true,
      loopAdditionalSlides: 1,
      slidesPerView: "auto",
    });
  });


