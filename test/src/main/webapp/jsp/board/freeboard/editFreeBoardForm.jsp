<%@ page import="gudiSpring.freeboard.dto.BoardDto" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>게시글 수정</title>
     <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f9;
            margin: 0;
            padding: 20px;
        }
        form {
            max-width: 600px;
            margin: 0 auto;
            background-color: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        }
        label {
            display: block;
            margin-bottom: 8px;
            font-weight: bold;
        }
        input[type="text"],
        textarea {
            width: 100%;
            padding: 8px;
            margin-bottom: 16px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }
        input[type="submit"] {
            background-color: #5c6bc0;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        input[type="submit"]:hover {
            background-color: #3949ab;
        }
        a {
            display: block;
            margin-top: 20px;
            text-align: center;
            text-decoration: none;
            color: #5c6bc0;
            font-weight: bold;
        }
        a:hover {
            text-decoration: underline;
        }
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
    </script>
</head>
<body>
    <h2>게시글 수정</h2>
    <form action="<%= request.getContextPath() %>/freeboard/edit" method="post" enctype="multipart/form-data">
        <input type="hidden" name="contentNo" value="${boardDto.contentNo}">
        	
        <label for="subject">제목:</label>
        <input type="text" id="subject" name="contentSubject" value="${boardDto.contentSubject}" required>

        <label for="text">내용:</label>
        <textarea id="text" name="contentText" rows="5" required>${boardDto.contentText}</textarea>

        <label for="file">첨부 파일: .png .jpeg .jpg .gif .webp만 업로드 가능합니다</label>
        <input type="file" id="file" name="contentFile"
        accept="image/jpeg, image/jpg, image/png, image/gif, image/webp"><br>
		
		  <c:if test="${not empty boardDto.contentFile}">
            <label class="delete-file">
                <input type="checkbox" id="deleteFile" name="deleteFile" value="true"> 기존 파일 삭제
            </label>
        </c:if>
        <input type="submit" value="수정 완료">
    </form>
    <a href="<%= request.getContextPath() %>/freeboardList">목록으로 돌아가기</a>
</body>
</html>
