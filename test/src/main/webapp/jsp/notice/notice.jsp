<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>�������װԽ���</title>
</head>
<body>
	 <h1>�������� ���</h1>
    <table border="1">
        <thead>
            <tr>
                <th>��ȣ</th>
                <th>����</th>
                <th>����</th>
                <th>����</th>
                <th>�ۼ���</th>
                <th>������</th>
                
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
        <a href="/test/freeboardList">�����Խ������� ����</a>
    </table>
</body>
</html>