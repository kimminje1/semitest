/**
 * 
 */
// 첨부파일을 저장할 배열
let filesArray = [];
let insertedFilesArray = []; // 본문에 삽입된 이미지를 관리할 배열

// 파일 선택 시 호출되는 함수
//기존 미리보기를 유지하면서 새로운 파일을 추가합니다.
//각 파일에 대한 checkbox와 이미지 미리보기를 생성하고, 이를 DOM에 추가합니다.
document.getElementById('file').addEventListener('change', handleFileSelect);
function handleFileSelect(event) {
    const files = Array.from(event.target.files); // 선택된 파일을 배열로 변환
     console.log('Selected files:', files); // 선택된 파일 출력

    files.forEach((file) => {
		 // filesArray에 새로운 파일 추가
		 
		  const currentIndex = filesArray.length; //현재 filesArray의 길이로 인덱스 설정
        filesArray.push(file); // 새로운 파일을 filesArray에 추가
          console.log('Stored file at index', currentIndex, ':', file);
        const fileItem = document.createElement('div');
        fileItem.className = 'image-preview';

        const reader = new FileReader();
        reader.onload = function(e) {
            const img = document.createElement('img');
            img.src = e.target.result;
            img.alt = file.name;
             img.style.width = '100px';  // 미리보기 이미지의 너비를 100px로 설정
            img.style.height = '100px'; // 미리보기 이미지의 높이를 100px로 설정
            img.style.objectFit = 'cover'; // 이미지가 부모 요소의 크기에 맞게 잘리도록 설정

            const checkbox = document.createElement('input');
            checkbox.type = "checkbox"; // 여러 개 선택 가능하게 checkbox로 설정
            checkbox.name = "selectedFiles"; // checkbox의 이름 설정->삽입에씀
            checkbox.value = currentIndex;  // 파일 인덱스를 값으로 설정

            fileItem.appendChild(checkbox);
            fileItem.appendChild(img);
           document.getElementById('file-list').appendChild(fileItem); // 기존 미리보기에 새 파일 추가
        };

        reader.readAsDataURL(file);
    });

//    filesArray = files; // 선택된 파일을 배열에 저장
}


// 선택된 파일만 업로드
document.querySelector('form').addEventListener('submit', function(event) {

    // 선택된 파일의 체크박스를 가져옴
    const checkboxes = document.querySelectorAll('input[name="selectedFiles"]:checked');

    // 선택된 파일에 대해 숨겨진 input 필드를 생성하여 추가
    checkboxes.forEach(checkbox => {
        const fileIndex = parseInt(checkbox.value);

        // 본문에 이미 삽입된 이미지가 아니면 숨겨진 필드에 추가
        if (!insertedFilesArray.includes(filesArray[fileIndex])) {
            const fileInput = document.createElement('input');
            fileInput.type = 'hidden';
            fileInput.name = 'contentFile';
            fileInput.value = filesArray[fileIndex].name; 
            this.appendChild(fileInput);
   
   		}
   
    });

    // 제목과 내용을 폼에 추가 (기본 form 필드로 있음)
    const subjectElement = document.getElementById('subject');
    const contentTextElement = document.getElementById('contentText');
    const hiddenContentTextInput = document.getElementById('hiddenContentText');

    // 제목이 비어 있는지 확인
    if (!subjectElement.value.trim()) {
        alert('제목을 입력해주세요.');
        event.preventDefault(); // 폼 제출을 막음
        return;
    }

    // 내용이 비어 있는지 확인
    if (!contentTextElement.innerHTML.trim()) {
        alert('본문 내용을 입력해주세요.');
        event.preventDefault(); // 폼 제출을 막음
        return;
    }
	  
    console.log('Setting contentSubject:', subjectElement.value); // 디버깅용 로그
    console.log('Setting contentText:', contentTextElement.innerHTML); // 디버깅용 로그
       
    //내용을 hidden 필드에 설정
    hiddenContentTextInput.value = contentTextElement.innerHTML;

});




// 본문에 선택된 이미지를 삽입하는 함수1checkbox에 해당하는 이미지를 본문에 삽입하는 역할을 합니다.
function insertImageFromInput() {
    const selectedCheckboxes = document.querySelectorAll('input[name="selectedFiles"]:checked');
     console.log('Selected checkboxes:', selectedCheckboxes);  // 디버깅용 로그
    if (selectedCheckboxes.length > 0) {
        selectedCheckboxes.forEach((checkbox) => {
            const selectedIndex = checkbox.value;
             console.log('Selected index:', selectedIndex); // 선택된 인덱스 출력
            const file = filesArray[selectedIndex];
              console.log('Selected file:', file); // 선택된 파일 객체 확인

			if (file instanceof Blob) {  // File 타입인지 확인 blob이면오류나는데왜나지?
				const reader = new FileReader();
				reader.onload = function(e) {
					insertImageToContent(e.target.result);
					
					
                    // 본문에 삽입된 파일을 배열에 추가
                    insertedFilesArray.push(file);
				};
				reader.readAsDataURL(file); // 파일을 읽어서 이미지 삽입
			} else {
				console.error('Error: Selected file is not a File type.');
			}

        });
    } else {
        alert('삽입할 이미지를 선택해주세요.');
    }
}

function insertImageToContent(imageSrc) {
    const contentDiv = document.getElementById('contentText');

   // 이미지 엘리먼트 생성
    const img = document.createElement('img');
    img.src = imageSrc;
    img.alt = "본문 이미지";
    img.style.display = 'block';
    img.style.maxWidth = '600px';
    img.style.height = 'auto';
    img.style.marginBottom = '10px';

    // 새로운 단락 생성
    const p = document.createElement('p');
    p.innerHTML = '<br>';

    // 이미지와 단락을 contentDiv에 추가
    contentDiv.appendChild(img);
    contentDiv.appendChild(p);
// // 이미지 HTML 문자열 생성
//    const imgTag = `<img src="${imageSrc}" alt="본문 이미지" style="max-width: 100%; height: auto; display: block; margin-bottom: 10px;">`;
//
//    // 기존 내용에 이미지 추가
//    contentDiv.innerHTML += imgTag;
}




