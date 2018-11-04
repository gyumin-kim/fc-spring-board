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
<body>
    <h1>${board.title}</h1>
    <p>${board.content}</p>
    ${board.id}<br>
    ${board.name}<br>
    ${board.regDate}<br>
    ${board.ipAddr}<br><br>

    <%-- 댓글 입력 --%>
    <form>
        <div class="form-group">
            <label for="content">댓글 달기</label>
            <textarea class="form-control" id="content" rows="3"></textarea>
        </div>
        <input type="hidden" id="board-id" value="${board.id}">
        <input type="hidden" id="member-id" value="${authUser.id}">
        <input type="button" id="comment-submit" value="등록"><br><br>
    </form>

    <%-- 댓글 목록 --%>
    <c:forEach var="comment" items="${commentList}">
        ${comment.name}<br>
        ${comment.content}<br><br>
    </c:forEach>

    <script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
    <script src="/js/main.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
</body>
</html>
