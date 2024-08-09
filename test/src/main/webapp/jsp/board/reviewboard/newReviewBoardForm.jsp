<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>새 게시글 작성</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
            background-color: #f9f9f9;
        }
        h2 {
            color: #333;
        }
        form {
            background-color: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            max-width: 600px;
            margin: auto;
        }
        label {
            display: block;
            margin-bottom: 8px;
            font-weight: bold;
        }
        input[type="text"], textarea, input[type="file"] {
            width: 100%;
            padding: 8px;
            margin-bottom: 12px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }
        input[type="submit"] {
            background-color: #4CAF50;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        input[type="submit"]:hover {
            background-color: #45a049;
        }
        a {
            display: inline-block;
            margin-top: 20px;
            text-decoration: none;
            color: #4CAF50;
        }
        a:hover {
            text-decoration: underline;
       
        }
        .image-preview {
    display: inline-block;
    margin-right: 10px;
    position: relative;
}

.image-preview img {
    width: 100px;  /* 이미지 미리보기 크기를 100px로 설정 */
    height: 100px; /* 이미지 미리보기 크기를 100px로 설정 */
    object-fit: cover; /* 이미지의 크기에 맞춰 자르기 */
}

.image-preview input[type="checkbox"] {
    position: absolute;  /* 체크박스를 이미지 위에 위치시키기 */
    top: 5px;  /* 체크박스 위치 (상단 여백) */
    left: 5px; /* 체크박스 위치 (좌측 여백) */
    transform: scale(1.5);  /* 체크박스를 크게 보이도록 크기 조정 */
}
 #preview {
            margin-top: 20px;
        }
        #editor {
            border: 1px solid #ccc;
            padding: 10px;
            height: 300px;
            overflow-y: scroll;
        }
    </style>
</head>
 
<body>
    <h2>새 게시글 작성</h2>

<form action="<%= request.getContextPath() %>/reviewboard/add" method="post" 
enctype="multipart/form-data"  >
    <label for="subject">제목:</label>
    <input type="text" id="subject" name="contentSubject" required>

    <label for="text">내용:</label>
        <textarea id="contentText" name="contentText" rows="10" style="width:100%;">
        </textarea>
    
    <label for="file">첨부 파일: .png .jpeg .jpg .gif .webp만 업로드 가능합니다</label>
    <button type="button" onclick="insertImagesIntoContent()">선택된 이미지 본문에 삽입</button>
    <input type="file" id="file" name="contentFile" 
    accept="image/jpeg, image/jpg, image/png, image/gif, image/webp" multiple
    onchange="handleFileSelect(event);"	 ><br>
	  <div id="file-list"></div>
	 
    
    <input type="submit" value="작성 완료">
</form>
    <a href="<%= request.getContextPath() %>/reviewboardList">목록으로 돌아가기</a>
</body>
 <!-- 자바스크립트 파일 로드 -->
  <script src="<%= request.getContextPath() %>/js/board/reviewboard/newReviewBorad.js"></script>


</html>
