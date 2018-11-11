<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>${board.title}</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" type="text/css" media="screen" href="/css/main.css" />
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
</head>

<body id="detail-bg">
    <%-- Nav Bar --%>
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
    </nav>
    </c:if>

    <%-- 글 정보 --%>
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
  
    <%-- 글 내용 --%>
    <div class="card border-light" style="width: 100%; margin-bottom: 35px;">
        <div class="card-body" style="padding: 10px 30px;">
            <p class="card-text">${board.content}</p>
        </div>
    </div>

    <%--첨부파일 나타내기--%>
    <p>${fileName}</p>
    <button type="button" onclick="location.href='/boards/download/${board.id}'">다운로드</button>

    <div id="board-detail-btns">
        <tr align="right" valign="middle">
            <td colspan="5">
                <c:if test="${isMember == true}">
                <button type="button" onclick="location.href='/boards/modify?boardId=${board.id}'">수정</button>
                <button type="button" onclick="location.href='/boards/delete?boardId=${board.id}&categoryType=${board.categoryId}'">삭제</button>
                </c:if>
                <button type="button" onclick="location.href='/boards/reply?boardId=${board.id}'">답글</button>
                <button type="button"
                        <c:if test="${criteria.searchType == null || criteria.keyword == null}">
                            onclick="location.href='/boards/${categoryId}?page=${criteria.page}'">
                        </c:if>
                        <c:if test="${criteria.searchType != null && criteria.keyword != null}">
                            onclick="location.href='/boards/${categoryId}?page=${criteria.page}&searchType=${criteria.searchType}&keyword=${criteria.keyword}'">
                        </c:if>
                        목록
                </button>
            </td>
        </tr>
    </div>

    <%-- 댓글 입력 form --%>
    <form>
        <div class="form-group">
            <label for="content" id="comment-label">댓글 달기</label>
            <textarea class="form-control" id="content" placeholder="댓글을 입력하세요" rows="3"></textarea>
        </div>
        <input type="hidden" id="board-id" value="${board.id}">
        <input type="hidden" id="auth-user-id" value="${authUser.id}">
        <input type="hidden" id="member-name" value="${memberName}">
        <input type="hidden" id="reg-date" value="${regDate}">
        <input type="button" id="comment-submit" value="등록"><br><br>
    </form>

    <%-- 댓글 목록 --%>
    <table class="table">
        <caption>Spring Board</caption>
        <thead>
            <tr class="table-success">
                <th scope="col" style="width: 15%">작성자</th>
                <th scope="col" style="width: 60%">내용</th>
                <th scope="col" style="width: 15%">등록일</th>
                <th scope="col" style="width: 5%"></th>
                <th scope="col" style="width: 5%"></th>
            </tr>
        </thead>
        <tbody id="comment-list">
            <c:forEach var="comment" items="${commentList}">
                <%-- 지워지지 않았거나 / 지워졌지만 대댓글이 있으면 보여줌 --%>
                <c:if test="${comment.isDeleted == 0 or (comment.isDeleted == 1 and comment.childCommentCount > 1)}">
                    <%-- 원댓글 --%>
                    <c:if test="${comment.id == comment.parentCommentId}">
                    <tr>
                        <td>${comment.name}</td>

                        <c:if test="${comment.isDeleted == 1 and comment.childCommentCount > 1}">
                            <td style="color: darkred">삭제된 댓글입니다.</td>
                        </c:if>
                        <c:if test="${comment.isDeleted == 0}">
                            <td>${comment.content}</td>
                        </c:if>

                        <c:if test="${comment.isDeleted == 0}">
                            <td>${comment.regDate}</td>
                            <td class="recommentBtn">댓글</td>
                            <%-- 댓글 단 본인에게만 삭제 버튼이 보임 --%>
                            <c:if test="${authUser.id == comment.memberId}">
                            <td class="delete-comment">삭제</td>
                            <input type="hidden" class="comment-id" value="${comment.id}">
                            </c:if>
                            <c:if test="${authUser.id != comment.memberId}">
                            <td></td>
                            </c:if>
                        </c:if>
                        <c:if test="${comment.isDeleted == 1 and comment.childCommentCount > 1}">
                            <td></td>
                            <td></td>
                            <td></td>
                        </c:if>
                    </tr>

                    <%-- 대댓글 form --%>
                    <tr class="recomment-tr" style="display: none">
                        <td colspan="100">
                            <form class="recomment-form">
                                <textarea class="recomment-content" cols="30" rows="10"></textarea>
                                <input type="hidden" class="recomment-board-id" value="${board.id}">
                                <input type="hidden" class="recomment-parent-comment-id" value="${comment.id}">
                                <input type="hidden" class="recomment-seq" value="${comment.seq}">
                                <input type="hidden" class="recomment-member-id" value="${authUser.id}">

                                <input type="button" class="recomment-comment-submit" value="등록"><br><br>
                            </form>
                        </td>
                    </tr>
                    </c:if>
                    <%-- 대댓글 form --%>
                    <%-- 원댓글 --%>

                    <%-- 대댓글 --%>
                    <%-- TODO: 대댓글에 대한 들여쓰기 UI --%>
                    <c:if test="${comment.id != comment.parentCommentId}">
                    <tr class="recomment-tr">
                        <td>ㄴ ${comment.name}</td>
                        <td>${comment.content}</td>
                        <td>${comment.regDate}</td>
                        <%-- 댓글버튼 없음. 원댓글에만 대댓글을 달 수 있음 --%>
                        <td></td>

                        <%-- TODO: 삭제 기능 --%>
                        <%-- 댓글 단 본인에게만 삭제 버튼이 보임 --%>
                        <c:if test="${authUser.id == comment.memberId}">
                        <td class="delete-comment">삭제</td>
                        <input type="hidden" class="comment-id" value="${comment.id}">
                        </c:if>

                        <c:if test="${authUser.id != comment.memberId}">
                        <td></td>
                        </c:if>
                    </tr>
                    </c:if>
                    <%-- 대댓글 --%>
                </c:if>
            </c:forEach>
        </tbody>
    </table>

    <script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
    <script src="/js/comment.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
</body>
</html>
