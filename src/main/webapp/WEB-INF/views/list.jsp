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
  
    <div align="left">
        <a href="/boards/list?categoryId=1">1번 게시판</a>
        <a href="/boards/list?categoryId=2">2번 게시판</a>
    </div>

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
    <%-- 페이징 처리 --%>
    <c:if test="${criteria.searchType == null || criteria.keyword == null}">
        <c:if test="${pageMaker.prev}">
            [<a href="/boards/list?categoryId=${categoryId}&Page=${pageMaker.startPage - 1}">&laquo;</a>]
        </c:if>

        <c:forEach begin="${pageMaker.startPage}" end="${pageMaker.endPage}" var="page">
            [<a href="/boards/list?categoryId=${categoryId}&Page=${page}">${page}</a>]
        </c:forEach>

        <c:if test="${pageMaker.next && pageMaker.endPage > 0}">
            [<a href="/boards/list?categoryId=${categoryId}&Page=${pageMaker.endPage + 1}">&raquo;</a>]
        </c:if>
    </c:if>

    <c:if test="${criteria.searchType != null && criteria.keyword != null}">
        <c:if test="${pageMaker.prev}">
            [<a href="/boards/search?categoryId=${categoryId}&Page=${pageMaker.startPage - 1}&searchType=${criteria.searchType}&keyword=${criteria.keyword}">&laquo;</a>]
        </c:if>

        <c:forEach begin="${pageMaker.startPage}" end="${pageMaker.endPage}" var="page">
            [<a href="/boards/search?categoryId=${categoryId}&Page=${page}&searchType=${criteria.searchType}&keyword=${criteria.keyword}">${page}</a>]
        </c:forEach>

        <c:if test="${pageMaker.next && pageMaker.endPage > 0}">
            [<a href="/boards/search?categoryId=${categoryId}&Page=${pageMaker.endPage + 1}&searchType=${criteria.searchType}&keyword=${criteria.keyword}">&raquo;</a>]
        </c:if>
    </c:if>

    <%-- 검색 창 --%>
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