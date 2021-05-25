<%@ page import="kr.mjc.changhee.web.dao.User" %>
<nav><span style="font-weight: bold;">[Spring MVC v2]</span> <a href="./">홈
</a> <a href="./springmvc/v2/user/userList">사용자</a>
    <% User sessionUser = (User) session.getAttribute("USER");
        if (sessionUser != null) { // 로그인을 했으면 %>
    <a href="./springmvc/v2/user/userInfo"><%= sessionUser.getName()%>
    </a>님 <a href="./springmvc/v2/user/logout">로그아웃</a>
    <% } else { // 로그인 안했으면 %>
    <a href="./springmvc/v2/user/loginForm">로그인</a> <a
            href="./springmvc/v2/user/userForm">회원가입</a>
    <%}%>
</nav>