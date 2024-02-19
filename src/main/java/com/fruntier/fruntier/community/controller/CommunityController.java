package com.fruntier.fruntier.community.controller;


import com.fruntier.fruntier.JwtTokenService;
import com.fruntier.fruntier.community.domain.*;
import com.fruntier.fruntier.community.exception.CommentException;
import com.fruntier.fruntier.community.exception.ArticleException;
import com.fruntier.fruntier.community.service.ArticleService;
import com.fruntier.fruntier.globalexception.UserNotLoggedInException;
import com.fruntier.fruntier.user.domain.User;
import com.fruntier.fruntier.user.exceptions.TokenValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/community")
public class CommunityController {
    private final ArticleService articleService;
    private final JwtTokenService jwtTokenService;

    @Autowired
    public CommunityController(ArticleService articleService, JwtTokenService jwtTokenService) {
        this.articleService = articleService;
        this.jwtTokenService = jwtTokenService;
    }

    @GetMapping
    public String communityMainPagePaged(
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(name = "page-size", required = false, defaultValue = "5") Integer pageSize,
            @RequestParam(name = "search", required = false, defaultValue = "") String searchKey,
            Model model
    ) {
        Page<Article> articleListPage = articleService.getArticleListPage(page, pageSize, searchKey);
        long totalPages = articleListPage.getTotalPages();
        long startPage = page >= 3 ? page - 2 : 1;
        List<Long> pageList = new ArrayList<>();
        for (long i = startPage; i < startPage + 5; i++) {
            pageList.add(i);
            if (i == totalPages)
                break;
        }

        model.addAttribute(articleListPage.toList());
        model.addAttribute("pageList", pageList);
        model.addAttribute("curPage", page);
        model.addAttribute("totalPages", totalPages);
        return "community/community-main";
    }

    @GetMapping("/article/{articleId}")
    public String articlePage(
            @PathVariable long articleId, Model model,
            @CookieValue(value = "authToken", required = false) String authCookie
    ) throws UserNotLoggedInException {
        try {
            User user = jwtTokenService.validateTokenReturnUser(authCookie);
            Article article = articleService.getArticle(articleId);
            model.addAttribute("article", article);
            model.addAttribute("comments", article.getComments());
            model.addAttribute("user", user);
        }
        // Article doesn't exist -> redirects to article main page
        catch (ArticleException articleException) {
            return "redirect:/community";
        } catch (TokenValidationException e) {
            throw new UserNotLoggedInException("User isn't logged in");
        }
        return "community/article";
    }

    @ResponseBody
    @PostMapping("/article/{articleId}/comment/new")
    public String addNewComment(
            @PathVariable long articleId,
            @RequestBody CommentDTO commentDTO,
            @CookieValue(value = "authToken", required = false) String authCookie
    ) throws UserNotLoggedInException {
        if (authCookie == null) {
            throw new UserNotLoggedInException("User isn't logged in");
        }

        try {
            User user = jwtTokenService.validateTokenReturnUser(authCookie);
            Article article = articleService.getArticle(articleId);
            articleService.saveComment(article, commentDTO, user);
        } catch (ArticleException articleException) {
            return "redirect:/community";
        } catch (TokenValidationException tokenValidationException) {
            throw new UserNotLoggedInException("User isn't logged in");
        }
        return "";
    }

    @ResponseBody
    @PatchMapping("/article/{articleId}/comment/{commentId}")
    public String editComment(
            @PathVariable long articleId,
            @PathVariable long commentId,
            @RequestBody CommentDTO commentDTO,
            @CookieValue(value = "authToken", required = false) String authCookie
    ) throws UserNotLoggedInException {
        if (authCookie == null) {
            throw new UserNotLoggedInException("User isn't logged in");
        }

        try {
            User user = jwtTokenService.validateTokenReturnUser(authCookie);
            System.out.println("commentDTO.getContent() = " + commentDTO.getContent());
            articleService.editComment(user, articleId, commentId, commentDTO.getContent());
        } catch (TokenValidationException tokenValidationException) {
            throw new UserNotLoggedInException("User isn't logged in");
        } catch (CommentException e) {
            return "redirect:/article/{articleId}/comment/{commentId}"; // refresh the page
        }
        return "";
    }

