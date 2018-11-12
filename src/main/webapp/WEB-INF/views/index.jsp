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
        <%-- 관리자일 경우 --%>
        <c:if test="${sessionScope.admin != null}">
            <a href="/admin"><button class="admin-btn btn btn-light">관리자</button></a>
        </c:if>
        <%-- 로그인 되어 있으면 로그아웃 버튼 보이게 --%>
        <c:if test="${sessionScope.authUser != null}">
            <a href="/logout"><button class="logout-btn btn btn-light">로그아웃</button></a>
        </c:if>
    </div>

    <%-- 로그인 되어 있지 않을 경우 --%>
    <c:if test="${sessionScope.authUser == null}">
    <%-- 클릭하면 로그인 modal popup --%>
    <a href="#" class="enter" data-toggle="modal" data-target="#loginModalCenter">
        <p>ENTER</p>
    </a>
    </c:if>

    <%-- 로그인 되어 있을 경우 --%>
    <c:if test="${sessionScope.authUser != null}">
    <div class="index-cards card-group">
        <div class="index-card card bg-light mb-3" style="max-width: 18rem;">
            <a href="/boards/1">
                <div class="card-body">
                    <h5 class="card-title">Category 1</h5>
                    <p class="card-text text-secondary">This is a wider card with supporting text below as a natural lead-in to additional content. This content is a little bit longer.</p>
                </div>
            </a>
        </div>
        <div class="index-card card bg-light mb-3" style="max-width: 18rem;">
            <a href="/boards/2">
                <div class="card-body">
                    <h5 class="card-title">Category 2</h5>
                    <p class="card-text text-secondary">This card has supporting text below as a natural lead-in to additional content.</p>
                </div>
            </a>
        </div>
        <div class="index-card card bg-light mb-3" style="max-width: 18rem;">
            <a href="/boards/3">
                <div class="card-body">
                    <h5 class="card-title">Category 3</h5>
                    <p class="card-text text-secondary">This is a wider card with supporting text below as a natural lead-in to additional content. This card has even longer content than the first to show that equal height action.</p>
                </div>
            </a>
        </div>
        <div class="index-card card bg-light mb-3" style="max-width: 18rem;">
            <a href="/boards/4">
                <div class="card-body">
                    <h5 class="card-title">Category 4</h5>
                    <p class="card-text text-secondary">This is a wider card with supporting text below as a natural lead-in to additional content. This card has even longer content than the first to show that equal height action.</p>
                </div>
            </a>
        </div>
    </div>
    </c:if>

    <%-- 하단 GitHub 아이콘(Font Awesome) --%>
    <a href="https://github.com/gyumin-kim/fc-spring-board" target="_blank" class="github-link">
        <i class="fab fa-github"></i>
    </a>

    <!-- 로그인 Modal -->
    <div class="modal fade" id="loginModalCenter" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="loginModalCenterTitle">로그인</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <%-- form을 submit하면 main.js에서 AJAX 이벤트 처리 --%>
                    이메일 : <input type="email" name="email" id="login-email" autocomplete="email" placeholder="이메일을 입력하세요" required><br>
                    암호 : <input type="password" name="password" id="login-password" autocomplete="off" placeholder="비밀번호를 입력하세요" required minlength="3" maxlength="20"><br><br>
                    <%-- 클릭하면 로그인 modal 사라지고, 회원가입 modal popup --%>
                    <a href="/signup" id="signup-btn" data-toggle="modal" data-dismiss="modal" data-target="#signupModalCenter">
                        <button type="button" class="btn btn-secondary">Sign up</button>
                    </a>
                    <input type="button" id="login-submit" class="btn btn-primary" value="Submit"/>
                </div>
            </div>
        </div>
    </div>

    <!-- 회원가입 Modal -->
    <div class="modal fade" id="signupModalCenter" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="signupModalCenterTitle">회원가입</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <%--<form method="POST" action="/signup">--%>
                        이메일 : <input type="email" name="email" id="signup-email" placeholder="이메일을 입력하세요" required><br>
                        이름 : <input type="text" name="name" id="signup-name" placeholder="이름을 입력하세요" required><br>
                        암호 : <input type="password" name="password" id="signup-password" placeholder="비밀번호를 입력하세요" required minlength="3" maxlength="20"><br>
                        <button type="button" id="signup-close" class="btn btn-secondary" data-dismiss="modal">Close</button>
                        <input type="button" id="signup-submit" class="btn btn-primary" value="Sign up"/>
                    <%--</form>--%>
                </div>
            </div>
        </div>
    </div>

    <script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
    <script src="/js/main.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
</body>
</html>
