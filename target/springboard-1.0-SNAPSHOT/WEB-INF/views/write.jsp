<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Title</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body>
<form method="post" action="/boards" enctype="multipart/form-data">
    <h1>write</h1>
    제목: <input type="text" name="title"><br>
    내용: <textarea name="content" cols="30" rows="30"></textarea><br>
    <input type="file" name="file"><br>
    <input type="submit">
</form>
</body>
</html>