package com.fruntier.fruntier.community.domain;

import com.fruntier.fruntier.community.domain.Article;
import com.fruntier.fruntier.user.domain.User;

import java.util.List;

public class ArticleRunTogether extends Article {
    private List<User> peer;
    private int expected_peer_num;
    private int expected_date;
    private int expected_time;
    private String additionalInfo;
    private boolean isConfirmed;

    public ArticleRunTogether(Long id, boolean isVisible, String mode, String content, int date, Long user_id, int recommend_number, List<User> peer, int expected_peer_num, int expected_date, int expected_time, String additionalInfo, boolean isConfirmed) {
//        super(id, isVisible, mode, content, date, user_id, recommend_number);
        this.peer = peer;
        this.expected_peer_num = expected_peer_num;
        this.expected_date = expected_date;
        this.expected_time = expected_time;
        this.additionalInfo = additionalInfo;
        this.isConfirmed = isConfirmed;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public List<User> getPeer() {
        return peer;
    }

    public void setPeer(List<User> peer) {
        this.peer = peer;
    }

    public int getExpected_peer_num() {
        return expected_peer_num;
    }

    public void setExpected_peer_num(int expected_peer_num) {
        this.expected_peer_num = expected_peer_num;
    }

    public int getExpected_date() {
        return expected_date;
    }

    public void setExpected_date(int expected_date) {
        this.expected_date = expected_date;
    }

    public int getExpected_time() {
        return expected_time;
    }

    public void setExpected_time(int expected_time) {
        this.expected_time = expected_time;
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }

    public void setConfirmed(boolean confirmed) {
        isConfirmed = confirmed;
    }
}
