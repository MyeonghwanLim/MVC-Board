<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.ArrayList, member.MemberVO"%>
<jsp:useBean id="mDAO" class="member.MemberDAO" />
<jsp:useBean id="mVO" class="member.MemberVO" />
<jsp:setProperty property="*" name="mVO" />
<%
	String action=request.getParameter("action");

	if(action.equals("insert")){
		if(mDAO.insert(mVO)){
			out.println("<script>alert('회원가입 완료!');opener.parent.location.reload();window.close();</script>");
		}
		else{
			out.println("<script>alert('회원가입 실패!');history.go(-1);</script>");
		}
	}
	else if(action.equals("login")){
		mVO=mDAO.selectOne(mVO);
		if(mVO!=null){
			session.setAttribute("mid", mVO.getMid());
			response.sendRedirect("ctrlB.jsp?action=main");
		}
		else{
			out.println("<script>alert('로그인 실패!');history.go(-1);</script>");
		}
	}
	else if(action.equals("logout")){
		session.invalidate();
		response.sendRedirect("ctrlB.jsp?action=main");
	}
	else if(action.equals("mypage")){ 
		MemberVO member=(MemberVO)session.getAttribute("member");
	
		if(member!=null){
			request.setAttribute("data", member);
			pageContext.forward("mypage.jsp");
		}
		else{
			throw new Exception("mypage 오류");
		}
	}
%>