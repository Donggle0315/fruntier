package com.fruntier.fruntier.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class FriendKey implements Serializable {
    @Column(name="user_id_1")
    private Long userId1;

    @Column(name="user_id_2")
    private Long userId2;

    public FriendKey(){}
    public FriendKey(Long userId1, Long userId2) {
        this.userId1 = userId1;
        this.userId2 = userId2;
    }

    public Long getUserId1() {
        return userId1;
    }

    public void setUserId1(Long userId1) {
        this.userId1 = userId1;
    }

    public Long getUserId2() {
        return userId2;
    }

    public void setUserId2(Long userId2) {
        this.userId2 = userId2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FriendKey friendKey = (FriendKey) o;
        return Objects.equals(userId1, friendKey.userId1) && Objects.equals(userId2, friendKey.userId2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId1, userId2);
    }
}
