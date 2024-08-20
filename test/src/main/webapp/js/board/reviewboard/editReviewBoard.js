/**
 * 
 */

document.addEventListener('DOMContentLoaded', function() {
    const deleteButtons = document.querySelectorAll('.delete-button');

    deleteButtons.forEach(function(button) {
        button.addEventListener('click', function(event) {
            // 기본 폼 제출 동작을 막음
            event.preventDefault();

            // 이미지 삭제 등 원하는 작업 수행
            const imgSrcToDelete = this.closest('form').querySelector('input[name="fileName"]').value;
            const contentDiv = document.getElementById('contentText');
            const imgToDelete = contentDiv.querySelector(`img[src*="${imgSrcToDelete}"]`);

            if (imgToDelete) {
                imgToDelete.remove();
                console.log(`Image with src ${imgSrcToDelete} has been removed from contentText.`);
            } else {
                console.log(`No image found with src ${imgSrcToDelete} in contentText.`);
            }

           
            this.closest('form').submit();
        });
    });
});

// 첨부파일을 저장할 배열
let filesArray = [];
let insertedFilesArray = []; // 본문에 삽입된 이미지를 관리할 배열

// 파일 선택 시 호출되는 함수
//기존 미리보기를 유지하면서 새로운 파일을 추가합니다.
//각 파일에 대한 checkbox와 이미지 미리보기를 생성하고, 이를 DOM에 추가합니다.


document.getElementById('file').addEventListener('change', handleFileSelect);

//URL.createObjectURL or filereader 중 filereader선택->작은거에좋다
function handleFileSelect(event) {
    const files = Array.from(event.target.files); // 선택된 파일을 배열로 변환
     console.log('Selected files:', files); // 선택된 파일 출력
	
	 const fileListDiv = document.getElementById('new-file-list');
    const existingFileNames = Array.from(fileListDiv.querySelectorAll('img'))
        .map(img => img.alt); // 기존 미리보기 이미지의 alt 속성에서 파일 이름 추출
	
	
    files.forEach((file) => {
		 // filesArray에 새로운 파일 추가
		 if (!existingFileNames.includes(file.name)) {
		  const currentIndex = filesArray.length; //현재 filesArray의 길이로 인덱스 설정
        filesArray.push(file); // 새로운 파일을 filesArray에 추가
          console.log('Stored file at index', currentIndex, ':', file);
        const fileItem = document.createElement('div');
        fileItem.className = 'image-preview';

        const reader = new FileReader();
        reader.onload = function(e) {
            const img = document.createElement('img');
            img.src = e.target.result; // 미리보기 이미지의 Data URL 사용
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
           document.getElementById('new-file-list').appendChild(fileItem); // 기존 미리보기에 새 파일 추가
        };
		 // FileReader를 사용하여 파일을 Data URL로 읽기
        reader.readAsDataURL(file);
      }  
    });
	
//    filesArray = files; // 선택된 파일을 배열에 저장
}

//1. Data URL을 사용하여 이미지를 본문에 삽입
//2. 서버에 파일을 업로드하고 서버 경로로 src를 업데이트
// 파일 삽입 로직을 별도의 함수로 분리 
//이친구가 실제 파일삽입
function insertSelectedFiles() {
    const checkboxes = document.querySelectorAll('input[name="selectedFiles"]:checked');

    checkboxes.forEach(checkbox => {
        const fileIndex = parseInt(checkbox.value);
        const file = filesArray[fileIndex];
        if (file instanceof File && !insertedFilesArray.includes(file)) {
            const filePath = 'img/reviewboard/' + file.name; // 서버에서 사용할 실제 경로 생성
            insertImageToContent(filePath); // 본문에 실제 경로 삽입
            insertedFilesArray.push(file); // 중복 삽입 방지
        } else {
            console.error('Error: Selected file is not a File type or already inserted.');
        }
    });
}

// 폼 제출 시 호출되는 로직
document.querySelector('form').addEventListener('submit', function(event) {
    insertSelectedFiles(); // 선택된 파일을 본문에 삽입

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

    // 내용이 사라지지 않도록 hidden 필드에 설정
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
			
			 
			 
		// 이미 삽입된 파일인지 확인하여 중복 삽입 방지
            if (file instanceof File && !insertedFilesArray.includes(file)) {
                const reader = new FileReader();
                reader.onload = function(e) {
                    insertImageToContent(e.target.result, file.name);
                };
                reader.readAsDataURL(file); //e.target.result src=dataurl이되게함

                insertedFilesArray.push(file); // 삽입된 파일을 기록하여 중복 방지
			
			} else {
				console.error('Error: Selected file is not a File type.');
			}

        });
    } else {
        alert('삽입할 이미지를 선택해주세요.');
    }
}

function insertImageToContent(previewSrc, filePath) {
    const contentDiv = document.getElementById('contentText');
	
	
   

    // 중복 삽입 방지: 이미 같은 경로의 이미지가 있는지 확인
    if ([...contentDiv.querySelectorAll('img')].some(img => img.dataset.filePath === filePath)) {
        console.warn('이미지가 이미 삽입되었습니다:', filePath);
        return;
    }
	
   // 이미지 엘리먼트 생성
    const img = document.createElement('img');
    img.src = previewSrc;
    img.alt = "본문 이미지";
  	 // previewSrc가 data URL인지 확인
   // Data URL이 아닌 경우에만 ID 설정
   
         if (!previewSrc.startsWith('data:image')) {
        const generatedId = generateRandomId();
        img.id = generatedId;  // 서버 경로 이미지에만 ID 설정
        console.log('Generated ID:', generatedId);
    }
   
    
   
    img.dataset.filePath = filePath; // 실제 서버 경로를 데이터 속성으로 저장	
    img.style.display = 'block';
    img.style.maxWidth = '800px';
    img.style.height = 'auto';
    img.style.marginBottom = '10px';

  	
    // 이미지와 단락을 contentDiv에 추가
    contentDiv.appendChild(img);
    
    
    
}
// 랜덤 ID 생성 함수
function generateRandomId() {
    return 'img-' + Math.random().toString(36).slice(2, 11);  // 'img-'로 시작하는 랜덤 ID 생성
}
  // 작성 완료 버튼 클릭 시 이미지 URL 삭제
        document.getElementById('completeButton').addEventListener('click', function() {
            const contentDiv = document.getElementById('contentText');
            const images = contentDiv.querySelectorAll('img');

            images.forEach(img => {
                if (img.src.startsWith('data:image')) {
                    img.remove(); // DOM에서 이미지 요소 제거
                }
            });

           
        });

