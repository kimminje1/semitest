<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div id="header">

  <div class="logo-container">
    <a class="logo-container--logo" href="${pageContext.request.contextPath}">DwY</a>
  </div>
 
  <div class="category-container">
    <button onclick="" class="btn">펜션</button>
    <button onclick="" class="btn">카페</button>
    <button onclick="" class="btn">식당</button>
  </div>
  
  <c:if test="${sessionScope.userDto == null}">
    <div class="auth-container">
      <button id="auth_signin" class="btn btn--auth" onclick="moveSigninPageFnc();">SignIn</button>
      <button id="auth_signup" class="btn btn--auth">SignUp</button>
    </div>
  </c:if> 
  
  <c:if test="${sessionScope.userDto.authority eq 'user'}">
    <div class="auth-container">
      <button id="user_page" class="btn btn--auth">My</button>
      <button id="auth_signout" class="btn btn--auth btn--signout">SignOut</button>
    </div>
  </c:if>

</div>