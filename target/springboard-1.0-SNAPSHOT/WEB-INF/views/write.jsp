<%@ page language="java" contentType="text/html; charset=EUC-KR"
         pageEncoding="EUC-KR" isELIgnored="false"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>writeform</title>
</head>
<body>
<h1>writeform</h1>
<br>

<form method="post" action="/boards/write" enctype="multipart/form-data">
    <h1>write</h1>
    <select name="categoryType">
        <option value="1" selected="selected">category 1</option>
        <option value="2">category 2</option>
        <option value="3">category 3</option>
        <option value="4">category 4</option>
    </select>
    제목: <input type="text" name="title"><br>
    내용: <textarea name="content" cols="30" rows="30"></textarea><br>
    <input type="file" name="file"><br>
    <input type="submit">
</form>
</body>
</html>
