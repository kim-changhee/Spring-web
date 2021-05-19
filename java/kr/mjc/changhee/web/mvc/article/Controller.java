package kr.mjc.changhee.web.mvc.article;

import kr.mjc.changhee.web.dao.Article;
import kr.mjc.changhee.web.dao.ArticleDao;
import kr.mjc.changhee.web.dao.User;
import kr.mjc.changhee.web.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLDataException;
import java.sql.SQLException;

@Component
public class Controller {
    private final UserDao userDao;
    private final ArticleDao articleDao;

    @Autowired
    public Controller(ArticleDao articleDao, UserDao userDao){
        this.articleDao = articleDao;
        this.userDao = userDao;
    }

    Article article = new Article();

    public void userList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("userList", userDao.listUsers(0, 100));

        request.getRequestDispatcher("/WEB-INF/jsp/mvc/article/userList.jsp")
                .forward(request, response);
    }

    public void userForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/WEB-INF/jsp/mvc/article/userForm.jsp")
                .forward(request, response);
    }

    public void loginForm(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/WEB-INF/jsp/mvc/article/loginForm.jsp")
                .forward(request, response);
    }

    public void userInfo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/WEB-INF/jsp/mvc/article/userInfo.jsp")
                .forward(request, response);
    }

    public void addUser(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        User user = new User();
        user.setEmail(request.getParameter("email"));
        user.setPassword(request.getParameter("password"));
        user.setName(request.getParameter("name"));

        try {
            userDao.addUser(user);
            response.sendRedirect(request.getContextPath() + "/mvc/article/userList");
        } catch (DuplicateKeyException e) {
            response.sendRedirect(request.getContextPath() +
                    "/mvc/article/userForm?msg=Duplicate email");
        }
    }

    public void login(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            User user = userDao.login(email, password);
            HttpSession session = request.getSession();
            session.setAttribute("USER", user);
            response.sendRedirect(request.getContextPath() + "/mvc/article/userInfo");
        } catch (EmptyResultDataAccessException e) {
            response.sendRedirect(request.getContextPath() +
                    "/mvc/article/loginForm?msg=Wrong email or password");
        }
    }

    public void manyPath(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        request.setAttribute("articleList", articleDao.listArticles(0 ,20));
        if(request.getRequestURI().equals("/mvc/article/articleList")) {        // 리스트 가져오기
            request.getRequestDispatcher("/WEB-INF/jsp/mvc/article/articleList.jsp")
                    .forward(request, response);
        }
        if(request.getRequestURI().equals("/mvc/article/addForm")){             // 글 추가
            request.getRequestDispatcher("/WEB-INF/jsp/mvc/article/addForm.jsp")
                    .forward(request, response);
        }
        if(request.getRequestURI().equals("/mvc/article/updateForm")){          // 글 수정
            request.getRequestDispatcher("/WEB-INF/jsp/mvc/article/updateForm.jsp")
                    .forward(request, response);
        }
        if(request.getRequestURI().equals("/mvc/article/deleteForm")){          // 글 삭제
            request.getRequestDispatcher("/WEB-INF/jsp/mvc/article/deleteForm.jsp")
                    .forward(request, response);
        }
        if(request.getRequestURI().equals("/mvc/article/error")){          // 로그아웃
            request.getRequestDispatcher("/WEB-INF/jsp/mvc/article/error.jsp")
                    .forward(request, response);
        }
    }

    public void getUsersArticles(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        request.setAttribute("getUsersArticles", articleDao.getUsersArticles(Integer.parseInt(request.getParameter("userId"))));

        request.getRequestDispatcher("/WEB-INF/jsp/mvc/article/updateForm.jsp")
                .forward(request, response);
    }

    public void getUsersArticlesDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("getUsersArticles", articleDao.getUsersArticles(Integer.parseInt(request.getParameter("userId"))));

        request.getRequestDispatcher("/WEB-INF/jsp/mvc/article/deleteForm.jsp")
                .forward(request, response);
    }

    public void getArticle(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        request.setAttribute("getArticle", articleDao.getArticle(Integer.parseInt(request.getParameter("articleId"))));

        request.getRequestDispatcher("/WEB-INF/jsp/mvc/article/articleInfo.jsp")
                .forward(request, response);
    }

    public void addArticle(HttpServletRequest request, HttpServletResponse response)
            throws IOException{
        article.setTitle(request.getParameter("title"));
        article.setContent(request.getParameter("content"));
        article.setName(request.getParameter("name"));
        article.setUserId(Integer.parseInt(request.getParameter("userId")));

        try{
            articleDao.addArticle(article);
            response.sendRedirect(request.getContextPath() + "/mvc/article/articleList");
        } catch (DuplicateKeyException e){
            response.sendRedirect( request.getContextPath() + "/mvc/article/articleForm?msg=Duplicate article");
        }
    }

    public void updateArticle(HttpServletRequest request, HttpServletResponse response)
            throws IOException{
        article.setTitle(request.getParameter("title"));
        article.setContent(request.getParameter("content"));
        article.setUserId(Integer.parseInt(request.getParameter("userId")));
        article.setArticleId(Integer.parseInt(request.getParameter("articleId")));

        try{
            articleDao.updateArticle(article);
            response.sendRedirect(request.getContextPath() + "/mvc/article/articleList");
        } catch (DuplicateKeyException e){
            response.sendRedirect( request.getContextPath() + "/mvc/article/updateForm?msg=Duplicate article");
        }
    }

    public void deleteArticle(HttpServletRequest request, HttpServletResponse response)
            throws IOException{
        article.setArticleId(Integer.parseInt(request.getParameter("articleId")));
        article.setUserId(Integer.parseInt(request.getParameter("userId")));

        try{
            articleDao.deleteArticle(article.getArticleId(), article.getUserId());
            response.sendRedirect(request.getContextPath() + "/mvc/article/articleList");
        } catch (DuplicateKeyException e){
            response.sendRedirect( request.getContextPath() + "/mvc/article/deleteForm?msg=Duplicate article");
        }

    }

}
