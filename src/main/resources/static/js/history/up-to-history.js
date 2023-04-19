function likeEvent(){
    const btnsLike = document.querySelectorAll(".btn-like");
    btnsLike.forEach((item)=>{
        item.addEventListener("click",(e)=>{
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
                        case 0 : alert("좋았었습니다!!")
                        $(item).parent().parent('li').addClass('cancel');
                        $(item).addClass('cancel')
                          break;
                        case 1 : alert("좋아요!")
                        $(item).parent().parent('li').removeClass('cancel');
                        $(item).removeClass('cancel')
                          break;
                      }
                },
                error:function(err){
                    console.log(err);
                }
            })
        })
    })
  }
function bookmarkEvent(){
    const btnsBookmark = document.querySelectorAll(".btn-bookmark");
    btnsBookmark.forEach((item)=>{
        item.addEventListener("click",(e)=>{
            const sendData = {
                "postNo":item.dataset.postno,
                // "bookmark":item.dataset.bookmark
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
                        case 0 : alert("북마크 했었어요!!")
                        $(item).addClass('cancel')
                          break;
                        case 1 : alert("북마크 했습니다!")
                        $(item).removeClass('cancel')
                          break;
                      }

                },
                error:function(err){
                    console.log(err);
                }
            })
        })
    })
}
function followEvent(){
    const btnsBookmark = document.querySelectorAll(".btn-follow");
    btnsBookmark.forEach((item)=>{
        item.addEventListener("click",(e)=>{
            const sendData = {
                "followID":item.dataset.followID,
            }
            console.log(sendData);
            $.ajax({
                url:"/member/follow",
                type:"post",
                data:sendData,
                success:function(response){
                    if(response){
                        alert("팔로우 했어요!");
                        item.dataset.followed = '1'
                        item.classList.add("followd");
                        item.parentElement.parentElement.classList.remove("delete");
                    }else{
                        alert("팔로우 했었어요!");
                        item.dataset.followed = '0'
                        item.classList.remove("followed");
                        item.parentElement.parentElement.classList.add("delete");
                    }
                },
                error:function(err){
                    console.log(err);
                }
            })
        })
    })
}