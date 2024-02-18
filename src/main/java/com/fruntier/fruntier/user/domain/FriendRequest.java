package com.fruntier.fruntier.user.domain;

import jakarta.persistence.*;

@Entity
public class FriendRequest {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    Long friendRequestId;

    @ManyToOne
    User fromUser;

    @ManyToOne
    User toUser;

    public FriendRequest () {

    }

    public Long getFriendRequestId() {
        return friendRequestId;
    }

    public void setFriendRequestId(Long friendRequestId) {
        this.friendRequestId = friendRequestId;
    }

    public User getFromUser() {
        return fromUser;
    }

    public void setFromUser(User fromUser) {
        this.fromUser = fromUser;
    }

    public User getToUser() {
        return toUser;
    }

    public void setToUser(User toUser) {
        this.toUser = toUser;
    }
}
