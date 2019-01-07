<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %><!-- 导入springmvc的form标签库 -->
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-3.2.1.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/paging.js"></script>
</head>
<script type="text/javascript">
</script>
<body style="background-color:#FFEBCD;">
	<form:form method="POST" id="forms" action="${pageContext.request.contextPath }/user/user" modelAttribute="user" >
		<div align="center">
			<form:hidden path="id"/>
			
			<!-- 显示给用户的数据 -->
			<p>名字:<form:input path="name" placeholder="请输入用户名"/><p/>
			<p>性别:<form:radiobuttons path="sex" items="${sexs}"/><p/>
			<p>地址:<form:input path="addr" placeholder="请输入地址"/><p/>
			<p>生日:<form:input path="birthday" placeholder="请输出生日期" type="date" /><p/>
			<p>邮箱:<form:input path="email" placeholder="请输入邮箱"/><p/>
			<c:choose>
				<c:when test="${user.id != null && user.id != '' }">
					<input type="hidden" name="_method" value="PUT">
					
					<!-- 保存后需要回显到查询的数据 -->
					<input id="pageNum" name="pageNum" type="hidden" value="${page.pageNum }"/>
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize }"/>
					<input id="order" name="order" type="hidden" value="${page.t.order }"/>
					<input id="userName" name="user.name" type="hidden" value="${page.t.user.name }">
					<input id="startBirthday" name="startBirthday" type="hidden" value="${page.t.startBirthday }">
					<input id="endBirthday" name="endBirthday" type="hidden" value="${page.t.endBirthday }">
					<input id="userAddr" name="user.addr" type="hidden" value="${page.t.user.addr }">
				</c:when>
				<c:otherwise>
					<p>密码:<form:input path="password" placeholder="密码" /><p/>
				</c:otherwise>
			</c:choose>
			<button id="submits" type="submit">立即提交</button>
			<button type="reset" id="resets">重置</button>
			<button type="button" onClick="javascript :history.back(-1);">返回</button>
		</div>
	</form:form>
</body>
</html>