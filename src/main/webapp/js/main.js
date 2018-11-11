let loginFormBtn = document.querySelector('#login-submit');
let signupFormBtn = document.querySelector('#signup-submit');

let handleError = function() {
    console.log('AJAX call error.');
};

let permissionAlert = function() {
    alert('권한이 없습니다. 관리자에게 문의하세요.');
};

loginFormBtn.addEventListener('click', () => {
    let loginEmail = document.getElementById('login-email').value;          // input 태그에 입력된 email
    let loginPassword = document.getElementById('login-password').value;    // input 태그에 입력된 password
    let signupBtn = document.querySelector('#signup-btn');

    let loginData = {
        'email': loginEmail,
        'password': loginPassword
    };

    $.ajax({
        method: 'GET',
        url: `/login?${$.param(loginData)}`,
        success: function(data) {
            let loginValidation = document.getElementById('loginValidation');
            if (document.body.contains(loginValidation))
                document.getElementById('loginValidation').remove();
            let p = document.createElement('p');
            p.setAttribute('id', 'loginValidation');

            if (data === 'nullValue') {
                p.innerText = '값을 입력하세요.';
                signupBtn.insertAdjacentElement('beforebegin', p);
            }
            else {
                if (data === 'loginSuccess')
                    window.location.href = '/';
                else if (data === 'noSuchMember')
                    p.innerText = '가입되지 않은 ID입니다.';
                else if (data === 'wrongPassword')
                    p.innerText = '비밀번호를 확인하세요.';
                signupBtn.insertAdjacentElement('beforebegin', p);
            }
        },
        error: handleError
    });
});

signupFormBtn.addEventListener('click', () => {
    let signupEmail = document.getElementById('signup-email').value;        // input 태그에 입력된 email
    let signupName = document.getElementById('signup-name').value;          // input 태그에 입력된 password
    let signupPassword = document.getElementById('signup-password').value;  // input 태그에 입력된 password
    let signupCloseBtn = document.querySelector('#signup-close');

    let signupData = {
        'email': signupEmail,
        'name': signupName,
        'password': signupPassword
    };

    $.ajax({
        method: 'GET',
        url: `/signup?${$.param(signupData)}`,  // signupData(parameter)를 Controller에서 @RequestParam으로 받음
        // data: signupData,    // Data to be sent to the server
        success: function(data) {
            let loginValidation = document.getElementById('loginValidation');
            if (document.body.contains(loginValidation))
                document.getElementById('loginValidation').remove();
            let p = document.createElement('p');
            p.setAttribute('id', 'loginValidation');

            if (data === 'noSuchMember') {
                // 가입 요청
                $.post('/signup', signupData);
                window.location.href = '/';
            }
            else if (data === 'existsSuchMember') {
                let p = document.createElement('p');
                p.innerText = '이미 존재하는 ID입니다.';
                signupCloseBtn.insertAdjacentElement('beforebegin', p);
            }
            else if (data === 'nullValue') {
                p.innerText = '값을 입력하세요.';
                signupCloseBtn.insertAdjacentElement('beforebegin', p);
            }
        },
        error: handleError
    });
});