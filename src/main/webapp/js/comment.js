let commentFormBtn = document.querySelector('#comment-submit');
let commentList = document.querySelector('#comment-list');

commentFormBtn.addEventListener('click', () => {
    console.log('commentFormBtn clicked!');
    let contentValue = document.getElementById('content').value;    // input 태그에 입력된 comment
    let boardId = document.getElementById('board-id').value;        // 댓글이 속해있는 게시물의 id
    let categoryId = document.getElementById('category-id').value;  // 댓글이 속해있는 게시물의 categoryId
    let memberId = document.getElementById('member-id').value;      //
    let memberName = document.getElementById('member-name').value;  //
    let regDate = document.getElementById('reg-date').value;        //

    let commentData = {
        'content': contentValue,
        'memberId': memberId,
        'categoryId': categoryId
    };
    console.log(commentData);
    document.getElementById('content').value = '';

    // BoardCommentController.submitComment()로 POST 요청 보냄
    $.ajax({
        method: 'POST',
        contentType: 'application/json; charset=UTF-8',
        accept: 'application/json',
        url: `/boards/${categoryId}/${boardId}/comment`,
        data: JSON.stringify(commentData),  // commentData: BoardCommentController에게 전송할 데이터
        dataType: 'text',                   // 'json'으로 하면 에러 발생
        success: function(data) {           // data: BoardCommentController로부터 받은 리턴값
            console.log('댓글 등록 성공');
            appendLatestComment(memberName, commentData.content, regDate);
        },
        error: handleError
    });
});

function handleError() {
    console.log('comment AJAX call error.');
}

/**
 * 추가한 댓글을 댓글 목록 맨 위에 붙이는 함수
 * @param name
 * @param content
 * @param regDate
 */
function appendLatestComment(name, content, regDate) {
    let tr = document.createElement('tr');
    tr.setAttribute('id', 'tr');
    commentList.insertAdjacentElement('afterbegin', tr);

    let tdName = document.createElement('td');
    let tdContent = document.createElement('td');
    let tdRegDate = document.createElement('td');
    tdName.innerHTML = name;
    tdContent.innerHTML = content;
    tdRegDate.innerHTML = regDate;
    tr.insertAdjacentElement('afterbegin', tdRegDate);
    tr.insertAdjacentElement('afterbegin', tdContent);
    tr.insertAdjacentElement('afterbegin', tdName);
}