

<%@ page import="java.util.List" %>
<%@ page import="kr.mjc.changhee.web.dao.Article" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport", content="width=device-width, initial-scale=1">
    <title>게시판 목록</title>
</head>
<body>
<p style="text-align: center;" ><b>기사 목록</b></p>
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
        List<Article> articleList = (List<Article>) request.getAttribute("articleList");
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
<div style="text-align: center;">
    <form method="post" action="userInfo">
        <input type="submit" value="홈으로">
    </form>
</div>
</body>
</html>