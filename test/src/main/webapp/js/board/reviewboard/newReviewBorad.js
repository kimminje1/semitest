/**
 * 
 */

	let filesArray = [];
    // 미리보기 유지

        function handleFileSelect(event) {
            let newFiles = Array.from(event.target.files);

            // 새로운 파일만 배열에 추가
            newFiles.forEach(function(newFile) {
                if (!filesArray.some(file => file.name === newFile.name)) {
                    filesArray.push(newFile);
                }
            });

            displaySelectedFiles();
        }

    // 선택된 파일을 본문에 삽입
    function insertImagesIntoContent() {
        var checkboxes = document.querySelectorAll('input[name="selectedFiles"]:checked');
        var content = document.getElementById('contentText');
	
        checkboxes.forEach(function(checkbox) {
            var fileName = checkbox.value;
            console.log("Selected file name:", fileName);
            var imgTag = "<<ImageDisplayed>>" + fileName + "<<ImageDisplayed>>";
            content.value += imgTag + "\n";
        });
    }

    // 파일 초기화 문제 해결



  
    // 첨부파일 미리보기
    function displaySelectedFiles() {
    const output = document.getElementById('file-list');
    output.innerHTML = ''; // 기존 미리보기 초기화

    filesArray.forEach((file) => {
        let fileItem = document.createElement('div');

        let reader = new FileReader();
        reader.onload = function(e) {
            let img = document.createElement('img');
            img.src = e.target.result;
            img.style.width = "100px";
            img.style.height = "100px";
            img.style.marginRight = "10px";

            let checkbox = document.createElement('input');
            checkbox.type = "checkbox";
            checkbox.name = "selectedFiles";
            checkbox.value = file.name;

            fileItem.appendChild(checkbox);
            fileItem.appendChild(img);
            output.appendChild(fileItem);
        };

        reader.readAsDataURL(file);
    });
}