    @ResponseBody
    @DeleteMapping("/article/{articleId}/comment/{commentId}")
    public String deleteComment(
            @PathVariable long articleId,
            @PathVariable long commentId,
            @CookieValue(value = "authToken", required = false) String authCookie
    ) throws UserNotLoggedInException {
        if (authCookie == null) {
            throw new UserNotLoggedInException("User isn't logged in");
        }
        try {
            User user = jwtTokenService.validateTokenReturnUser(authCookie);
            articleService.deleteComment(user, articleId, commentId);
        } catch (TokenValidationException tokenValidationException) {
            throw new UserNotLoggedInException("User isn't logged in");
        } catch (CommentException e) {
            return "redirect:/article/{articleId}/comment/{commentId}"; // refresh the page
        }
        return "";
    }

    @GetMapping("/article/new")
    public String newArticlePage(
            @CookieValue(value = "authToken", required = false) String authCookie
    ) throws UserNotLoggedInException {
        if (authCookie == null) {
            throw new UserNotLoggedInException("User isn't logged in");
        }

        try {
            User user = jwtTokenService.validateTokenReturnUser(authCookie);
        } catch (TokenValidationException tokenValidationException) {
            throw new UserNotLoggedInException(tokenValidationException.getMessage());
        }
        return "community/new-article";
    }

    @ResponseBody
    @PostMapping("/article/new")
    public Article saveNewArticle(
            @RequestBody ArticleDTO articleDTO,
            @CookieValue(value = "authToken", required = false) String authCookie
    ) throws UserNotLoggedInException {

        Article article;

        // If not authorized, can't make new article
        if (authCookie == null) {
            throw new UserNotLoggedInException("User isn't logged in");
        }

        try {
            User user = jwtTokenService.validateTokenReturnUser(authCookie);
            article = articleService.saveNewArticle(articleDTO, user);

        } catch (TokenValidationException tokenValidationException) {
            throw new UserNotLoggedInException(tokenValidationException.getMessage());
        }
        return article;
    }

    @GetMapping("/article/{articleId}/edit")
    public String editArticleView(
            @PathVariable long articleId,
            @CookieValue(value = "authToken", required = false) String authCookie,
            Model model
    ) throws UserNotLoggedInException {
        // If not authorized, can't make new article
        if (authCookie == null) {
            throw new UserNotLoggedInException("User isn't logged in");
        }

        try {
            User user = jwtTokenService.validateTokenReturnUser(authCookie);
            Article article = articleService.getArticle(articleId);
            model.addAttribute("article", article);
        } catch (TokenValidationException tokenValidationException) {
            throw new UserNotLoggedInException(tokenValidationException.getMessage());
        } catch (ArticleException e) {
            return "redirect: /community";
        }
        return "community/edit-article";
    }

    @PatchMapping("/article/{articleId}/edit")
    @ResponseStatus(code = HttpStatus.SEE_OTHER)
    public String editArticle(
            @PathVariable long articleId,
            @RequestBody ArticleDTO articleDTO,
            @CookieValue(value = "authToken", required = false) String authCookie
    ) throws UserNotLoggedInException {
        if (authCookie == null) {
            throw new UserNotLoggedInException("User isn't logged in");
        }
        try {
            User user = jwtTokenService.validateTokenReturnUser(authCookie);
            articleService.editArticle(articleId, user, articleDTO);
            return "redirect:/community/article/{articleId}";
        } catch (TokenValidationException tokenValidationException) {
            throw new UserNotLoggedInException(tokenValidationException.getMessage());
        } catch (ArticleException e) {
            return "redirect:/community";
        }
    }

    @ResponseBody
    @DeleteMapping("/article/{articleId}")
    public String deleteArticle(
            @PathVariable long articleId,
            @CookieValue(value = "authToken", required = false) String authCookie
    ) throws UserNotLoggedInException {
        if (authCookie == null) {
            throw new UserNotLoggedInException("User isn't logged in");
        }

        try {
            User user = jwtTokenService.validateTokenReturnUser(authCookie);
            articleService.deleteArticle(articleId, user);
        } catch (TokenValidationException tokenValidationException) {
            throw new UserNotLoggedInException(tokenValidationException.getMessage());
        } catch (ArticleException e) {
            return "redirect: /community";
        }
        return "";
    }
}
