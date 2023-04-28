
// 스와이퍼

  
    let swiperd = new Swiper( ".profileMySwipers", {
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
        500: {
          slidesPerView: 1,  
          spaceBetween: 100,
        },
        320: {
          slidesPerView: 1,  
          spaceBetween: 10,
        },
      },
    });


    let swiper = new Swiper( ".profileMySwiper", {
      slidesPerView: 6,
      spaceBetween: 300,
      loop: true,
      autoplay: {
        delay: 3000, //add
        disableOnInteraction: false,
      },
      breakpoints: {
      856: {
        slidesPerView: 6,  
        spaceBetween: 300,
      },
      855: {
        slidesPerView: 2,  
        spaceBetween: 0,
      },
      800: {
        slidesPerView: 2,  
        spaceBetween: 100,
      },
      700: {
        slidesPerView: 2,  
        spaceBetween: 150,
      },
      600: {
        slidesPerView: 2,  
        spaceBetween: 200,
      },
      501: {
        slidesPerView: 2,  
        spaceBetween: 350,
      },
      500: {
        slidesPerView: 1,  
        spaceBetween: 350,
      },
      
      
    }
    });

