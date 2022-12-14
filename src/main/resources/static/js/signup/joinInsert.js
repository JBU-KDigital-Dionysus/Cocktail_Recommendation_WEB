userInsertForm.cstId.addEventListener("change", checkUserId)

function checkUserId() {
    userInsertForm.cstId.classList.remove("is-invalid");
    userInsertForm.cstId.classList.remove("is-valid");

    let cstId = userInsertForm.cstId.value;
    var pattern1 = /[0-9]/;
    var pattern2 = /[a-zA-Z]/; //영어

    let url = "/signup/checkUserId.do?cstId=" + cstId;

    var regExp = /[`~!@#$%^&*|\\'";:\/?{}\[\]\-_]/g;
    var idInput = $("#uid")
    $.ajax({
        type: "POST",
        url: "/signup/checkId",
        data: {
            "cstId": idInput.val()
        },
        success: function (data) {
            if (data === 1) {
                userIdInvalid.innerText = "이미 존재하는 아이디입니다."
                userInsertForm.cstId.classList.add("is-invalid")
            } else {
                if (cstId.length < 4) {
                    userIdInvalid.innerText = "4글자 이상으로 작성하세요.";
                    userInsertForm.cstId.classList.add("is-invalid");
                } else if (cstId.length > 20) {
                    userIdInvalid.innerText = "20자 이하로 작성하세요.";
                    userInsertForm.cstId.classList.add("is-invalid");
                } else if (!pattern2.test(cstId)) {
                    userIdInvalid.innerText = "영어가 포함 되어 있지 않습니다.";
                    userInsertForm.cstId.classList.add("is-invalid");
                } else if (!pattern1.test(cstId)) {
                    userIdInvalid.innerText = "숫자가 포함되어 있지 않습니다.";
                    userInsertForm.cstId.classList.add("is-invalid");
                } else if (regExp.test(cstId)) {
                    userIdInvalid.innerText = "허용되지 않는 문자가 포함되어 있습니다.";
                    userInsertForm.cstId.classList.add("is-invalid");
                } else {
                    userInsertForm.cstId.classList.remove("is-invalid")
                    userIdInvalid.innerHTML = '사용 가능한 아이디 입니다.';
                    userInsertForm.cstId.classList.add("is-valid");
                }
            }
        }
    })
}


function check_pw() {
    userInsertForm.cstPw.classList.remove("is-invalid");
    userInsertForm.cstPw.classList.remove("is-valid");
    userInsertForm.userPW2.classList.remove("is-invalid");
    userInsertForm.userPW2.classList.remove("is-valid");


    let cstPw = userInsertForm.cstPw.value;
    var pw = document.getElementById('pw').value;
    var SC = ["!", "@", "#", "$", "%"];
    var pattern1 = /[0-9]/;
    var pattern2 = /[a-zA-Z]/; //영어
    var check_SC = 0;

    if (pw.length < 6 || pw.length > 16) {
        check2.innerHTML = '비밀번호는 6글자 이상, 16글자 이하만 이용 가능합니다.'
        userInsertForm.cstPw.classList.add("is-invalid");
    }
    for (var i = 0; i < SC.length; i++) {
        if (pw.indexOf(SC[i]) != -1) {
            check_SC = 1;
        }
    }
    if (check_SC == 0) {
        check2.innerHTML = '!,@,#,$,% 의 특수문자가 들어가 있지 않습니다.';
        userInsertForm.cstPw.classList.add("is-invalid");
    }
    if (!pattern2.test(cstPw)) {
        check2.innerHTML = '영어가 포함되어 있지 않습니다. ';
        userInsertForm.cstPw.classList.add("is-invalid");
    }
    if (!pattern1.test(cstPw)) {
        check2.innerHTML = '숫자가 포함되어 있지 않습니다. ';
        userInsertForm.cstPw.classList.add("is-invalid");
    }
    if (pw.length > 6 && pw.length < 16 && check_SC != 0) {
        userInsertForm.cstPw.classList.add("is-valid");
    }
    if (pattern2.test(cstPw) && pattern1.test(cstPw) && pw.length > 6 && pw.length < 16 && check_SC != 0 && document.getElementById('pw').value != '' && document.getElementById('pw2').value != '') {
        if (document.getElementById('pw').value == document.getElementById('pw2').value) {
            userPWInvalid1.innerText = "비밀번호가 일치합니다.";
            userInsertForm.userPW2.classList.add("is-valid");
            const target = document.getElementById('target_btn');
            target.disabled = false;

        } else {
            userPWInvalid2.innerText = "비밀번호가 일치하지 않습니다.";
            userInsertForm.userPW2.classList.add("is-invalid");

        }
    }
}

function checkReg(event) {
    const regExp = /[^ㄱ-ㅎ|가-힣]/g; // 한글만 허용
    const del = event.target;
    if (regExp.test(del.value)) {
        del.value = del.value.replace(regExp, '');
    }
};

function checkReg2(event) {
    const regExp = /[^0-9]+/g; //숫자만 입력하는 정규식
    const del = event.target;
    if (regExp.test(del.value)) {
        del.value = del.value.replace(regExp, '');
    }
};


//joinform_check 함수로 유효성 검사
function joinform_check() {
    document.userInsertForm.submit(); //유효성 검사의 포인트
}
