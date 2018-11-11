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
</head>
<body id="list-bg">
    <%--<c:if test="${readPermission == false}">--%>
        <%--<script>permissionAlert()</script>--%>
    <%--</c:if>--%>

    <div align="center">
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <a class="navbar-brand" href="/">Spring board</a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
                <div class="navbar-nav">
                    <a class="nav-item nav-link" href="/boards/1">게시판 1<span class="sr-only">(current)</span></a>
                    <a class="nav-item nav-link" href="/boards/2">게시판 2</a>
                    <a class="nav-item nav-link" href="/boards/3">게시판 3</a>
                    <a class="nav-item nav-link" href="/boards/4">게시판 4</a>
                </div>
            </div>
            <%-- TODO 회원정보 폼 생성해야함 --%>
            <c:if test="${sessionScope.authUser != null}">
            <div class="navbar-nav" align="right">
                <a class="nav-item nav-link" href="/memberInfo">회원정보</a>
                <a class="nav-item nav-link" href="/logout">로그아웃</a>
            </div>
            </c:if>
        </nav>

        <h3 class="category-h2">게시판 ${categoryId}</h3>
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

            <c:forEach items="${boards}" var="board">
            <tr>
                <td>${board.id}</td>
                <%-- 제목 링크에 페이지값과 검색값을 같이 넣어 이전 목록으로 돌아올 수 있도록 함 --%>
                <td>
                    <c:forEach begin="1" end="${board.depth}" step="1"> <b>[RE]</b></c:forEach>
                    <c:if test="${criteria.searchType == null}">
                        <a href="/boards/${categoryId}/${board.id}?page=${criteria.page}">
                    </c:if>
                    <c:if test="${criteria.searchType != null && criteria.keyword != null}">
                        <a href="/boards/${categoryId}/${board.id}?page=${criteria.page}&searchType=${criteria.searchType}&keyword=${criteria.keyword}">
                    </c:if>
                    ${board.title}</a>  <%-- 제목 출력 --%>
                </td>

                <td>${board.name}</td>
                <td>${board.regDate}</td>
                <td>${board.ipAddr}</td>
            </tr>
            </c:forEach>
        </table>

        <br>
        <%-- 검색값이 없을 경우에 대한 페이징 처리 --%>
        <c:if test="${criteria.searchType == null}">
            <c:if test="${pageMaker.prev}">
                [<a href="/boards/${categoryId}?page=${pageMaker.startPage - 1}">&laquo;</a>]
            </c:if>

            <c:forEach begin="${pageMaker.startPage}" end="${pageMaker.endPage}" var="page">
                [<a href="/boards/${categoryId}?page=${page}">${page}</a>]
            </c:forEach>

            <c:if test="${pageMaker.next && pageMaker.endPage > 0}">
                [<a href="/boards/${categoryId}?page=${pageMaker.endPage + 1}">&raquo;</a>]
            </c:if>
        </c:if>
        <%-- 검색값이 있을 경우에 대한 페이징 처리 --%>
        <c:if test="${criteria.searchType != null && criteria.keyword != null}">
            <c:if test="${pageMaker.prev}">
                [<a href="/boards/${categoryId}?page=${pageMaker.startPage - 1}&searchType=${criteria.searchType}&keyword=${criteria.keyword}">&laquo;</a>]
            </c:if>

            <c:forEach begin="${pageMaker.startPage}" end="${pageMaker.endPage}" var="page">
                [<a href="/boards/${categoryId}?page=${page}&searchType=${criteria.searchType}&keyword=${criteria.keyword}">${page}</a>]
            </c:forEach>

            <c:if test="${pageMaker.next && pageMaker.endPage > 0}">
                [<a href="/boards/${categoryId}?page=${pageMaker.endPage + 1}&searchType=${criteria.searchType}&keyword=${criteria.keyword}">&raquo;</a>]
            </c:if>
        </c:if>

        <%-- 검색 창 --%>
        <form name="search" method="get" action="/boards/${categoryId}" onsubmit="return check()">
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

    <script type="text/javascript">
        function check() {
            if (document.search.keyword.value == '') {
                alert('검색어를 입력하세요');
                document.search.keyword.focus();
                return false;
            }
        }
    </script>
    <script src="/js/main.js"></script>
</body>
</html>