package com.fruntier.fruntier.community.controller;


import com.fruntier.fruntier.JwtTokenService;
import com.fruntier.fruntier.community.domain.*;
import com.fruntier.fruntier.community.exception.CommentException;
import com.fruntier.fruntier.community.exception.MissingArticleException;
import com.fruntier.fruntier.community.service.ArticleService;
import com.fruntier.fruntier.community.service.ArticleServiceImpl;
import com.fruntier.fruntier.globalexception.UserNotLoggedInException;
import com.fruntier.fruntier.user.domain.User;
import com.fruntier.fruntier.user.exceptions.TokenValidationException;
import org.antlr.v4.runtime.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
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
    public String communityMainPage(Model model) {
        List<Article> articleList = articleService.getArticleListPage(1);

        model.addAttribute(articleList);
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
        catch (MissingArticleException missingArticleException) {
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
        } catch (MissingArticleException missingArticleException) {
            return "redirect:/community";
        } catch (TokenValidationException tokenValidationException) {
            throw new UserNotLoggedInException("User isn't logged in");
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

}
