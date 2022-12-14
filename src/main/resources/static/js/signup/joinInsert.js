userInsertForm.ctId.addEventListener("change",checkUserId)

async function checkUserId(){
	userInsertForm.ctId.classList.remove("is-invalid");
	userInsertForm.ctId.classList.remove("is-valid");
	
	let ctId=userInsertForm.ctId.value;
	var pattern1 = /[0-9]/; 
	var pattern2 = /[a-zA-Z]/; //영어
	
	let url="/signup/checkUserId.do?ctId="+ctId;

	if(ctId.length<8){
		userIdInvalid.innerText="8글자 이상 작성하세요.";
		userInsertForm.ctId.classList.add("is-invalid");
		}
		
	if (!pattern1.test(ctId)){
		userIdInvalid.innerText="숫자가 포함되어 있지 않습니다."; 
		userInsertForm.ctId.classList.add("is-invalid");
		}
		
	if(!pattern2.test(ctId)){
		userIdInvalid.innerText="영어가 포함 되어 잇지 않습니다.";
		userInsertForm.ctId.classList.add("is-invalid");
		}
		
	if(ctId.length>7&&pattern1.test(ctId) && pattern2.test(ctId)) {
		let resp=await fetch(url);
		if(resp.status==200){
			let json=await resp.json();		
			if(json.check==1){
				userIdInvalid.innerHTML='사용중인 아아디 입니다.';
				userInsertForm.ctId.classList.add("is-invalid");
			}else if(json.check==0){
				userIdInvalid.innerHTML='사용가능한 아이디 입니다.';
				userInsertForm.ctId.classList.add("is-valid");
			}else if(json.check==-1){
				alert("db 조회 실패(다시시도)");
			}
		}else{
			alert("통신 장애(다시시도)"+resp.status);
		}
	} 
	}
		
	

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    function check_pw(){
	   		userInsertForm.userPW2.classList.remove("is-invalid");
			userInsertForm.userPW2.classList.remove("is-valid");
			userInsertForm.ctPw.classList.remove("is-invalid");
			userInsertForm.ctPw.classList.remove("is-valid");
			
			let ctPw=userInsertForm.ctPw.value;
            var pw = document.getElementById('pw').value;
            var SC = ["!","@","#","$","%"];
            var pattern1 = /[0-9]/; 
            var pattern2 = /[a-zA-Z]/; //영어
            var check_SC = 0;
 
            if(pw.length < 6 || pw.length>16){
				check2.innerHTML='비밀번호는 6글자 이상, 16글자 이하만 이용 가능합니다.'
				userInsertForm.ctPw.classList.add("is-invalid");
            }
            for(var i=0;i<SC.length;i++){
                if(pw.indexOf(SC[i]) != -1){
                    check_SC = 1;
                }
            }
            if(check_SC == 0 ){
				check2.innerHTML='!,@,#,$,% 의 특수문자가 들어가 있지 않습니다.';
				userInsertForm.ctPw.classList.add("is-invalid");
            }
            if( !pattern2.test(ctPw)){
				  check2.innerHTML='영어가 포함되어 있지 않습니다. ';
					userInsertForm.ctPw.classList.add("is-invalid");
			}
			if(!pattern1.test(ctPw)){
				  check2.innerHTML='숫자가 포함되어 있지 않습니다. ';
					userInsertForm.ctPw.classList.add("is-invalid");
			}
            if(pw.length > 6 && pw.length <16 && check_SC != 0 ) {
				check1.innerHTML='사용할 수 있는 비밀번호입니다.';
				userInsertForm.ctPw.classList.add("is-valid");
			}
            if(pattern2.test(ctPw) && pattern1.test(ctPw) && pw.length > 6 && pw.length <16 && check_SC != 0 && document.getElementById('pw').value !='' && document.getElementById('pw2').value!=''){
                if(document.getElementById('pw').value==document.getElementById('pw2').value){
					userPWInvalid1.innerText="비밀번호가 일치합니다.";
					userInsertForm.userPW2.classList.add("is-valid");
					const target = document.getElementById('target_btn');
  					target.disabled = false;
				
                }
                else {
					userPWInvalid2.innerText="비밀번호가 일치하지 않습니다.";
					userInsertForm.userPW2.classList.add("is-invalid");
                   
                }
            }
        }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////

function onlyKorFunc(t){
  var regexp = /[a-z0-9]|[ \[\]{}()<>?|`~!@#$%^&*-_+=,.;:\"'\\]/g;
  t.onkeyup = function(e){
    var v = this.value;
    this.value = v.replace(regexp,'');
  }
}
function checkReg(event) {
 const regExp = /[^ㄱ-ㅎ|가-힣]/g; // 한글만 허용
  const del = event.target;
  if (regExp.test(del.value)) {
    del.value = del.value.replace(regExp, '');
  }
};

////////////////////////////////////////////////

function inputPhoneNumber(obj) {
    var number = obj.value.replace(/[^0-9]/g, "");
    var phone = "";

    if(number.length < 4) {
        return number;
    } else if(number.length < 7) {
        phone += number.substr(0, 3);
        phone += "-";
        phone += number.substr(3);
    } else if(number.length < 11) {
        phone += number.substr(0, 3);
        phone += "-";
        phone += number.substr(3, 3);
        phone += "-";
        phone += number.substr(6);
    } else {
        phone += number.substr(0, 3);
        phone += "-";
        phone += number.substr(3, 4);
        phone += "-";
        phone += number.substr(7);
    }
    obj.value = phone;
}
////////////////////////////////////////////////

//joinform_check 함수로 유효성 검사
function joinform_check() {
	
  //변수에 담아주기
  var uid = document.getElementById("uid");
  var pwd = document.getElementById("pw");
  var repwd = document.getElementById("pw2");
  var uname = document.getElementById("name");
 
  var female = document.getElementById("female");
  var male = document.getElementById("male");
  var mobile = document.getElementById("phone");
  var email_id = document.getElementById("email");

  if (uid.value == "") { //해당 입력값이 없을 경우 같은말: if(!uid.value)
    alert("아이디를 입력하세요.");
    uid.focus(); //focus(): 커서가 깜빡이는 현상, blur(): 커서가 사라지는 현상
    return false; //return: 반환하다 return false:  아무것도 반환하지 말아라 아래 코드부터 아무것도 진행하지 말것
  };

  if (pwd.value == "") {
    alert("비밀번호를 입력하세요.");
    pwd.focus();
    return false;
  };
  
 

  if (repwd.value !== pwd.value) {
    alert("비밀번호가 일치하지 않습니다..");
    repwd.focus();
    return false;
  };

  if (uname.value == "") {
    alert("이름을 입력하세요.");
    uname.focus();
    return false;
  };

  if (!female.checked && !male.checked) { //둘다 미체크시
    alert("성별을 선택해 주세요.");
    female.focus();
    return false;
  }
  
  
  

  var reg = /^[0-9]+/g; //숫자만 입력하는 정규식
  if (!reg.test(mobile.value)) {
    alert("전화번호는 숫자만 입력할 수 있습니다.");
    mobile.focus();
    return false;
  }

  if (email_id.value == "") {
    alert("이메일 주소를 입력하세요.");
    email_id.focus();
    return false;
  }



  //입력 값 전송
  
  document.userInsertForm.submit(); //유효성 검사의 포인트   
}