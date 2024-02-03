package com.fruntier.fruntier.community.service;

import com.fruntier.fruntier.community.domain.Comment;

public interface CommentService {
    /**
     * 댓글 생성 기능
     * @param comment 댓글 인스턴스
     */
    void createComment(Comment comment);

    /**
     * 댓글 검색 기능
     * @param articleId 게시글 아이디
     * @param commentId 댓글 아이디
     * @return 댓글 인스턴스
     */
    Comment findCommentById(Long articleId, Long commentId);

    /**
     * 댓글 삭제 기능 - 본인 확인 절차 필요
     * @param articleId 삭제할 게시글 아이디
     * @param commentId 삭제할 댓글 아이디
     */
    void deleteComment(Long articleId, Long commentId);
}
