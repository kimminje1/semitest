<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>새 게시글 작성</title>
        <link rel="stylesheet" type="text/css" 
    href="${pageContext.request.contextPath}/css/board/freeboard/newFreeBoard.css">
    <style>
      
    </style>
</head>
<body>
    <h2>새 게시글 작성</h2>

<form action="<%= request.getContextPath() %>/freeboard/add" method="post" 
enctype="multipart/form-data"  >
    <label for="subject">제목:</label>
    <input type="text" id="subject" name="contentSubject" required>

    <label for="text">내용:</label>
    <textarea id="text" name="contentText" rows="5" required></textarea>

    <label for="file">첨부 파일: .png .jpeg .jpg .gif .webp만 업로드 가능합니다</label>
    <input type="file" id="file" name="contentFile"
    accept="image/jpeg, image/jpg, image/png, image/gif, image/webp" ><br>

    <input type="submit" value="작성 완료">
</form>
    <a href="<%= request.getContextPath() %>/freeboardList">목록으로 돌아가기</a>
    <script src="${pageContext.request.contextPath}/js/board/freeboard/newFreeBoard.js"></script>
</body>
</html>
