package com.fruntier.fruntier.community.repository;

import com.fruntier.fruntier.community.domain.Article;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ManagedTypes;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
