let commentFormBtn = document.querySelector('#comment-submit');

commentFormBtn.addEventListener('click', () => {
    console.log('commentFormBtn clicked!');
    let content = document.getElementById('content').value;         // input 태그에 입력된 comment
    let boardId = document.getElementById('board-id').value;        // 댓글이 속해있는 게시물의 id
    // let categoryId = document.getElementById('category-id').value;  // 댓글이 속해있는 게시물의 categoryId
    let categoryId = 1;

    // let memberId = document.getElementById('member-id').value;      // 댓글이 속해있는 게시물의 id
    let memberId = 2;      // 댓글이 속해있는 게시물의 id

    let commentData = {
        'content': content,
        'boardId': boardId,
        'memberId': memberId
    };
    console.log(commentData);

    // BoardCommentController.submitComment()로 POST 요청 보냄
    $.ajax({
        method: 'POST',
        contentType: 'application/json; charset=UTF-8',
        accept: 'application/json',
        url: `/boards/${categoryId}/${boardId}/comment`,
        data: JSON.stringify(commentData),  // commentData: BoardCommentController에게 전송할 데이터
        dataType: 'text',                   // 'json'으로 하면 에러 발생
        success: function(data) {           // data: BoardCommentController로부터 받은 리턴값
            // 성공시 댓글 목록 보여주는 또다른 AJAX call 필요

        },
        error: handleError
    });
});

function handleError() {
    console.log('AJAX call error.');
}