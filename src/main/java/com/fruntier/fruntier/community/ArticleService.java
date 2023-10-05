package com.fruntier.fruntier.community;

public interface ArticleService {
    /**
     * 게시글 생성 기능
     * @param article 게시글 인스턴스
     */
    void createArticle(Article article);

    /**
     * 게시글 검색 기능
     * @param articleId 게시글 아이디
     * @return 게시글 인스턴스
     */
    Article findArticleById(Long articleId);

    /**
     * 게시글 수정 기능 - 수정 가능한 것만 수정하도록 함
     * @param articleId 수정할 게시글 아이디
     * @param new_article 수정할 내용이 담긴 게시글 인스턴스
     */
    void modifyArticle(Long articleId, Article new_article);

    /**
     * 게시글 삭제 기능 - 본인 확인 절차 필요
     * @param articleId 삭제할 게시글 아이디
     */
    void deleteArticle(Long articleId);

    /**
     * 게시글 추천 기능
     * @param articleId 추천할 게시글 아이디
     * @param recommend true 추천 / false 비추
     */
    void recommendArticle(Long articleId, boolean recommend);
}
