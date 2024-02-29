package com.fruntier.fruntier.community.service;

import com.fruntier.fruntier.community.domain.*;
import com.fruntier.fruntier.community.exception.CommentException;
import com.fruntier.fruntier.community.exception.ArticleException;
import com.fruntier.fruntier.user.domain.User;
import org.springframework.data.domain.Page;

public interface ArticleService {
    public Article saveNewArticle(ArticleDTO articleDTO, User user);
    Page<Article> getArticleListPage(int page, int size, String searchKey);
    public Article getArticle(Long articleId) throws ArticleException;
    Comment saveComment(Article article, CommentDTO commentDTO, User user);

    void deleteComment(User user, long commentId) throws CommentException;

    void editComment(User user, long commentId, String content) throws CommentException;

    void deleteArticle(Article article, User user) throws ArticleException;

    void editArticle(long articleId, User user, ArticleDTO articleDTO) throws ArticleException;
}
