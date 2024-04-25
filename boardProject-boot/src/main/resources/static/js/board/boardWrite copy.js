/* 선택된 이미지 미리보기 */
const previewList = document.querySelectorAll(".preview"); // img 태그 5개
const inputImageList = document.querySelectorAll(".inputImage"); // input 태그 5개
const deleteImageList = document.querySelectorAll(".delete-image"); // x버튼 5개


// 이미지 선택 이후 취소를 누를 경우를 대비한 백업 이미지
// (백업 원리 -> 복제품으로 기존 요소를 대체함)
const backupInputList = new Array(inputImageList.length);

/* ***** input 태그 값 변경 시(파일 선택 시) 실행할 함수 ***** */
/**
 * @param inputImage : 파일이 선택된 input 태그
 * @param order : 이미지 순서
 */
const changeImageFn = (inputImage, order) => {

  const maxSize = 1024 * 1024 * 10; 
  const file = inputImage.files[0]; 

  if(file == undefined) {

    console.log("파일 선택 취소됨"); 

    const temp = backupInputList[order].cloneNode(true); 

    inputImage.after(temp); 
    inputImage.remove(); 
    inputImage = temp; 

    inputImage.addEventListener("change", e => {

      changeImageFn(e.target, order); 

    }); 

    return; 
  }





}; 