function likeEvent(item){
  const sendData = {
    "postNo":item.dataset.postno
    //좋아요는 기록 조작의 우려가 있어서 서버에서 좋아요 여부 처리
    }
    $.ajax({
        url:"/member/like",
        type:"post",
        data:sendData,
        success:function(response){
            switch(response){
                case -1 : alert("로그인 정보가 업써용 여따가 당신 이름 석쟈만 적어쥬세용") 
                location.href="/member/login"
                  break;
                case 0 : 
                  $(item).removeClass("btn-dark");
                  break;
                case 1 : 
                  $(item).addClass("btn-dark");
                  break;
              }
        },
        error:function(err){
            console.log(err);
        }
    })
  }
function bookmarkEvent(item){
  const sendData = {
    "postNo":item.dataset.postno,
  }
  $.ajax({
      url:"/member/bookmark",
      type:"post",
      data:sendData,
      success:function(response){
          console.log(response);
          switch(response){
              case -1 : alert("로그인 정보가 업써용 여따가 당신 이름 석쟈만 적어쥬세용") 
              location.href="/member/login"
                break;
              case 0 : 
              $(item).removeClass('btn-info')
                break;
              case 1 : 
              $(item).addClass('btn-info')
                break;
            }

      },
      error:function(err){
          console.log(err);
      }
  })
}
function followEvent(){
    const btnsBookmark = document.querySelectorAll(".btn-follow");
    btnsBookmark.forEach((item)=>{
        item.addEventListener("click",(e)=>{
            const sendData = {
                "followID":item.dataset.followid,
            }
            console.log(sendData);
            $.ajax({
                url:"/member/follow",
                type:"post",
                data:sendData,
                success:function(response){
                    if(response){
                        item.classList.remove("btn-secondary");
                      }else{
                        item.classList.add("btn-secondary");
                    }
                },
                error:function(err){
                    console.log(err);
                }
            })
        })
    })
}


// // // // // // // // // // // // // // // // // // // // // // // // // // // //
//                        마이 페이지에서 사용하는 JS들                            //
// // // // // // // // // // // // // // // // // // // // // // // // // // // //

function setIcon(event) {
    var reader = new FileReader();

    reader.onload = function (event) {
      $("#testProfile").attr("src",event.target.result);

      //appendChild말고 replace방법을 찾아보자.
      //이것이 그 방법이다.
    };

    reader.readAsDataURL(event.target.files[0]);
  }
  function setBaseIcon(){
    $("#testProfile").attr("th:src" , "@{/upload/}+${userInfo.userIconReal}");
  }

  function removeIcon(){
    let file= $("input[type=file]");
    file.val(""); 
      var img = $("#testProfile")
      img.attr("src","/images/sampleprofile.jpg");
  }

  function updateUserNM(){
    console.log($("#userNM").val()+" "+$("#userIcon").val());
    $.ajax({
      url:"/member/update",
      type:"POST",
      data:{
        "userIcon" : $("#userIcon").val(),
        "userNM" : $("#userNM").val()
      },
      success:function(response){
        alert("회원 정보를 수정했습니다.");
        location.href="/member/mypage";
      },
      error:function(err){
        console.log(err);
      }
    })
  }

  function updateProfile(){
    const file = $("#updateProfile-uploadFile")[0];
    const sendData = new FormData(file);
    $.ajax({
      url:"/member/update",
      type:"POST",
      contentType:false,
      processData:false,
      data:sendData,
      success:function(response){
        console.log(response);
        if(response == 1){
          alert("프로필 사진을 업데이트 했습니다.");
          location.href="/member/mypage";
        }else{
          alert("문제가 발생하였습니다.");
        }
      },
      error:function(err){
        console.log(err);
      }
    })

  }

  function openInputNM(){
    $("#updateProfile-userNM").removeClass("hidden");
    $("#originalProfile-userNM").addClass("hidden");
    $("#profile-modify").addClass("hidden");
  }
  function closeInputNM(){
    $("#updateProfile-userNM").addClass("hidden");
    $("#originalProfile-userNM").removeClass("hidden");
    $("#profile-modify").removeClass("hidden");
  }

  function recentDelete(){
    $.ajax({
      url:"/member/recent/delete",
      type:"POST",
      success:function(response){
        console.log(response)
        switch(response){
          case -1 : alert("로그인 정보가 없습니다.");
          break;
          case 1 : alert("시청기록을 모두 삭제했습니다.");
          location.reload(true);
          break;
        }
      },
      error:function(err){
        console.log(err);
        alert("알 수 없는 오류가 발생했습니다.");
      }
    })
  }