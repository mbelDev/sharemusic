
// 스와이퍼

  $('.swiper').each(function(index) {
    t = $(this);
    t.addClass('swiepr-' + index);
  
    let swiper = new Swiper( ".swiper", {
      speed: 3000,
      loop: true,
      slidesPerView: "auto",
      spaceBetween: 10,
      autoplay: {
        delay: 3000, //add
        disableOnInteraction: false,
      }
    });
  });

  $('.profileMySwiper').each(function(index) {
    t = $(this);
    t.addClass('profileMySwiper-' + index);
  
    let swiper = new Swiper( ".profileMySwiper", {
      speed: 3000,
      loop: true,
      slidesPerView: 5,
      // spaceBetween: 5,
      autoplay: {
        delay: 3000, //add
        disableOnInteraction: false,
      }
    });
  });


