<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>게시판 목록</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <script>
        function list(categoryId, page){
            location.href="${path}/boards/list?categoryId="+categoryId+"&Page="+page;
        }
    </script>

    <script type="text/javascript">
        function check() {
            if (document.search.keyword.value == '') {
                alert('검색어를 입력하세요');
                document.search.keyword.focus();
                return false;
            }
        }
    </script>

</head>
<body>
<div align="center">
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
        [<a href="/boards/list?categoryId=${categoryId}&Page=${pageMaker.startPage - 1}">&laquo;</a>]
    </c:if>

    <c:forEach begin="${pageMaker.startPage}" end="${pageMaker.endPage}" var="page">
        [<a href="/boards/list?categoryId=${categoryId}&Page=${page}">${page}</a>]
    </c:forEach>

    <c:if test="${pageMaker.next && pageMaker.endPage > 0}">
        [<a href="/boards/list?categoryId=${categoryId}&Page=${pageMaker.endPage + 1}">&raquo;</a>]
    </c:if>

    <form name="search" method="get" action="/boards/search" onsubmit="return check()">
        <table width="200" boarder="0" align="center">
            <tr>
                <td align="center" valign="bottom">
                    <select name="searchType">
                        <option value="title" selected="selected">제목</option>
                        <option value="content">내용</option>
                        <option value="name">이름</option>
                        <option value="titleOrContent">제목+내용</option>
                    </select>
                </td>
                <td>
                    <input type="search" size="16" name="keyword">
                </td>
                <td>
                    <input type="hidden" name="categoryId" value="${categoryId}">
                    <input type="submit" value="검색">
                </td>
            </tr>
        </table>
    </form>

</div>
</body>
</html>