
/* ************* TERM ************** */

const checkboxes = document.querySelectorAll('.check');
const checkAll = document.querySelector("#checkAll"); 
const check1 = document.querySelector("#check1"); 
const check2 = document.querySelector("#check2"); 
const agreeBtn = document.querySelector("#agreeBtn"); 


checkAll.addEventListener('change', e => {
    
    let checkAll = e.target; 

    if (checkAll.checked) {
        // 모든 체크박스를 선택함
        checkboxes.forEach(function(checkbox) {
            checkbox.checked = true;
        });
    } else {
        // checkAll 체크박스가 체크 해제되었을 때, 모든 체크박스를 선택 해제함
        checkboxes.forEach(function(checkbox) {
            checkbox.checked = false;
        });
    }
});


agreeBtn.addEventListener('click', e => {

    e.preventDefault(); 

    // check1과 check2가 모두 선택되었는지 확인
    if (check1.checked && check2.checked) {
        // 다음 페이지로 넘어감
        location.href = "/member/signup/signupForm"; 
        
    } else {
        // 두 체크박스 중 하나라도 선택되지 않았을 경우 경고 메시지 출력
        alert("이용약관 및 개인정보 수집 및 이용에 모두 동의해주세요.");

    }
});