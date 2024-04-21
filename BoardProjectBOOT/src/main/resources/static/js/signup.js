//필수 입력 항목의 유효성 검사 여부를 체크하기 위한 객체 
// true : 유효
// false : 유효하지 않음

const checkObj = {
    "memberEmail" : false, 
    "authKey" : false,
    "memberPw" : false, 
    "memberPwConfirm" : false, 
    "memberNickname"  : false,
    "memberTel"       : false
}; 

const memberEmail = document.querySelector("#memberEmail");
const emailMessage = document.querySelector("#emailMessage");

memberEmail.addEventListener("input", e => {

    checkObj.authKey = false; 
    document.querySelector("#authKeyMessage").innerText = ""; 

    const inputEmail = e.target.value; 

    if(inputEmail.trim().length === 0) {
        emailMessage.innerText = "메일을 받을 수 있는 이메일을 입력해주세요"; 
        emailMessage.classList.remove('confirm', 'error'); 
        checkObj.memberEmail = false; 
        memberEmail.value = ""; 
        return; 
    }
    
    const regExp = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

    if( !regExp.test(inputEmail)) {
        emailMessage.innerText = "알맞은 이메일 형식으로 작성해주세요"; 
        emailMessage.classList.add('error'); 
        emailMessage.classList.remove('confirm'); 
        checkObj.memberEmail = false; 
        return; 
    }
    // 유효한 이메일 형식인지 검사(중복검사 비동기형식) 
    fetch("/member/checkEmail?memberEmail="+inputEmail)
    .then(resp => resp.text())
    .then(count => {

        if(count == 1) {
            emailMessage.innerText = "이미 사용중인 이메일입니다"; 
            emailMessage.classList.add('error'); 
            emailMessage.classList.remove('confirm'); 
            checkObj.memberEmail = false; 
            return; 
        }
        emailMessage.innerText = "사용 가능한 이메일입니다"; 
        emailMessage.classList.add('confirm'); 
        emailMessage.classList.remove('error'); 
        checkObj.memberEmail = true; 
    }).catch(error=> {
        console.log(error); 
    }); 

}); 