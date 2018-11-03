let signupBtn = document.querySelector('#signup-btn');
let loginFormBtn = document.querySelector('#loginSubmit');

loginFormBtn.addEventListener('click', () => {
    let loginEmail = document.getElementById('login-email').value;          // input 태그에 입력된 email
    let loginPassword = document.getElementById('login-password').value;    // input 태그에 입력된 password

    let loginData = {
        'email': loginEmail,
        'password': loginPassword
    };

    $.ajax({
        method: 'POST',
        url: `/login?${$.param(loginData)}`,
        data: loginData,    // Data to be sent to the server
        success: function(data) {
            if (data === 'loginSuccess') {
                window.location.href = '/';
            }
            else {
                let loginValidation = document.getElementById('loginValidation');
                if (document.body.contains(loginValidation))
                    document.getElementById('loginValidation').remove();
                let p = document.createElement('p');
                p.setAttribute('id', 'loginValidation');

                if (data === 'noSuchMember')
                    p.innerText = '가입되지 않은 ID입니다.';
                else if (data === 'wrongPassword')
                    p.innerText = '비밀번호를 확인하세요.';
                signupBtn.insertAdjacentElement('beforebegin', p);
            }
        },
        error: handleError
    });
});

function handleError() {
    console.log('Login: AJAX call error.');
}