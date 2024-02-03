package com.fruntier.fruntier.community.controller;


import com.fruntier.fruntier.JwtTokenService;
import com.fruntier.fruntier.community.domain.Article;
import com.fruntier.fruntier.community.domain.ArticleDTO;
import com.fruntier.fruntier.community.domain.ArticleStatus;
import com.fruntier.fruntier.community.domain.CommentDTO;
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
    public String articlePage(@PathVariable long articleId, Model model) {
        try {
            Article article = articleService.getArticle(articleId);
            model.addAttribute("article", article);
        }
        // Article doesn't exist -> redirects to article main page
        catch (MissingArticleException missingArticleException) {
            return "redirect:/community";
        }
        return "community/article";
    }

    @ResponseBody
    @PostMapping("/article/{articleId}/comment/new")
    public String addNewComment(
            @PathVariable long articleId,
            @RequestBody CommentDTO commentDTO
    ) {
        try {
            Article article = articleService.getArticle(articleId);
            articleService.saveComment(article, commentDTO);
        }
        catch (MissingArticleException missingArticleException){
            return "redirect:/community";
        }
        return "";
    }

    @GetMapping("/article/new")
    public String newArticlePage() {
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
            article = articleService.saveNewArticle(articleDTO, user.getId());

        } catch (TokenValidationException tokenValidationException) {
            throw new UserNotLoggedInException(tokenValidationException.getMessage());
        }
        return article;
    }

}
