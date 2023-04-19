//덧글 입력 처리 함수
function addReplyEvent(){
    $(".btn-reply").click(()=>{
        $.ajax({
          url: "/board/reply",
          type:"POST",
          data:{
          replyCont:$(".replyCont").val(),
          replyHidden:$(".check-hidden").is(":checked")==true ? 1 : 0,
          replyGroup:0,
          replyLevel:0,
          replyStep:0
        },
        success: function(response){
          console.log(response);
          alert("덧글을 작성했습니다!");
        },
        error:function(err){
          console.log(err);
          alert("로그인 정보가 없습니다 로그인 해 주세요.");
          location.href='/member/login';
        }
      })
      .done(function( fragment ){
          $("#reply-container").replaceWith(fragment);
          addReplyReplyEvent();
          addDeleteReplyEvent();
      });
    })
}

//덧글의 덧글을 달기위해 입력창을 생성하는 함수
function addReplyReplyEvent(){
    const replyBtns = $(".replyReplyBtn")
    replyBtns.each((index,item)=>{
        console.log(index);
        $(item).click((e)=>{
            $('div.replyReply').remove();
            $(e.target).parent().parent('li').after("<div class='board-view--replyBox__input replyReply'> <input type='text' class='replyCont' id='replyCont'/> <input type='button' class='btn-primary btn-reply' id='btn-reply-reply' value='작성'/> <input type='checkbox' class='check-hidden-reply'><span id='closeReply'></span><span>비밀글로 작성하기</span> </div>");
            $("#btn-reply-reply").data("replyno",$(e.target).data("replyno"));
            console.log($("#btn-reply-reply").data("replyno"));

            $("#btn-reply-reply").click(()=>{
                $.ajax({
                    url:"/board/reply/reply",
                    type:"POST",
                    data:{
                        replyGroup : $("#btn-reply-reply").data("replyno"),
                        replyCont : $("#replyCont").val(),
                        replyHidden:$(".check-hidden-reply").is(":checked")==true ? 1 : 0,
                    },
                    success:function(response){
                        console.log(response);
                    },
                    error:function(err){
                        console.log(err);
                        alert("로그인 정보가 없습니다. 로그인 해 주세요.");
                        location.href='/member/login';
                    }
                })
                .done(function( fragment ){
                    $("#reply-container").replaceWith(fragment);
                    alert("등록되었습니다.");
                    addReplyEvent();
                    addDeleteReplyEvent();
                });
            })
        })
    })
}

//덧글 삭제 확인 모달을 띄우는 이벤트
function addDeleteReplyEvent() {
    const deleteReplyBtns = document.querySelectorAll("#deleteReplyBtn");
    deleteReplyBtns.forEach((item)=>{
      item.addEventListener("click",(e)=>{
        const target = document.querySelector("#deleteReplyConfirm");
        const replyNo = e.target.dataset.replyno;
        target.dataset.replyno = replyNo;
        $("#deleteReplyModal").addClass("modal-open");
      })
    })

  }

//덧글 삭제 모달에서 확인 누르면 발생하는 이벤트
function modalReplyDelete(){
    $("#deleteReplyConfirm").click((e)=>{
    console.log(e.target.dataset.replyno);
    $.ajax({
        url:"/board/reply/delete",
        type:"POST",
        data:{
            replyNo:e.target.dataset.replyno
        },
        success:function(reponse){
            console.log("삭제 완료");
            
            $(deleteReplyModal).removeClass("modal-open");
            
        },
        error:function(err){
            console.log(err);
            alert("로그인 정보가 없습니다. 로그인 해 주세요.");
            $(deleteReplyModal).removeClass("modal-open");
            location.href='/member/login';
        }
    })
    .done(function( fragment ){
        $("#reply-container").replaceWith(fragment);
        alert("삭제되었습니다.");
        addReplyReplyEvent();
        addDeleteReplyEvent();
    });
    })

}