<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>게시판 목록</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f9f9f9;
            margin: 0;
            padding: 20px;
        }
        h1 {
            text-align: center;
            color: #333;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
        }
        th, td {
            padding: 12px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        th {
            background-color: #4CAF50;
            color: white;
        }
        tr:nth-child(even) {
            background-color: #f2f2f2;
        }
        a {
            color: #4CAF50;
            text-decoration: none;
        }
        a:hover {
            text-decoration: underline;
        }
        button {
            background-color: #4CAF50;
            color: white;
            padding: 10px 20px;
            margin: 5px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        button:hover {
            background-color: #45a049;
        }
        .button-container {
            text-align: center;
            margin-top: 20px;
        }
    </style>
    <script type="text/javascript">
        function goToNotice() {
            window.location.href = '/test/notices'; // notice 임시
        }
    </script>
</head>
<body>
    <h1>게시판 목록</h1>
    <table>
        <thead>
            <tr>
                <th>번호</th>
                <th>제목</th>
                <th>내용</th>
                <th>첨부파일</th>
                <th>게시판 정보 번호</th>
                <th>작성일</th>
                <th>수정일</th>
                <th>사용자 번호</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="board" items="${boardList}">
                <tr>
                    <td><c:out value="${board.contentNo}" /></td>
                    <td><a href="/test/freeboard/Detail?contentNo=${board.contentNo}">
                        <c:out value="${board.contentSubject}" /></a>
                    </td>
                    <td><c:out value="${board.contentText}" /></td>
                    <td><c:out value="${board.contentFile}" /></td>
                    <td><c:out value="${board.contentBoardInfoNo}" /></td>
                    <td><c:out value="${board.contentCreDate}" /></td>
                    <td><c:out value="${board.contentUpdateDate}" /></td>
                    <td><c:out value="${board.userNo}" /></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <div class="button-container">
        <button onclick="goToNotice()">임시공지사항</button>
        <button onclick="location.href='<%=request.getContextPath()%>/freeboard/add'">새 글 작성</button>
    </div>
</body>
</html>
