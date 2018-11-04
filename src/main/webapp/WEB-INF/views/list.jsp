<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>게시판 목록</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" type="text/css" media="screen" href="/css/main.css" />
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">

    <script>
        function list(categoryId, page){
            location.href="${path}/boards/list?categoryId="+categoryId+"&Page="+page;
        }
    </script>
</head>
<body>
    <div align="center">
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <a class="navbar-brand" href="/">Spring board</a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
                <div class="navbar-nav">
                    <a class="nav-item nav-link" href="/boards/list?categoryId=1">게시판 1<span class="sr-only">(current)</span></a>
                    <a class="nav-item nav-link" href="/boards/list?categoryId=2">게시판 2</a>
                    <a class="nav-item nav-link" href="/boards/list?categoryId=3">게시판 3</a>
                    <a class="nav-item nav-link" href="/boards/list?categoryId=4">게시판 4</a>
                </div>
            </div>
        </nav>

        <b>글 목 록</b>
        <table border="0" width="100%">
            <tr bgcolor="white">
                <td align="right"><a href="/boards/write">글쓰기</a></td>
            </tr>
        </table>

    <table border="1" width="100%">
        <tr bgcolor="gray">
            <th>글 번호</th>
            <th width="40%">제목</th>
            <th>작성자</th>
            <th>작성일</th>
            <th>IP</th>
        </tr>

        <tr>
            <c:forEach items="${boards}" var="board">
            <td>${board.id}</td>
            <td>${board.title}</td>
            <td>${board.name}</td>
            <td>${board.regDate}</td>
            <td>${board.ipAddr}</td>
        </tr>
        </c:forEach>
    </table>
        <br>
        <c:if test="${pageMaker.prev}">
            [<a href="javascript:list('${map.boards.categoryId}', '${pageMaker.startPage - 1}')">&laquo;</a>]
        </c:if>

        <c:forEach begin="${pageMaker.startPage}" end="${pageMaker.endPage}" var="page">
            [<a href="javascript:list('${map.boards.categoryId}','${page}')">${page}</a>]
        </c:forEach>

        <c:if test="${pageMaker.next && pageMaker.endPage > 0}">
            [<a href="javascript:list('${map.boards.categoryId}','${pageMaker.endPage + 1}')">&raquo;</a>]
        </c:if>
    </div>



</body>
</html>