package com.fruntier.fruntier.community.service;

import com.fruntier.fruntier.community.domain.*;
import com.fruntier.fruntier.community.exception.MissingArticleException;
import com.fruntier.fruntier.community.repository.ArticleRepository;
import com.fruntier.fruntier.community.repository.CommentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class ArticleServiceImpl implements ArticleService {
    private Logger logger = LoggerFactory.getLogger("articleServiceLogger");
    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public ArticleServiceImpl(ArticleRepository articleRepository,
                              CommentRepository commentRepository) {
        this.articleRepository = articleRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public ArticleStatus matchStringToArticleStatus(String articleStatusString) {
        logger.info("articleStatusString: {}", articleStatusString);
        return switch (articleStatusString) {
            case "normal" -> ArticleStatus.NORMAL;
            case "notVisible" -> ArticleStatus.NOT_VISIBLE;
            default -> ArticleStatus.NORMAL;
        };
    }

    @Override
    public Article saveNewArticle(ArticleDTO articleDTO, Long userId) {
        ArticleStatus articleStatus = matchStringToArticleStatus(articleDTO.getStatus());

        Article article = new Article();

        article.setTitle(articleDTO.getTitle());
        article.setDate(LocalDateTime.now());
        article.setStatus(articleStatus);
        article.setContent(articleDTO.getContent());
        article.setAuthorId(userId);

        article = articleRepository.save(article);

        return article;
    }

    @Override
    public List<Article> getArticleListPage(int page) {
        return articleRepository.findAll();
    }

    @Override
    public Article getArticle(Long articleId) throws MissingArticleException{
        Optional<Article> articleOptional = articleRepository.findById(articleId);

        if(articleOptional.isEmpty()){
            throw new MissingArticleException("Article doesn't exist!");
        }

        return articleOptional.get();
    }

    @Override
    public void saveComment(Article article, CommentDTO commentDTO) {
        Comment comment = new Comment();
        comment.setContent(commentDTO.getContent());
        comment.setDate(LocalDateTime.now());

        article.addComment(comment);

        comment = commentRepository.save(comment);
    }
}