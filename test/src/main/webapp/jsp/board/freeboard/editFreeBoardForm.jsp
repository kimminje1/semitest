<%@ page import="gudiSpring.freeboard.dto.BoardDto" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>게시글 수정</title>
    <!-- CSS 파일 연결 -->
    <link rel="stylesheet" type="text/css" 
    href="${pageContext.request.contextPath}/css/board/freeboard/editFreeBoard.css">
   
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
          <script src="${pageContext.request.contextPath}/js/board/freeboard/editFreeBoard.js"></script>
</body>
</html>
