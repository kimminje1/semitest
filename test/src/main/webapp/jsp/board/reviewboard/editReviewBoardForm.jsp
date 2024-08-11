<%@ page import="gudiSpring.reviewboard.dto.ReviewBoardDto" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>게시글 수정</title>
    <link rel="stylesheet" type="text/css" 
    href="${pageContext.request.contextPath}/css/board/reviewboard/editReviewBoard.css">
        <style>
       
    </style>
    <script>
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
        function deleteFile(fileName, contentNo) {
            if (confirm("정말로 이 파일을 삭제하시겠습니까?")) {
                fetch(`${window.location.origin}${window.location.pathname}/deleteFile`, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({
                        contentNo: contentNo,
                        fileName: fileName
                    })
                })
                .then(response => response.json())
                .then(data => {
                    if (data.success) {
                        document.getElementById(`file-${fileName}`).remove();
                    } else {
                        alert('파일 삭제에 실패했습니다.');
                    }
                })
                .catch(error => {
                    console.error('파일 삭제 중 오류가 발생했습니다.', error);
                    alert('파일 삭제 중 오류가 발생했습니다.');
                });
            }
        }
    </script>
</head>
<body>
    <h2>게시글 수정</h2>
    <form action="<%= request.getContextPath() %>/reviewboard/edit" method="post" enctype="multipart/form-data">
        <input type="hidden" name="contentNo" value="${boardDto.contentNo}">
        	
        <label for="subject">제목:</label>
        <input type="text" id="subject" name="contentSubject" value="${boardDto.contentSubject}" required>

        <label for="text">내용:</label>
        <textarea id="text" name="contentText" rows="5" required>${boardDto.contentText}</textarea>

        <label for="file">첨부 파일: .png, .jpeg, .jpg, .gif, .webp만 업로드 가능합니다</label>
        <input type="file" id="file" name="contentFile" accept="image/jpeg, image/jpg, image/png, image/gif, image/webp" multiple><br>

          <!-- 기존 파일 리스트 -->
        <c:if test="${not empty boardDto.contentFiles}">
            <div class="file-list">
                <p>기존 파일:</p>
                <c:forEach var="file" items="${boardDto.contentFiles}">
                    <div id="file-${file}" class="file-item">
                        <span>${file}</span>
                        <button type="button" onclick="deleteFile('${file}', ${boardDto.contentNo})">삭제</button>
                    </div>
                </c:forEach>
            </div>
        </c:if>

        <input type="submit" value="수정 완료">
    </form>
    <a href="<%= request.getContextPath() %>/reviewboardList">목록으로 돌아가기</a>
</body>
</html>
