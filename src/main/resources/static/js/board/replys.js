//덧글 입력 처리 함수
function addReplyEvent(item){
    //item = 작성 버튼
    const content = $(item).siblings('.replyCont').val();
    const hidden = $(item).closest('.board-view--replyBox-input').find('.check-hidden').is(":checked")==true ? 1 : 0 ; 
    //작성 버튼을 기준으로 형제 element에서 덧글 내용과 비밀글 체크를 받아온다
    $.ajax({
        url: "/board/reply",
        type:"POST",
        data:{
        replyCont : content,
        replyHidden : hidden,
        replyGroup : 0,
        replyLevel : 0,
        replyStep : 0
    },
    success: function(response){
        console.log(response);
        alert("덧글을 작성했습니다!");
        $(".replyCont").val('');
    },
    error:function(err){
        console.log(err);
        alert("로그인 정보가 없습니다 로그인 해 주세요.");
        location.href='/member/login';
    }
    })
    .done(function( fragment ){
        $("#reply-container").replaceWith(fragment);
        addDeleteReplyEvent();
    });
}

// 덧글의 덧글 입력 처리 함수
function addReplyReplyEvent(item){
    const replyno = $(item).data("replyno");
    //작성 버튼의 data-set 에서 덧글 번호를 가져온다
    const content = $(item).siblings('.replyCont').val();
    const hidden = $(item).closest('.board-view--replyBox-input').find('.check-hidden').is(":checked")==true ? 1 : 0 ; 
    $.ajax({
        url:"/board/reply/reply",
        type:"POST",
        data:{
            replyGroup : replyno,
            replyCont : content,
            replyHidden : hidden,
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
        $(content).val('');
    });
}

//덧글의 수정처리 이벤트
function modifyReplyEvent(item){
    const replyno = $(item).data("replyno");
    const content = $(item).siblings('.replyCont').val();
    const hidden = $(item).closest('.board-view--replyBox-input').find('.check-hidden').is(":checked")==true ? 1 : 0 ; 
    $.ajax({
        url:"/board/reply/modify",
        type:"POST",
        data:{
            replyNo : replyno,
            replyCont : content,
            replyHidden : hidden,
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
        alert("성공적으로 수정되었습니다.");
    });
}

//덧글의 덧글을 달기위해 입력창을 생성하는 함수
function openReplyReplyEvent(item){
    $('div.replyReply').remove();
    const test = $(".board-view--replyBox-input").clone();
    const replyno = $(item).data("replyno");
    $(test).addClass("replyReply");
    $(test).find('.btn-reply').data("replyno",replyno);
    $(test).find('.btn-reply').attr("onclick","addReplyReplyEvent(this)");

    $(item).closest('li.reply').after(test);
}

//덧글 수정을 위해 수정 입력창을 생성하는 함수
function openModifyReplyEvent(item){
    $('div.modifyReply').remove();
    $('.reply-contents--text.hidden').removeClass('hidden');
    const root = $(item).closest('div.reply-contents');
    const originTxt = root.find('.context').text();
    const modifyInput = $(".board-view--replyBox-input").clone();
    const replyno = $(item).closest('li.reply').data("replyno");
    root.find('.reply-contents--text').addClass("hidden");
    $(modifyInput).find('.replyCont').val(originTxt);
    $(modifyInput).addClass("modifyReply");
    $(modifyInput).find('.btn-reply').data("replyno",replyno);
    $(modifyInput).find('.btn-reply').attr("onclick","modifyReplyEvent(this)");
    
    root.find('.reply-contents--box').before(modifyInput);
}

//덧글 삭제 확인 모달을 띄우는 이벤트
function openDeleteReplyEvent(item) {
    const target = document.querySelector("#deleteReplyConfirm");
    const replyNo = $(item).closest('.reply').data("replyno");
    target.dataset.replyno = replyNo;
    console.log(replyNo);
    $("#deleteReplyModal").addClass("modal-open");
  }

//덧글 삭제 모달에서 확인 누르면 발생하는 이벤트
function modalReplyDelete(item){
    const replyno = item.dataset.replyno;
    $.ajax({
        url:"/board/reply/delete",
        type:"POST",
        data:{
            replyNo : replyno
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
    });
}