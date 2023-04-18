
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
      },
      breakpoints: {
        1446: {
          slidesPerView: "auto", 
          // spaceBetween: 40,
        },
        1372: {
          slidesPerView: 2,  
          spaceBetween: 40,
        },
        320: {
          slidesPerView: 1,  
          spaceBetween: 10,
        },
      },
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


