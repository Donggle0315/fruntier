package com.fruntier.fruntier.community.service;

import com.fruntier.fruntier.community.domain.*;
import com.fruntier.fruntier.community.exception.CommentException;
import com.fruntier.fruntier.community.exception.ArticleException;
import com.fruntier.fruntier.community.repository.ArticleRepository;
import com.fruntier.fruntier.community.repository.CommentRepository;
import com.fruntier.fruntier.user.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;


@Service
@Slf4j
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public ArticleServiceImpl(ArticleRepository articleRepository,
                              CommentRepository commentRepository) {
        this.articleRepository = articleRepository;
        this.commentRepository = commentRepository;
    }

    private ArticleStatus matchStringToArticleStatus(String articleStatusString) {
        log.debug("articleStatusString: {}", articleStatusString);
        return switch (articleStatusString) {
            case "normal" -> ArticleStatus.NORMAL;
            case "notVisible" -> ArticleStatus.NOT_VISIBLE;
            default -> ArticleStatus.NORMAL;
        };
    }

    @Override
    public Article saveNewArticle(ArticleDTO articleDTO, User user) {
        ArticleStatus articleStatus = matchStringToArticleStatus(articleDTO.getStatus());

        Article article = new Article();

        article.setTitle(articleDTO.getTitle());
        article.setDate(LocalDateTime.now());
        article.setStatus(articleStatus);
        article.setContent(articleDTO.getContent());
        article.setAuthor(user);
        article.setType(articleDTO.getArticleType());

        article = articleRepository.save(article);
        log.debug("Saved Article: {}", article.getId());

        return article;
    }

    @Override
    public Page<Article> getArticleListPage(int page, int size, String searchKey) {
        page = calculatePageNum(page, size);
        Pageable pageRequest = PageRequest.of(page, size, Sort.by("date").descending());

        // find article without search
        if (searchKey.isEmpty()) {
            return articleRepository.findAll(pageRequest);
        } else {
            // find article with search
            return getArticleListPageWithSearch(pageRequest, searchKey);
        }
    }

    private Page<Article> getArticleListPageWithSearch(Pageable pageRequest, String searchKey) {
        ExampleMatcher matcher = ExampleMatcher.matchingAll()
                .withIgnoreCase()
                .withIgnoreNullValues()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Article> articleExample = Example.of(Article.fromTitle(searchKey), matcher);
        return articleRepository.findAll(articleExample, pageRequest);
    }

    private int calculatePageNum(int page, int size) {
        int totalPages = (int) (articleRepository.count() / size) + 1;
        if (page < 0) {
            page = 0;
        } else if (page > totalPages) {
            page = totalPages;
        }
        return page - 1;
    }

    @Override
    public Article getArticle(Long articleId) throws ArticleException {
        return articleRepository.findById(articleId).orElseThrow(
                () -> new ArticleException("Article doesn't exist!")
        );
    }

    @Override
    public Comment saveComment(Article article, CommentDTO commentDTO, User user) {
        Comment comment = new Comment();
        comment.setContent(commentDTO.getContent());
        comment.setDate(LocalDateTime.now());
        comment.setAuthor(user);

        article.addComment(comment);

        comment = commentRepository.save(comment);
        log.debug("Saved Comment: {}", comment.getId());
        return comment;
    }

    @Override
    public void deleteComment(User user, long commentId) throws CommentException {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new CommentException("Missing Comment")
        );
        if (!comment.isSameAsAuthor(user)) {
            throw new CommentException("Not Author of Comment");
        }
        // Passed safety check
        commentRepository.delete(comment);
        log.debug("Deleted Comment: {}", comment.getId());
    }

    @Override
    public void editComment(User user, long commentId, String content) throws CommentException {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new CommentException("No Comment Found")
        );
        if (!comment.isSameAsAuthor(user)) {
            throw new CommentException("Not Author of Comment");
        }

        comment.setContent(content);

        // Passed safety check
        commentRepository.save(comment);
        log.debug("Edited Comment: {}", comment.getId());
    }

    @Override
    public void deleteArticle(Article article, User user) throws ArticleException {
        if (!article.isSameAsAuthor(user)) {
            throw new ArticleException("Article's author does not match!");
        }
        articleRepository.delete(article);
        log.debug("Deleted Article: {}", article.getId());
    }

    @Override
    public void editArticle(long articleId, User user, ArticleDTO articleDTO) throws ArticleException {
        Article article = articleRepository.findById(articleId).orElseThrow(
                () -> new ArticleException("Article does not exist!")
        );

        if (!article.isSameAsAuthor(user)) {
            throw new ArticleException("Article's author does not match!");
        }

        article.setTitle(articleDTO.getTitle());
        article.setStatus(matchStringToArticleStatus(articleDTO.getStatus()));
        article.setContent(articleDTO.getContent());

        articleRepository.save(article);
    }
}
