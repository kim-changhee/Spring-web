<%@ page import="kr.mjc.changhee.web.dao.User" %>
<%@ page import="kr.mjc.changhee.web.dao.Article" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Optional" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport", content="width=device-width, initial-scale=1">
    <title>글 수정하기</title>
</head>
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
<form method = "post" action = "/mvc/article/updateArticle">
    <table  style="padding-top:50px" align = center width=700 border=0 cellpadding=2 >
        <tr>
            <td height=20 align= center bgcolor=#ccc>글 수정하기</td>
        </tr>
        <tr>
            <td bgcolor=white>
                <table>
                    <tr>
                        <td>제목</td>
                        <td><input type = text name = title size=60 required></td>
                    </tr>
                    <tr>
                        <td>사용자 번호</td>
                        <td><input type = text name = userId size=10 maxlength=10 required value="<%=user.getUserId()%>"></td>
                    </tr>
                    <tr>
                        <td>글 번호</td>
                        <td><input type = text name = articleId size=10 maxlength=10 required></td>
                    </tr>

                    <tr>
                        <td>내용</td>
                        <td><textarea name = content cols=85 rows=15 required></textarea></td>
                    </tr>
                </table>
                <div style="text-align: center;">
                    <form method="post" action="userInfo">
                        <input type = "submit" value="수정">
                    </form>
                </div>
            </td>
        </tr>
    </table>
</form>
<br>

<br>
<div style="text-align: center;">
    <form method="post" action="userInfo">
        <input type="submit" value="홈으로">
    </form>
</div>
<p style="color:#ff0000;"><%= Optional.ofNullable(request.getParameter("msg"))
        .orElse("")%>
</body>
</html>