<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>관리자 페이지</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" type="text/css" media="screen" href="/css/main.css" />
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
</head>

<body style="margin: 40px 100px">
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
<table class="table table-borderless">
    <thead>
        <tr class="table-active">
            <th scope="col" style="width: 10%">id</th>
            <th scope="col" style="width: 30%">email</th>
            <th scope="col" style="width: 15%">name</th>
            <th scope="col" style="width: 45%">permission</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach items="${members}" var="member">
        <tr>
            <td>${member.id}</td>
            <td>${member.email}</td>
            <td>${member.name}</td>
            <td>
                <%-- 권한 리스트 및 적용 --%>
                <form method="post" action="/admin">
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="checkbox" id="permission-read" name="permissionRead">
                        <label class="form-check-label" for="permission-read">읽기</label>
                    </div>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="checkbox" id="permission-write" name="permissionWrite">
                        <label class="form-check-label" for="permission-write">쓰기</label>
                    </div>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="checkbox" id="permission-update" name="permissionUpdate">
                        <label class="form-check-label" for="permission-update">수정</label>
                    </div>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="checkbox" id="permission-delete" name="permissionDelete">
                        <label class="form-check-label" for="permission-delete">삭제</label>
                    </div>
                    <input type="hidden" id="member-id" value="${member.id}" name="memberId">
                    <button type="submit" class="btn btn-secondary">적용</button>
                </form>
            </td>
        </tr>
        </c:forEach>
    </tbody>
</table>

<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
</body>
</html>
