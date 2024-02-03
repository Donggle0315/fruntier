package com.fruntier.fruntier.community.repository;

import com.fruntier.fruntier.community.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
