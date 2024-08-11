<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>공지사항 상세보기</title>
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
            width: 60%;
            margin: 20px auto;
            border-collapse: collapse;
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
        }

        th, td {
            padding: 12px;
            border: 1px solid #ddd;
            text-align: left;
        }

        th {
            background-color: #3949ab;
            color: #fff;
            width: 30%;
        }

        td {
            background-color: #fff;
            color: #333;
        }

        .buttons {
            text-align: center;
            margin-top: 20px;
        }

        .buttons a {
            text-decoration: none;
            color: #3949ab;
            font-weight: bold;
            padding: 10px 20px;
            border: 1px solid #3949ab;
            border-radius: 4px;
            background-color: #fff;
            margin: 0 10px;
        }

        .buttons a:hover {
            background-color: #3949ab;
            color: #fff;
        }
    </style>
</head>
<body>
    <h1>공지사항 상세보기</h1>
    <table>
        <tr>
            <th>번호</th>
            <td>${notice.contentNo}</td>
        </tr>
        <tr>
            <th>제목</th>
            <td>${notice.contentSubject}</td>
        </tr>
        <tr>
            <th>내용</th>
            <td>${notice.contentText}</td>
        </tr>
        <tr>
            <th>파일</th>
            <td>${notice.contentFile}</td>
        </tr>
        <tr>
            <th>게시판 정보 번호</th>
            <td>${notice.contentBoardInfoNo}</td>
        </tr>
        <tr>
            <th>작성일</th>
            <td>${notice.contentCreDate}</td>
        </tr>
        <tr>
            <th>수정일</th>
            <td>${notice.contentUpdateDate}</td>
        </tr>
        <tr>
            <th>작성자</th>
            <td>관리자</td>
        </tr>
    </table>
    <div class="buttons">
        <a href="notices">목록으로 돌아가기</a>
        <a href="/test/freeboardList">자유게시판으로 가기</a>
    </div>
</body>
</html>
