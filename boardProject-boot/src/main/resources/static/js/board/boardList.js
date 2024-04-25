/* 글쓰기 버튼 클릭시 */
const insertBtn = document.querySelector("#insertBtn"); 

// 글쓰기 버튼이 존재할 때 (로그인 상태인 경우)
if(insertBtn != null) {
    insertBtn.addEventListener("click", () => {

        // get 방식 요청 (주소)
        // /editBoard/1/insert  
        location.href = `/editBoard/${boardCode}/insert`; 



    });
}; 
