<%@ page language="java" contentType="text/html; charset=EUC-KR"
         pageEncoding="EUC-KR" isELIgnored="false"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>modifyBoard</title>
</head>
<body>
<h1>modifyBoard</h1>
<br>

<form method="post" action="/boards/modify" enctype="multipart/form-data">
    <h1>modify</h1>
    제목: <input type="text" name="title" value="${board.title}"><br>
    내용: <textarea name="content" cols="80" rows="25"}>${board.content}</textarea><br>
    <input type="file" name="file"><br>
    <input type="hidden" name="categoryId" value="${board.categoryId}">
    <input type="hidden" name="id" value="${board.id}">
    <input type="submit">
</form>
</body>
</html>

