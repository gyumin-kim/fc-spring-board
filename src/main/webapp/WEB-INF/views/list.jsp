<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>게시판 목록</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <script>
        $(document).ready(function () {
            $("#btnWrite").click(function(){
                // 페이지 주소 변경
                location.href="${path}/boards/list";
            });
        });
        // 원하는 페이지로 이동 시 검색 조건, 키워드 값을 유지하기 위함
        function list(page){
            location.href="${path}/boards/list?categoryId="+categoryId+"&startPage="+startPage;
        }
    </script>

</head>
<body>
<b> 글 목 록</b>
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
</body>
</html>