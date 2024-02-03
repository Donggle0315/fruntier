package com.fruntier.fruntier.community.service;

import com.fruntier.fruntier.community.domain.Article;
import com.fruntier.fruntier.community.domain.ArticleDTO;
import com.fruntier.fruntier.community.domain.ArticleStatus;
import com.fruntier.fruntier.community.domain.CommentDTO;
import com.fruntier.fruntier.community.exception.MissingArticleException;

import java.util.List;

public interface ArticleService {
    public ArticleStatus matchStringToArticleStatus(String articleStatusString);
    public Article saveNewArticle(ArticleDTO articleDTO, Long userId);
    public List<Article> getArticleListPage(int page);
    public Article getArticle(Long articleId) throws MissingArticleException;

    void saveComment(Article article, CommentDTO commentDTO);
}
