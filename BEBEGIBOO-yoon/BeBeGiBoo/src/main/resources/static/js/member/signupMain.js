/* *************MAIN************** */

/* 체크박스 하나만 선택하기 */

const checkboxes = document.querySelectorAll('.selectOne');

checkboxes.forEach(checkbox => {
    checkbox.addEventListener('change', function() {
        checkboxes.forEach(cb => {
            if (cb !== this) {
                cb.checked = false;
            }
        });
    });
}); 


/* 제출하기 버튼 클릭시 이동 */ 
const signUpBtn = document.getElementById('signUpBtn');
const donatorCheckbox = document.getElementById('donator');
const acceptorCheckbox = document.getElementById('acceptor');

document.getElementById('submitBtn').addEventListener('click', e => {

    e.preventDefault(); 

    // 기부자/피기부자 선택시 이동 
    if (donatorCheckbox.checked || acceptorCheckbox.checked) {

        location.href = "/member/signup/signupTerm";
    
    } else {
        alert('기부자 또는 피기부자 중 최소 한 가지를 선택해야 합니다.');
    }
   
});