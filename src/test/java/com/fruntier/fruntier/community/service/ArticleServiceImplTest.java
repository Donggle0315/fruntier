package com.fruntier.fruntier.community.service;

import com.fruntier.fruntier.community.domain.ArticleStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@SpringJUnitConfig
class ArticleServiceImplTest {

    @Autowired
    ArticleService articleService;

    @Test
    void matchStringToArticleStatus(){
        // given
        String normal = "normal";
        String notVisible = "notVisible";

        // when
        ArticleStatus normalStatus = articleService.matchStringToArticleStatus(normal);
        ArticleStatus notVisibleStatus = articleService.matchStringToArticleStatus(notVisible);

        // then
        assertThat(normalStatus).isEqualTo(ArticleStatus.NORMAL);
        assertThat(notVisibleStatus).isEqualTo(ArticleStatus.NOT_VISIBLE);
    }

}