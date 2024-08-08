<%@ page import="gudiSpring.freeboard.dto.BoardDto" %>
<%@ page import="gudiSpring.comment.dto.CommentDto" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>게시글 상세 조회</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f9;
            margin: 0;
            padding: 20px;
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
            background-color: #5c6bc0;
            color: white;
        }
        ul.comments {
            list-style-type: none;
            padding: 0;
        }
        li.comment {
            margin-top: 10px;
            padding: 10px;
            background-color: #e8eaf6;
            border-radius: 5px;
        }
        button {
            background-color: #5c6bc0;
            color: white;
            padding: 6px 12px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            margin-right: 5px;
        }
        button:hover {
            background-color: #3949ab;
        }
        .edit-form {
            display: none;
            margin-top: 5px;
        }
        .edit-form textarea {
            width: 100%;
            box-sizing: border-box;
        }
        .back-link {
            display: inline-block;
            margin-top: 20px;
            text-decoration: none;
            color: #5c6bc0;
            font-weight: bold;
        }
        .back-link:hover {
            text-decoration: underline;
        }
    </style>
    <script>
        function confirmDelete(commentNo, contentNo) {
            if (confirm("정말로 이 댓글을 삭제하시겠습니까?")) {
                window.location.href =
                    '<%= request.getContextPath() %>/deleteComment?commentNo=' + commentNo + '&contentNo=' + contentNo;
            }
        }
        function validateForm(form) {
            var content = form.commentContent.value.trim();
            if (content === '') {
                alert('댓글 내용을 입력하세요.');
                return false;
            }
            return true;
        }
        function openEditForm(commentNo) {
            var form = document.getElementById('editForm-' + commentNo);
            form.style.display = form.style.display === 'none' ? 'block' : 'none';
        }
        function confirmDeletePost(contentNo) {
            if (confirm("정말로 이 게시글을 삭제하시겠습니까?")) {
                window.location.href = '<%= request.getContextPath() %>/deletePost?contentNo=' + contentNo;
            }
        }
    </script>
</head>
<body>
    <h2>게시글 상세 조회</h2>
    <!-- 게시글 정보 출력 -->
    <table>
        <%
            BoardDto boardDto = (BoardDto) request.getAttribute("boardDto");
            if (boardDto != null) {
        %>
        <tr>
            <th>번호</th>
            <td><%= boardDto.getContentNo() %></td>
        </tr>
        <tr>
            <th>제목</th>
            <td><%= boardDto.getContentSubject() %> </td>
        </tr>
		<tr>
            <th>내용</th>
              <td>
              <%= boardDto.getContentText()%>
              <c:if test="${not empty boardDto.contentFile}">
      <img src="${pageContext.request.contextPath}/image/freeboard/${fn:replace(boardDto.contentFile, '/test/images/', '')}" alt="Attached Image3" />
			</c:if>
            </td>
   
          
        </tr>
        <tr>
            <th>파일</th>
            <td>
               <%= boardDto.getContentFile() %>
            </td>
        </tr>

		<tr>
            <th>작성일</th>
            <td><%=boardDto.getContentCreDate()%></td>
        </tr>
        <tr>
            <th>수정일</th>
            <td><%= boardDto.getContentUpdateDate() %></td>
        </tr>
        <tr>
            <th>작성자</th>
            <td><%= boardDto.getUserNo() %></td>
        </tr>
    </table>
    <!-- 게시글 수정 버튼 추가 -->
    <button onclick="location.href='<%= request.getContextPath() %>/freeboard/edit?contentNo=<%= boardDto.getContentNo() %>'">수정</button>
    <!-- 게시글 삭제 버튼 추가 -->
    <button onclick="confirmDeletePost(<%= boardDto.getContentNo() %>)">게시글 삭제</button>
    <h3>댓글 달기</h3>
    <form action="<%=request.getContextPath()%>/addComment" method="post" onsubmit="return validateForm(this);">
        <input type="hidden" name="contentNo" value="<%=boardDto.getContentNo()%>">
        <textarea name="commentContent" rows="4" cols="50" placeholder="댓글을 입력하세요"></textarea>
        <br>
        <input type="submit" value="댓글 추가">
    </form>
    <h3>댓글</h3>
    <ul class="comments">
        <c:forEach var="comment" items="${commentList}">
            <li class="comment">
                <p><strong>댓글 번호:</strong> <c:out value="${comment.commentNo}"/></p>
                <p><strong>내용:</strong> <c:out value="${comment.contentComment}"/></p>
                <p><strong>작성일:</strong> <c:out value="${comment.commentCreDate}"/></p>
                <button onclick="confirmDelete(${comment.commentNo}, <%= boardDto.getContentNo() %>)">삭제</button>
                <button onclick="openEditForm(${comment.commentNo})">수정</button>
                <!-- 수정 폼 -->
                <div id="editForm-${comment.commentNo}" class="edit-form">
                    <form action="<%=request.getContextPath()%>/editComment" method="post" onsubmit="return validateForm(this);">
                        <input type="hidden" name="commentNo" value="${comment.commentNo}">
                        <input type="hidden" name="contentNo" value="<%=boardDto.getContentNo()%>">
                        <textarea name="commentContent" rows="3">${comment.contentComment}</textarea>
                        <input type="submit" value="수정 완료">
                    </form>
                </div>
            </li>
        </c:forEach>
    </ul>
    <% } %>
    <a href="<%=request.getContextPath() %>/freeboardList" class="back-link">목록으로 돌아가기</a>
</body>
</html>
