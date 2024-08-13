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
        fetch(`${window.location.origin}/board/reviewboard/deleteFile`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: new URLSearchParams({
                contentNo: contentNo,
                fileName: fileName
            })
        })
        .then(response => response.text())
        .then(data => {
            if (data === "파일 삭제 성공") {
                document.getElementById(`file-${fileName}`).remove(); // UI에서 파일 요소 제거
                alert("파일이 성공적으로 삭제되었습니다.");
            } else {
                alert("파일 삭제 실패: " + data);
            }
        })
        .catch(error => {
            console.error('파일 삭제 중 오류가 발생했습니다.', error);
            alert('파일 삭제 중 오류가 발생했습니다.');
        });
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
document.querySelector('form').addEventListener('submit', function(event) {
	const contentTextDiv = document.getElementById('contentText');
	const hiddenInput = document.createElement('input');
	hiddenInput.type = 'hidden';
	hiddenInput.name = 'contentText';
	hiddenInput.value = contentTextDiv.innerHTML; // HTML 내용을 숨겨진 필드로 복사

	this.appendChild(hiddenInput);

	// 기존 파일 업로드 로직
	const checkboxes = document.querySelectorAll('input[name="selectedFiles"]:checked');
	const formData = new FormData(this);

	checkboxes.forEach(checkbox => {
		const file = filesArray[checkbox.value];
		formData.append('contentFile', file);
	});

	// formData 내용을 확인하기 위한 디버그 로그
	for (let pair of formData.entries()) {
		console.log(pair[0] + ', ' + pair[1]);
	}

	// AJAX를 통해 파일 업로드 처리
	fetch(contextPath + '/reviewboard/add', {
		method: 'POST',
		body: formData
	}).then(response => {
		if (response.ok) {
			window.location.href = contextPath + '/board/reviewboard/list';
		} else {
			alert('파일 업로드 실패');
		}
	});

	event.preventDefault(); //  폼의 기본 제출 동작을 방지
});

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