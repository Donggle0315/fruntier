package com.fruntier.fruntier.community.domain;

public class CommentDTO {
    private long authorId;
    private String content;

    public CommentDTO() {
    }

    public long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(long authorId) {
        this.authorId = authorId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
