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
    public ArticleController(ArticleDao articleDao){this.articleDao = articleDao;}

    /**
     * 게시글 목록 화면
     */
    @GetMapping("/articleList")
    public void articleList(@RequestParam(required = false, defaultValue = "1") int page,
                            Model model){
        int count = 25;
        int offset = (page - 1) * count;
        List<Article> articleList = articleDao.listArticles(offset, count);
        model.addAttribute("articleList", articleList);
    }

    /**
     * 게시글 보기 화면
     */
    @GetMapping("/getArticle")
    public void getArticle(@RequestParam int articleId, Model model){
        Article article = articleDao.getArticle(articleId);
        model.addAttribute("article", article);
    }

    /**
     * 게시글 쓰기 화면
     */
    @GetMapping("/writeArticle")
    public String writeArticle(HttpSession session){
        User user = (User) session.getAttribute("USER");
        if(user == null){
            return "redirect:/springmvc/v2/user/loginForm";
        }
        else {
            return "redirect:/springmvc/v2/article/articleForm";
        }
    }

    /**
     * 게시글 쓰기
     */
    @PostMapping("/addArticle")
    public String addArticle(@ModelAttribute Article article, HttpSession session){
        User user = (User) session.getAttribute("USER");
        article.setName(user.getName());
        article.setUserId(user.getUserId());
        articleDao.addArticle(article);
        return "redirect:/springmvc/v2/article/articleList";
    }

    /**
     * 게시글 수정 화면
     */
    @GetMapping("/articleEdit")
    public String articleEdit(@RequestParam int articleId,
                              HttpSession session, Model model) {
        User user = (User) session.getAttribute("USER");
        Article article = articleDao.getArticle(articleId);
        if (user == null) {
            return "redirect:/springmvc/v2/user/loginForm";
        }
        else {
            model.addAttribute("article", article);
            if (user.getUserId() == article.getUserId())
                return "redirect:/springmvc/v2/article/articleEdit";
        }
        return "redirect:/springmvc/v2/article/articleList";
    }

    @PostMapping("/updateArticle")
    public String updateArticle(@ModelAttribute Article article, HttpSession session){
        User user = (User) session.getAttribute("USER");
        article.setUserId(user.getUserId());
        articleDao.updateArticle(article);
        return "redirect:/springmvc/v2/article/articleList";
    }

    @GetMapping("/deleteArticle")
    public String deleteArticle(@RequestParam int articleId, HttpSession session, RedirectAttributes attributes){
        User user = (User) session.getAttribute("USER");

        if (user == null) {
            return "redirect:/springmvc/v2/user/loginForm";
        } else {
            Article article = articleDao.getArticle(articleId);
            if (user.getUserId() == article.getUserId()) {
                articleDao.deleteArticle(articleId, user.getUserId());
            }
            return "redirect:/springmvc/v2/article/articleList";
        }
    }
}
