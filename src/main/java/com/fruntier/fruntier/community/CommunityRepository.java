package com.fruntier.fruntier.community;

public interface CommunityRepository {
    /**
     * 게시글을 저장소에 저장
     * @param article 게시글 인스턴스
     */
    void saveArticle(Article article);

    /**
     * 게시글을 저장소에서 검색
     * @param articleId 게시글 아이디
     * @return 게시글 인스턴스
     */
    Article findByArticleId(Long articleId);

    /**
     * 게시글 저장소에서 삭제
     * @param articleId 게시글 아이디
     * @return 삭제 성공 여부
     */
    boolean deleteArticle(Long articleId);

    /**
     * 댓글 저장소에 저장
     * @param comment 댓글 인스턴스
     */
    void saveComment(Comment comment);

    /**
     * 댓글을 저장소에서 검색
     * @param articleId 게시글 아이디
     * @param commentId 댓글 아이디
     * @return 댓글 인스턴스
     */
    Comment findByCommentId(Long articleId, Long commentId);

    /**
     * 댓글을 저장소에서 삭제
     * @param articleId 게시글 아이디
     * @param commentId 댓글 아이디
     * @return 삭제 성공 여부
     */
    boolean deleteComment(Long articleId, Long commentId);
}
