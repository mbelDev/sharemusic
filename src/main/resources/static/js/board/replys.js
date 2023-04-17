function addReplyEvent(){
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
                        replyNo : $("#btn-reply-reply").data("replyno"),
                        replyCont : $("#replyCont").val(),
                        replyHidden:$(".check-hidden-reply").is(":checked")==true ? 1 : 0,
                    },
                    success:function(response){
                        console.log(response);
                    },
                    error:function(err){
                        console.log(err);
                    }
                })
                .done(function( fragment ){
                    $("#reply-container").replaceWith(fragment);
                    alert("등록되었습니다.");
                    addDeleteReplyEvent();
                });
            })
        })
    })
}