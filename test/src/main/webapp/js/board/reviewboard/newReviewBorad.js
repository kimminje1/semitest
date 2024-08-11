// 첨부파일을 저장할 배열
let filesArray = [];

// 파일 선택 시 호출되는 함수
function handleFileSelect(event) {
    const files = Array.from(event.target.files);
    const output = document.getElementById('file-list');
    output.innerHTML = ''; // 기존 미리보기 초기화

    files.forEach((file, index) => {
        const fileItem = document.createElement('div');
        fileItem.className = 'image-preview';

        const reader = new FileReader();
        reader.onload = function(e) {
            const img = document.createElement('img');
            img.src = e.target.result;

            const checkbox = document.createElement('input');
            checkbox.type = "checkbox";
            checkbox.name = "selectedFiles";
            checkbox.value = index;  // 파일 인덱스를 값으로 설정

            fileItem.appendChild(checkbox);
            fileItem.appendChild(img);
            output.appendChild(fileItem);
        };

        reader.readAsDataURL(file);
    });

    filesArray = files; // 선택된 파일을 배열에 저장
}

// 선택된 파일만 업로드
document.querySelector('form').addEventListener('submit', function(event) {
    const checkboxes = document.querySelectorAll('input[name="selectedFiles"]:checked');
    const formData = new FormData();

    checkboxes.forEach(checkbox => {
        const file = filesArray[checkbox.value];
        formData.append('contentFile', file);
    });

    // 제목과 내용도 추가
    formData.append('contentSubject', document.getElementById('subject').value);
    formData.append('contentText', document.getElementById('contentText').value);

    // AJAX를 통해 파일 업로드 처리
    fetch(contextPath + '/reviewboard/add', {
        method: 'POST',
        body: formData
    }).then(response => {
        if (response.ok) {
            window.location.href = contextPath + '/reviewboardList';
        } else {
            return response.text().then(text => { 
                console.log('서버 응답:', text);
                alert('파일 업로드 실패: ' + text); 
            });
        }
    }).catch(error => {
        console.error('업로드 중 오류 발생:', error);
        alert('파일 업로드 중 오류가 발생했습니다: ' + error.message);
    });

    event.preventDefault(); // 기본 폼 제출 방지
});
