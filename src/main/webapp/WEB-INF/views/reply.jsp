<%--
  Created by IntelliJ IDEA.
  User: crazy
  Date: 18. 11. 6
  Time: 오후 1:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Reply</title>
</head>
<form method="post" action="/boards/reply" enctype="multipart/form-data">
    <h1>reply</h1>
    제목: <input type="text" name="title"><br>
    내용: <textarea name="content" cols="30" rows="30"></textarea><br>
    <input type="file" name="file"><br>
    <input type="hidden" name="boardId" value="${boardId}">
    <input type="submit">
</form>
</body>
</html>
