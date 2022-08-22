<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<form action="ctrlM.jsp" method="post">
					<c:choose>
						<c:when test="${mid != null}">
							<input type="hidden" name="action" value="logout">
							<input type="submit" value="로그아웃">
						</c:when>
						<c:otherwise>
							<input type="hidden" name="action" value="login">
							ID&nbsp;<input type="text" name="mid">&nbsp;&nbsp;PW&nbsp;<input type="password" name="mpw">&nbsp;&nbsp;
							<input type="submit" value="로그인">
						</c:otherwise>
					</c:choose>
					</form>