<%@ page import="kr.mjc.changhee.web.dao.Article" %>
<%@ page import="java.util.List" %>
<%@ page import="kr.mjc.changhee.web.dao.User" %>
<!DOCTYPE html>
<html>
<title>기사 삭제</title>
<body>
<%
    User user = (User) session.getAttribute("USER");
%>
<p style="text-align: center;" ><b>사용자 <<%=user.getName()%>>님의 기사 목록</b></p>
<p style="text-align: center;">자세히 보려면 글 제목을 클릭하세요.</p>
<table style="margin-left: auto; margin-right: auto" border="2" cellpadding="10">
    <thead>
    <tr>
        <th>글 번호</th>
        <th>글 제목</th>
        <th>사용자 번호</th>
        <th>글쓴이</th>
    </tr>
    </thead>
    <tbody>
    <%
        List<Article> articleList = (List<Article>) request.getAttribute("getUsersArticles");
        for (Article article : articleList) {%>
    <tr>
        <td><%=article.getArticleId()%></td>
        <td align="center"><a href="getArticle?articleId=<%= article.getArticleId()%>"> <%= article.getTitle()%></a></td>
        <td align="center"><%=article.getUserId()%></td>
        <td align="center"><%=article.getName()%></td>
    </tr>
    <%
        }
    %>
    </tbody>
</table>
<br>
<p style="text-align: center;"><b>기사 삭제</b></p>
<form method="post" action="/mvc/article/deleteArticle">
    <table style="margin-left: auto; margin-right: auto">
        <tr>
            <td>기사 번호</td>
            <td><input type = text name = articleId size=10 maxlength=10 required></td>
        </tr>
        <tr>
            <td>사용자 번호</td>
            <td><input type = text name = userId size=10 maxlength=10 required value="<%=user.getUserId()%>"></td>
        </tr>
    </table>
    <div style="text-align: center;">
        <form method="post" action="userInfo">
            <input type = "submit" value="삭제하기">
        </form>
    </div>
</form>
<br>
<div style="text-align: center;">
    <form method="post" action="userInfo">
        <input type="submit" value="홈으로">
    </form>
</div>
</body>
</html>
