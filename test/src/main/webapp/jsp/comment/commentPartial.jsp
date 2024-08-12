<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>
	<c:forEach var="comment" items="${commentList}">
    <li class="comment">
        <p><strong>${comment.commentNo}:</strong> ${comment.contentComment}</p>
        <p>${comment.commentCreDate}</p>
    </li>
</c:forEach>
	
</body>
</html>