// 쿠키에서 key가 일치하는 value 얻어오기 함수 

const getCookie = (key) => {

    const cookies = document.cookie; //"k=v"; "k=v"

    // cookies 문자열을 배열 형태로 변환 
    const cookieList = cookies.split("; ")
    .map(el => el.split("=")); // ["k=v", "k=v"] -> ["k","v"]
    // 배열.map(함수) : 배열의 각 요소를 이용해 함수 수행 후 결과 값으로
    // 새로운 배열을 만들어서 반환 

    // 배열을 객체 형태로 변환 
    const obj =  {}; // 비어있는 객체 선언 

    for(let i=0; i<cookieList.length; i++) {
        const k = cookieList[i][0];
        const v = cookieList[i][1]; 
        obj[k] = v;   
    }

    return obj[key]; //매개변수 key와 obj 객체 key가 일치하는 요소의 value값 반환

}

//console.log(getCookie(saveId));


const loginEmail = document.querySelector("#loginForm input[name='memberEmail']"); 
//로그인 안된 상태인 경우에 수행
if(loginEmail != null) { //로그인 창에 이메일 입력 부분이 화면에 있을 때

    //쿠키 중 key 값이 saveId인 요소의 value 얻어오기 
    const saveId = getCookie("saveId"); //undefined 또는 이메일 
     
    // saveId 값이 있을 경우 
    if(saveId != undefined) {
        loginEmail.value = saveId; 

        document.querySelector("input[name='saveId']").checked = true; 
        
    }


}