<%@ page import="kr.mjc.changhee.web.dao.User" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>글 쓰기</title>
</head>
<body>
<%
    User user = (User) session.getAttribute("USER");
%>
<form method = "post" action = "addArticle">
    <table  style="padding-top:50px" align = center width=700 border=0 cellpadding=2 >
        <tr>
            <td height=20 align= center bgcolor=#ccc>글쓰기</td>
        </tr>
        <tr>
            <td bgcolor=white>
                <table>
                    <tr>
                        <td>작성자</td>
                        <td><input type = text name = name size=20 required></td>
                    </tr>
                    <tr>
                        <td>사용자 번호</td>
                        <td><input type = text name = userId size=10 maxlength=10 required value="<%=user.getUserId()%>"></td>
                    </tr>
                    <tr>
                        <td>제목</td>
                        <td><input type = text name = title size=60 required></td>
                    </tr>
                    <tr>
                        <td>내용</td>
                        <td><textarea name = content cols=85 rows=15 required></textarea></td>
                    </tr>
                </table>
                <div style="text-align: center;">
                    <form method="post" action="userInfo">
                        <input type = "submit" value="작성">
                    </form>
                </div>
            </td>
        </tr>
    </table>
</form>
<br>
<div style="text-align: center;">
    <form method="post" action="userInfo">
        <input type="submit" value="홈으로">
    </form>
</div>
</body>
</html>