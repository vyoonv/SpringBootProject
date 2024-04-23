//console.log("아직 미완성");


// 쿠키에서 key가 일치하는 value 얻어오기 함수 
// 쿠키는 "K=V; K=V; ..." 형식

// 배열.map(함수) : 배열의 각 요소를 이용해 함수 수행 후 결과 값으로 새로운 배열을 만들어서 반환

const getCookie = (key) => {

    //document.cookie = "test" + "=" + "유저일";

   const cookies = document.cookie; // "K=V; K=V; ...

    //console.log(cookies);

    // cookies 문자열을 배열 형태로 변환
    const cookieList = cookies.split(";") //["K:V", "K:"V" ]
                    .map(el => el.split("=")); // ["K": "V", "K":"V" ]...
                    
   // console.log(cookieList)

   // 배열 -> 객체로 변환 (그래야 다루기 쉬움)
   const obj = {}; //비어있는 객체 선언

   for(let i=0; i<cookieList.length; i++){
        const k = cookieList[i][0]; // key 값
        const v = cookieList[i][1]; // value 값
        obj[k] = v; // 객체에 추가
   }
   //console.log(obj);

   return obj[key]; // 매개변수로 전달 받은 key와 
                    // obj 객체에 저장된 키가 일치하는 요소의 value 값 반환
}

const loginEmail = document.querySelector("#loginForm input[name='memberEmail']");

//로그인 안된 상태인 경우에 수행 
if(loginEmail != null) { //로그인창의 이메일 입력부분이 화면에 있을 때

    // 쿠키 중 key 값이 "saveId"인 요소의 value 얻어오기
    const saveId = getCookie("saveId"); // undefined(체크 해제시) 또는 이메일 (체크시)

    //saveId 값이 있을 경우
    if(saveId != undefined){
        loginEmail.value = saveId; // 쿠키에서 얻어온 값을 input에 value로 세팅

        // 아이디 저장 체크박스에 체크 해두기
        document.querySelector("input[name='saveId']").checked = true;
    }
};

// 이메일, 비밀번호 미작성 시 로그인 막기
const loginForm  = document.querySelector("#loginForm");

const loginPw = document.querySelector("#loginForm input[name='memberPw']");

// #loginForm이 화면에 존재할 때 (== 로그인 상태 아닐 때)
if(loginForm != null) {

    // 제출 이벤트 발생 시
    loginForm.addEventListener("submit", e => {
        // 이메일 미작성
        if(loginEmail.value.trim().length === 0){
            alert("이메일을 작성해주세요!");
            e.preventDefault(); // 기본 이벤트(제출) 막기
            loginEmail.focus();// 초점 이동
            return;
        }

         // 비밀번호 미작성
         if(loginPw.value.trim().length === 0){
            alert("비밀번호를 작성해주세요!");
            e.preventDefault(); // 기본 이벤트(제출) 막기
            loginPw.focus();// 초점 이동
            return;
        }
    });
}

//----------------------------------------------------------------

/* 빠른 로그인 */

const quickLoginBtns = document.querySelectorAll(".quick-login");

quickLoginBtns.forEach((item, index) => {
    // item : 현재 반복 시 꺼내온 객체
    // index : 현재 반복 중인 인덱스

    // quickLoginBtns 요소인 button 태그 하나씩 꺼내서 이벤트 리스너 추가
    item.addEventListener("click", () => {

        const email = item.innerText; //버튼에 작성된 이메일 얻어오기
        location.href = "/member/quickLogin?memberEmail=" + email;
    });
});

//-------------------------------------------------------------------

/* 회원 목록 조회(비동기) */

//조회 버튼
const selectMemberList = document.querySelector("#selectMemberList");

//tbody 
const memberList = document.querySelector("#memberList");

// td 요소를 만들고 text 추가 후 반환
const createTd = (text) => {
    const td = document.createElement("td");
    td.innerText = text;
    return td;
}
// 조회 버튼 클릭시 
selectMemberList.addEventListener("click", () => {

    // 1) 비동기로 회원 목록 조회
    // ( 포함할 회원 정보 : 회원번호, 이메일, 닉네임, 탈퇴여부)
    fetch("/member/selectMemberList")
    .then(resp => resp.json()) //JSON.parse(resp)
    .then(result => {

    /* (==) .then(resp => resp.text()
        .then(result => {  
        const data = JSON.parse(result)*/

        // 이전 내용 삭제
        memberList.innerHTML ="";

        // tbody에 들어갈 요소를 만들고 값 세팅 후 추가해주기
        result.forEach((member, index) => {
           
            //member : 현재 반복 접근 중인 요소
            // index : 현재 접근 중인 인덱스

            //tr 만들어서 그 안에 td 만들고, append 후 
            // tr을 tbody에 append 해주기
            const keyList = ['memberNo', 'memberEmail','memberNickname','memberDelFl'];

            const tr = document.createElement("tr");
            
            /* 
               같은 방법이지만 코드가 길어지는 방법이라 주석처리 안한 부분으로 하면 
               간단히 할 수 있음
            const td = document.createElement("td");
            td.innerTect = member['memberNo'];
            tr.append(td);

            ...

            */

            // <tr></tr>
            keyList.forEach(key => tr.append(createTd(member[key])));

            // tbodt 자식으로 tr 추가
            memberList.append(tr);
            
        });
    });   
});

//------------------------------------------------

const resetMemberNo = document.querySelector("#resetMemberNo");
const rresetPw = document.querySelector("#resetPw");

resetPw.addEventListener("click", () => {

    //입력 받은 회원 번호 얻어오기
    const inputNo = resetMemberNo.value;

    if(inputNo.trim().length == 0){
        alert("회원 번호 입력해주세요");
        return;
    }

    fetch("/member/resetPw", {
        method : "PUT", // PUT : 수정 요청 방식
        headers : {"Content-Type" : "application/json"},
        body : inputNo
    })
    .then(resp => resp.text())
    .then(result => {
        if(result >0) {
            alert("초기화 성공")
        }else{
            alert("해당 회원이 존재하지 않습니다.")
        }
    })
});

//--------------------------------------------------

// 특정 회원(회원번호) 탈퇴 복구 (알아서 해보기>..!!!)
