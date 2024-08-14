/**
 * 
 */
function validateFileInput() {
	const fileInput = document.getElementById('file');
	const filePath = fileInput.value;
	const allowedExtensions = /(\.jpg|\.jpeg|\.png|\.gif|\.webp)$/i;

	if (!allowedExtensions.exec(filePath)) {
		alert('이미지 파일만 업로드 가능합니다. (jpg, jpeg, png, gif, webp)');
		fileInput.value = '';
		return false;
	}
	return true;
}
//지우기
function deleteFile(fileName, contentNo) {
    if (confirm("정말로 이 파일을 삭제하시겠습니까?")) {
        // AJAX를 사용하지 않고 페이지 리다이렉트로 처리
        const requestUrl = `${window.location.origin}/test/board/reviewboard/deleteFile?contentNo=${contentNo}&fileName=${encodeURIComponent(fileName)}`;
        window.location.href = requestUrl;
    }
}

///미리보기용
// 첨부파일을 저장할 배열
let filesArray = [];


// 파일 선택 시 호출되는 함수
function handleFileSelect(event) {
	const files = Array.from(event.target.files); // 선택된 파일을 배열로 변환
	console.log('Selected files:', files); // 선택된 파일 출력

	files.forEach((file, index) => {
		const currentIndex = filesArray.length; //현재 filesArray의 길이로 인덱스 설정
		filesArray.push(file); // 새로운 파일을 filesArray에 추가
		console.log('Stored file at index', currentIndex, ':', file); // 저장된 파일과 인덱스 출력
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
			checkbox.name = "selectedFiles"; // checkbox의 이름 설정
			checkbox.value = currentIndex;  // 파일 인덱스를 값으로 설정

			fileItem.appendChild(checkbox);
			fileItem.appendChild(img);
			document.getElementById('file-list').appendChild(fileItem); // 기존 미리보기에 새 파일 추가
		};

		reader.readAsDataURL(file);
	});
}

// 본문에 선택된 이미지를 삽입하는 함수
function insertImageFromInput() {
	const selectedCheckboxes = document.querySelectorAll('input[name="selectedFiles"]:checked');
	console.log('Selected checkboxes:', selectedCheckboxes);  // 디버깅용 로그

	if (selectedCheckboxes.length > 0) {
		selectedCheckboxes.forEach((checkbox) => {
			const selectedIndex = checkbox.value;
			console.log('Selected index:', selectedIndex); // 선택된 인덱스 출력
			const file = filesArray[selectedIndex];
			console.log('Selected file:', file); // 선택된 파일 객체 확인

			if (file instanceof File) {
				const reader = new FileReader();
				reader.onload = function(e) {
					insertImageToContent(e.target.result);
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
// 본문 내용 업데이트
document.getElementById('editForm').addEventListener('submit', function(event) {
    // 본문 내용을 hidden input에 복사
    const contentTextDiv = document.getElementById('contentText');
    const hiddenInput = document.createElement('input');
    hiddenInput.type = 'hidden';
    hiddenInput.name = 'contentText';
    hiddenInput.value = contentTextDiv ? contentTextDiv.innerHTML : ''; // contentTextDiv가 null이 아닐 경우에만 값을 설정
    this.appendChild(hiddenInput);

    // 선택된 파일들을 폼에 추가
    const checkboxes = document.querySelectorAll('input[name="selectedFiles"]:checked');
    checkboxes.forEach(checkbox => {
        const file = filesArray[checkbox.value];
        const fileInput = document.createElement('input');
        fileInput.type = 'hidden';
        fileInput.name = 'contentFile';
        fileInput.files = [file];
        this.appendChild(fileInput);
    });

});
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
}