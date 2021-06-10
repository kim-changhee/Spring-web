<%@ page import="kr.mjc.changhee.web.dao.User" %>
<%
    try{
%>
<!DOCTYPE html>
<html>
<body>
<p style="text-align: center;"><b>사용자 정보</b></p>
<%
    User user = (User) session.getAttribute("USER");
%>
<table style="margin-left: auto; margin-right: auto" border="1" cellpadding="10">
    <thead>
    <tr>
        <th>사용자 번호</th>
        <th>이메일</th>
        <th>이름</th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td><%=user.getUserId()%></td>
        <td><%=user.getEmail()%></td>
        <td><%=user.getName()%></td>
    </tr>
    </tbody>
</table>
<p style="text-align: center;">
    <a href="/mvc/article/articleList">글 목록 이동</a>
</p>

<p style="text-align: center;">
    <a href="/mvc/article/addForm">글 쓰기</a>
</p>

<p style="text-align: center;">
    <a href="getUsersArticles?userId=<%=user.getUserId()%>">글 수정</a>
</p>

<p style="text-align: center;">
    <a href="getUsersArticlesDelete?userId=<%=user.getUserId()%>">글 삭제</a>
</p>

<br>
<div style="text-align: center;">
    <form method="post" action="loginForm">
        <input type="submit" value="로그아웃">
    </form>
</div>
<%
    } catch (NullPointerException e){
        response.sendRedirect(request.getContextPath() + "/mvc/article/error");
    }
%>
</body>
</html>