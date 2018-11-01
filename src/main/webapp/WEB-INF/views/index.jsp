<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>index</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" type="text/css" media="screen" href="/css/main.css" />
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.4.2/css/all.css" integrity="sha384-/rXc/GQVaYpyDdyxK+ecHPVYJSN9bmVFBvjA/9eOB+pb3F2w2N6fc5qB9Ew5yIns" crossorigin="anonymous">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <link href="https://fonts.googleapis.com/css?family=Alegreya|Pacifico|Trocchi|Do+Hyeon|Song+Myung|Yeon+Sung" rel="stylesheet">
</head>
<body class="bg">
    <div class="top-header">
        <span class="logo">AMERICAN APPAREL</span>
        <%-- 로그인 되어 있으면 로그아웃 버튼 보이게 --%>
        <c:if test="${sessionScope.authUser != null}">
            <a href="/logout"><button class="logout-button">로그아웃</button></a>
        </c:if>
    </div>

    <%-- 클릭하면 modal(로그인 or 회원가입) popup --%>
    <a href="#" class="enter" data-toggle="modal" data-target="#exampleModalCenter">
        <p>ENTER</p>
    </a>

    <a href="https://github.com/gyumin-kim/fc-spring-board" target="_blank" class="github-link">
        <i class="fab fa-github"></i>
    </a>

    <!-- Modal(로그인 or 회원가입) -->
    <div class="modal fade" id="exampleModalCenter" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalCenterTitle">
                        <c:if test="${sessionScope.authUser == null}">로그인</c:if>
                        <c:if test="${sessionScope.authUser != null}">회원가입</c:if>
                    </h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <c:if test="${sessionScope.authUser == null}">
                        <form method="POST" action="/login">
                            아이디 : <input type="text" name="name" required><br>
                            암호 : <input type="password" name="password" required><br><br>
                            <%-- modal-footer --%>
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                            <input type="submit" class="btn btn-primary" value="Submit"/>
                        </form>
                    </c:if>
                    <c:if test="${sessionScope.authUser != null}">
                        <form method="POST" action="/signup">
                            아이디 : <input type="text" name="name" required><br>
                            암호 : <input type="password" name="password" required minlength="3" maxlength="20"><br>
                            <%-- modal-footer --%>
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                            <input type="submit" class="btn btn-primary" value="Submit"/>
                        </form>
                    </c:if>
                </div>
            </div>
        </div>
    </div>

    <script src="/js/main.js"></script>
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
</body>
</html>
