<%@ page import="kr.mjc.changhee.web.dao.Article" %>
<%@ page import="kr.mjc.changhee.web.dao.User" %>

<!DOCTYPE html>
<html>
<body>
<h3>기사 정보</h3>
<%
    Article article = (Article) request.getAttribute("getArticle");
%>
<table border="2" cellpadding="10">
    <thead>
    <tr>
        <th>글 번호</th>
        <th>글 내용</th>
        <th>사용자 번호</th>
        <th>사용자 이름</th>
        <th>처음 업로드 날짜</th>
        <th>업데이트 날짜</th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td><%=article.getArticleId()%></td>
        <td align="center"><%=article.getContent()%></td>
        <td align="center"><%=article.getUserId()%></td>
        <td align="center"><%=article.getName()%></td>
        <td align="center"><%=article.getCdate()%></td>
        <td align="center"><%=article.getUdate()%></td>
    </tr>
    </tbody>
</table>
<input type="button" value="뒤로가기" onClick="history.go(-1)">
</body>
</html>
