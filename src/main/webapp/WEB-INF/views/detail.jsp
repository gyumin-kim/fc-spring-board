<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>${board.title}</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" type="text/css" media="screen" href="/css/main.css" />
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
</head>
<body style="margin: 40px 100px">
    <table class="table table-borderless" style="margin-bottom: 5px;">
        <thead>
        <tr class="table-active">
            <th scope="col" style="width: 5%">번호</th>
            <th scope="col" style="width: 65%">제목</th>
            <th scope="col" style="width: 10%">작성자</th>
            <th scope="col" style="width: 10%">등록일</th>
            <th scope="col" style="width: 10%">IP Address</th>
        </tr>
        </thead>
        <tbody>
            <tr style="padding-bottom: 5px;">
                <td>${board.id}</td>
                <td><strong>${board.title}</strong></td>
                <td>${board.name}</td>
                <td>${board.regDate}</td>
                <td>${board.ipAddr}</td>
            </tr>
        </tbody>
    </table>

    <div class="card border-light" style="width: 100%; margin-bottom: 60px;">
        <div class="card-body" style="padding: 10px 30px;">
            <p class="card-text">${board.content}</p>
        </div>
    </div>

    <%-- 댓글 입력 --%>
    <form>
        <div class="form-group">
            <label for="content" id="comment-label">댓글 달기</label>
            <textarea class="form-control" id="content" rows="3"></textarea>
        </div>
        <input type="hidden" id="board-id" value="${board.id}">
        <%-- BoardController.detail()에서 category attribute를 가져왔다고 가정 --%>
        <input type="hidden" id="category-id" value="${category.id}">
        <input type="hidden" id="member-id" value="${authUser.id}">
        <input type="button" id="comment-submit" value="등록"><br><br>
    </form>

    <%-- 댓글 목록 --%>
    <table class="table">
        <caption>Spring Board</caption>
        <thead>
        <tr class="table-success">
            <th scope="col" style="width: 15%">작성자</th>
            <th scope="col" style="width: 75%">내용</th>
            <th scope="col" style="width: 10%">IP Address</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="comment" items="${commentList}">
        <tr>
            <td>${comment.name}</td>
            <td>${comment.content}</td>
            <td>${comment.ipAddr}</td>
        </tr>
        </c:forEach>
        </tbody>
    </table>

    <script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
    <script src="/js/comment.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
</body>
</html>
