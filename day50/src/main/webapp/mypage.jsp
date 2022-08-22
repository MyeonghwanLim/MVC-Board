<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>마이페이지</title>
</head>
<body>

<script type="text/javascript">
	function del(){
			id=prompt('아이디를 입력하세요');
			pw=prompt('비밀번호를 입력하세요.')
		    ans=confirm('정말 회원 탈퇴하시겠습니까?')
			
			if(id=="${data.mid}" && pw== "${data.mpw}"){
				if(ans==true){
					document.mForm.action.value="mdelete";
					document.mForm.submit();
				}
				else{
					return;
				}
			}
		
	}
</script>

<form  name="mForm" action="controller.jsp" method="post">
 <input type="hidden" name="action" value="mupdate">

	<table border="1">
	
		<tr>
			<td>아이디</td>
			<td><input type="text" name="mid" value="${data.mid}" readonly></td>
		</tr>
		<tr>
			<td>비밀번호</td>
			<td><input type="password" name="mpw" value="${data.mpw}" required></td>
		</tr>
		<tr>
			<td>이름</td>
			<td><input type="text" name="mname" value="${data.mname}" required></td>
		</tr>
		
		<tr>
			<td colspan="2" align="right">
				<input type="submit" value="회원정보변경"><input type="button" value="회원탈퇴" onClick="del()">
			</td>
		</tr>
	</table>
</form>
<hr>
<a href="controller.jsp?action=main">메인으로 돌아가기</a>
<a href="controller.jsp?action=mdelete">회원탈퇴</a>

</body>
</html>