<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>공지사항게시판</title>
</head>
<body>
	 <h1>공지사항 목록</h1>
    <table border="1">
        <thead>
            <tr>
                <th>번호</th>
                <th>제목</th>
                <th>내용</th>
                <th>파일</th>
                <th>작성일</th>
                <th>수정일</th>
                
            </tr>
        </thead>
        <tbody>
            <c:forEach var="notice" items="${notices}">
                <tr>
                    <td>${notice.contentNo}</td>
                   <td><a href="noticeDetail?contentNo=${notice.contentNo}">
                   ${notice.contentSubject}</a></td>
                    <td>${notice.contentText}</td>
                    <td>${notice.contentFile}</td>
                    <td>${notice.contentCreDate}</td>
                    <td>${notice.contentUpdateDate}</td>
                    
                </tr>
            </c:forEach>
        </tbody>
        <a href="/test/freeboardList">자유게시판으로 가기</a>
    </table>
</body>
</html>