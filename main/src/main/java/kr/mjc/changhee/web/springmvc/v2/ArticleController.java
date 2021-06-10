package kr.mjc.changhee.web.springmvc.v2;

import kr.mjc.changhee.web.dao.Article;
import kr.mjc.changhee.web.dao.ArticleDao;
import kr.mjc.changhee.web.dao.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller("articleControllerV2")
@RequestMapping("/springmvc/v2/article")
public class ArticleController {

    private final ArticleDao articleDao;

    @Autowired
    public ArticleController(ArticleDao articleDao) {
        this.articleDao = articleDao;
    }

    /**
     * 게시글 목록 화면
     */
    @GetMapping("/articleList")
    public void articleList(
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "20") int count,
            HttpSession session, Model model) {

        int offset = (page - 1) * count;  // 목록의 시작 위치
        List<Article> articleList = articleDao.listArticles(offset, count);
        int totalCount = articleDao.countArticles();
        model.addAttribute("articleList", articleList);
        model.addAttribute("totalCount", totalCount);

        // 현재 페이지와 카운트를 세션에 저장한다. 글보기, 글쓰기, 글삭제 후에 돌아올 목록
        session.setAttribute("currentPage", page);
        session.setAttribute("countPerPage", count);
        // forward /WEB-INF/springmvc/v2/article/articleList.jsp
    }

    /**
     * 내 게시글 목록 화면
     */
    @GetMapping("/userArticles")
    public void userArticles(
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "10") int count,
            @SessionAttribute("USER") User user,
            HttpSession session, Model model){

        int offset = (page - 1) * count;
        List<Article> userArticles = articleDao.getUsersArticles(user.getUserId(), offset, count);
        int totalCount = articleDao.countUserArticles(user.getUserId());
        model.addAttribute("userArticles", userArticles);
        model.addAttribute("totalCount", totalCount);

        session.setAttribute("currentPage", page);
        session.setAttribute("countPerPage", count);
    }
    /**
     * 게시글 보기 화면
     */
    @GetMapping("/articleView")
    public void articleView(int articleId, Model model) {
        Article article = articleDao.getArticle(articleId);
        model.addAttribute("article", article);
        // forward /WEB-INF/springmvc/v2/article/articleView.jsp
    }

    /**
     * 게시글 수정 화면
     */
    @GetMapping("/articleEdit")
    public String articleEdit(@RequestParam int articleId,
                              @SessionAttribute("USER") User user, Model model) {
        // 자기글 체크
        Article article = articleDao.getArticle(articleId);
        if (user.getUserId() != article.getUserId()) // 사용자가 다르면 401 Unauthorized
            return "error/401";

        model.addAttribute("article", article);
        return "springmvc/v2/article/articleEdit";
    }

    /**
     * 게시글 등록 액션
     */
    @PostMapping("/addArticle")
    public String addArticle(@ModelAttribute Article article,
                             @SessionAttribute("USER") User user,
                             @SessionAttribute String countPerPage) {
        // 세션의 user로 사용자 정보를 설정한다.
        article.setUserId(user.getUserId());
        article.setName(user.getName());

        articleDao.addArticle(article);

        // 등록 후에 목록의 1페이지로 간다.
        return "redirect:/springmvc/v2/article/articleList?page=1&count=" +
                countPerPage;
    }

    /**
     * 게시글 수정 액션
     */
    @PostMapping("/updateArticle")
    public String updateArticle(@ModelAttribute Article article,
                                @SessionAttribute("USER") User user) {
        article.setUserId(user.getUserId());

        int result = articleDao.updateArticle(article);
        if (result == 0)  // 사용자가 다르면 수정이 안됨. 400 Bad Request
            return "error/400";

        return "redirect:/springmvc/v2/article/articleView?articleId=" +
                article.getArticleId();
    }

    /**
     * 게시글 삭제 액션
     */
    @GetMapping("/deleteArticle")
    public String deleteArticle(int articleId,
                                @SessionAttribute("USER") User user,
                                @SessionAttribute String currentPage,
                                @SessionAttribute String countPerPage) {
        int result = articleDao.deleteArticle(articleId, user.getUserId());
        if (result == 0) {// 사용자가 다르면 삭제가 안됨. 401 Unauthorized
            return "error/401";
        }

        // 삭제 후에 목록의 원래 페이지로 간다.
        return String
                .format("redirect:/springmvc/v2/article/articleList?page=%s&count=%s",
                        currentPage, countPerPage);
    }
}
