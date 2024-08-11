<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>새 게시글 작성</title>
<link rel="stylesheet" type="text/css" 
    href="${pageContext.request.contextPath}/css/board/reviewboard/newReviewBoard.css">
<style>
</style>
    <!-- contextPath를 JavaScript 변수로 설정 -->
<script type="text/javascript">
        var contextPath = "<%= request.getContextPath() %>";
</script>
    
</head>

 
<body>
    <h2>새 게시글 작성</h2>
 <form action="<%= request.getContextPath() %>/reviewboard/add" method="post" enctype="multipart/form-data">
        <label for="subject">제목:</label>
        <input type="text" id="subject" name="contentSubject" required>

        <label for="contentText">내용:</label>
        <textarea id="contentText" name="contentText" rows="10" style="width:100%;"></textarea>
    
        <label for="file">첨부 파일: .png .jpeg .jpg .gif .webp만 업로드 가능합니다</label>
        <input type="file" id="file" name="contentFile" accept="image/jpeg, image/jpg, image/png, image/gif, image/webp" multiple onchange="handleFileSelect(event);"><br>
        <div id="file-list"></div>
    
        
   
	 
    
    <input type="submit" value="작성 완료">
</form>
    <a href="<%= request.getContextPath() %>/reviewboardList">목록으로 돌아가기</a>
</body>


 <!-- 자바스크립트 파일 로드 -->
  <script src="<%= request.getContextPath() %>/js/board/reviewboard/newReviewBorad.js"></script>


</html>
