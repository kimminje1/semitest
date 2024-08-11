<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="EUC-KR">
    <title>공지사항 게시판</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f4f4f9;
            margin: 0;
            padding: 20px;
            color: #333;
        }

        h1 {
            text-align: center;
            color: #5c6bc0;
        }

        table {
            width: 80%;
            margin: 20px auto;
            border-collapse: collapse;
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
        }

        th, td {
            padding: 12px;
            border: 1px solid #ddd;
            text-align: center;
        }

        th {
            background-color: #3949ab;
            color: #fff;
        }

        tr:nth-child(even) {
            background-color: #f9f9f9;
        }

        tr:hover {
            background-color: #e8eaf6;
        }

        a {
            text-decoration: none;
            color: #3949ab;
            font-weight: bold;
        }

        a:hover {
            text-decoration: underline;
        }

        .back-link {
            display: block;
            text-align: center;
            margin-top: 20px;
            font-size: 16px;
        }

        .back-link a {
            color: #3949ab;
            padding: 10px 20px;
            border: 1px solid #3949ab;
            border-radius: 4px;
            background-color: #fff;
        }

        .back-link a:hover {
            background-color: #3949ab;
            color: #fff;
        }
    </style>
</head>
<body>
    <h1>공지사항 목록</h1>
    <table>
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
                    <td><a href="noticeDetail?contentNo=${notice.contentNo}">${notice.contentSubject}</a></td>
                    <td>${notice.contentText}</td>
                    <td>${notice.contentFile}</td>
                    <td>${notice.contentCreDate}</td>
                    <td>${notice.contentUpdateDate}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <div class="back-link">
        <a href="/test/freeboardList">자유게시판으로 가기</a>
    </div>
</body>
</html>
