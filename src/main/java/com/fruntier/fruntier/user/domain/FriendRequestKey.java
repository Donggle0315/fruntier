package com.fruntier.fruntier.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class FriendRequestKey implements Serializable {
    @Column(name="from_user_id")
    private Long fromUserId;

    @Column(name="to_user_id")
    private Long toUserId;

    public FriendRequestKey(){}
    public FriendRequestKey(Long fromUserId, Long toUserId) {
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
    }

    public Long getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(Long fromUserId) {
        this.fromUserId = fromUserId;
    }

    public Long getToUserId() {
        return toUserId;
    }

    public void setToUserId(Long toUserId) {
        this.toUserId = toUserId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FriendRequestKey that = (FriendRequestKey) o;
        return Objects.equals(fromUserId, that.fromUserId) && Objects.equals(toUserId, that.toUserId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fromUserId, toUserId);
    }
}
