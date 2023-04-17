function likeEvent(){
    const btnsLike = document.querySelectorAll(".btn-like");
    btnsLike.forEach((item)=>{
        item.addEventListener("click",(e)=>{
            const sendData = {
                "postNo":item.dataset.postno,
                "userID":item.dataset.userid
                //좋아요는 기록 조작의 우려가 있어서 서버에서 좋아요 여부 처리
            }
            console.log(sendData);
            $.ajax({
                url:"/member/like",
                type:"post",
                data:sendData,
                success:function(response){
                    if(response){
                      alert("좋아요!");
                      item.classList.add("liked");
                      item.parentElement.parentElement.classList.remove("delete");
                    }else{
                      alert("좋았었어요!");
                      item.classList.remove("liked");
                      item.parentElement.parentElement.classList.add("delete");
                      console.log(item.parentElement.parentElement);
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
                "userID":item.dataset.userid,
                "bookmark":item.dataset.bookmark
            }
            console.log(sendData);
            $.ajax({
                url:"/member/bookmark",
                type:"post",
                data:sendData,
                success:function(response){
                    if(response){
                        alert("북마크 했어요!");
                        item.dataset.bookmark = '1'
                        item.classList.add("bookmarked");
                    }else{
                        alert("북마크 했었어요!");
                        item.dataset.bookmark = '0'
                        item.classList.remove("bookmarked");
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
                "userID":item.dataset.userid
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