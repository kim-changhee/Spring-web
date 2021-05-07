<%@ page import="java.util.Optional" %>

<!DOCTYPE html>
<html>
<body>
<h3>로그인</h3>
<form action="login" method="post">
    <p><input type="email" name="email" placeholder="이메일" required autofocus/></p>
    <p><input type="password" name="password" placeholder="비밀번호" required/></p>
    <p>
        <button type="submit">로그인</button>
    </p>
</form>
<p style="color:red;"><%= Optional.ofNullable(request.getParameter("msg"))
        .orElse("")%>
</p>

<p>
    <a href="/mvc/article/articleList">기사 목록 보기</a>
</p>

</body>
</html>