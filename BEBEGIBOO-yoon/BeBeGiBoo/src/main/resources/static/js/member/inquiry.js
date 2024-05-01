const memberName = document.querySelector("#memberName");
const phoneNum = document.querySelector("#phoneNum");
const email =  document.querySelector("#email");

//라디오 버튼 클릭 시 input 영역 나타나도록


// change가 일어났을 떄 event 


var checkedList = document.querySelectorAll("input[name='check']");
console.log(checkedList);
for(var i=0; i<checkedList.length; i++){
    console.log(checkedList[i]); //<input type="radio" id="phoneNum" name = "check">
    checkedList[i].addEventListener("change",e=>{
        /*
        if(checkedList[i].checked){
            console.log("checked", checkedList[i].checked);
            var targetId = checkedList[i].id + "Area";
            var targetElement = document.getElementById(targetId);
            console.log("targetElement::",targetElement);
            if(targetElement){
                targetElement.style.display='block';
            }
        }*/
    });


}


//이름, input 영역 값 넘기기POST

const findBtn = document.querySelector("#findBtn");


