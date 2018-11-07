<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>writeform</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" type="text/css" media="screen" href="/css/main.css" />
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
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
        내용: <textarea name="content" cols="30" rows="10"></textarea><br>
        <input type="file" name="file"><br>
        <input type="submit">
    </form>
</body>
</html>
