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
    <style type="text/css">
        #wrap {
            width: 1000px;
            margin: 0 auto 0 auto;
        }
        #detailBoard{
            text-align :center;
        }
        #title{
            /*height : 16;*/
            font-family :'돋움';
            /*font-size : 12;*/
            text-align :center;
        }
    </style>
</head>

<body style="margin: 40px 100px">
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
    </nav>
  
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
            <%--첨부파일 나타내기--%>
            <td>${fileName}</td> <button type="button" onclick="location.href='/boards/download/${board.id}'">다운로드</button>
          
            <tr align="right" valign="middle">
                <td colspan="5">
                    <button type="button" onclick="location.href='/boards/modify'">수정</button>
                    <button type="button" onclick="location.href='/boards/delete'">삭제</button>
                    <button type="button" onclick="location.href='/boards/write'">답글</button>
                    <button type="button"
                            <c:if test="${criteria.searchType == null || criteria.keyword == null}">
                                onclick="location.href='/boards/${categoryId}?page=${criteria.page}'">
                            </c:if>
                            <c:if test="${criteria.searchType != null && criteria.keyword != null}">
                                onclick="location.href='/boards/${categoryId}?page=${criteria.page}&searchType=${criteria.searchType}&keyword=${criteria.keyword}'">
                            </c:if>
                            목록</button>
                </td>
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
