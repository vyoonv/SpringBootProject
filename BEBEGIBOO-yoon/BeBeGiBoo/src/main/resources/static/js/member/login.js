const loginId = document.querySelector("#loginForm input[name='memberId']");
const idMessage = document.querySelector("#idMessage");

const loginPw = document.querySelector("#loginForm input[name='memberPw']");
const pwMessage = document.querySelector("#pwMessage");

const loginForm = document.querySelector("#loginForm");

const checkObj = {
    "loginId" : false,
    "loginPw" : false
}


//아이디 유효성 검사
loginId.addEventListener("input", e=>{
    const inputId = e.target.value;
    if(inputId.trim().length < 6){
        idMessage.style.visibility = 'visible';
        checkObj.loginId = false;

    }else{
        idMessage.style.visibility ='hidden';
        checkObj.loginId= true;
    }
    
});

loginPw.addEventListener("input", e=>{
    const inputPw = e.target.value;
    if(inputPw.trim().length < 6) {
        pwMessage.style.visibility = 'visible';
        checkObj.loginPw = false;
 

    }else{
        pwMessage.style.visibility = 'hidden';
        checkObj.loginPw = true;
    }

});

loginForm.addEventListener("submit", e=>{
    console.log(checkObj.loginId, checkObj.loginPw);
    for(let key in checkObj){ 

        if( !checkObj[key]){ 
            let str; 

            switch(key){
                case "loginId" : 
                    str = "아이디가 유효하지 않습니다"; break;
                case "loginPw":
                    str = "비밀번호가 유효하지 않습니다"; break;
            }

            alert(str);
            e.preventDefault();

            return;
        }
    }
});


//-------------아이디 기억하기-----------------

const getCookie= (key) =>{

    const cookies= document.cookie;
    console.log(cookies);

    const cookieList = cookies.split("; ")
                        .map(el => el.split("="));

    const obj = {}; //쿠키 추가 객체

    for(let i = 0; i < cookieList.length; i++){
        const key = cookieList[i][0];
        const value = cookieList[i][1];

        obj[key] = value;
    }

    return obj[key]; 

}

if(loginId != null){
    const saveId = getCookie("saveId");

    if(saveId != undefined){
        loginId.value = saveId;
        document.querySelector("#saveId").checked = true;
        checkObj.loginId = true;
    }
}





