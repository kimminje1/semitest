<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Event Add</title>
<link
  href="
  https://cdn.jsdelivr.net/npm/reset-css@5.0.2/reset.min.css"
  rel="stylesheet">
<link rel="stylesheet"
  href="${pageContext.request.contextPath}/css/common/common.css" />
<link rel="stylesheet"
  href="${pageContext.request.contextPath}/css/common/admin.css" />
<link rel="stylesheet"
  href="${pageContext.request.contextPath}/css/admin/nav.css" />
<link rel="stylesheet"
  href="${pageContext.request.contextPath}/css/admin/event/addEvent.css" />
<script defer type="text/javascript"
  src="${pageContext.request.contextPath}/js/movePage/movePageFncs.js"></script>
</head>
<body>

  <c:if test="${sessionScope.adminDto == null}">
    <c:redirect url="/admin/error" />
  </c:if>

  <jsp:include page="../nav.jsp"></jsp:include>

  <div id="main-container">

    <div class="main-container__content">
  
        <!-- event form -->
        <div class="event-form">
          <form action="./add" method="post">
  
            <div class="event-form__content">
              <label class="text--black text--bold width--lg">이벤트 명</label> 
              <input name="eventName" class="text--black width--lg" />
            </div>
  
            <!-- TODO: 시분초 추가할 지 고민 -->
            <div class="event-form__content">
              <label class="text--black text--bold width--sm">시작일</label> 
              <input type="date" name="openDate" class="text--black width--sm" />
            </div>
  
            <div class="event-form__content">
              <label class="text--black text--bold width--sm">종료일</label> 
              <input type="date" name="closeDate"  class="text--black width--sm" />
            </div>
  
            <!-- submit button -->
            <input type="submit" value="추가" class="btn btn--submit" />
          </form>
          
        </div><!-- event-form -->
  
      </div>
      <!-- main-container__content -->
  </div>
  <!-- main-container -->

</body>
</html>