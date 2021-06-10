package kr.mjc.changhee.web.mvc.article;

import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/mvc/article/*")
public class DispatcherServlet extends HttpServlet {

    @Autowired
    Controller Controller;


    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uri = request.getRequestURI();

        switch(uri){
            case "/mvc/article/articleList" , "/mvc/article/addForm" , "/mvc/article/updateForm", "/mvc/article/deleteForm",
                    "/mvc/article/error" -> Controller.manyPath(request, response);
            case "/mvc/article/getArticle" -> Controller.getArticle(request, response);
            case "/mvc/article/updateArticle" -> Controller.updateArticle(request, response);
            case "/mvc/article/deleteArticle" -> Controller.deleteArticle(request,response);
            case "/mvc/article/addArticle" -> Controller.addArticle(request, response);
            case "/mvc/article/userList" -> Controller.userList(request, response);
            case "/mvc/article/userForm" -> Controller.userForm(request, response);
            case "/mvc/article/loginForm" -> Controller.loginForm(request, response);
            case "/mvc/article/userInfo" -> Controller.userInfo(request, response);
            case "/mvc/article/addUser" -> Controller.addUser(request, response);
            case "/mvc/article/login" -> Controller.login(request, response);
            case "/mvc/article/getUsersArticles" -> Controller.getUsersArticles(request, response);
            case "/mvc/article/getUsersArticlesDelete" -> Controller.getUsersArticlesDelete(request, response);

        }
    }
}
